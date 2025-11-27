package com.github.systeminvecklare.libs.generallexer;

import java.io.IOException;
import java.io.StringReader;

public class TestGeneralLexer {

	public static void main(String[] args) throws IOException {
		//TODO
		// 1. Add way to find source position
		// 2. Make it gradlew and buildable in jitpack
		
		String asmExample = """
					bits 64
					default rel
					
					segment .data
					var0_str db 72, 101, 108, 108, 111, 32, 119, 111, 114, 108, 100, 33
					var0_len equ $ - var0_str
					
					segment .text
					global main
					extern GetStdHandle
					extern WriteConsoleA
					extern ExitProcess
					
					STD_OUTPUT_HANDLE equ -11     ; constant from WinBase.h
					
					main:
					; prologue
					push rbp
					mov  rbp, rsp
					sub  rsp, 32
					; get handle to console output
					mov  ecx, STD_OUTPUT_HANDLE
					call GetStdHandle
					mov  rbx, rax             ; save handle
					
					; write message
					mov  rcx, rbx
					lea  rdx, [var0_str]
					mov  r8d, var0_len
					xor  r9d, r9d
					sub  rsp, 32
					call WriteConsoleA
					add  rsp, 32
					; exit cleanly
					xor ecx, ecx
					call ExitProcess""";
		GeneralLexer.asmLexer().lex(new StringReader(asmExample), new TokenDebugPrinter());
	}
}
