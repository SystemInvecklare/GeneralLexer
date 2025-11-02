package com.github.systeminvecklare.libs.generallexer;

import java.io.IOException;

public interface IPeakableCharStream {
	boolean hasNext() throws IOException; // hasNext(1)
	boolean hasNext(int chars) throws IOException;
	char peak() throws IOException; // peak(0)
	char peak(int skipped) throws IOException;
}
