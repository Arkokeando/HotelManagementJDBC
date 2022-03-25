package vista;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import dao.*;

public class Principal {
	
	private static Scanner teclado = new Scanner(System.in);
	
	public static void main(String[] args) {
		
		try {
			int opcion;
			do {
				menu();
				opcion = Integer.parseInt(teclado.nextLine());
				tratarMenu(opcion);
				

			} while (opcion != 0);
		} catch (SQLException ex) {
			System.out.println("Error: " + ex.getMessage());
		}
	}
	public static void menu() {

		System.out.println("EMPLOYEE-HOTEL MANAGEMENT SYSTEM");
		System.out.println("+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+");
		System.out.println("0. Leave");
		System.out.println("1. Employee Menu");
		System.out.println("2. Hotel Menu");

	}
	private static void tratarMenu(int opcion) throws SQLException {
		switch (opcion) {
		case 0:
			DAOEmpleado dao = DAOEmpleado.getInstance();
			dao.cerrarSesion();
			break;
		case 1:
			submenuEmpleado();
			tratarSubmenuEmpleado(opcion);
			break;
		case 2:
			submenuHotel();
			tratarSubmenuHotel(opcion);
			break;
		}
		
	}

	public static void submenuEmpleado() {
		System.out.println("EMPLOYEE");
		System.out.println("+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+");
		System.out.println("0. Leave");
		System.out.println("1. List of employees");
		System.out.println("2. List of hotels where an emplooyee works");
		System.out.println("3. Insert employee");
		System.out.println("4. Delete employee");
		System.out.println("5. Delete hotel where an employee works");
		System.out.println("6. Add hotel where an employee works");
	}
	private static void tratarSubmenuEmpleado(int opcion) throws SQLException {
		String dni,nombreEmpleado, cadena;
		int codHotel;
		do {
			opcion = Integer.parseInt(teclado.nextLine());
			switch (opcion) {
			case 1://list employee
				listarEmpleados();
				break;
			case 2://List hotels where an employee works
				System.out.println("Enter the employee's ID:");
				dni=teclado.nextLine();
				hotelesTrabajaEmpleado(dni);
				break;
			case 3://Insert employee
				//asking for data
				System.out.println("Enter the name of the new employee:");
				nombreEmpleado=teclado.nextLine();
				System.out.println("Enter the employee's ID:");
				dni= teclado.nextLine();
				//inserts in db
				insertarEmpleado(dni,nombreEmpleado);				
				break;
			case 4://Delete employee                   (/) COMPROBADO 
				//asking for PK dni(ID)
				System.out.println("Enter the employee's ID:");
				dni= teclado.nextLine();
				//delete from db
				borrarEmpleado(dni);
				break;
			case 5://delete hotel where an employee works
				//asking for PK's of hotel and employee
				System.out.println("Enter the employee's ID:");
				dni= teclado.nextLine();
				System.out.println("Enter the ID of the Hotel");
				if (esEntero(cadena=teclado.nextLine())) {
					codHotel=Integer.parseInt(cadena);
					eliminarHotelParaEmpleado(dni, codHotel);
				}else {
					submenuEmpleado();
				}
				break;
			case 6://Add hotel where an employee works
				//asking for PK's of hotel and employee
				System.out.println("Enter the employee's ID:");
				dni= teclado.nextLine();
				System.out.println("Enter the ID of the Hotel");
				if (esEntero(cadena=teclado.nextLine())) {
					codHotel=Integer.parseInt(cadena);
					addEmpleadoAHotel(dni,codHotel);
				}else {
					submenuEmpleado();
				}
				
				break;
			}
		} while (opcion!=0);
	}
	private static void listarEmpleados() throws SQLException{
		DAOEmpleado dao = DAOEmpleado.getInstance();
		List<Empleado> lista=null;
		
		lista=dao.findAll();
		if (lista!=null) {
			for(Empleado e : lista) {
				System.out.println(e.toString());
			}
		}else {
			System.out.println("There are no employees registered in the Database");
		}
	}
	private static void hotelesTrabajaEmpleado(String dni) throws SQLException {
		DAOEmpleado dao=DAOEmpleado.getInstance();
		Empleado e=null;
		//I check that the employee exists
		e=dao.findPK(dni);
		if (e!=null) {
			DAOTrabajaEH daoEH =DAOTrabajaEH.getInstance();
			List<Hotel> lista=null;
			lista=daoEH.findAllHotelesTrabajaUnEmpleado(dni);
			//Here I get a list of hotels but they only have the codHotel
			if (lista!=null) {
				//I go through the list of hotels and print them
				for(Hotel h:lista) {
					System.out.println(h.toString());
				}
			}else {
				System.out.println("This employee does not currently work in any hotel");
			}
		}else {
			System.out.println("There is no employee with that ID registered in the Database");
		}
	}
	private static void eliminarHotelParaEmpleado(String dni, int codHotel) throws SQLException {
		DAOHotel daoHotel=DAOHotel.getInstance();
		Hotel hotel=daoHotel.findPK(codHotel);
		if(hotel==null) {
			System.out.println("There is no hotel with code "+codHotel);
		}else {
			DAOEmpleado daoEmp=DAOEmpleado.getInstance();
			Empleado empleado=daoEmp.findPK(dni);
			if (empleado==null) {
				System.out.println("There is no employee with ID "+dni);
			}else {
				DAOTrabajaEH daoT=DAOTrabajaEH.getInstance();
				daoT.delete(empleado, hotel);
				System.out.println("Removed successfully");
			}
		}
		
	}
	private static void insertarEmpleado(String dni, String nombreEmpleado) throws SQLException {
		DAOEmpleado dao=DAOEmpleado.getInstance();
		Empleado empleado=dao.findPK(dni);
		if(empleado!=null) {
			System.out.println("There is already an employee with that ID.");
		}else {
			empleado= new Empleado(dni,nombreEmpleado);
			dao.insert(empleado);
		}
		
	}
	private static void borrarEmpleado(String dni) throws SQLException {
		DAOEmpleado dao=DAOEmpleado.getInstance();
		dao.delete(dni);
		System.out.println("Removed successfully");
		
	}
	
