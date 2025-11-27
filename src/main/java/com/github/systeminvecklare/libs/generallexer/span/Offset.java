package com.github.systeminvecklare.libs.generallexer.span;

import java.util.Objects;

public class Offset {
	public final int row;
	public final int column;
	
	public Offset(int row, int column) {
		this.row = row;
		this.column = column;
	}
	
	@Override
	public String toString() {
		return row+" : "+column;
	}

	@Override
	public int hashCode() {
		return Objects.hash(column, row);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Offset other = (Offset) obj;
		return column == other.column && row == other.row;
	}
}
