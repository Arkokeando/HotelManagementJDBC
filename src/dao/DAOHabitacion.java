package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import singleton.DBConnection;

public class DAOHabitacion {
	private Connection con = null;

	private static DAOHabitacion instance = null;

	private DAOHabitacion() throws SQLException {
		con = DBConnection.getConnection();
	}

	public static DAOHabitacion getInstance() throws SQLException {
		if (instance == null)
			instance = new DAOHabitacion();
		return instance;
	}
	public List<Habitacion> findAll() throws SQLException{
		List<Habitacion> result=new ArrayList<>();
		boolean hayDatos=false;
		Hotel hotel;
		// I search all the rooms
		try(PreparedStatement ps=con.prepareStatement("SELECT * FROM habitacion");){
			ResultSet rs=ps.executeQuery();
			while (rs.next()) {
				// Returns something from the query
				hayDatos=true;
				/* I create a tipoHabitacion and make a valueof to convert the returned String into an enum
				 * and be able to dump it later in the constructor
				*/
				TipoHabitacion enumerado = TipoHabitacion.valueOf(rs.getString("tipoHabitacion"));
				// Add each result
				hotel= new Hotel(rs.getInt("codHotel"));
				result.add(new Habitacion(rs.getInt("codHabitacion"),hotel,enumerado));
			}
			rs.close();
		}// If there is no data
		if (!hayDatos) {
			result=null;
		}
		return result;
	}
	public void insert(Habitacion h)throws SQLException{
		try(PreparedStatement ps=con.prepareStatement("INSERT INTO habitacion (codHabitacion,codHotel,tipoHabitacion) VALUES (?,?,?)");){
			ps.setInt(1, h.getCodHabitacion());
			ps.setInt(2, h.getHotel().getCodHotel());
			ps.setString(3, String.valueOf(h.getTipoHabitacion()));
			
			ps.executeUpdate();
		}
	}
	
	public Habitacion findPK(int codHabitacion) throws SQLException{
		Hotel hotel;
		Habitacion habitacion=null;
		try(PreparedStatement ps=con.prepareStatement("SELECT * FROM habitacion WHERE codHabitacion=?");){
			ps.setInt(1, codHabitacion);
			ResultSet rs=ps.executeQuery();
			while (rs.next()) {
				TipoHabitacion enumerado = TipoHabitacion.valueOf(rs.getString("tipoHabitacion"));
				hotel= new Hotel(rs.getInt("codHotel"));
				habitacion=new Habitacion(rs.getInt("codHabitacion"),hotel,enumerado);
			}
			rs.close();
		}
		
		return habitacion;
	}
	public List<Habitacion> habitacionDeUnHotel(int codHotel) throws SQLException{
		List<Habitacion> result=new ArrayList<>();
		Hotel hotel;
		boolean hayDatos=false;
		try(PreparedStatement ps=con.prepareStatement("SELECT * FROM habitacion WHERE codHotel=?");){
			ps.setInt(1, codHotel);
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				hayDatos=true;
				TipoHabitacion enumerado = TipoHabitacion.valueOf(rs.getString("tipoHabitacion"));
				hotel= new Hotel(rs.getInt("codHotel"));
				result.add(new Habitacion(rs.getInt("codHabitacion"),hotel,enumerado));
			}
			rs.close();
		}
		if (!hayDatos) {
			result=null;
		}
		return result;
	}
	
	public List<Habitacion> habitacionTipo(TipoHabitacion tipoHab) throws SQLException{
		List<Habitacion> result=new ArrayList<>();
		boolean hayDatos=false;
		Hotel hotel;
		
		try(PreparedStatement ps=con.prepareStatement("SELECT * FROM habitacion WHERE tipoHabitacion=?");){
			// I put the type of room they ask me for
			ps.setString(1, tipoHab.toString());
			ResultSet rs=ps.executeQuery();
			while (rs.next()) {
				// Returns something from the query
				hayDatos=true;
				// Add each result
				hotel= new Hotel(rs.getInt("codHotel"));
				result.add(new Habitacion(rs.getInt("codHabitacion"),hotel,tipoHab));
			}
			rs.close();
		}// If there is no data
		if (!hayDatos) {
			result=null;
		}
		return result;
	}
	public void delete(int codHabitacion) throws SQLException {
		try(PreparedStatement ps=con.prepareStatement("DELETE FROM habitacion WHERE codHabitacion=?");){
			ps.setInt(1, codHabitacion);
			ps.executeUpdate();
		}
	}
	
	public void cerrarSesion() throws SQLException {
		con.close();

	}
}

