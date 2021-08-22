package py.una.entidad;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.ArrayList;
import java.util.Iterator;

public class HospitalJSON {


    public static void main(String[] args) throws Exception {
    	HospitalJSON representacion = new HospitalJSON();
    	
    	System.out.println("Ejemplo de uso 1: pasar de objeto a string");
    	Hospital p = new Hospital();
        p.setId(1L);
        p.setNombre("Hospital Capiata");
        p.getCamas().add(new Cama(1L,1L,false,true));
        p.getCamas().add(new Cama(2L,1L,false,true));
        p.getCamas().add(new Cama(3L,1L,false,true));
        p.getCamas().add(new Cama(4L,1L,false,true));

    	String r1 = representacion.objetoString(p);
    	System.out.println(r1);

    	System.out.println("\n*************************************************************************");
    	System.out.println("\nEjemplo de uso 2: pasar de string a objeto");
    	String un_string = "{\"cedula\":123123,\"nombre\":\"Ana\",\"apellido\":\"Perez\",\"asignaturas\":[\"Sistemas Distribuidos\",\"Fisica\",\"Inteligencia Artificial\"]}";

    }




    public static String listHospitales_toString(ArrayList<Hospital> hospitales ){
        JSONObject obj = new JSONObject();

        JSONArray list = new JSONArray();
        for(Hospital h: hospitales){
            list.add(HospitalJSON.objetoString(h));
        }

        obj.put("hospitales", list);
        return obj.toJSONString();
    }

    public static String objetoString(Hospital p) {

        JSONObject obj = new JSONObject();
        obj.put("id_hospital", p.getId());
        obj.put("nombre", p.getNombre());

        JSONArray list = new JSONArray();
        for(Cama temp: p.getCamas()){
            list.add(CamaJSON.objetoString(temp));
        }

        obj.put("camas", list);
        return obj.toJSONString();
    }

    public static ArrayList<Hospital> listHospitalJSON_toHospitales(String str, String nombre_campo) throws Exception {
        ArrayList<Hospital> hospitales = new ArrayList<Hospital>();
        JSONParser parser = new JSONParser();

        Object obj = parser.parse(str.trim());
        JSONObject jsonObject = (JSONObject) obj;

        JSONArray msg = (JSONArray) jsonObject.get(nombre_campo);
        Iterator<String> iterator = msg.iterator();
        while (iterator.hasNext()) {
            hospitales.add(stringToHospital(iterator.next()));
        }
        return hospitales;
    }



    public static Hospital stringToHospital(String str) throws Exception {
        Hospital p = new Hospital();
        JSONParser parser = new JSONParser();

        Object obj = parser.parse(str.trim());
        JSONObject jsonObject = (JSONObject) obj;

        Long hospital_id = (Long) jsonObject.get("id_hospital");
        p.setId(hospital_id);
        p.setNombre((String)jsonObject.get("nombre"));

        JSONArray msg = (JSONArray) jsonObject.get("camas");
        Iterator<String> iterator = msg.iterator();
        while (iterator.hasNext()) {
            p.getCamas().add(CamaJSON.stringObjeto(iterator.next()));
        }
        return p;
    }

}
