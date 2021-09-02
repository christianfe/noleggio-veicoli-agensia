package it.univaq.disim.oop.bhertz.business;

@SuppressWarnings("serial")
public class TypeNotEmptyException extends BusinessException {

	public TypeNotEmptyException() {
		super();
	}

	public TypeNotEmptyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public TypeNotEmptyException(String message, Throwable cause) {
		super(message, cause);
	}

	public TypeNotEmptyException(String message) {
		super(message);
	}

	public TypeNotEmptyException(Throwable cause) {
		super(cause);
	}

}
