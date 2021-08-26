package archivos.entidad;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class MensajeJson {
    /*estado
    * estado, donde:
    "0" corresponde a una transacción exitosa.
    "-1" transacción indeterminada.
    Mayor a "1" un código de error o mensaje relacionado a la transacción.
    mensaje
    Palabra “ok” si no existe error.
    El detalle el error si existe.
    tipo_operacion:
    1: ver_estado
    2: crear_cama
    3: eliminar_cama
    4: ocupar_cama
    5: desocupar_cama
    Otros a considerar
    Cuerpo u otros datos específicos según el tipo de operación.
*/
    public static String paqueteJson(Long estado, String mensaje, Long tipo_operacion, Long tipo_objeto, String objetos_json){
        JSONObject obj = new JSONObject();
        obj.put("estado", estado);
        obj.put("mensaje", mensaje);
        obj.put("tipo_operacion", tipo_operacion);
        obj.put("tipo_objeto", tipo_objeto);
        obj.put("listas", objetos_json);

        return obj.toJSONString();
    }

    public static Mensaje stringObjeto(String str) throws Exception {
        JSONParser parser = new JSONParser();

        Object obj = parser.parse(str.trim());
        JSONObject jsonObject = (JSONObject) obj;

        Long estado = (Long) jsonObject.get("estado");
        String mensaje = (String) jsonObject.get("mensaje");
        Long tipo_operacion = (Long) jsonObject.get("tipo_operacion");
        Long tipo_objeto = (Long) jsonObject.get("tipo_objeto");
        Mensaje paquete;
        if(tipo_objeto == 0){
            paquete = new Mensaje<Hospital>();
            String json_hospitales = (String) jsonObject.get("listas");
            paquete.setLista(HospitalJSON.listHospitalJSON_toHospitales(json_hospitales));
        }else{
            paquete = new Mensaje<Cama>();
            String json_hospitales = (String) jsonObject.get("listas");
            paquete.setLista(CamaJSON.listCamaJSON_toCama(json_hospitales));
        }
        paquete.setEstado(estado);
        paquete.setMensaje(mensaje);
        paquete.setTipo_operacion(tipo_operacion);
        paquete.setTipo_objeto(tipo_objeto);
        return paquete;
    }
}