	public static void submenuHotel() {
		System.out.println("HOTEL");
		System.out.println("+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+");
		System.out.println("00. Leave.");
		System.out.println("01. List of hotels.");
		System.out.println("02. List of rooms.");
		System.out.println("03. List rooms of a type.");
		System.out.println("04. List of rooms of a hotel.");
		System.out.println("05. List employees of a hotel.");
		System.out.println("06. Add new hotel.");
		System.out.println("07. Add new room in hotel.");
		System.out.println("08. Delete employee who works in a hotel.");
		System.out.println("09. Delete hotel room.");
		System.out.println("10. Add employee who works in a hotel.");
	}
	private static void tratarSubmenuHotel(int opcion) throws SQLException {
		int codHotel,codHabitacion;
		String cadena;
		String dni,nombreHotel;
		do {
			opcion = Integer.parseInt(teclado.nextLine());
			switch (opcion) {
			case 1:// List of hotels
				listarHoteles();
				break;
			case 2:// List of rooms 
				listarHabitaciones();
				break;
			case 3:// List rooms of a type
				String cadenaTipoHab;
				TipoHabitacion tipoHab=null;
				boolean hayError;
				do {
					System.out.println("What type of room do you want to list?" + Arrays.toString(TipoHabitacion.values()));
					cadenaTipoHab=teclado.nextLine().toUpperCase();
					
					try {
						tipoHab=TipoHabitacion.valueOf(cadenaTipoHab);
						hayError=false;
					}catch (IllegalArgumentException e) {
						hayError=true;
					}
				}while(hayError);
				listarHabitacionesTipo(tipoHab);
				break;
			case 4:// List of rooms of a hotel 
				System.out.println("Enter the ID of the Hotel: ");
				if (esEntero(cadena=teclado.nextLine())) {
					codHotel=Integer.parseInt(cadena);
					habitacionesDeHotel(codHotel);
				}else {
					submenuHotel();
				}
				break;
			case 5:// List employees of a hotel
				System.out.println("Enter the ID of the Hotel: ");
				if (esEntero(cadena=teclado.nextLine())) {
					codHotel=Integer.parseInt(cadena);
					empleadosTrabajaHotel(codHotel);
				}else {
					submenuHotel();
				}
				
				break;
			case 6:// Add new hotel
				System.out.println("Enter the ID of the new Hotel:");
				if (esEntero(cadena=teclado.nextLine())) {
					codHotel=Integer.parseInt(cadena);
					System.out.println("Enter the name of the new hotel:");
					nombreHotel=teclado.nextLine();
					addHotel(codHotel,nombreHotel);
				}else {
					submenuHotel();
				}
				
				break;
			case 7:// Add new room in hotel
				System.out.println("What hotel is the new room from?");
				if (esEntero(cadena=teclado.nextLine())) {
					codHotel=Integer.parseInt(cadena);
					crearHabitacionDeHotel(codHotel);
				}else {
					submenuHotel();
				}
				break;
			case 8:// Delete employee who works in a hotel
				System.out.println("Enter the employee's ID: ");
				dni=teclado.nextLine();
				System.out.println("Enter the ID of the Hotel: ");
				if (esEntero(cadena=teclado.nextLine())) {
					codHotel=Integer.parseInt(cadena);
					deleteEmpleadoEnHotel(dni,codHotel);
				}else {
					submenuHotel();
				}
				break;
			case 9:// Delete hotel room
				System.out.println("Enter the room ID:");
				if (esEntero(cadena=teclado.nextLine())) {
					codHabitacion=Integer.parseInt(cadena);
					System.out.println("Enter the ID of the Hotel: ");
					if (esEntero(cadena=teclado.nextLine())) {
						codHotel=Integer.parseInt(cadena);
						deleteHabitacion(codHabitacion,codHotel);
					}else {
						submenuHotel();
					}
				}
				break;
			case 10:// Add employee who works in a hotel
				System.out.println("Enter the employee's ID:");
				dni=teclado.nextLine();
				System.out.println("Enter the ID of the Hotel: ");
				if (esEntero(cadena=teclado.nextLine())) {
					codHotel=Integer.parseInt(cadena);
					addEmpleadoAHotel(dni,codHotel);
				}else {
					submenuHotel();
				}
				break;
			}
		} while (opcion!=0);
	}

