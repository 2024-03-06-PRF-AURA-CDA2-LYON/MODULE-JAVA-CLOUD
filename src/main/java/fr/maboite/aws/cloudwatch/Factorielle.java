package fr.maboite.aws.cloudwatch;

public class Factorielle {

	private static final long COMPUTE_TIME_MILLIS = 60_000;
	
	public static void main(String[] args) {
		long endTime = System.currentTimeMillis() + COMPUTE_TIME_MILLIS;
		while(System.currentTimeMillis() < endTime) {
			factorielle(30);
		}
	}

	private static long factorielle(int i) {
		long factorielle = i;
		while(i > 2) {
			i--;
			factorielle *= i;
		}
		return factorielle;
	}
	
	
}
