package py.una.entidad;

import java.util.ArrayList;

public class Mensaje <T>{
    private Long estado;
    private Long tipo_operacion;
    private String mensaje;

    private ArrayList<T> lista;

    public Long getEstado() {
        return estado;
    }

    public void setEstado(Long estado) {
        this.estado = estado;
    }

    public Long getTipo_operacion() {
        return tipo_operacion;
    }

    public void setTipo_operacion(Long tipo_operacion) {
        this.tipo_operacion = tipo_operacion;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public ArrayList<T> getLista() {
        return lista;
    }

    public void setLista(ArrayList<T> lista) {
        this.lista = lista;
    }
}
