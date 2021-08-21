package py.una.entidad;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.Iterator;

public class HospitalJSON {


    public static void main(String[] args) throws Exception {
    	HospitalJSON representacion = new HospitalJSON();
    	
    	System.out.println("Ejemplo de uso 1: pasar de objeto a string");
    	Hospital p = new Hospital();
        p.setId(1);
        p.setNombre("Hospital Capiata");
        p.getCamas().add(new Cama(1,1,false,true));
        p.getCamas().add(new Cama(2,1,false,true));
        p.getCamas().add(new Cama(3,1,false,true));
        p.getCamas().add(new Cama(4,1,false,true));

    	String r1 = representacion.objetoString(p);
    	System.out.println(r1);

    	System.out.println("\n*************************************************************************");
    	System.out.println("\nEjemplo de uso 2: pasar de string a objeto");
    	String un_string = "{\"cedula\":123123,\"nombre\":\"Ana\",\"apellido\":\"Perez\",\"asignaturas\":[\"Sistemas Distribuidos\",\"Fisica\",\"Inteligencia Artificial\"]}";
    	
    	Persona r2 = representacion.stringObjeto(un_string);
    	System.out.println(r2.nombre + " " + r2.apellido + " " +r2.cedula );
        for(String temp: r2.getAsignaturas()){
        	System.out.println(temp);
        }
    }

    public static String objetoString(Hospital p) {

        JSONObject obj = new JSONObject();
        obj.put("id_hospital", p.getId());
        obj.put("nombre", p.getNombre());

        JSONArray list = new JSONArray();
        for(Cama temp: p.getCamas()){
            list.add(temp);
        }

        obj.put("camas", list);
        return obj.toJSONString();
    }


    public static String objetoString(Persona p) {	
    	
		JSONObject obj = new JSONObject();
        obj.put("cedula", p.getCedula());
        obj.put("nombre", p.getNombre());
        obj.put("apellido", p.getApellido());

        JSONArray list = new JSONArray();
        
        for(String temp: p.getAsignaturas()){
        	list.add(temp);
        }
       // if(list.size() > 0) {
        	obj.put("asignaturas", list);
        //}

        return obj.toJSONString();
    }
    
    
    public static Persona stringObjeto(String str) throws Exception {
    	Persona p = new Persona();
        JSONParser parser = new JSONParser();

        Object obj = parser.parse(str.trim());
        JSONObject jsonObject = (JSONObject) obj;

        Long cedula = (Long) jsonObject.get("cedula");
        p.setCedula(cedula);
        p.setNombre((String)jsonObject.get("nombre"));
        p.setApellido((String)jsonObject.get("apellido"));
        
        JSONArray msg = (JSONArray) jsonObject.get("asignaturas");
        Iterator<String> iterator = msg.iterator();
        while (iterator.hasNext()) {
        	p.getAsignaturas().add(iterator.next());
        }
        return p;
	}


    public static Hospital stringToHospital(String str) throws Exception {
        Hospital p = new Hospital();
        JSONParser parser = new JSONParser();

        Object obj = parser.parse(str.trim());
        JSONObject jsonObject = (JSONObject) obj;

        Integer hospital_id = (Integer) jsonObject.get("id_hospital");
        p.setId(hospital_id);
        p.setNombre((String)jsonObject.get("nombre"));

        JSONArray msg = (JSONArray) jsonObject.get("camas");
        Iterator<Cama> iterator = msg.iterator();
        while (iterator.hasNext()) {
            p.getCamas().add(iterator.next());
        }
        return p;
    }

}
