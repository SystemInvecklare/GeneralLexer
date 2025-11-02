package com.github.systeminvecklare.libs.generallexer;

import java.util.function.Consumer;

import com.github.systeminvecklare.libs.generallexer.token.GroupToken;
import com.github.systeminvecklare.libs.generallexer.token.IToken;

public class TokenDebugPrinter implements Consumer<IToken> {
	private int indent = 0;
	
	@Override
	public void accept(IToken t) {
		printIndent();
		if(t instanceof GroupToken) {
			GroupToken groupToken = (GroupToken) t;
			System.out.println("Group "+groupToken.getStart());
			indent++;
			for(IToken token : groupToken.getTokens()) {
				this.accept(token);
			}
			indent--;
			printIndent();
			System.out.println(groupToken.getEnd());
		} else {
			System.out.println(t);
		}
	}
	
	private void printIndent() {
		for(int i = 0; i < indent; ++i) {
			System.out.print("  ");
		}
	}
}
