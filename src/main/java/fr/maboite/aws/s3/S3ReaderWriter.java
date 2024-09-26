package fr.maboite.aws.s3;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.Bucket;
import software.amazon.awssdk.services.s3.model.ListBucketsResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

/**
 * Simple programme de listage de compartiments
 * et d'écriture dans un compartiment. Doit être lancé avec un argument : 
 * le nom du compartiment sur lequel écrire.
 */
public class S3ReaderWriter {

	private static final int ERROR_STATUS = 1;

	public static void main(String[] args) {
		if (args.length == 0) {
			System.out.println("Au moins un argument requis, le nom du compartiment S3.");
			System.exit(ERROR_STATUS);
		}
		String bucketName = args[0];

		Region region = Region.EU_WEST_3;
		S3Client s3 = S3Client.builder().region(region).build();

		S3ReaderWriter s3ReaderWriter = new S3ReaderWriter();

		s3ReaderWriter.listBuckets(s3);

		Bucket bucketFound = s3ReaderWriter.getBucket(s3, bucketName);
		if (bucketFound == null) {
			System.out.println("Aucun bucket trouvé avec le nom : " + bucketName);
			System.exit(ERROR_STATUS);
		}

		s3ReaderWriter.writeS3(s3, bucketFound.name(),
				"fichier-" + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME), "origine.txt");
	}

	/**
	 * Ecrit un objet sur le bucket, à partir d'un fichier
	 * 
	 * @param s3
	 * @param bucketName
	 * @param objectKey
	 * @param objectPath
	 */
	public void writeS3(S3Client s3, String bucketName, String objectKey, String objectPath) {
		try {
			Map<String, String> metadata = new HashMap<>();
			metadata.put("author", "Jean Dupont");
			metadata.put("version", "1.0.0");

			PutObjectRequest putOb = PutObjectRequest
					.builder()
					.bucket(bucketName)
					.key(objectKey)
					.metadata(metadata)
					.build();

			s3.putObject(putOb, RequestBody.fromFile(new File(objectPath)));
			System.out.println("Fichier :  " + objectKey + " mis dans le compartiment : " + bucketName);

		} catch (S3Exception e) {
			System.err.println(e.getMessage());
			System.exit(ERROR_STATUS);
		}
	}

	/**
	 * Liste tous les buckets présents sur le client s3
	 * 
	 * @param s3
	 */
	public void listBuckets(S3Client s3) {
		try {
			ListBucketsResponse response = s3.listBuckets();
			List<Bucket> bucketList = response.buckets();
			System.out.println("Affichage des compartiments accessibles");
			for(Bucket bucket : bucketList) {
				System.out.println("Compartiment : " + bucket.name());
			}
			//Le code ci-dessus, pourrait être transformé
			//Dans le code ci-dessous si on veut utiliser des lambdas
//			bucketList.forEach(bucket -> {
//				System.out.println("Compartiment : " + bucket.name());
//			});
		} catch (S3Exception e) {
			System.err.println(e.awsErrorDetails().errorMessage());
			System.exit(ERROR_STATUS);
		}
	}

	/**
	 * Renvoie le bucket du client s3, et avec le nom bucketName. Renvoie null si
	 * aucun bucket n'est trouvé.
	 * 
	 * @param s3
	 * @param bucketName
	 * @return
	 */
	public Bucket getBucket(S3Client s3, String bucketName) {
		try {
			ListBucketsResponse response = s3.listBuckets();
			for (Bucket bucket : response.buckets()) {
				if (bucketName.equals(bucket.name())) {
					return bucket;
				}
			}

		} catch (S3Exception e) {
			System.err.println(e.awsErrorDetails().errorMessage());
			System.exit(ERROR_STATUS);
		}
		return null;
	}

}
