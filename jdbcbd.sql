Create table empleado (
dni varchar(20) primary key,
nombreEmpleado varchar(20)
);

Create table hotel (
codHotel integer primary key,
nombreHotel varchar(30)
);

Create table trabajaEmpleadoHotel(
dni varchar(20),
codHotel integer,
primary key(dni,codHotel),
CONSTRAINT FK_emptrab FOREIGN KEY (dni) REFERENCES empleado (dni),
CONSTRAINT FK_hottrab FOREIGN KEY (codHotel) REFERENCES hotel (codHotel)
);

Create table habitacion(
codHabitacion integer,
codHotel integer,
tipoHabitacion ENUM('INDIVIDUAL','DOBLE','SUITE'),
CONSTRAINT FK_habhot FOREIGN KEY (codHotel) REFERENCES hotel(codHotel),
primary key (codHabitacion,codHotel)
);




insert into empleado values ('11111111','Antonio López');
insert into empleado values ('22222222', 'Pedro Jimenez');
insert into empleado values ('33333333', 'Lola Dominguez');

insert into hotel values (1,'Barceló Oromana');
insert into hotel values (2,'Barceló Silos');
insert into hotel values (3,'Barceló El Castillo');
insert into hotel values (4,'Nuevo Barceló');

insert into trabajaEmpleadoHotel values ('11111111',1);
insert into trabajaEmpleadoHotel values ('11111111',2);
insert into trabajaEmpleadoHotel values ('11111111',3);
insert into trabajaEmpleadoHotel values ('22222222',2);
insert into trabajaEmpleadoHotel values ('22222222',3);
insert into trabajaEmpleadoHotel values ('33333333',3);

insert into habitacion values (1101,1,'DOBLE');
insert into habitacion values (1301,1,'SUITE');
insert into habitacion values (1102,1,'INDIVIDUAL');
insert into habitacion values (1222,2,'DOBLE');
insert into habitacion values (1111,2,'INDIVIDUAL');
insert into habitacion values (3001,3,'SUITE');
insert into habitacion values (3002,3,'SUITE');
insert into habitacion values (3003,3,'DOBLE');