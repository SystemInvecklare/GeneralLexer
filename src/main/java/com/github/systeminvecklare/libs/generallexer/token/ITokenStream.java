package com.github.systeminvecklare.libs.generallexer.token;

import java.io.IOException;

public interface ITokenStream extends IPeakableTokenStream {
	IToken next() throws IOException;
	void skip(int skip) throws IOException;
}
