package archivos.bd;

import archivos.entidad.*;

import java.sql.SQLException;
import java.util.ArrayList;

public class TestHospitalDAO2 {


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
			ArrayList<Hospital> convertidos = HospitalJSON.listHospitalJSON_toHospitales(hospitales_json);
			for(Hospital h: convertidos){
				System.out.println(h);
				for(Cama c: h.getCamas()){
					System.out.println("\t"+c);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		String mensaje_json_hospitales = MensajeJson.paqueteJson(1L,"ok",1L,
				0L,HospitalJSON.listHospitales_toString(hospitales));
		String mensaje_json_camas= MensajeJson.paqueteJson(1L,"ok",1L,
				1L,CamaJSON.listCama_toJsonCama(hospitales.get(0).getCamas()));
		try {

			Mensaje mensaje = MensajeJson.stringObjeto(mensaje_json_hospitales);
			System.out.println(mensaje);
			System.out.println("Imprime Json convertido");
			for(Hospital h: (ArrayList<Hospital>) mensaje.getLista()){
				System.out.println(h);
				for(Cama c: h.getCamas()){
					System.out.println(c);
				}
			}
			System.out.println("Este es el json de las camas");
			Mensaje mensaje_camas = MensajeJson.stringObjeto(mensaje_json_camas);
			System.out.println(mensaje_camas);
			System.out.println("Estos son las camas del json");
			for(Cama c: (ArrayList<Cama>)mensaje_camas.getLista()){
				System.out.println(c);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
