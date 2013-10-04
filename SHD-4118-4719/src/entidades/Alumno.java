package entidades;
//aqui estoy
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import modelo.conexion;

public class Alumno {
	private String nombre, apaterno, amaterno, fechaNacimiento, sexo;
	private carrera carrera;
	private Integer idAlumno;
	//private conexion miConexion;
	//esta no va
	public Alumno(){	this.nombre="";
		this.apaterno="";
		this.amaterno="";
		this.fechaNacimiento="";
		this.sexo="";
		this.carrera=new carrera();
		this.miConexion = new conexion();

	}
	public Alumno(String _nombre, String _apaterno, String _amaterno,
			String _fechaNacimiento, String _sexo, carrera _carrera) {
		this.nombre = _nombre;
		this.apaterno = _apaterno;
		this.amaterno = _amaterno;
		this.fechaNacimiento = _fechaNacimiento;
		this.sexo = _sexo;
		this.carrera = _carrera;
		this.miConexion = new conexion();

	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApaterno() {
		return apaterno;
	}

	public void setApaterno(String apaterno) {
		this.apaterno = apaterno;
	}

	public String getAmaterno() {
		return amaterno;
	}

	public void setAmaterno(String amaterno) {
		this.amaterno = amaterno;
	}

	public String getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(String fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public carrera getCarrera() {
		return carrera;
	}

	public void setCarrera(carrera carrera) {
		this.carrera = carrera;
	}
	public Integer getIdAlumno() {
		return idAlumno;
	}
	public void setIdAlumno(Integer idAlumno) {
		this.idAlumno = idAlumno;
	}
	public conexion getMiConexion() {
		return miConexion;
	}
	public void setMiConexion(conexion miConexion) {
		this.miConexion = miConexion;
	}
	public String guardar(String comboCarrera){
		String mensaje="";
		Integer idCarrera=0;
		try{
			if(miConexion.conectar()==true){
				String consultaID= "SELECT id from Carrera where siglas='"+comboCarrera+"'";
				PreparedStatement coman = miConexion.getConexion().prepareStatement(consultaID);
				ResultSet res = coman.executeQuery();
				
				while(res.next()){
					idCarrera = res.getInt("id");
				}
				String consulta ="INSERT INTO alumno VALUES(default, ?, ?, ?, ?, ?, ?)";
				PreparedStatement comando = miConexion.getConexion().prepareStatement(consulta);
				comando.setString(1,this.nombre);
				comando.setString(2, this.apaterno);
				comando.setString(3, this.amaterno);
				comando.setString(4, this.fechaNacimiento);
				comando.setString(5, this.sexo);
				comando.setInt(6, idCarrera);
				comando.executeUpdate();
				mensaje="Datos insertados con exito.";
				System.out.println(mensaje);
			}
		}catch(Exception e){
			mensaje="No se insertaron los datos.";
			System.out.println(e.getMessage());
		}finally{
			miConexion.desconectar();
		}
		return mensaje;
	}
	public String Eliminar(int id)
	{
		String mensaje="";
		try 
		{
			if(miConexion.conectar()==true)
			{
				String Eliminar="delete from alumno where idalumno = "+id;
				PreparedStatement comando = miConexion.getConexion().prepareStatement(Eliminar);
				comando.executeUpdate();
				mensaje="Datos eliminados corretamente";
			}
		} 
		catch (Exception e) 
		{
			mensaje="No se pudieron eliminar los datos";
			e.printStackTrace();
		}
		finally
		{
			miConexion.desconectar();
		}
		return mensaje;
	}
	
	
	
	public ObservableList<Alumno> listarDatos(){
		try{
			ObservableList<Alumno> lista = FXCollections.observableArrayList();
			if(miConexion.conectar()){
				String consulta = "SELECT * FROM alumno";
				Statement comando = miConexion.getConexion().createStatement();
				ResultSet resultado = comando.executeQuery(consulta);
				if(resultado!=null){
					while(resultado.next()){
						Alumno a = new Alumno();
						a.setIdAlumno(resultado.getInt("idAlumno"));
						a.setNombre(resultado.getString("nombre"));
						a.setApaterno(resultado.getString("apaterno"));
						a.setAmaterno(resultado.getString("amaterno"));
						a.setFechaNacimiento(resultado.getString("fechaNacimiento"));
						a.setSexo(resultado.getString("sexo"));
						//a.setCarrera(resultado.getString("idCarrera"));
						lista.add(a);
					}
				}
				resultado.close();
			}
			return lista;
		}catch(Exception ex){
			System.out.println(ex.getMessage());
			return null;
		}finally{
			miConexion.desconectar();
		}
	}
	public String modificar(){
		String mensaje="";
		try{
		if(miConexion.conectar()==true){
			String consulta = "UPDATE alumno set nombre=?, apaterno=?, amaterno=?, fechaNacimiento=?, sexo=? WHERE idAlumno="+this.getIdAlumno();
			PreparedStatement comando = miConexion.getConexion().prepareStatement(consulta);
			comando.setString(1,this.nombre);
			comando.setString(2, this.apaterno);
			comando.setString(3, this.amaterno);
			comando.setString(4, this.fechaNacimiento);
			comando.setString(5, this.sexo);
			//comando.setString(6, this.carrera);
			comando.executeUpdate();
			mensaje = "Datos modificados con exito";
		}else{
			mensaje = "No se modificaron los datos";
		}
		}catch(Exception e){
			e.getMessage();
		}
		return mensaje;
	}
	
}
