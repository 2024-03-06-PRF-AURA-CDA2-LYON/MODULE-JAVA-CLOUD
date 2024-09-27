package fr.maboite.aws.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

/**
 * Une simple lambda qui traite un autre événement (de type RobertoRecord) et qui 
 * renvoie une chaîne de caractères.
 */
public class LambdaRoberto implements RequestHandler<RobertoRecord, RobertoRecord> {
	
	@Override
	/**
	 * Traite event. Affiche le contenu du record et renvoie 
	 * la date de naissance.
	 */
	public RobertoRecord handleRequest(RobertoRecord event, Context context) {
		LambdaLogger logger = context.getLogger();
		logger.log("Salut, la lambda roberto démarre \n");
		logger.log("Nom : " + event.name() + "\n");
		logger.log("Date de naissance : " + event.dateNaissance() + "\n");
		return event;
		
	}
}
