package py.una.bd;

import py.una.entidad.*;

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

		String cama = CamaJSON.objetoString(hospitales.get(0).getCamas().get(0));
		System.out.println(cama);
		String hospital = HospitalJSON.objetoString(hospitales.get(0));
		System.out.println(hospital);
		try {
			Hospital hospi = HospitalJSON.stringToHospital(hospital);
			System.out.println(hospi);
			for(Cama c: hospi.getCamas()){
				System.out.println(c);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Formato incorrecto");
		}

		String hospitales_json = HospitalJSON.listHospitales_toString(hospitales);
		System.out.println(hospitales_json);
		try {
			ArrayList<Hospital> convertidos = HospitalJSON.listHospitalJSON_toHospitales(hospitales_json, "hospitales");
			for(Hospital h: convertidos){
				System.out.println(h);
				for(Cama c: h.getCamas()){
					System.out.println("\t"+c);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
