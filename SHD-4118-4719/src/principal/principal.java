package principal;

import java.io.IOException;

import entidades.carrera;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class principal extends Application {

	@Override
	public void start(Stage primaryStage) throws IOException {
		
		Parent root = FXMLLoader.load(getClass().getResource("../vista/fxml/Menu.fxml"));
		Scene escena = new Scene(root);
		primaryStage.setTitle("Menu");
		escena.getStylesheets().add(principal.class.getResource("stilos.css").toExternalForm());
		primaryStage.setScene(escena);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	@FXML protected void Salir()
	{
		System.exit(0);
	}
	
	@FXML protected void clickPersona(ActionEvent e) throws IOException{
		Stage primaryStage = new Stage();
	
		Parent root = FXMLLoader.load(getClass().getResource("../vista/fxml/Carrera.fxml"));
		Scene escena = new Scene(root);
		primaryStage.setTitle("Carrera");
		escena.getStylesheets().add(principal.class.getResource("stilos.css").toExternalForm());
		primaryStage.setScene(escena);
		primaryStage.initModality(Modality.WINDOW_MODAL);
		primaryStage.initOwner(((Node)e.getSource()).getScene().getWindow());
		primaryStage.show();
	}
	@FXML protected void clickAlumno(ActionEvent e) throws IOException{
		Stage primaryStage = new Stage();
	
		Parent root = FXMLLoader.load(getClass().getResource("../vista/fxml/ventanAlumno.fxml"));
		Scene escena = new Scene(root);
		primaryStage.setTitle("Alumno");
		escena.getStylesheets().add(principal.class.getResource("stilos.css").toExternalForm());
		primaryStage.setScene(escena);
		primaryStage.initModality(Modality.WINDOW_MODAL);
		primaryStage.initOwner(((Node)e.getSource()).getScene().getWindow());
		primaryStage.show();
	}

}

