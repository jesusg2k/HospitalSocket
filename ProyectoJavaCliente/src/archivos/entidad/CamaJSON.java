package archivos.entidad;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.ArrayList;
import java.util.Iterator;

public class CamaJSON {


    public static void main(String[] args) throws Exception {
    }

    public static String objetoString(Cama p) {

        JSONObject obj = new JSONObject();
        obj.put("id_hospital", p.getHospital_id());
        obj.put("id_cama", p.getCama_id());
        obj.put("ocupado", p.isOcupado());
        obj.put("habilitado", p.isHabilitado());

        return obj.toJSONString();
    }


    public static String listCama_toJsonCama(ArrayList<Cama> camas ){
        JSONObject obj = new JSONObject();
        JSONArray list = new JSONArray();
        for(Cama c: camas){
            list.add(CamaJSON.objetoString(c));
        }
        obj.put("camas", list);
        return obj.toJSONString();
    }

    public static ArrayList<Cama> listCamaJSON_toCama(String str) throws Exception {
        ArrayList<Cama> camas = new ArrayList<>();
        JSONParser parser = new JSONParser();

        Object obj = parser.parse(str.trim());
        JSONObject jsonObject = (JSONObject) obj;

        JSONArray msg = (JSONArray) jsonObject.get("camas");
        Iterator<String> iterator = msg.iterator();
        while (iterator.hasNext()) {
            camas.add(stringObjeto(iterator.next()));
        }
        return camas;
    }

    public static Cama stringObjeto(String str) throws Exception {
        JSONParser parser = new JSONParser();

        Object obj = parser.parse(str.trim());
        JSONObject jsonObject = (JSONObject) obj;

        Long hospital_id = (Long) jsonObject.get("id_hospital");
        Long cama_id = (Long) jsonObject.get("id_cama");
        Boolean ocupado = (Boolean) jsonObject.get("ocupado");
        Boolean habilitado = (Boolean) jsonObject.get("habilitado");
        Cama cama = new Cama(hospital_id,cama_id, ocupado, habilitado);
        return cama;
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
        Iterator<Cama> iterator = msg.iterator();
        while (iterator.hasNext()) {

            p.getCamas().add(iterator.next());
        }
        return p;
    }

}
