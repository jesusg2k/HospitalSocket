package py.una.entidad;

public class Cama {
    private int hospital_id;
    private int cama_id;
    private boolean ocupado;
    private boolean habilitado;

    public Cama(){

    }
    public Cama(int hospital_id, int cama_id, boolean ocupado, boolean habilitado) {
        this.hospital_id = hospital_id;
        this.cama_id = cama_id;
        this.ocupado = ocupado;
        this.habilitado = habilitado;
    }

    public int getHospital_id() {
        return hospital_id;
    }

    public void setHospital_id(int hospital_id) {
        this.hospital_id = hospital_id;
    }

    public int getCama_id() {
        return cama_id;
    }

    public void setCama_id(int cama_id) {
        this.cama_id = cama_id;
    }

    public boolean isOcupado() {
        return ocupado;
    }

    public void setOcupado(boolean ocupado) {
        this.ocupado = ocupado;
    }

    public boolean isHabilitado() {
        return habilitado;
    }

    public void setHabilitado(boolean habilitado) {
        this.habilitado = habilitado;
    }

    @Override
    public String toString() {
        return cama_id + " " + hospital_id+ ((isOcupado())?"Ocupado":"No ocupado") + " "+ (isHabilitado()?"Habilitado":"Deshabilitado");
    }
}
