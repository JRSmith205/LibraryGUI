package application;

import java.util.ArrayList;
import java.io.*;



//this class will contain the methods of the library. It will derive some of the methods from the MediaItem class --> We'll call on this class though with the LibraryGUI class
public class LibraryClass {
	
	private ArrayList<MediaItem> items;
	
	public LibraryClass(){
		this.items = new ArrayList<>();//change from an array to array list so we can save the library object in the LibraryGUI class
	}
	
	public void addNewItem(String title, String format) {
		MediaItem newItem = new MediaItem(title, format);//we'll put this in the ArrayList, I believe
		items.add(newItem);
		
	}

	public void markLoaned(String title, String name, String date) {
		for(MediaItem searchItem : items) {//will check the ArrayList items for an object matching the name of the searchItem that will be enetered in the textfield -> if it matches, do...
			if (searchItem.getTitle().equalsIgnoreCase(title)) {
				searchItem.markLoanedOut(name, date);//this will give the searchItem object the onLoan status and will hopefully prevent others from taking it on loan
				return;
			}
		}
	}
	
	public void markReturned (String title, String date) {
		for(MediaItem searchItem : items) {
			if(searchItem.getTitle().equalsIgnoreCase(title)) {
				searchItem.markReturnedIn(date);//this will switch the onLoan status for the item back to false and making it available again!
				System.out.println("Thank you for returning " + searchItem.getTitle());
				return;
			}
		}
		
	}
	
	 public ArrayList<MediaItem> listAllItems() {//this is used to list all items when called up -> okay wait, do I need this??
	        return items;
	    }

	
	public void toFile(String fileName) {//have to open the file stream somehow and push this info (the ArrayList<> items) through --> DON'T FORGET TO CLOSE THE FILE STREAM AFTER USING IT!!!
		try {
			FileOutputStream fileOutput = new FileOutputStream(fileName);
			ObjectOutputStream objectOut = new ObjectOutputStream(fileOutput);//put the fileOutput object into the objectOutput -> put the arraylist into the objectOutput
			objectOut.writeObject(items);
		}
		catch(IOException ex) {
			ex.printStackTrace();
		}
	}
	
	public void fromFile(String fileName) throws ClassNotFoundException {//why do i need a throw exception here, but not in the toFile method?
		try {
			FileInputStream fileIn = new FileInputStream(fileName);
			ObjectInputStream objectIn = new ObjectInputStream(fileIn); //getting the info from the fileIn 
			//what am i missing here???? --> okay, i think I got it?
			items = (ArrayList<MediaItem>)objectIn.readObject();
		}
		catch(IOException ex) {
			ex.printStackTrace();
		}
		
	}


	

	

	

	}
