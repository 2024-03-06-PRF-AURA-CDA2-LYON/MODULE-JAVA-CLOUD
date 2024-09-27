package fr.maboite.aws.lambda;

/**
 * POJO Plain Old Java Object : 
 * un bon vieil objet Java.
 * Une classe avec des attributs
 * et un getter/setter par attribut.
 */
public class IntegerPojo {

	private int x;
	private int y;
	private String message;
	
	public IntegerPojo() {
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
