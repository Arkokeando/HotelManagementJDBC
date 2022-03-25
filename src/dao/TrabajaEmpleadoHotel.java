package dao;

public class TrabajaEmpleadoHotel {

	private Empleado empleado;
	private Hotel hotel;
	
	public TrabajaEmpleadoHotel(Empleado empleado,Hotel hotel) {
		this.empleado=empleado;
		this.hotel=hotel;
	}

	public Empleado getEmpleado() {
		return empleado;
	}

	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}

	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}
}
