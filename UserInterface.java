package JavaFx1;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.util.Callback;


/**
 * <p> Title: UserInterface Class. </p>
 * 
 * <p> Description: The Java/FX-based user interface for the Effort Log Merge and Analysis Tool. The class works with String
 * objects and passes work to other classes to deal with all other aspects of the computation.</p>

 * @author Jawahar
 * 
 * @version 1.0	2018-03-18   JavaFX-based GUI implementation of the merge and analysis tool.
 * 
 */

public class UserInterface {
	
	/**********************************************************************************************

	Attributes
	
	**********************************************************************************************/
	
	/* Constants used to parameterize the graphical user interface.  We do not use a layout manager for
	   this application. Rather we manually control the location of each graphical element for exact
	   control of the look and feel. */

	// These are the application values required by the user interface
	private Label lable_Tile = new Label("Effort Logger Merge and Analysis Tool");
	private Label lable_SourceDirectory = new Label("Source Directory");  
	private TextField text_SourceDirectory = new TextField();	
	private Button button_Browse = new Button("Browse");
	private Button button_ReportGenrate = new Button("Generate Analysis Report");
	private Button button_Quit = new Button("Quit");
	CheckBox checkBox_Label= new CheckBox("Select All");
	HBox hbox = new HBox(checkBox_Label);
	Stage primaryStage;
	String absolutePath ="";
	final ObservableList<FileList> data = FXCollections.observableArrayList();
	final ArrayList<String> names = new ArrayList<String>();
	ListView<FileList> listView = new ListView<FileList>();
	ArrayList<String> str = new ArrayList<String>(); // Store the names of the selected files
		
	
	/********
	 * This below code snippet is for selecting and deselecting a particular checklist items
	 */
	ChangeListener<Boolean> listener = new ChangeListener<Boolean>() {
		@Override
		public void changed(ObservableValue<? extends Boolean> paramObservableValue, Boolean paramT1, Boolean selected) {
			if(selected){
				boolean flag = true;
				for (FileList fList : data) {
					if(!fList.getSelected()){
						flag = false;
						break;
					}
				}
				if(flag){
					checkBox_Label.setSelected(true);
				}
			}
			else{
				checkBox_Label.setSelected(false);
			}
		}
	};
	
	
	
	/**********************************************************************************************

	Constructors
	
	**********************************************************************************************/

