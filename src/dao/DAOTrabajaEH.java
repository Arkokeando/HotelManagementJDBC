package dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import singleton.DBConnection;

public class DAOTrabajaEH {

	private Connection con = null;

	private static DAOTrabajaEH instance = null;

	private DAOTrabajaEH() throws SQLException {
		con = DBConnection.getConnection();
	}

	public static DAOTrabajaEH getInstance() throws SQLException {
		if (instance == null)
			instance = new DAOTrabajaEH();

		return instance;
	}
	
	public void cerrarSesion() throws SQLException {
		con.close();

	}
	public List<Empleado> findAllEmpleadosDeUnHotel(int codHotel) throws SQLException{
		List<Empleado> result=new ArrayList<>();
		boolean hayDatos=false;
		// I search all the hotels
		try(PreparedStatement ps=con.prepareStatement("SELECT * FROM empleado e INNER JOIN trabajaEmpleadoHotel t ON e.dni=t.dni WHERE t.codHotel=?");){
			ps.setInt(1, codHotel);
			ResultSet rs=ps.executeQuery();
			while (rs.next()) {
				// Returns something from the query
				hayDatos=true;
				// Add each result
				result.add(new Empleado(rs.getString("e.dni"),rs.getString("nombreEmpleado")));
			}
			rs.close();
		}// If there is no data
		if (!hayDatos) {
			result=null;
		}
		return result;
	}
	
	public void insert(Empleado e,Hotel h) throws SQLException{
		try(PreparedStatement ps = con.prepareStatement("INSERT INTO trabajaEmpleadoHotel (dni,codHotel) VALUES(?,?)");){
			ps.setString(1, e.getDni());
			ps.setInt(2, h.getCodHotel());
			
			ps.executeUpdate();
		}
	}
	public void delete(Empleado e, Hotel h) throws SQLException{
		try(PreparedStatement ps = con.prepareStatement("DELETE FROM trabajaEmpleadoHotel WHERE dni=? AND codHotel=?");){
			ps.setString(1, e.getDni());
			ps.setInt(2, h.getCodHotel());
			
			ps.executeUpdate();
		}
		
	}
	
	
	public List<Hotel> findAllHotelesTrabajaUnEmpleado(String dni) throws SQLException{
		List<Hotel> result=new ArrayList<>();
		boolean hayDatos=false;
		// Searching all the hotels
		try(PreparedStatement ps=con.prepareStatement("SELECT * FROM hotel h INNER JOIN trabajaEmpleadoHotel t ON h.codHotel=t.codHotel WHERE t.dni=?");){
			ps.setString(1, dni);
			ResultSet rs=ps.executeQuery();
			while (rs.next()) {
				// Returns something from the query
				hayDatos=true;
				// Add each result
				result.add(new Hotel(rs.getInt("h.codHotel"),rs.getString("h.nombreHotel")));
			}
			rs.close();
		}// If there is no data
		if (!hayDatos) {
			result=null;
		}
		return result;
	}

	
}
