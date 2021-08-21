package py.una.bd;

import py.una.entidad.Cama;
import py.una.entidad.Hospital;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CamaDAO {


	public List<Cama> camas_habilitadas() {
		/*creamos la consulta*/
		String query = "SELECT * FROM cama WHERE habilitado_cama = 'true'";

		List<Cama> lista = new ArrayList<>();

		Connection conn = null;
		try
		{
			conn = Bd.connect();
			ResultSet rs = conn.createStatement().executeQuery(query);

			while(rs.next()) {
				Cama c = new Cama();
				c.setCama_id(rs.getInt(1));
				c.setHospital_id(rs.getInt(2));
				c.setOcupado(rs.getBoolean(3));
				c.setHabilitado(rs.getBoolean(4));
				lista.add(c);
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

	public List<Cama> camas_disponibles() {
		/*creamos la consulta*/
		String query = "SELECT * FROM cama WHERE ocupacion_cama = 'false' AND habilitado_cama = 'true'";

		List<Cama> lista = new ArrayList<>();

		Connection conn = null;
		try
		{
			conn = Bd.connect();
			ResultSet rs = conn.createStatement().executeQuery(query);

			while(rs.next()) {
				Cama c = new Cama();
				c.setCama_id(rs.getInt(1));
				c.setHospital_id(rs.getInt(2));
				c.setOcupado(rs.getBoolean(3));
				c.setHabilitado(rs.getBoolean(4));
				lista.add(c);
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
	public List<Cama> seleccionar_todos() {
		/*creamos la consulta*/
		String query = "SELECT * FROM cama";

		List<Cama> lista = new ArrayList<>();

		Connection conn = null;
        try
        {
        	conn = Bd.connect();
        	ResultSet rs = conn.createStatement().executeQuery(query);

        	while(rs.next()) {
        		Cama c = new Cama();
        		c.setCama_id(rs.getInt(1));
        		c.setHospital_id(rs.getInt(2));
        		c.setOcupado(rs.getBoolean(3));
        		c.setHabilitado(rs.getBoolean(4));
        		lista.add(c);
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
	public List<Cama> seleccionarByID_Cama(int id) {
		String query = "SELECT * FROM cama where cama_id = ? ";
		List<Cama> lista = new ArrayList<>();

		Connection conn = null;
		try
		{
			conn = Bd.connect();
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();


			while(rs.next()) {
				Cama c = new Cama();
				c.setCama_id(rs.getInt(1));
				c.setHospital_id(rs.getInt(2));
				c.setOcupado(rs.getBoolean(3));
				c.setHabilitado(rs.getBoolean(4));
				lista.add(c);
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
	public List<Cama> seleccionarByID_Hospital(int id) {
		String query = "SELECT * FROM cama where hospital_id = ? ";
		List<Cama> lista = new ArrayList<>();

		Connection conn = null;
		try
		{
			conn = Bd.connect();
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();


			while(rs.next()) {
				Cama c = new Cama();
				c.setCama_id(rs.getInt(1));
				c.setHospital_id(rs.getInt(2));
				c.setOcupado(rs.getBoolean(3));
				c.setHabilitado(rs.getBoolean(4));
				lista.add(c);
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
	public int ocupar_cama(Cama cama) throws SQLException {

		String SQL = "UPDATE cama SET ocupacion_cama = 'true' WHERE cama_id = ? AND ocupacion_cama = 'false'";

		Connection conn = null;
		int exito = 0;
		try
		{
			conn = Bd.connect();
			PreparedStatement pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, cama.getCama_id());

			int affectedRows = pstmt.executeUpdate();
			// check the affected rows
			if (affectedRows > 0) {
				exito = 1;
			}else{
				exito = 0;
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

		return exito;
	}
	public int eliminar_cama(Cama cama) throws SQLException {

		String SQL = "UPDATE cama SET habilitado_cama = 'false' WHERE cama_id = ?";

		Connection conn = null;
		int exito = 0;
		try
		{
			conn = Bd.connect();
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, cama.getCama_id());

			int affectedRows = pstmt.executeUpdate();
			// check the affected rows
			if (affectedRows > 0) {
				exito = 1;
			}else{
				exito = 0;
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

		return exito;
	}
	public int desocupar_cama(Cama cama) throws SQLException {

		String SQL = "UPDATE cama SET ocupacion_cama = 'false' WHERE cama_id = ? AND ocupacion_cama = 'true'";

		Connection conn = null;
		int exito = 0;
		try
		{
			conn = Bd.connect();
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, cama.getCama_id());

			int affectedRows = pstmt.executeUpdate();
			// check the affected rows
			if (affectedRows > 0) {
				exito = 1;
			}else{
				exito = 0;
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

		return exito;
	}
    public int insertar(Hospital hospital) throws SQLException {

        String SQL = "INSERT INTO cama(hospital_id, ocupacion_cama, habilitado_cama) "
                + "VALUES(?,?,?)";

        int id = 0;
        Connection conn = null;

        try
        {
        	conn = Bd.connect();
        	PreparedStatement pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, hospital.getId());
            pstmt.setBoolean(2, false); //ocupacion
            pstmt.setBoolean(3, true);  //habilitado
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
