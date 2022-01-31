package lex;

public class Token {
	public static final int TK_IDENTIFICADOR = 0;
	public static final int TK_NUMERO = 1;
	public static final int TK_OPERADOR = 2;
	public static final int TK_ATRIBUICAO = 3;
	
	private int tipo;
	private String texto;
	
	public Token (int tipo, String texto) {
		super();
		this.tipo = tipo;
		this.texto = texto;
	}
	
	public Token() {
		super();
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}
	
	@Override
	public String toString() {
		return "Token [tipo = "+ tipo +", texto = " + texto +"]";
	}
}
