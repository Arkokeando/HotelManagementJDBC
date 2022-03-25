package dao;

public class Habitacion {

	

	private int codHabitacion;
	private Enum<TipoHabitacion> tipoHabitacion;
	private Hotel hotel;

	public Habitacion(int codHabitacion, Hotel hotel, TipoHabitacion tipoHabitacion) {
		this.codHabitacion = codHabitacion;
		this.hotel = hotel;
		this.tipoHabitacion = tipoHabitacion;
	}
	@Override
	public String toString() {
		return "Room [ID= " + codHabitacion + ", type= " + tipoHabitacion + ", hotel= " + hotel
				+ "]";
	}

	public int getCodHabitacion() {
		return codHabitacion;
	}

	public void setCodHabitacion(int codHabitacion) {
		this.codHabitacion = codHabitacion;
	}

	public Enum<TipoHabitacion> getTipoHabitacion() {
		return tipoHabitacion;
	}

	public void setTipoHabitacion(Enum<TipoHabitacion> tipoHabitacion) {
		this.tipoHabitacion = tipoHabitacion;
	}

	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}

}
