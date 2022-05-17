package it.prova.gestioneappartamento.test;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import it.prova.gestioneappartamento.dao.AppartamentoDAO;
import it.prova.gestioneappartamento.model.Appartamento;

public class TestAppartamento {

	public static void main(String[] args) {
		
		AppartamentoDAO appartamentoDAOInstance = new AppartamentoDAO();
		
		System.out.println("In tabella appartamento ci sono: " + appartamentoDAOInstance.list().size() + " elementi.");
		
		// testInsert(appartamentoDAOInstance);
		
		// testUpdate(appartamentoDAOInstance);
		
		// testDelete(appartamentoDAOInstance);
		
		// testFindById(appartamentoDAOInstance);
		
		TestFindByExample(appartamentoDAOInstance);
	}

	
	
	private static void testInsert(AppartamentoDAO appartamentoDAOInstance) {
		java.util.Date data1 = null;
		try {
			data1 =  new SimpleDateFormat("dd/MM/yyyy").parse("30/05/1951");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 Appartamento appartamentoDaInserire = new Appartamento("TIBURTINA",110,160000,data1);
		 
		 int quantiAppartamentiInseriti = appartamentoDAOInstance.insert(appartamentoDaInserire);
		 
		 if(quantiAppartamentiInseriti != 1) {
			 throw new RuntimeException("testInsertAppartamento : FAILED");
		 }	
	}
	
	
	private static void testUpdate(AppartamentoDAO appartamentoDAOInstance) {
		java.util.Date data1 = null;
		try {
			data1 =  new SimpleDateFormat("dd/MM/yyyy").parse("30/05/1981");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Appartamento cheDevoModificare = new Appartamento();
		cheDevoModificare.setQuartiere("ZONA CENTRO");
		cheDevoModificare.setMetriQuadrati(150);
		cheDevoModificare.setPrezzo(220000);
		cheDevoModificare.setDataCostruzione(data1);
		cheDevoModificare.setId(1);
		
		int quantiAppartamentiModificati = appartamentoDAOInstance.update(cheDevoModificare);
		
		if(quantiAppartamentiModificati != 1) {
			 throw new RuntimeException("testUpdateAppartamento : FAILED");
		}
	}
	
	private static void testDelete(AppartamentoDAO appartamentoDAOInstance) {
		List<Appartamento> lista = appartamentoDAOInstance.list();
		
		Appartamento daEliminare = lista.get(3);
		
		int quantiEliminati = appartamentoDAOInstance.delete(daEliminare);
		
		if(quantiEliminati < 1) {
			 throw new RuntimeException("testDeleteAppartamento : FAILED");
		}
	}
	
	
	private static void testFindById(AppartamentoDAO appartamentoDAOInstance) {
		Appartamento cheRicerco = appartamentoDAOInstance.list().get(0);
		
		Appartamento result = appartamentoDAOInstance.findById(cheRicerco.getId());
		
		if(result == null || !result.getQuartiere().equals(appartamentoDAOInstance.list().get(0).getQuartiere())){
			throw new RuntimeException("testFINDBYIDAppartamento : FAILED");
		}
	}
	
	private static void TestFindByExample(AppartamentoDAO appartamentoDAOInstance) {
		java.util.Date data1 = null;
		try {
			data1 =  new SimpleDateFormat("dd/MM/yyyy").parse("10/05/1900");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Appartamento inEntrata = new Appartamento("ZON",1,1,data1);
		
		List<Appartamento> lista = appartamentoDAOInstance.findByExample(inEntrata);
		
		if(lista.size() < 1) {
			throw new RuntimeException("testFINDBYEXAMPLEAppartamento : FAILED");
		}
		for(Appartamento list : lista) {
			System.out.println(lista);
		}
	}
	
	
	private static void TestFindByExample2(AppartamentoDAO appartamentoDAOInstance) {
		java.util.Date data1 = null;
		try {
			data1 =  new SimpleDateFormat("dd/MM/yyyy").parse("10/05/1900");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Appartamento inEntrata = new Appartamento("ZON",1,1,data1);
		
		List<Appartamento> lista = appartamentoDAOInstance.findByExample2(inEntrata);
		
		if(lista.size() < 1) {
			throw new RuntimeException("testFINDBYEXAMPLEAppartamento : FAILED");
		}
		for(Appartamento list : lista) {
			System.out.println(lista);
		}
	}
}
