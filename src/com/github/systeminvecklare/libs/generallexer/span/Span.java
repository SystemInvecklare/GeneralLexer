package com.github.systeminvecklare.libs.generallexer.span;

import java.util.Objects;

public class Span {
	public final Offset start;
	public final Offset end;
	
	public Span(Offset start, Offset end) {
		this.start = start;
		this.end = end;
	}
	
	@Override
	public String toString() {
		return "["+start+" -> "+end+")";
	}
	
	public boolean isEmpty() {
		return start.equals(end);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(end, start);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Span other = (Span) obj;
		return Objects.equals(end, other.end) && Objects.equals(start, other.start);
	}

	public static Span empty(Offset offset) {
		return new Span(offset, offset);
	}
	
	public static Span singleCharacter(Offset offset) {
		return new Span(offset, new Offset(offset.row, offset.column + 1));
	}
}
