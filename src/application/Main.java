/**
 * The Main class is the class that launches the banking application.
 * @author Joshua Atienza, Kyle Lee
 */
package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	
	/**
	 * Places UI controls in a scene and displays scene in stage
	 * @param primaryStage The stage to be generated and displayed in JavaFX
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("TransactionManager.fxml"));
			Scene scene = new Scene(root,600,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setTitle("Transaction Manager");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Launches the application 
	 * @param args Arguments passed by command line
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
