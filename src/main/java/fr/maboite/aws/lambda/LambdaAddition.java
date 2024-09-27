package fr.maboite.aws.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

/**
 * Une simple lambda qui traite un événement avec 3 informations, et fait une
 * addition.
 */
public class LambdaAddition implements RequestHandler<IntegerRecord, Integer> {
	
	@Override
	/**
	 * Traite event. Affiche le message de event, et renvoie la somme de x et y de
	 * l'event.
	 */
	public Integer handleRequest(IntegerRecord event, Context context) {
		LambdaLogger logger = context.getLogger();
		logger.log("Message de l'événement : " + event.message());
		logger.log("Salut, la lambda démarre");
		return event.x() + event.y();
	}
}
