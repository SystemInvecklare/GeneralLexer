package com.github.systeminvecklare.libs.generallexer.token;

import java.io.IOException;

public interface IPeakableTokenStream {
	boolean hasNext() throws IOException; // hasNext(1)
	boolean hasNext(int chars) throws IOException;
	IToken peak() throws IOException; // peak(0)
	IToken peak(int skipped) throws IOException;
}
