package it.prova.gestioneappartamento.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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
	
	
	public int delete(Appartamento appartamento) {
		if(appartamento == null) {
			throw new RuntimeException("Impossibile eliminare Appartamento nullo");
		}
		
		int result = 0;
		try (Connection c = MyConnection.getConnection();
				PreparedStatement ps = c.prepareStatement("delete from appartamento where id = ?")){
			
			ps.setLong(1,appartamento.getId());
			
			result = ps.executeUpdate();
		}  catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return result;
	}
	
	
	public Appartamento findById(Long id) {
		Appartamento result = null;
		try (Connection c = MyConnection.getConnection();
				PreparedStatement ps = c.prepareStatement("select * from appartamento where id = ?")) {

			ps.setLong(1, id);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					result = new Appartamento();
					result.setQuartiere(rs.getString("quartiere"));
					result.setMetriQuadrati(rs.getInt("metriquadrati"));
					result.setPrezzo(rs.getInt("prezzo"));
					result.setDataCostruzione(rs.getDate("datacostruzione"));
					result.setId(rs.getLong("id"));
				} else {
					result = null;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return result;
	}
	
	
	public List<Appartamento> findByExample(Appartamento example) {
		List<Appartamento> result = new ArrayList<>();

		if (example.getQuartiere() == null || example.getQuartiere() == "") {
			throw new RuntimeException("Impossibile trovare Appartamento nullo");
		}

		try (Connection c = MyConnection.getConnection();
				PreparedStatement ps = c.prepareStatement(
						"select * from appartamento where  quartiere like ? and metriquadrati > ? and prezzo > ? and datacostruzione < ? ")) {

			//ps.setLong(1, example.getId());

			for(int i = 0;i < list().size();i++) {
				String quartiereCompleto = list().get(i).getQuartiere();
					if(quartiereCompleto.contains(example.getQuartiere())) {
					ps.setString(1, quartiereCompleto);
					}
			}
			
			ps.setInt(2, example.getMetriQuadrati());

			ps.setInt(3, example.getPrezzo());

			try {
				if (example.getDataCostruzione().before(new SimpleDateFormat("dd/MM/yyyy").parse("16/05/2022"))) {
					ps.setDate(4, new java.sql.Date(example.getDataCostruzione().getTime()));
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					Appartamento ap = new Appartamento();
					//ap.setId(rs.getLong("id"));
					ap.setQuartiere(rs.getString("quartiere"));
					ap.setMetriQuadrati(rs.getInt("metriquadrati"));
					ap.setPrezzo(rs.getInt("prezzo"));
					ap.setDataCostruzione(rs.getDate("datacostruzione"));

					result.add(ap);
				}

			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return result;
	}
}
