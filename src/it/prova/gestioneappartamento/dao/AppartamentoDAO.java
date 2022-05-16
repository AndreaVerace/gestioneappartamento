package it.prova.gestioneappartamento.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import it.prova.gestioneappartamento.connection.MyConnection;
import it.prova.gestioneappartamento.model.Appartamento;

public class AppartamentoDAO {

	public List<Appartamento> list(){
		
		List<Appartamento> result = new ArrayList<>();
		try (Connection c = MyConnection.getConnection();
				Statement s = c.createStatement();
				ResultSet rs = s.executeQuery("select * from appartamento")){
			
			while (rs.next()) {
				Appartamento temp = new Appartamento();
				temp.setQuartiere(rs.getString("quartiere"));
				temp.setMetriQuadrati(rs.getInt("metriquadrati"));
				temp.setPrezzo(rs.getInt("prezzo"));
				temp.setDataCreazione(rs.getDate("datacreazione"));
				
				result.add(temp);
			}
		}  catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return result;
		
	}
	
}
