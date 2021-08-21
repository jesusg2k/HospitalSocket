package py.una.entidad;
import java.util.ArrayList;
import java.util.List;

public class Hospital {

	Integer id;
	String nombre;

	List<Cama> camas;

	public Hospital() {
		camas = new ArrayList<Cama>();
	}

	public Hospital(Integer id, String pnombre) {
		this.id = id;
		this.nombre = pnombre;
		camas = new ArrayList<Cama>();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Cama> getCamas() {
		return camas;
	}

	public void setCamas(List<Cama> camas) {
		this.camas = camas;
	}
}
