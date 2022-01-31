package lex;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class IsiScanner {
	private char[] content;
	private int    estado;
	private int    pos;
	
	public IsiScanner(String arquivo) {
		try {
			String txtConteudo;
			txtConteudo = new String(Files.readAllBytes(Paths.get(arquivo)),StandardCharsets.UTF_8);
			
			System.out.println("_________________");
			System.out.println(txtConteudo);
			System.out.println("_________________");
			content = txtConteudo.toCharArray(); //converte a string num array de caracteres
			pos=0; //começa no 0
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Token nextToken() {
		char atualChar;
		Token token;
		String termo = "";
		
		if (isEOF()) {
			return null;
		}
		
		estado = 0;
		
		while(true) {
			atualChar = nextChar();
			
			switch(estado) {
			case 0:
				if (isChar(atualChar)) {
					termo += atualChar;
					estado = 1;
				}
				else if(isDigit(atualChar)) {
					estado = 2;
					termo += atualChar;
				}
				else if(isSpace(atualChar)) {
					estado = 0;
				}
				else if (isEqual(atualChar)) {
//					termo += atualChar;
//					token = new Token();
//					token.setTipo(Token.TK_OPERADOR);
//					token.setTexto(termo);
//					return token;
					termo += atualChar;
					estado = 3;
				}
				else if(isOperator(atualChar)) {
					termo += atualChar;
					estado = 4;
				}
				else {
					System.out.println("ERROR: Símbolo não reconhecido");
				}
				break;
			case 1:
				if (isChar(atualChar) || isDigit(atualChar)) {
					estado = 1;
					termo += atualChar;
				}
				else if(isSpace(atualChar) || isOperator(atualChar) || isEOF(atualChar)) {
					if (!isEOF(atualChar)) {
						back();
					}
					token= new Token();
					token.setTexto(termo);
					token.setTipo(token.TK_IDENTIFICADOR);
					return token;
				}
				else {
					System.out.println("ERRROR: Identificador desconhecido");
				}
				break;
			case 2:
				if(isDigit(atualChar) || atualChar == '.') {
					estado = 2;
					termo += atualChar;
					
				}
				else if(!isChar(atualChar) || isEOF(atualChar)) {
					if(!isEOF(atualChar)) {
						back();
					}
					token= new Token();
					token.setTexto(termo);
					token.setTipo(token.TK_NUMERO);
					return token;					
				}
				else {
					System.out.println("ERROR: Número não reconhecido");
				}
				break;
			case 3:
				if (isOperator(atualChar) || isEqual(atualChar)) {
					termo += atualChar;
					estado = 4;
				}
				else if (!isOperator(atualChar) || (isEqual(atualChar)) || isEOF(atualChar)) {
					if (!isEOF(atualChar)) {
						back();
					}
					token = new Token();
					token.setTipo(token.TK_ATRIBUICAO);
					token.setTexto(termo);
					return token;
				}
				else {
					System.out.println("ERROR: Simbolo desconhecido");
				}
				break;
			case 4:
				termo += atualChar;
				token = new Token();
				token.setTipo(Token.TK_OPERADOR);
				token.setTexto(termo);
				return token;
//				if(!isEqual(atualChar) || (!isOperator(atualChar)) || isEOF(atualChar)) {
//					if (!isEOF(atualChar)) {
//						back();
//					}
//					token = new Token();
//					token.setTipo(token.TK_OPERADOR);
//					token.setTexto(termo);
//					return token;
//				}
//				else {
//					System.out.println("ERROR: Simbolo desconhecido");
//				}
//				break;
			}			
		}	
	}
	
	//----------------------------------------------------------------
	
	private boolean isDigit(char c) {
		return c >= '0' && c <= '9';
	}
	
	
	private boolean isChar(char c) {
		return (c >= 'a' && c <= 'z') || (c>='A' && c <= 'Z');
	}
	
	private boolean isEqual(char c) {
		return c == '=';
	}
	
	private boolean isOperator(char c) {
		return c == '>' || c == '<' || c == '!';
	}
	
	
	private boolean isSpace(char c) {
		return c == ' ' || c == '\t' || c == '\n' || c == '\r'; 
	}
	
	
	private char nextChar() {
		if (isEOF()) {
			return '\0';
		}
		return content[pos++];
	}
	
	
	private boolean isEOF() {
		return pos >= content.length;
	}
	
		
    private void back() {
    	pos--;
    }
    
    
    private boolean isEOF(char c) {
    	return c == '\0';
    }	
}
