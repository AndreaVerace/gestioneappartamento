package it.prova.gestioneappartamento.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import it.prova.gestioneappartamento.connection.MyConnection;
import it.prova.gestioneappartamento.model.Appartamento;


//

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
				temp.setDataCostruzione(rs.getDate("datacostruzione"));
				
				result.add(temp);
			}
		}  catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return result;	
	}
	
	
	public int insert(Appartamento appartamento) {
		if(appartamento == null ) {
			throw new RuntimeException("Impossibile inserire Appartamento nullo");
		}
		
		int result = 0;
		try (Connection c = MyConnection.getConnection();
				PreparedStatement ps = c
						.prepareStatement("INSERT INTO appartamento(quartiere,metriquadrati,prezzo,datacostruzione) values (?,?,?,?)")){
			
			ps.setString(1, appartamento.getQuartiere());
			ps.setInt(2, appartamento.getMetriQuadrati());
			ps.setInt(3, appartamento.getPrezzo());
			ps.setDate(4, new java.sql.Date(appartamento.getDataCostruzione().getTime()));
			
			result = ps.executeUpdate();
		}   catch (Exception e) {
			e.printStackTrace();
			// rilancio in modo tale da avvertire il chiamante
			throw new RuntimeException(e);
		}
		return result;
	}	
		
	
	public int update(Appartamento appartamento) {
		if (appartamento == null) {
			throw new RuntimeException("Impossibile inserire Appartamento nullo");
		}

		int result = 0;
		try (Connection c = MyConnection.getConnection();
				PreparedStatement ps = c.prepareStatement(
						"update appartamento set quartiere = ?,metriquadrati = ?,datacostruzione = ?,prezzo = ? where id = ?")) {

			ps.setString(1, appartamento.getQuartiere());
			ps.setInt(2, appartamento.getMetriQuadrati());
			ps.setDate(3, new java.sql.Date(appartamento.getDataCostruzione().getTime()));
			ps.setInt(4, appartamento.getPrezzo());
			ps.setLong(5, appartamento.getId());
			
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return result;
	}
	
}
