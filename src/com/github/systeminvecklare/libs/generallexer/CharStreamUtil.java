package com.github.systeminvecklare.libs.generallexer;

import java.io.IOException;

public class CharStreamUtil {
	private CharStreamUtil() {}
	
	public static boolean peakMatches(IPeakableCharStream charStream, String string) throws IOException {
		if(!charStream.hasNext(string.length())) {
			return false;
		}
		for(int i = 0; i < string.length(); ++i) {
			if(charStream.peak(i) != string.charAt(i)) {
				return false;
			}
		}
		return true;
	}
}
