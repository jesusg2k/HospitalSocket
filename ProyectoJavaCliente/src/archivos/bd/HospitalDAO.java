package py.una.bd;

import py.una.entidad.Cama;
import py.una.entidad.Hospital;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HospitalDAO {

    public ArrayList<Hospital> hospitales_detalles(){
        /*conseguir los hospitales*/
        ArrayList<Hospital> hospitales = new ArrayList<>(seleccionar());
        /*conseguimos todas las camas*/
        ArrayList<Cama> camas = new ArrayList<>(new CamaDAO().camas_habilitadas());

        /*asociamos los hospitales con su ID*/
        HashMap<Long, Hospital> hashMap = new HashMap<>();
        for(Hospital hospital: hospitales) hashMap.put(hospital.getId(), hospital);

        /*cargamos las camas a los hospitales*/
        for(Cama cama: camas){
            Hospital hospital = hashMap.get(cama.getHospital_id());
            hospital.agregar_cama(cama);
        }
        return hospitales;
    }


	public List<Hospital> seleccionar() {
		String query = "SELECT * FROM hospital";
		
		List<Hospital> lista = new ArrayList<>();
		
		Connection conn = null; 
        try 
        {
        	conn = Bd.connect();
        	ResultSet rs = conn.createStatement().executeQuery(query);

        	while(rs.next()) {
        		Hospital h = new Hospital();
        		h.setId(rs.getLong(1));
        		h.setNombre(rs.getString(2));
        		lista.add(h);
        	}
        	
        } catch (SQLException ex) {
            System.out.println("Error en la seleccion: " + ex.getMessage());
        }
        finally  {
        	try{
        		conn.close();
        	}catch(Exception ef){
        		System.out.println("No se pudo cerrar la conexion a BD: "+ ef.getMessage());
        	}
        }
		return lista;

	}
	public List<Hospital> seleccionarByID(int id) {
		String SQL = "SELECT * FROM hopistal WHERE hospital_id = ? ";
		List<Hospital> lista = new ArrayList<Hospital>();
		
		Connection conn = null; 
        try 
        {
        	conn = Bd.connect();
        	PreparedStatement pstmt = conn.prepareStatement(SQL);
        	pstmt.setInt(1, id);
        	
        	ResultSet rs = pstmt.executeQuery();

        	while(rs.next()) {
                Hospital h = new Hospital();
                h.setId(rs.getLong(1));
                h.setNombre(rs.getString(2));
                lista.add(h);
        	}
        	
        } catch (SQLException ex) {
            System.out.println("Error en la seleccion: " + ex.getMessage());
        }
        finally  {
        	try{
        		conn.close();
        	}catch(Exception ef){
        		System.out.println("No se pudo cerrar la conexion a BD: "+ ef.getMessage());
        	}
        }
		return lista;

	}
    public int insertar(Hospital h) throws SQLException {

        String SQL = "INSERT INTO hospital(hospital_name) "
                + "VALUES(?)";

        int id = 0;
        Connection conn = null;
        
        try 
        {
        	conn = Bd.connect();
        	PreparedStatement pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, h.getNombre());
            int affectedRows = pstmt.executeUpdate();
            // check the affected rows 
            if (affectedRows > 0) {
                // get the ID back
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        id = rs.getInt(1);
                    }
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error en la insercion: " + ex.getMessage());
        }
        finally  {
        	try{
        		conn.close();
        	}catch(Exception ef){
        		System.out.println("No se pudo cerrar la conexion a BD: "+ ef.getMessage());
        	}
        }
        	
        return id;
    }

}
