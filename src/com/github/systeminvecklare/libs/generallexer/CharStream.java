package com.github.systeminvecklare.libs.generallexer;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class CharStream implements ICharStream {
	private final Reader reader;
	private final List<Character> queued = new ArrayList<Character>();
	private boolean endOfReaderStreamReached = false;
	
	public CharStream(Reader reader) {
		this.reader = reader;
	}
	
	private void tryQueue(int atLeast) throws IOException {
		if(endOfReaderStreamReached) {
			return;
		}
		while(queued.size() < atLeast) {
			int c = reader.read();
			if(c == -1) {
				endOfReaderStreamReached = true;
				return;
			}
			queued.add((char) c);
		}
	}
	
	private void forceQueue(int chars) throws IOException {
		tryQueue(chars);
		if(queued.size() < chars) {
			throw new IOException("End of stream!");
		}
	}

	@Override
	public boolean hasNext() throws IOException {
		tryQueue(1);
		return !queued.isEmpty();
	}

	@Override
	public boolean hasNext(int chars) throws IOException {
		tryQueue(chars);
		return queued.size() >= chars;
	}

	@Override
	public char next() throws IOException {
		forceQueue(1);
		return queued.remove(0);
	}

	@Override
	public char peak() throws IOException {
		forceQueue(1);
		return queued.get(0);
	}

	@Override
	public char peak(int skipped) throws IOException {
		forceQueue(skipped + 1);
		return queued.get(skipped);
	}

	@Override
	public void skip(int skip) throws IOException {
		forceQueue(skip);
		for(int i = 0; i < skip; ++i) {
			queued.remove(0);
		}
	}
}
