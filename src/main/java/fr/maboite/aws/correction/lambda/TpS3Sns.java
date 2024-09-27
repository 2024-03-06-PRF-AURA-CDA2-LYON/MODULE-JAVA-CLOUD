package fr.maboite.aws.correction.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.lambda.runtime.events.models.s3.S3EventNotification.S3EventNotificationRecord;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;
import software.amazon.awssdk.services.sns.model.SnsException;

/**
 * Lambda pour récupérer un événement de S3 et envoyer un message
 * sur une rubrique SNS
 */
public class TpS3Sns implements RequestHandler<S3Event, String> {

	private final static String TOPIC_ARN = "arn:aws:sns:eu-west-3:622333992348:FormationFrancois1";

	@Override
	public String handleRequest(S3Event event, Context context) {
		// Récupération du logger lambda
		LambdaLogger logger = context.getLogger();

		// Récupération du record S3 à partir de l'event
		S3EventNotificationRecord record = event.getRecords().get(0);
		// Récupération du compartiement et de la clé pour affichage dans la log
		String srcBucket = record.getS3().getBucket().getName();
		String srcKey = record.getS3().getObject().getUrlDecodedKey();
		logger.log("RECORD: " + record);
		logger.log("Compartiment S3 : " + srcBucket);
		logger.log("Clé de l'objet : " + srcKey);
		// Retour du chemin de l'objet
		// return srcBucket + "/" + srcKey;

		String objectPath = srcBucket + "/" + srcKey;
		Region currentRegion = Region.EU_WEST_3;
		// Envoi d'un message personnalisé via S3
		try {

			SnsClient snsClient = SnsClient.builder().region(currentRegion).build();

			PublishRequest request = PublishRequest.builder()
					.message("L'objet : " + objectPath + " a été inséré ou modifié dans le S3").targetArn(TOPIC_ARN)
					.build();

			PublishResponse result = snsClient.publish(request);
			logger.log(result.messageId() + " Message sent. Status is " + result.sdkHttpResponse().statusCode());
			snsClient.close();

		} catch (SnsException e) {
			logger.log("Erreur : " + e.awsErrorDetails().errorMessage());
			return "Erreur : " + e.awsErrorDetails().errorMessage();
		}
		return "Traitement OK";
	}


}