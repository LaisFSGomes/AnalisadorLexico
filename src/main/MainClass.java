package main;

import lex.IsiScanner;
import lex.Token;

public class MainClass {
	public static void main(String[] args) {
		try {
			IsiScanner scanner = new IsiScanner("input.isi");
			Token token = null;
			
			do {
				token = scanner.nextToken();
				if (token != null) {
					System.out.println(token);
				}
			} while (token != null);
		} catch (Exception e) {
			System.out.println("Erro Lexico "+ e.getMessage());
		}
	}
}
