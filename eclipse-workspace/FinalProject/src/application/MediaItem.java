package application;

//use this class as the storage area for the libraryClass essentially
//okay, so we need to make this serialized?!?! Follow the basic structure with this link (scroll down to William Kinaan) https://stackoverflow.com/questions/3429921/what-does-serializable-mean
public class MediaItem implements java.io.Serializable {

	private String title;
	private String format;
	private static boolean loanedOut;
	private String markReturned;
	static boolean onLoan;
	static String loanedTo;
	static String dateLoaned;
	


	public MediaItem() {
		this.title = title;
        this.format = format;
        this.onLoan = false;
        this.loanedTo = null;
        this.dateLoaned = null;
	}
	
	public MediaItem(String title, String format) {
		this.title = title;
        this.format = format;
        this.onLoan = false;
        this.loanedTo = null;
        this.dateLoaned = null;
	}

	//use this to set the title to whatever the user sets it to
	public String setTitle(String title) {
		this.title = title;
		return title;
		
	}
	
	//use to return the title when prompted
	String getTitle() {
		return title;
	}
	
	//use to set the format type of the item to whatever the user sets it to
	public String setItemFormat(String itemFormat) {
		this.format = format;
		return format;
	}
	
	//return the item format when prompted
	String getItemFormat() {
		return format;
	}
	
	//this used to set if the status of the item is marked as loan 
	boolean setLoanedOut(boolean loanedOut) {
		this.loanedOut = loanedOut;
		return loanedOut;
	}
	
	public static boolean getLoanedOut() {
		return loanedOut;
	}
	
	public String setDateLoaned(String dateLoaned) {
		this.dateLoaned = dateLoaned;
		return dateLoaned;
	}
	
	public String getDateLoaned() {
		return dateLoaned;
	}
	
	public String setLoanedTo(String loanedTo) {
		this.loanedTo = loanedTo;
		return loanedTo;
	}
	
	public String getLoanedTo() {
		return loanedTo;
	}
	
	public static boolean isOnLoan() {
		return onLoan;
	}
	public void setOnLoan(boolean onLoan) {
		this.onLoan = onLoan;
	}


	void markLoanedOut(String name, String date) {
		if (onLoan) {
			System.out.println("We're sorry, this is already being loaned out. ");
		}
		else {
			onLoan = true;
			loanedTo = name;
			dateLoaned = date;
			System.out.println("Item is marked on loan. ");}
		
	}
	
	void markReturnedIn (String date) {
		if (!onLoan) {//if the item doesn't have the onLoan flag, it will trigger this
			System.out.println("This item is not currently on loan. ");
		}
		else { 
		onLoan = false;
		dateLoaned = date;
		System.out.print("Item has been returned on " + dateLoaned + "\n");
			
		}
	}
	
	@Override
	public String toString() {
		String status = onLoan ? "Loaned to " + loanedTo + " on " + dateLoaned : "Available";
		return "Title: " + title + "\nFormat: " + format + "\nStatus: " + status;
	}
	
}