	private static void addHotel(int codHotel, String nombreHotel) throws SQLException {
		DAOHotel daoHotel=DAOHotel.getInstance();
		Hotel hotel=daoHotel.findPK(codHotel);
		if(hotel!=null) {
			System.out.println("There is already a hotel with that ID.");
		}else {
			hotel= new Hotel(codHotel,nombreHotel);
			daoHotel.insert(hotel);
			System.out.println("Added successfully.");
		}
		
	}
	private static void deleteEmpleadoEnHotel(String dni, int codHotel) throws SQLException {
		DAOHotel daoHotel=DAOHotel.getInstance();
		Hotel hotel=daoHotel.findPK(codHotel);
		if(hotel==null) {
			System.out.println("There is no hotel with the ID "+codHotel);
		}else {
			DAOEmpleado daoEmp=DAOEmpleado.getInstance();
			Empleado empleado=daoEmp.findPK(dni);
			if (empleado==null) {
				System.out.println("There is no employee with the ID "+dni);
			}else {
				DAOTrabajaEH daoT=DAOTrabajaEH.getInstance();
				daoT.delete(empleado, hotel);
				System.out.println("Removed successfully.");
			}
		}
		
	}
	private static void addEmpleadoAHotel(String dni, int codHotel) throws SQLException{
		DAOHotel daoHotel=DAOHotel.getInstance();
		Hotel hotel=daoHotel.findPK(codHotel);
		if(hotel==null) {
			System.out.println("There is no hotel with the ID "+codHotel);
		}else {
			DAOEmpleado daoEmp=DAOEmpleado.getInstance();
			Empleado empleado=daoEmp.findPK(dni);
			if (empleado==null) {
				System.out.println("There is no employee with the ID "+dni);
			}else {
				DAOTrabajaEH daoT=DAOTrabajaEH.getInstance();
				daoT.insert(empleado, hotel);
				System.out.println("Added successfully.");
			}
		}
	}
	private static void crearHabitacionDeHotel(int codHotel) throws SQLException{
		DAOHotel dao=DAOHotel.getInstance();
		Hotel hotel=dao.findPK(codHotel);
		Habitacion hab;
		boolean hayError;
		int codHabitacion;
		String cadenaTipoHab;
		TipoHabitacion tipoHab = null;
		// I check that the hotel exists
		if (hotel==null) {
			System.out.println("There is no hotel with the ID "+codHotel);
		}else {
			// if it exists I keep asking for data
			System.out.println("Enter the room ID:");
			codHabitacion = Integer.parseInt(teclado.nextLine());
			do {
				System.out.println("Enter the type of room: " + Arrays.toString(TipoHabitacion.values()));
				cadenaTipoHab = teclado.nextLine().toUpperCase();
				try {
					tipoHab = TipoHabitacion.valueOf(cadenaTipoHab);
					hayError=false;
				}catch (IllegalArgumentException e) {
					hayError=true;
				}
			}while(hayError);
			// once I have the data, I insert it into the DB
			DAOHabitacion daoHab=DAOHabitacion.getInstance();
			hab=new Habitacion(codHabitacion, hotel, tipoHab);
			daoHab.insert(hab);
			System.out.println("Created successfully.");
		}
		
	}
	private static void deleteHabitacion(int codHabitacion, int codHotel) throws SQLException {
		boolean existe=false;
		DAOHabitacion daoHab=DAOHabitacion.getInstance();
		DAOHotel dao=DAOHotel.getInstance();
		Hotel hotel=dao.findPK(codHotel);
		// I check that the hotel exists
		if (hotel==null) {
			System.out.println("There is no hotel with the ID "+codHotel);
		}else {
			Habitacion habitacion=daoHab.findPK(codHabitacion);
			// I check that the room exists
			if (habitacion==null) {
				System.out.println("There is no room with that ID.");
			}else {
				List<Habitacion> lista=null;
				// I'm looking for the rooms of that hotel
				lista=daoHab.habitacionDeUnHotel(codHotel);
				// if it has rooms
				if (lista!=null) {
					for(Habitacion h:lista) {
						/* I check that in the list of all the rooms with that room code there is one
						 * with the codHotel entered
						 */
						if (h.getHotel().getCodHotel()==codHotel) {
							existe=true;
						}
					}
				}
			// If it exists, I delete it.
			}if(existe==true) {
				daoHab.delete(codHabitacion);
				System.out.println("Successfully deleted.");
			}else {// If not, I would say that there is no room with that ID in that hotel
				System.out.println("There is no room with ID "+codHabitacion+" in the hotel with ID "+codHotel+".");
			}
		}
		
	}
	private static void habitacionesDeHotel(int codHotel) throws SQLException{
		DAOHabitacion dao=DAOHabitacion.getInstance();
		List<Habitacion> lista=null;
		lista=dao.habitacionDeUnHotel(codHotel);
		if (lista!=null) {
			for(Habitacion h : lista) {
				System.out.println(h);
			}
		}else {
			System.out.println("There are no rooms registered in this hotel");
		}
	}
	
