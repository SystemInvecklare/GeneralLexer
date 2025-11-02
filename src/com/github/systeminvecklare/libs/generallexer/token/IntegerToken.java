package com.github.systeminvecklare.libs.generallexer.token;

public class IntegerToken implements IToken {
	private final int value;

	public IntegerToken(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
	
	@Override
	public String toString() {
		return "Integer["+value+"]";
	}
}
