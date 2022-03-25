package dao;

public class Empleado {

	

	private String dni;
	private String nombreEmpleado;
	
	public Empleado(String dni,String nombre) {
		this.dni=dni;
		this.nombreEmpleado=nombre;
	}
	public Empleado(String dni) {
		this.dni=dni;
	}
	@Override
	public String toString() {
		return "Employee [dni= " + dni + ", name= " + nombreEmpleado + "]";
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getNombre() {
		return nombreEmpleado;
	}

	public void setNombre(String nombre) {
		this.nombreEmpleado = nombre;
	}
	
}
