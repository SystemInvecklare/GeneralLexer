package com.github.systeminvecklare.libs.generallexer.state;

import java.io.IOException;

import com.github.systeminvecklare.libs.generallexer.ICharStream;

public interface IStateSelector {
	float getMatch(ICharStream charStream) throws IOException;
	ILexerState createState();
}
