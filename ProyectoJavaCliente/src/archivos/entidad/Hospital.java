package py.una.entidad;
import java.util.ArrayList;
import java.util.List;

public class Hospital {

	Long id;
	String nombre;

	ArrayList<Cama> camas;

	public Hospital(Long id) {
		this.id = id;
	}

	public Hospital() {
		camas = new ArrayList<Cama>();
	}

	public Hospital(Long id, String pnombre) {
		this.id = id;
		this.nombre = pnombre;
		camas = new ArrayList<Cama>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public ArrayList<Cama> getCamas() {
		return camas;
	}

	public void setCamas(ArrayList<Cama> camas) {
		this.camas = camas;
	}

	public void agregar_cama(Cama cama){
		camas.add(cama);
	}

	@Override
	public String toString() {
		return id + " " + nombre + " can. camas: "+ camas.size();
	}
}
