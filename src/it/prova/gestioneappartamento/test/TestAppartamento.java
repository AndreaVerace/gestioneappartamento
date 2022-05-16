package it.prova.gestioneappartamento.test;

import it.prova.gestioneappartamento.dao.AppartamentoDAO;

public class TestAppartamento {

	public static void main(String[] args) {
		
		AppartamentoDAO appartamentoDAOInstance = new AppartamentoDAO();
		
		System.out.println("In tabella appartamento ci sono: " + appartamentoDAOInstance.list().size() + " elementi.");
	}

	
	
	
}
