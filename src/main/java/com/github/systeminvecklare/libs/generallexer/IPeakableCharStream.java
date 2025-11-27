package com.github.systeminvecklare.libs.generallexer;

import java.io.IOException;

import com.github.systeminvecklare.libs.generallexer.span.Offset;

public interface IPeakableCharStream {
	boolean hasNext() throws IOException; // hasNext(1)
	boolean hasNext(int chars) throws IOException;
	char peak() throws IOException; // peak(0)
	char peak(int skipped) throws IOException;
	Offset getOffset();
}
