package vista.controlador;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import modelo.conexion;

import entidades.Alumno;
import entidades.carrera;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class controladorAlumno implements Initializable{
	ObservableList<carrera> datos;
	conexion con = new conexion();
	@FXML private TextField txtNombre;
	@FXML private TextField txtPaterno;
	@FXML private TextField txtMaterno;
	@FXML private TextField txtFecha;
	@FXML private ComboBox<String> cboSexo;
	@FXML private ComboBox<carrera> cboCarrera;
	@FXML private Label lblMensajes;
	@FXML private TableView<Alumno> tbAlumno;
	@FXML private Button btnEliminar;
	
	Alumno alumnoModificado= new Alumno();
	public void initialize(URL arg0, ResourceBundle arg1) {
		con.conectar();
		 datos= FXCollections.observableArrayList(
		            new carrera()
		            );
		 String nom="";
		 ResultSet result = null;
		 if(con.conectar()==true)
			{
				String query="select siglas from carrera where activo='0'";
				PreparedStatement comando = null;
				try {
					comando = con.getConexion().prepareStatement(query);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					result=comando.executeQuery();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					while(result.next())
					{
						nom=result.getString("siglas");
						System.out.println(nom);
						datos.add(new carrera(nom.toString()));
					
					   
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
		cboCarrera.setItems(datos);
		TableColumn<Alumno, String> colNombre = new TableColumn<Alumno, String>("Nombre");
		colNombre.setCellValueFactory(new PropertyValueFactory<Alumno, String>("nombre"));
		TableColumn<Alumno, String> colPaterno = new TableColumn<Alumno, String>("Paterno");
		colPaterno.setCellValueFactory(new PropertyValueFactory<Alumno, String>("apaterno"));
		TableColumn<Alumno, String> colMaterno = new TableColumn<Alumno, String>("Materno");
		colMaterno.setCellValueFactory(new PropertyValueFactory<Alumno, String>("amaterno"));
		TableColumn<Alumno, String> colFecha = new TableColumn<Alumno, String>("Fecha Nac.");
		colFecha.setCellValueFactory(new PropertyValueFactory<Alumno, String>("fechaNacimiento"));
		TableColumn<Alumno, String> colSexo = new TableColumn<Alumno, String>("Sexo");
		colSexo.setCellValueFactory(new PropertyValueFactory<Alumno, String>("sexo"));
		TableColumn<Alumno, String> colCarrera = new TableColumn<Alumno, String>("Carrera");
		colCarrera.setCellValueFactory(new PropertyValueFactory<Alumno, String>("carrera"));
		tbAlumno.getColumns().addAll(colNombre, colPaterno, colMaterno, colFecha, colSexo, colCarrera);
		this.llenarTabla();
		tbAlumno.setOnMouseClicked(new tabla());
		 
	}
	
	}
@FXML protected void registrar(){
		
		try{
			if(txtNombre.getText().trim().isEmpty() 
					| txtPaterno.getText().trim().isEmpty() 
					| txtMaterno.getText().trim().isEmpty() 
					| txtFecha.getText().trim().isEmpty()){
				lblMensajes.setText("Faltan datos por ingresar");
			}else{
				Alumno a = new Alumno(txtNombre.getText(), txtPaterno.getText(), txtMaterno.getText(), txtFecha.getText(), cboSexo.getValue(), cboCarrera.getValue());
				String idCarrera = cboCarrera.getValue().toString();
				lblMensajes.setText(a.guardar(idCarrera));
				this.llenarTabla();
				limpiar();
			}
		}catch(Exception e){
			e.getMessage();
		}
	}
	
	@FXML protected void Eliminar()
	{
	try 
	{
		
			con.conectar();
			int id=0;
			String sql = "select idalumno from alumno where fechanacimiento ='"+txtFecha.getText()+"'";
			System.out.println(sql);
			PreparedStatement com = con.getConexion().prepareStatement(sql);
			ResultSet r = com.executeQuery();
			while(r.next()){
				id=r.getInt("idalumno");
			}
			Alumno c = new Alumno(txtNombre.getText(),txtPaterno.getText(),
					txtMaterno.getText(),txtFecha.getText(), cboSexo.getValue(),cboCarrera.getValue());
			c.Eliminar(id);
			llenarTabla();
			this.limpiar();
			
		
	} 
	catch (Exception e) 
	{
		e.printStackTrace();
	}
}

	

	@FXML protected void actualizar(){
		try{
			if(alumnoModificado!=null){
				if(txtNombre.getText().trim().isEmpty() | 
						txtPaterno.getText().trim().isEmpty() | 
						txtMaterno.getText().trim().isEmpty() | 
						txtFecha.getText().trim().isEmpty()) {
					lblMensajes.setText("Faltan datos por ingresar.");
				}else{
					carrera nombre =cboCarrera.getValue();
					Alumno miAlumno = new Alumno(txtNombre.getText(), 
							txtPaterno.getText(), 
							txtMaterno.getText(),
							txtFecha.getText(),
							cboSexo.getValue(),
							nombre);
							miAlumno.setIdAlumno(alumnoModificado.getIdAlumno());
					miAlumno.modificar();
					alumnoModificado=null;
					this.llenarTabla();
					limpiar();
					
				}
			}else{
				lblMensajes.setText("No se seleccionaron datos.");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void llenarTabla(){
		Alumno a = new Alumno();
		ObservableList<Alumno> lista = a.listarDatos();
		tbAlumno.setItems(lista);
		tbAlumno.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	}
	public class tabla implements EventHandler<MouseEvent>{

		@Override
		public void handle(MouseEvent event) {
			if(event.getClickCount()==2){
				alumnoModificado = tbAlumno.getSelectionModel().getSelectedItem();
				if(alumnoModificado!=null){
					txtNombre.setText(alumnoModificado.getNombre());
					txtPaterno.setText(alumnoModificado.getApaterno());
					txtMaterno.setText(alumnoModificado.getAmaterno());
					txtFecha.setText(alumnoModificado.getFechaNacimiento());
					cboSexo.setValue(alumnoModificado.getSexo());
					cboCarrera.setValue(alumnoModificado.getCarrera());
				}
			}
		}
	}
	public void limpiar(){
		txtNombre.setText("");
		txtMaterno.setText("");
		txtPaterno.setText("");
		txtFecha.setText("");
	}
}