	private static void empleadosTrabajaHotel(int codHotel) throws SQLException{
		DAOHotel dao=DAOHotel.getInstance();
		Hotel h=null;
		// I check that the hotel exists
		h=dao.findPK(codHotel);
		if (h!=null) {
			DAOTrabajaEH daoEH=DAOTrabajaEH.getInstance();
			List<Empleado> lista=null;
			lista=daoEH.findAllEmpleadosDeUnHotel(codHotel);
			if (lista!=null) {
				for(Empleado e:lista) {
					System.out.println(e.toString());
				}
			}else {
				System.out.println("This hotel currently has no employees");
			}
		}else {
			System.out.println("There is no hotel with that code registered in the Database");
		}
	}
	
	private static void listarHoteles() throws SQLException{
		DAOHotel dao = DAOHotel.getInstance();
		List<Hotel> lista=null;
		
		lista=dao.findAll();
		if (lista!=null) {
			for(Hotel h : lista) {
				System.out.println(h);
			}
		}else {
			System.out.println("There are no hotels registered in the Database");
		}
	}
	
	private static void listarHabitaciones() throws SQLException{
		DAOHabitacion dao = DAOHabitacion.getInstance();
		List<Habitacion> lista=null;
		
		lista=dao.findAll();
		if (lista!=null) {
			for(Habitacion h : lista) {
				System.out.println(h.toString());
			}
		}else {
			System.out.println("There are no rooms registered in the Database");
		}
	}
	private static void listarHabitacionesTipo(TipoHabitacion tipoHab) throws SQLException{
		DAOHabitacion dao= DAOHabitacion.getInstance();
		List<Habitacion> lista=null;
		
		lista=dao.habitacionTipo(tipoHab);
		if (lista!=null) {
			for (Habitacion h : lista) {
				System.out.println(h.toString());
			}
		}else {
			System.out.println("There are no "+tipoHab +" rooms registered in the Database.");
		}
	}
	public static boolean esEntero(String cadena) {
		boolean entero;
		try {
			Integer.parseInt(cadena);
			entero=true;
		} catch (Exception e) {
			entero=false;
			System.out.println();
		}
		
		return entero;
	}
}
