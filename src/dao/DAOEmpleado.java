package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import singleton.DBConnection;

public class DAOEmpleado {
	private Connection con = null;

	private static DAOEmpleado instance = null;

	private DAOEmpleado() throws SQLException {
		con = DBConnection.getConnection();
	}

	public static DAOEmpleado getInstance() throws SQLException {
		if (instance == null)
			instance = new DAOEmpleado();

		return instance;
	}
	public Empleado findPK(String dni) throws SQLException{
		Empleado result=null;
		boolean hayDatos=false;
		try(PreparedStatement ps=con.prepareStatement("SELECT * FROM empleado WHERE dni=?");){
			ps.setString(1, dni);
			ResultSet rs=ps.executeQuery();
			while (rs.next()) {
				hayDatos=true;
				result=new Empleado(rs.getString("dni"),rs.getString("nombreEmpleado"));
			}
			rs.close();
		}
		
		return result;
	}
	
	public List<Empleado> findAll() throws SQLException{
		List<Empleado> result=new ArrayList<>();
		boolean hayDatos=false;
		// I search all the hotels
		try(PreparedStatement ps=con.prepareStatement("SELECT * FROM empleado");){
			ResultSet rs=ps.executeQuery();
			while (rs.next()) {
				// Returns something from the query
				hayDatos=true;
				// add each result
				result.add(new Empleado(rs.getString("dni"),rs.getString("nombreEmpleado")));
			}
			rs.close();
		}// If there is no data
		if (!hayDatos) {
			result=null;
		}
		return result;
	}
	public void delete(String dni) throws SQLException {
		Empleado empleado=findPK(dni);
		if (empleado==null) {
			System.out.println("There is no employee with ID "+dni);
		}else {
			try(PreparedStatement ps=con.prepareStatement("DELETE FROM empleado WHERE dni=?");){
				ps.setString(1, dni);
				ps.executeUpdate();
			}
		}
	}
	
	
	public void cerrarSesion() throws SQLException {
		con.close();

	}

	public void insert(Empleado e) throws SQLException {
		try(PreparedStatement ps=con.prepareStatement("INSERT INTO empleado (dni,nombreEmpleado) VALUES (?,?)");){
			ps.setString(1, e.getDni());
			ps.setString(2, e.getNombre());
			
			ps.executeUpdate();
		}
		
	}
}
