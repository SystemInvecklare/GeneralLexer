package com.github.systeminvecklare.libs.generallexer.span;

public class GeneralLexerRuntimeException extends RuntimeException {
	private static final long serialVersionUID = 6431864470367423682L;
	
	private final Span span;

	public GeneralLexerRuntimeException(Span span) {
		super();
		this.span = span;
	}

	public GeneralLexerRuntimeException(String message, Throwable cause, Span span) {
		super(message, cause);
		this.span = span;
	}

	public GeneralLexerRuntimeException(String message, Span span) {
		super(message);
		this.span = span;
	}

	public GeneralLexerRuntimeException(Throwable cause, Span span) {
		super(cause);
		this.span = span;
	}
	
	public Span getSpan() {
		return span;
	}
}
