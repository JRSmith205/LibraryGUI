package application;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

//use this class to create the user interface and call up the other classes to this one to execute the GUI
public class LibraryGUI extends Application {
	
	//difference between ArrayList and the ObservableList https://stackoverflow.com/questions/41920217/what-is-the-difference-between-arraylist-and-observablelist
	private LibraryClass itemObject = new LibraryClass();//we'll use this as the LibraryClass object
	private ObservableList<MediaItem> observableList = FXCollections.<MediaItem>observableArrayList(itemObject.listAllItems());//okay, i got this right the first time - used this link (Scroll to James_D) https://stackoverflow.com/questions/26195243/creating-an-observable-list-collection
	
	@Override
	public void start (Stage primaryStage) {	
		
		TextField itemTitle = new TextField();
		itemTitle.setPromptText("Title");
		TextField itemFormat = new TextField();
		itemFormat.setPromptText("Format");
		TextField itemDate = new TextField();
		itemDate.setPromptText("Date");
		
		
		
		ListView<MediaItem>listView = new ListView<>(observableList);//create the listview object to be able to show the list from the array

		//WHY IS IT NOT SHOWING THE OBSERVABLE LIST BEFORE I PRESS A BUTTON - DO I NEED AN ACTION WHEN THE WINDOW OPENS??
		
		Button btAddInventory = new Button("Add Inventory");
		btAddInventory.setOnAction(e ->{
			String title = itemTitle.getText();
			String format = itemFormat.getText();
	
			if(title.isEmpty() == false && format.isEmpty() == false) {//if both textfields are occupied
				itemObject.addNewItem(title, format);
				observableList.setAll(itemObject.listAllItems());
				itemTitle.clear();
				itemFormat.clear();
			}
			
		});
		
		Button btDelete = new Button("Delete Item");
		btDelete.setOnAction(e ->{
			MediaItem deleteItem = listView.getSelectionModel().getSelectedItem();//object to identify whatever item and delete it from the arraylist
			itemObject.listAllItems().remove(deleteItem);
			observableList.setAll(itemObject.listAllItems());
			
			
			itemTitle.clear();
			itemFormat.clear();
			
			
			
		});
		
		//now it's not letting me check out individual items. It checks everything out and makes it unavailable at once -> Had the same problem with the midterm. COME BACK TO THIS!
		//It's also not saving the status when closing the program
		Button btCheckOut = new Button("Check Out");
		btCheckOut.setOnAction(e -> {
			
			MediaItem loanOut = listView.getSelectionModel().getSelectedItem();//whatever we select, that title will be linked with the itemObject.markLoaned below
			if(loanOut != null) {//saying if there's nothing there, it won't execute this button
				TextInputDialog dialog = new TextInputDialog();
				dialog.setTitle("Loan Item");
				dialog.setContentText("Name of person and today's date (name, date): ");
				dialog.showAndWait().ifPresent(result -> {
					String[]nameAndDate = result.split(",");//like in assignment 10, we have to find the comma and remove it in case the user includes a comma
					if (nameAndDate.length == 2) {
						itemObject.markLoaned(loanOut.getTitle(), nameAndDate[0], nameAndDate[1]);
						observableList.setAll(itemObject.listAllItems());
						System.out.println("Checked Out");
					
					}
				});
			}
		});
		
		//having same issue as the check out 
		Button btCheckIn = new Button("Check In");
		btCheckIn.setOnAction(e ->{
			
			MediaItem checkIn = listView.getSelectionModel().getSelectedItem();
			if (checkIn != null) {
				TextInputDialog dialog = new TextInputDialog();
				dialog.setTitle("Return Item");
				dialog.setContentText("Date item was returned: ");
				dialog.showAndWait().ifPresent(result -> {
					
					String date = result;
					itemObject.markReturned(checkIn.getTitle(), date);//IT'S NOT CHANGING. WHY??????
					observableList.setAll(itemObject.listAllItems());
					System.out.println("Checked In");
					
				});
				
			}
			
		});

		Button btListAll = new Button("View All Items");
		btListAll.setOnAction(e ->{
			
			observableList.setAll(itemObject.listAllItems());
			itemTitle.clear();//don't technically need these here, but it'd be good in case
			itemFormat.clear();
		});
		
		HBox hBox = new HBox(25, btAddInventory,btDelete, btCheckOut, btCheckIn, btListAll);
		hBox.setPadding(new Insets(10,10,10,10));
		hBox.setAlignment(Pos.BOTTOM_CENTER);
		VBox vBox = new VBox(listView, itemTitle, itemFormat, hBox);
		vBox.setPadding(new Insets(25,25, 0,25));
		
		//add to scene and stage
		Scene scene = new Scene(vBox, 400, 300);
		primaryStage.setTitle("Library");
		primaryStage.setScene(scene);
		primaryStage.show();
		
		primaryStage.setOnCloseRequest(e -> itemObject.toFile("libraryFile.txt"));//this creates the file - now we have to link it with the ArrayList (had help from my Brother getting this to work)
		try {
			itemObject.fromFile("libraryFile.txt");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		
	}
	
	
	public static void main(String[] args) {

		Application.launch(args);
	}

}
