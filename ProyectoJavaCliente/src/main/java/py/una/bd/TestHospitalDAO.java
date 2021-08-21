package py.una.bd;

import py.una.entidad.Cama;
import py.una.entidad.Hospital;
import py.una.entidad.Persona;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TestHospitalDAO {

	
	
	public static void main(String args[]) throws SQLException{
		ArrayList<Hospital> hospitales = new HospitalDAO().hospitales_detalles();
		for(Hospital h: hospitales){
			System.out.println(h);
			for(Cama c: h.getCamas()){
				System.out.println("\t"+c);
			}
		}


	}
	
	
}
