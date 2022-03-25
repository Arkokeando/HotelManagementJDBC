package dao;

public class Hotel {

	private int codHotel;
	private String nombreHotel;
	
	public Hotel(int codHotel,String nombreHotel) {
		this.codHotel=codHotel;
		this.nombreHotel=nombreHotel;
	}
	public Hotel(int codHotel) {
		this.codHotel=codHotel;
	}

	public int getCodHotel() {
		return codHotel;
	}

	@Override
	public String toString() {
		return "Hotel [ID= " + codHotel + ", name= " + nombreHotel + "]";
	}

	public void setCodHotel(int codHotel) {
		this.codHotel = codHotel;
	}

	public String getNombreHotel() {
		return nombreHotel;
	}

	public void setNombreHotel(String nombreHotel) {
		this.nombreHotel = nombreHotel;
	}
	
}
