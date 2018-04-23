package JavaFx1;

import javafx.application.Application;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
//import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class LoginForm extends Application{
	
	TextField txtUserId = new TextField();
	PasswordField txtPass = new PasswordField();
	Label lblMessage = new Label();
	public UserInterface theGUI;
	
	public final static double WINDOW_WIDTH = 800;
	public final static double WINDOW_HEIGHT = 600;
	//DatePicker datePicker = new DatePicker();
	
	
		
	public void UInterface(Pane theRoot)
	{	
        
       	
		Label lblHead = new Label("Login Form");
		
		Label lblUserId = new Label("User Id");
		Label lblPass = new Label("Password");
				
		
		Button btnSubmit = new Button("Submit");
		Button btnExit = new Button("Quit");
		
				
		lblHead.setLayoutX(300); lblHead.setLayoutY(50);
		lblHead.setFont(Font.font("Arial",24));
		
		
		lblUserId.setLayoutX(50); lblUserId.setLayoutY(100);
		lblPass.setLayoutX(50); lblPass.setLayoutY(150);
		
		txtUserId.setLayoutX(200); txtUserId.setLayoutY(100);
		txtPass.setLayoutX(200); txtPass.setLayoutY(150);
		
		btnSubmit.setLayoutX(150); btnSubmit.setLayoutY(300);
		
		btnSubmit.setOnAction((event) -> { submitAction(); });
		
		
		btnExit.setLayoutX(350); btnExit.setLayoutY(300);
		btnExit.setOnAction((event) -> { System.exit(0); });
		
		lblMessage.setLayoutX(200); lblMessage.setLayoutY(350);
		lblMessage.setFont(Font.font("Arial",24));
		
		//datePicker.setLayoutX(200); datePicker.setLayoutY(250);
		
		//datePicker.setOnAction((event) -> { datePickerAction(); });
		ComboBox <String> comboBox = new ComboBox<String>();
		comboBox.getItems().addAll("AD","Calc","Physics");
		comboBox.setLayoutX(230); comboBox.setLayoutY(200);
		comboBox.setPromptText("Subject");
		theRoot.getChildren().addAll(lblHead, lblUserId,lblPass,txtUserId,txtPass,
                btnSubmit,btnExit,lblMessage,comboBox);
		//,datePicker);
		//,);
		
		
			}
	
	
	
	
	@Override 
	   public void start(Stage stage) {
		
		stage.setTitle("Login Form");			// Label the stage (a window)
		
		Pane theRoot = new Pane();
		
        UInterface(theRoot);
		Scene scene = new Scene(theRoot,600,400); //Creating a scene object
	     
		stage.setScene(scene);    //Adding scene to the stage 
	      
	     stage.show();            //Displaying the contents of the stage
	}
	
	
	
	public static void main(String args[]){ 
	      launch(args); 
	   } 
	
	
	
	
	public void submitAction() {
		
		String checkUser = txtUserId.getText().toString();
		String  checkPw = txtPass.getText().toString();
		
		if(checkUser.equals("Jawahar") && checkPw.equals("8585") || 
				checkUser.equals("Mahesh") && checkPw.equals("170003")	){
		      lblMessage.setText("Congratulations!");
		      
		      lblMessage.setTextFill(Color.GREEN);
		      Stage theStage = new Stage();
		      theStage.setTitle("Effort Log Merge and Analysis Tool");				// Label the stage (a window)
				
			  Pane theRoot = new Pane();							// Create a pane within the window
				
			  theGUI = new UserInterface(theRoot);					// Create the Graphical User Interface
				
			  Scene theScene = new Scene(theRoot, WINDOW_WIDTH, WINDOW_HEIGHT);	// Create the scene
				
			  theStage.setScene(theScene);							// Set the scene on the stage
			
			  theStage.show();										// Show the stage to the user
				
		      
		      
		}
		else{
		    lblMessage.setText("Invalid User Id or Password.");
		    lblMessage.setTextFill(Color.RED);
		    }
		
	}} 
