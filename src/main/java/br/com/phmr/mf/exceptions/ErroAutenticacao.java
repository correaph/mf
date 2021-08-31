package br.com.phmr.mf.exceptions;

public class ErroAutenticacao extends RuntimeException {

	private static final long serialVersionUID = -7956300051168895088L;

	public ErroAutenticacao(String mensagem) {
		super(mensagem);
	}
	
}
