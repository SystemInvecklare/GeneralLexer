package com.github.systeminvecklare.libs.generallexer.token;

public class NameToken implements IToken {
	private final String name;

	public NameToken(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return "Name["+name+"]";
	}
}
