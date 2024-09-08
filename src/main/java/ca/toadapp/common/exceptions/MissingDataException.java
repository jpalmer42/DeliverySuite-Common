package ca.toadapp.common.exceptions;

public class MissingDataException extends Exception {
	private static final long serialVersionUID = -5942555022970118501L;

	public MissingDataException( String message ) {
		super( message );
	}
}
