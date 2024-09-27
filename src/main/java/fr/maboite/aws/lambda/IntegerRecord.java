package fr.maboite.aws.lambda;

/**
 * Un record : une classe immutable qui contient des arguments.
 * Le record fournit un constructeur avec tous les arguments, 
 * des getters (et des méthodes toString(), hashCode() et equals())
 */
public record IntegerRecord(int x, int y, String message) {
}