// **********************************************
// Title:  Library Of Reigh
// Author: Alengta Jernigan
// Course Section: CMIS202-ONL1 (Seidel) Spring 2024
// File: Reigh.java
// Description: Arraylist,megasort
//Small collection of books sort by author's last name classify genere or number of pages (library display)
// **********************************************

package Lproject;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

//Class to view books
public class ViewLibraryOfReigh {//reference eventmain.java
	private static DisplayBL<String> outline = new DisplayBL<String>();
	private static DisplayBL<Integer> intoutline = new DisplayBL<Integer>();
        private static String bookPath = "src\\libraryofreigh.txt";//reference to file path
	private static ArrayList<Book> bookArray;
	
	//build the scene for displaying books
	public static Scene buildLibraryScene() throws FileNotFoundException {//reference eventmain.java
		//create the arraylist of type Book to store books from file
		int listSize = 0;
		try {
                    //cleanDuplicates(); //clean the file of duplicates before building ui

			bookArray = createLibrary();
			listSize = bookArray.size();
		} catch (NullPointerException e){
			outline.DisplayErrorMessage(e.toString());
		}
		
		//sort the arraylist
		mergeSort(0, listSize - 1);
		
                //build ScrollPane so that if large enough the user can scroll through their books
		ScrollPane sPane = new ScrollPane(buildLibrary());
		sPane.setFitToHeight(true);
		sPane.setFitToWidth(true);
                
		//create borderpane
		BorderPane bPane = new BorderPane();
                bPane.setTop(outline.DisplaySimpleHBox("Book Information"));
		bPane.setCenter(sPane);
		bPane.setBottom(eventBox());
		
		//create scene using borderpane
		Scene scene = new Scene(bPane);
		return scene;
	}
	
	//Assemble the arraylist
	public static ArrayList<Book> createLibrary() {
		try {
			File file = new File(bookPath);
			Scanner scan = new Scanner(file);
			
			//book list using generics to make arraylist of type Book
			ArrayList<Book> bookList  = new ArrayList<Book>();
			ArrayList<Book> returnList = buildList(scan, bookList);
			
			return returnList;
		} catch (Exception e) {
			outline.DisplayErrorMessage(e.toString());
		}
		return null;
	}
	
	//add each line from file to a spot on the arraylist
	public static ArrayList<Book> buildList(Scanner scan, ArrayList<Book> list) {//textfile layout
		//Scan for next book	
		while (scan.hasNextLine()) {
			//Create tokenizer
			String temp = (String) scan.nextLine();
			if (temp.length() < 1) {
				System.out.println("next line empty");
				break;
			}
			StringTokenizer str = new StringTokenizer((String)temp, "|");
			
			//make a new book
			Book book = new Book();
			
			//separate tokens
			for (int i = 0; str.hasMoreTokens(); i++) {
				if (i == 0) {
					book.setTitle(str.nextToken());//get title of book
				}
				else if (i == 1) {
					book.setAuthor(str.nextToken());//get author name
				}
				else if (i == 2) {
					book.setGenre(str.nextToken());//get genre
				}
				else {
					book.setPageCount(Integer.parseInt(str.nextToken()));//get page count
				}
			}
			//Add assembled book to list
			list.add(book);
		}
		return list;
	}
	
	//Comments on merge sort efficiency
	/*
	 The way that merge sort works is by taking on a divide and conquer type approach. It recursively breaks down a problem into two or more 
	 sub-problems. After the two halves are sorted, the algorithm then merges them. 
	 
	 The space complexity for merge sort is O(n) but shouldn't be too relevant becuase users will not have large enough library 
	 for this to be relevant.
	 
	 The time complexity is the O(nLogn) in all cases.As the user libraries grow larger the complexity will 
	 not increase and will remain consistent. Eventually the program will slow down as libraries get larges but for small library will be ok.
        If a users library were to be extremely large, merge sort would be more efficient than; for example quick sort. This is because 
	 the worst case for merge sort is O(nLogn), whereas quick sorts is O(n).
	 */
	
	//starts the sorting process
	public static void mergeSort(int startIndex, int endIndex) {
		if (startIndex < endIndex && (endIndex - startIndex) >= 1) {
			//separate the halves
			int midPoint = (endIndex + startIndex) / 2;

			mergeSort(startIndex, midPoint);
			mergeSort(midPoint + 1, endIndex);
			
			//merge the indexes into one
			merge(startIndex, midPoint, endIndex);
		}
	}
	
