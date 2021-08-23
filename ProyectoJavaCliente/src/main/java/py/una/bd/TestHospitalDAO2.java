package py.una.bd;

import py.una.entidad.*;

import java.sql.SQLException;
import java.util.ArrayList;

public class TestHospitalDAO2 {


	public static void main(String args[]) throws SQLException{
		try{

			ArrayList<Hospital> hospitales = new HospitalDAO().hospitales_detalles();
			String json_hospitales = HospitalJSON.listHospitales_toString(hospitales);
			String paquete_a_enviar = MensajeJson.paqueteJson(0L,"OK",1L,0L,json_hospitales);
			System.out.println(paquete_a_enviar.getBytes().length);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