	/**********
	 * This method initializes all of the elements of the graphical user interface. These assignments
	 * determine the location, size, font and change and event handlers for each GUI object.
	 */
	public UserInterface(Pane theRoot) {
		// Setting up the Layout for 'Select All' check box.
		hbox.setLayoutX(100);
		hbox.setLayoutY(125);
		// Setting up the size and position of the listView.
		listView.setLayoutX(100);
		listView.setLayoutY(150);
		listView.setMaxSize(600, 170);
		
		/********
		 * This below code snippet is for selecting and deselecting a particular checklist items
		 */
			checkBox_Label.selectedProperty().addListener(new ChangeListener<Boolean>() {

				@Override
				public void changed(ObservableValue<? extends Boolean> paramObservableValue, Boolean paramT1, Boolean paramT2) {
					
					if(paramT2){   
						
						for (FileList fList : data)							
							fList.setSelected(true);
					}
					else{
							for (FileList fList : data) 
								fList.setSelected(false);							
						}
				}
			});
			
			for (FileList file : data) {
				file.selectedProperty().addListener(listener);
			}
			listView.setItems(data);

			Callback<FileList, ObservableValue<Boolean>> getProperty = new Callback<FileList, ObservableValue<Boolean>>() {
				@Override
				public BooleanProperty call(FileList layer) {

					return layer.selectedProperty();

				}
			};
			
			Callback<ListView<FileList>, ListCell<FileList>> forListView = CheckBoxListCell.forListView(getProperty);
			listView.setCellFactory(forListView);
		
		
		// Label theScene with the name of the project, centered at the top of the pane
		setupLabelUI(lable_Tile, "Arial", 24, LoginForm.WINDOW_WIDTH, Pos.CENTER, 0, 10);
		
		// Label the text field of the source directory just above it, left aligned
		setupLabelUI(lable_SourceDirectory, "Arial", 14, LoginForm.WINDOW_WIDTH, Pos.BASELINE_LEFT, 10, 80);
		
		// Establish the first text input source directory field.
		setupTextUI(text_SourceDirectory, "Arial", 14, 300, Pos.BASELINE_LEFT, 122, 80, true);
			
					
		// Establish theBrowse button, position it, and link it to methods to accomplish its work
		setupButtonUI(button_Browse, "Symbol", 12, 14, Pos.BASELINE_LEFT, 425, 80);
		button_Browse.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	DirectoryChooser directoryChooser = new DirectoryChooser();
                File selectedDirectory = directoryChooser.showDialog(primaryStage);
                 
                if(selectedDirectory != null){
                	
                    text_SourceDirectory.setText(selectedDirectory.getAbsolutePath());
                    absolutePath = selectedDirectory.getAbsolutePath();
                }
                
                // Checking for the .xlsx files in a selected source directory
                List <String> fileNamesList = new ArrayList <String> ();
        		try {
        			Files.newDirectoryStream(Paths.get(absolutePath),
        					path -> path.toString().endsWith(".xlsx")).forEach(filePath -> fileNamesList.add(filePath.toString()));
        		}catch (IOException e) {    
        			e.printStackTrace();
        		}

        		for (String name : fileNamesList) {
        			data.add(new FileList(false, name.substring(absolutePath.length()+1))); // Adding files name in the listView
        		}
            }
        });
				
		// Establish the Report Genrate Button button, position it, and link it to methods to accomplish its work
		setupButtonUI(button_ReportGenrate, "Symbol", 20, 20, Pos.BASELINE_LEFT, 10, LoginForm.WINDOW_HEIGHT-50);
		button_ReportGenrate.setOnAction(new EventHandler<ActionEvent>() {
			
			
			/*********
			 * This code takes out the names of the files which are to be merged 
			 * 
			 */
			
			public void handle(ActionEvent paramT) {
			for (FileList file : data) {
				
				if(file.getSelected()) {
				str.add(file.getName());
				
				}
		    
			}  
			
	
			
			Runtime rs = Runtime.getRuntime();
			 
		    try {
		      rs.exec("Merged files.xlsx");
		    }
		    catch (IOException e) {
		    }   
		}
		
			
		});
		
		// Establish the Quit button, position it, and link it to methods to accomplish its work
		setupButtonUI(button_Quit, "Symbol", 20, 20, Pos.BASELINE_LEFT, LoginForm.WINDOW_WIDTH -75, LoginForm.WINDOW_HEIGHT- 50);
		button_Quit.setOnAction((event) -> { System.exit(0); });
		
			
		// Place all of the just-initialized GUI elements into the pane
		
		theRoot.getChildren().addAll(lable_Tile, lable_SourceDirectory, text_SourceDirectory, button_Browse, button_ReportGenrate, button_Quit,
				listView, hbox);
				
	}
		
	/**********
	 * Private local method to initialize the standard fields for a label
	 */
	private void setupLabelUI(Label l, String ff, double f, double w, Pos p, double x, double y){
		l.setFont(Font.font(ff, f));
		l.setMinWidth(w);
		l.setAlignment(p);
		l.setLayoutX(x);
		l.setLayoutY(y);		
	}
	
	/**********
	 * Private local method to initialize the standard fields for a text field
	 */
	private void setupTextUI(TextField t, String ff, double f, double w, Pos p, double x, double y, boolean e){
		t.setFont(Font.font(ff, f));
		t.setMinWidth(w);
		t.setMaxWidth(w);
		t.setAlignment(p);
		t.setLayoutX(x);
		t.setLayoutY(y);		
		t.setEditable(e);
	}
	
	/**********
	 * Private local method to initialize the standard fields for a button
	 */
	private void setupButtonUI(Button b, String ff, double f, double w, Pos p, double x, double y){
		b.setFont(Font.font(ff, f));
		b.setMinWidth(w);
		b.setAlignment(p);
		b.setLayoutX(x);
		b.setLayoutY(y);		
	}
	
	/***********
	 * This Class defines the listers for the checkbox list, the lister for the selecting and deselecting
	 * the checkbox items are, as per the below code segment.
	 */
	
	class FileList {
		private final SimpleBooleanProperty selected;
		private final SimpleStringProperty name;

		public FileList(boolean id, String name) {
			this.selected = new SimpleBooleanProperty(id);
			this.name = new SimpleStringProperty(name);
		}

		public boolean getSelected() {
			return selected.get();
		}

		public void setSelected(boolean selected) {
			this.selected.set(selected);
		}

		public String getName() {
			return name.get();
		}

		public void setName(String fName) {
			name.set(fName);
		}

		public SimpleBooleanProperty selectedProperty() {
			return selected;
		}

		@Override
		public String toString() {
			return getName();
		}
	}

}

