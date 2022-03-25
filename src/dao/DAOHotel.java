package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import singleton.DBConnection;

public class DAOHotel {
	private Connection con = null;

	private static DAOHotel instance = null;

	private DAOHotel() throws SQLException {
		con = DBConnection.getConnection();
	}

	public static DAOHotel getInstance() throws SQLException {
		if (instance == null)
			instance = new DAOHotel();

		return instance;
	}
	
	public void cerrarSesion() throws SQLException {
		con.close();

	}
	public Hotel findPK(int codHotel) throws SQLException{
		Hotel result=null;
		boolean hayDatos=false;
		try(PreparedStatement ps=con.prepareStatement("SELECT * FROM hotel WHERE codHotel=?");){
			ps.setInt(1, codHotel);
			ResultSet rs=ps.executeQuery();
			while (rs.next()) {
				hayDatos=true;
				result=new Hotel(rs.getInt("codHotel"),rs.getString("nombreHotel"));
			}
			rs.close();
		}
		if (!hayDatos) {
			result=null;
		}
		return result;
	}
	
	public List<Hotel> findAll() throws SQLException{
		List<Hotel> result=new ArrayList<>();
		boolean hayDatos=false;
		// I search all the hotels
		try(PreparedStatement ps=con.prepareStatement("SELECT * FROM hotel");){
			ResultSet rs=ps.executeQuery();
			while (rs.next()) {
				// Returns something from the query
				hayDatos=true;
				// Add each result
				result.add(new Hotel(rs.getInt("codHotel"),rs.getString("nombreHotel")));
			}
			rs.close();
		}// If there is no data
		if (!hayDatos) {
			result=null;
		}
		return result;
	}

	public void insert(Hotel h) throws SQLException {
		try(PreparedStatement ps=con.prepareStatement("INSERT INTO hotel (codHotel,nombreHotel) VALUES (?,?)");){
			ps.setInt(1, h.getCodHotel());
			ps.setString(2, h.getNombreHotel());
			
			ps.executeUpdate();
		}
	}
}