	//puts the arraylist back together
	public static void merge(int startIndex, int midPoint, int endIndex) {
		
		ArrayList<Book> mergeSortedArray = new ArrayList<Book>();
		
		int leftIndex = startIndex;
		int rightIndex = midPoint + 1;
		
		while(leftIndex <= midPoint && rightIndex <= endIndex) {

			if (bookArray.get(leftIndex).getLastName().charAt(0) <= bookArray.get(rightIndex).getLastName().charAt(0)) {
				mergeSortedArray.add(bookArray.get(leftIndex));
				leftIndex++;
			} else {
				mergeSortedArray.add(bookArray.get(rightIndex));
				rightIndex++;
			}
		}
		
		//One while loop below will execute
		while(leftIndex <= midPoint) {
			mergeSortedArray.add(bookArray.get(leftIndex));
			leftIndex++;
		}
		
		while(rightIndex <= endIndex) {
			mergeSortedArray.add(bookArray.get(rightIndex));
			rightIndex++;
		}

		int i = 0;
		int k = startIndex;
		while (i < mergeSortedArray.size()) {
			bookArray.set(k, (Book) mergeSortedArray.get(i++));
			k++;
		}
	}
	
	//Build UI library button scene
	private static HBox buildLibrary() {
		//establish boxes
		HBox hBox = new HBox();
		VBox titleBox = new VBox();
		VBox authorBox = new VBox();
		VBox genreBox = new VBox();
		VBox pageBox = new VBox(); 
		
		//pad the vboxes
		titleBox.setPadding(new Insets(15, 15, 15, 15));
		authorBox.setPadding(new Insets(15, 15, 15, 15));
		genreBox.setPadding(new Insets(15, 15, 15, 15));
		pageBox.setPadding(new Insets(15, 15, 15, 15));
		
		//add labels
		titleBox.getChildren().add(outline.DisplayMainLabel("Title", "black")); //create title header
		authorBox.getChildren().add(outline.DisplayMainLabel("Author", "black")); //create author header
		genreBox.getChildren().add(outline.DisplayMainLabel("Genre", "black")); //create genre header
		pageBox.getChildren().add(outline.DisplayMainLabel("Pages", "black")); //create page count header
		
		//set list size variable 
		int listSize = bookArray.size();
		//Add each individual book to display
		for (int i = 0; i < listSize; i++) {
			//get the book at the specified spot
			Book book = (Book) bookArray.get(i);
			//add each part of the book to display in order
			titleBox.getChildren().add(outline.DisplaySmallLabel(book.getTitle()));
			authorBox.getChildren().add(outline.DisplaySmallLabel(book.getAuthor()));
			genreBox.getChildren().add(outline.DisplaySmallLabel(book.getGenre()));
			pageBox.getChildren().add(intoutline.DisplayNumSmallLabel(book.getPageCount()));
		}
		
		//add vboxes to hbox
		hBox.getChildren().addAll(titleBox, authorBox, genreBox, pageBox);
		hBox.setAlignment(Pos.CENTER);
		
		return hBox;
	}

	//create buttons for ui
	private static HBox eventBox() throws FileNotFoundException {
		//set up hbox
		HBox hBox = new HBox(15);
		hBox.setPadding(new Insets(15, 15, 15, 15));
		hBox.setStyle("-fx-background-color: black");
		EventMain eventManager = new EventMain();
		
		//create buttons
		Button btHome = outline.DisplayButton("Home");
		btHome.setOnAction(eventManager.buttonHome);
		
		//add a new book
		Button btAdd = outline.DisplayButton("New Book");
		btAdd.setOnAction(eventManager.buttonAdd);
		
		//Clean the library of duplicates
		//Button btClean = outline.DisplayButton("Remove Duplicates");
		//btClean.setOnAction(eventManager.buttonClean);

		//Open the description
		Button btHelp = outline.DisplayButton("Help");
		btHelp.setOnAction(eventManager.buttonHelp);
		
		//Quit the application
		Button btQuit = outline.DisplayButton("Quit");
		btQuit.setOnAction(eventManager.buttonQuit);
		
		//add buttons
		hBox.getChildren().addAll(btHome, btAdd, btHelp, btQuit);
		
		//set alignment
		hBox.setAlignment(Pos.CENTER);
		
		return hBox;
	}
}