package archivos.entidad;

public class Cama {
    private Long hospital_id;
    private Long cama_id;
    private boolean ocupado;
    private boolean habilitado;

    public Cama(Long cama_id) {
        this.cama_id = cama_id;
    }

    public Cama(){

    }
    public Cama(Long hospital_id, Long cama_id, boolean ocupado, boolean habilitado) {
        this.hospital_id = hospital_id;
        this.cama_id = cama_id;
        this.ocupado = ocupado;
        this.habilitado = habilitado;
    }

    public Long getHospital_id() {
        return hospital_id;
    }

    public void setHospital_id(Long hospital_id) {
        this.hospital_id = hospital_id;
    }

    public Long getCama_id() {
        return cama_id;
    }

    public void setCama_id(Long cama_id) {
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
