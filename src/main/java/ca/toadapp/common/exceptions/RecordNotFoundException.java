package ca.toadapp.common.exceptions;

public class RecordNotFoundException extends Exception {
	private static final long serialVersionUID = 7897856119892127666L;

	public RecordNotFoundException( String message ) {
		super( message );
	}
}
