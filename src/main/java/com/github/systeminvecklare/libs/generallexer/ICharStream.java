package com.github.systeminvecklare.libs.generallexer;

import java.io.IOException;

public interface ICharStream extends IPeakableCharStream {
	char next() throws IOException;
	void skip(int skip) throws IOException;
}
