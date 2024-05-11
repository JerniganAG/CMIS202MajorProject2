// **********************************************
// Title:  Library Of Reigh
// Author: Alengta Jernigan
// Course Section: CMIS202-ONL1 (Seidel) Spring 2024
// File: EventMain.java
// Description: EventHandler the main menu buttons view, add,help, home, quit,queue save and add 
//Small collection of books sort by author's last name classify genere or number of pages
// **********************************************

package Lproject;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class EventMain {
	
	//references for other classes
	public ButtonView buttonView = new ButtonView();
	public ButtonAdd buttonAdd = new ButtonAdd();
	public ButtonHelp buttonHelp = new ButtonHelp();
	public ButtonHome buttonHome = new ButtonHome();
	public ButtonQuit buttonQuit = new ButtonQuit();
        public ButtonQAdd buttonQAdd = new ButtonQAdd();
	//public ButtonClean buttonClean = new ButtonClean();
        public ButtonQSave buttonQSave = new ButtonQSave();
        //public ButtonDes buttonDes = new ButtonDes();
	
    // EventHandler the view library buttons
	class ButtonView implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
                    try {
                        Reigh.stage.setScene(ViewLibraryOfReigh.buildLibraryScene());//reference viewlibraryofReigh
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(EventMain.class.getName()).log(Level.SEVERE, null, ex);
                    }
		}
	}
	
	//EventHandle the add to library buttons
	class ButtonAdd implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
                    try {
                        Reigh.stage.setScene(AddBookEntry.buildBookScene());//reference viewlibraryofReigh
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(EventMain.class.getName()).log(Level.SEVERE, null, ex);
                    }
		}
	}

	//EventHandle the help menu buttons
	class ButtonHelp implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
                    try {
                        Reigh.stage.setScene(Help.buildHelpScene());//reference viewlibraryofReigh
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(EventMain.class.getName()).log(Level.SEVERE, null, ex);
                    }
		}
	}
	
	//EventHandle the main menu buttons
	class ButtonHome implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
                    try {
                        Reigh.stage.setScene(Reigh.buildStartScene());//reference viewlibraryofReigh
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(EventMain.class.getName()).log(Level.SEVERE, null, ex);
                    }
		}
	}
	
	//EventHandle the quit buttons
	class ButtonQuit implements EventHandler<ActionEvent> {//reference viewlibraryofReigh
		@Override
		public void handle(ActionEvent e) {
			Platform.exit();
		}
	}
  
	/*//EventHandle the clean duplicates button
	class ButtonClean implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			ViewLibraryOfReigh.cleanDuplicates();
			Reigh.stage.setScene(ViewLibraryOfReigh.buildLibraryScene());
		}
	}*/
    //Button the add to queue button
	class ButtonQAdd implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			AddBookEntry.saveBookToQueue(AddBookEntry.bPane);//save the book to queue
		}
	}
	
	//Button the save from queue button
	class ButtonQSave implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			AddBookEntry.saveBooksInQueue(AddBookEntry.bPane);//Save the books in queue to file
		}
	}


}