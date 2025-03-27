/** Daniela Molina
 *  cs8bwans
 *  Feb 21, 2018
 *  
 *  This file is what runs the front end of the 2048 game. The GUI is the
 *  visual representaltion of the 2048 game, which runs with the help of
 *  Board.java
 */
import javafx.application.*;
import javafx.scene.control.*;
import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.event.*;
import javafx.scene.input.*;
import javafx.scene.text.*;
import javafx.geometry.*;
import java.util.*;

import java.io.*;

/** This class is the GUI of the 2048 Game, the visual representation */
public class Gui2048 extends Application
{
    private String outputBoard; // The filename for where to save the Board
    private Board board; // The 2048 Game Board

    private GridPane pane;

	private Tile tile;
	// 2D array to hold tiles
	private Tile[][] grid;
	// Text to update score
	private Text score;
	private StackPane stack;
	private StackPane gameoverPane;
	private Text gOver;
	private BorderPane border;

	// To avoid magic numbers, these numbers are the 2048 tile values
	private final int num2 = 2;
	private final int num4 = 4;
	private final int num8 = 8;
	private final int num16 = 16;
	private final int num32 = 32;
	private final int num64 = 64;
	private final int num128 = 128;
	private final int num256 = 256;
	private final int num512 = 512;
	private final int num1024 = 1024;
	private final int num2048 = 2048;

    @Override
    public void start(Stage primaryStage)
    {
        // Process Arguments and Initialize the Game Board
        processArgs(getParameters().getRaw().toArray(new String[0]));

		// Create Border pane
		border = new BorderPane();
		HBox hbox = new HBox();
		border.setTop(hbox);
		// Create 2048 title and score
		Text title = new Text("2048");
		score = new Text("Score: " + board.getScore());
		// Change font and size of text
		title.setFont(Font.font("Times New Roman", FontWeight.BOLD, 65));
		score.setFont(Font.font("Times New Roman", FontWeight.BOLD, 45));
		hbox.getChildren().addAll(title, score);
		hbox.setAlignment(Pos.CENTER);
		hbox.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
		hbox.setSpacing(70);
		hbox.setStyle("-fx-background-color: rgb(230, 242, 255)");

        // Create the pane that will hold all of the visual objects
        pane = new GridPane();
        pane.setAlignment(Pos.CENTER);
        pane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
        pane.setStyle("-fx-background-color: rgb(230, 242, 255)");
        // Set the spacing between the Tiles
        pane.setHgap(15); 
        pane.setVgap(15);

		border.setCenter(pane);

		// Game over pane
		stack = new StackPane();
		Scene scene = new Scene(stack);
		gameoverPane = new StackPane();
		gOver = new Text("YOU SUCK!");
		gOver.setFont(Font.font("Times New Roman", FontWeight.BOLD, 80));
		gOver.setFill(Color.BLACK);
		gameoverPane.setAlignment(gOver, Pos.CENTER);
		gameoverPane.getChildren().addAll(gOver);
		gameoverPane.setStyle("-fx-background-color: rgb(230, 242, 255, 0)");
		gOver.setOpacity(0);


		//gameoverPane.getChildren().addAll();
		stack.getChildren().addAll(border, gameoverPane);

		scene.setOnKeyPressed(new myKeyHandler());
    	primaryStage.setTitle("Gui2048");
		// Set the scene to the stage to display the pane
   		primaryStage.setScene(scene);
   		primaryStage.show();
		
		grid = new Tile[board.GRID_SIZE][board.GRID_SIZE];
		// Add Tiles to GridPane with corresponding gridsize
		for(int y = 0; y < board.GRID_SIZE; y++) {
			for(int x = 0; x < board.GRID_SIZE; x++) {
				grid[y][x] = addTile(tile, x, y);
			}
		}
			     
    }

	/** Inner class to hold Rectangles with a Text object */
	public class Tile {
		// Instance variables
		private Rectangle rectangle;
		private Text text;

		/** No-arg constructor */
		public Tile() {
			//create an empty tile
			this(" ",Constants2048.COLOR_EMPTY,Constants2048.COLOR_VALUE_DARK);

		}
		/** Constructor to create a tile
		 *  @param string: a String object to make the Text
		 */
		public Tile(String string, Color r, Color t) {
			rectangle = new Rectangle();
			text = new Text();
			text.setText(string);
			rectangle.setFill(r);
			text.setFill(t);
		}

		/** Getter for rectangle
		 *  @return Rectangle object of Tile
		 */
		public Rectangle getRectangle() {
			return this.rectangle;
		}

		/** Getter for text
		 *  @return Text object of Tile
		 */
		public Text getText() {
			return this.text;
		}

		/** Update color of a single tile */
		public void updateColor() {
			// Check what the tile value is
			String tileValue = this.getText().getText();
			// If value is empty
			if(tileValue.equals("0")) {
				getRectangle().setFill(Constants2048.COLOR_EMPTY);
			}
			// If value is 2
			else if(tileValue.equals("2")) {
				getRectangle().setFill(Constants2048.COLOR_2);
			}
			// If value is 4
			else if(tileValue.equals("4")) {
				getRectangle().setFill(Constants2048.COLOR_4);
			}
			// If value is 8
			else if(tileValue.equals("8")) {
				getRectangle().setFill(Constants2048.COLOR_8);
			}
			// If value is 16
			else if(tileValue.equals("16")) {
				getRectangle().setFill(Constants2048.COLOR_16);
			}
			// If value is 32
			else if(tileValue.equals("32")) {
				getRectangle().setFill(Constants2048.COLOR_32);
			}
			// If value is 64
			else if(tileValue.equals("64")) {
				getRectangle().setFill(Constants2048.COLOR_64);
			}
			// If value is 128
			else if(tileValue.equals("128")) {
				getRectangle().setFill(Constants2048.COLOR_128);
			}
			// If value is 256
			else if(tileValue.equals("256")) {
				getRectangle().setFill(Constants2048.COLOR_256);
			}
			// If value is 512
			else if(tileValue.equals("512")) {
				getRectangle().setFill(Constants2048.COLOR_512);
			}
			// If value is 1024
			else if(tileValue.equals("1024")) {
				getRectangle().setFill(Constants2048.COLOR_1024);
			}
			// If value is 2048
			else if(tileValue.equals("2048")) {
				getRectangle().setFill(Constants2048.COLOR_2048);			}
			// If value is any value greater than 2048
			else {
				getRectangle().setFill(Constants2048.COLOR_OTHER);
			}

		}

		/** Update color of a single tile's text */
		public void updateTextColor() {
			// Check what the tile value is
			String tileValue = this.getText().getText();
			// If value is 0, set Text to " "
			if(tileValue.equals("0")) {
				getText().setText(" ");
			}
			// If value is less than 8
			if(tileValue.equals("2") || tileValue.equals("4")) {
				getText().setFill(Constants2048.COLOR_VALUE_DARK);
			}
			//If value is greater than 
			else {
				getText().setFill(Constants2048.COLOR_VALUE_LIGHT);
			}
		}
	}
	
	/** Helper method to make a single tile and add it onto the Grid
	 *  @param tile: Tile object to add
	 *  @param column: int value of which column in GridPane to add Tile to
	 *  @param row: int value of which row in GridPane to add Tile to
	 */
	public Tile addTile(Tile tile, int column, int row) {
		// Check what the individual tile value is by accessing board grid
		int tileNumber = board.getGrid()[row][column];
		// If value is empty
		if(tileNumber == 0) {
			tile = new Tile();
		}
		// If value is 2
		else if(tileNumber == num2) {
			tile = new Tile("2", Constants2048.COLOR_2,
							Constants2048.COLOR_VALUE_DARK);
		}
		// If value is 4
		else if(tileNumber == num4) {
			tile = new Tile("4", Constants2048.COLOR_4,
							Constants2048.COLOR_VALUE_DARK);
		}
		// If value is 8
		else if(tileNumber == num8) {
			tile = new Tile("8", Constants2048.COLOR_8,
							Constants2048.COLOR_VALUE_LIGHT);
		}
		// If value is 16
		else if(tileNumber == num16) {
			tile = new Tile("16", Constants2048.COLOR_16,
							Constants2048.COLOR_VALUE_LIGHT);
		}
		// If value is 32
		else if(tileNumber == num32) {
			tile = new Tile("32", Constants2048.COLOR_32,
							Constants2048.COLOR_VALUE_LIGHT);
		}
		// If value is 64
		else if(tileNumber == num64) {
			tile = new Tile("64", Constants2048.COLOR_64,
							Constants2048.COLOR_VALUE_LIGHT);
		}
		// If value is 128
		else if(tileNumber == num128) {
			tile = new Tile("128", Constants2048.COLOR_128,
							Constants2048.COLOR_VALUE_LIGHT);
		}
		// If value is 256
		else if(tileNumber == num256) {
			tile = new Tile("256", Constants2048.COLOR_256,
							Constants2048.COLOR_VALUE_LIGHT);
		}
		// If value is 512
		else if(tileNumber == num512) {
			tile = new Tile("512", Constants2048.COLOR_512,
							Constants2048.COLOR_VALUE_LIGHT);
		}
		// If value is 1024
		else if(tileNumber == num1024) {
			tile = new Tile("1024", Constants2048.COLOR_1024,
							Constants2048.COLOR_VALUE_LIGHT);
		}
		// If value is 2048
		else if(tileNumber == num2048) {
			tile = new Tile("2048", Constants2048.COLOR_2048,
							Constants2048.COLOR_VALUE_LIGHT);
		}
		// If value is any value greater than 2048
		else {
			String strTileNum = Integer.toString(tileNumber);
			tile = new Tile(strTileNum, Constants2048.COLOR_OTHER,
							Constants2048.COLOR_VALUE_LIGHT);
		}

		// Set size of Tile
		tile.getRectangle().setWidth(100);
		tile.getRectangle().setHeight(100);
		
		// Set size of Text
		tile.getText().setFont(Font.font("Times New Roman", FontWeight.BOLD, 30));
		// Allign text
		GridPane.setHalignment(tile.getText(), HPos.CENTER);

		//add Tile onto Grid
		pane.add(tile.getRectangle(), column, row);
		pane.add(tile.getText(), column, row);

		return tile;
	}


	/** Update GUI */
	public void updateGUI() {
		//loop through board and update based on board's grid
		for(int y = 0; y < board.GRID_SIZE; y++) {
			for(int x = 0; x < board.GRID_SIZE; x++) {
				int tileValue = board.getGrid()[y][x];
				String tileV = Integer.toString(tileValue);
				grid[y][x].getText().setText(tileV);
				//update rectangle and text color
				grid[y][x].updateColor();
				grid[y][x].updateTextColor();
				score.setText("Score: " + board.getScore());
			}
		}
	}

	/** Private inner class which implements the EventHandler<KeyEvent>
	 *  interface
	 *  The class that ties in the user's input to the game
	 */
	private class myKeyHandler implements EventHandler<KeyEvent> {
		@Override
		public void handle(KeyEvent k) {
			// Get the key that was pressed
			KeyCode key = k.getCode();
			// Check if user input is up
			if(key == KeyCode.UP) {
				// Move up
				if(board.move(Direction.UP)) {
					board.addRandomTile();
					updateGUI();
					System.out.println("Moving Up");
					if(board.isGameOver()) {
						gameoverPane.setStyle("-fx-background-color: rgb(230, 242, 255, 0.7)");
						gOver.setOpacity(1);

					}
				}
			}
			// Check if user input is down
			else if(key == KeyCode.DOWN) {
				// Move down
				if(board.move(Direction.DOWN)) {
					board.addRandomTile();
					updateGUI();
					System.out.println("Moving Down");
					// Check if game is over
					if(board.isGameOver()) {
						gameoverPane.setStyle("-fx-background-color: rgb(230, 242, 255, 0.7)");
						gOver.setOpacity(1);
					}
				}
			}
			// Check if user input is left
			else if(key == KeyCode.LEFT) {
				// Move left
				if(board.move(Direction.LEFT)) {
					board.addRandomTile();
					updateGUI();
					System.out.println("Moving Left");
					// Check if game is over
					if(board.isGameOver()) {
						gameoverPane.setStyle("-fx-background-color: rgb(230, 242, 255, 0.7)");
						gOver.setOpacity(1);
					}
				}
			}
			else if(key == KeyCode.RIGHT) {
				// Move right
				if(board.move(Direction.RIGHT)) {
					board.addRandomTile();
					updateGUI();
					System.out.println("Moving Right");
					// Check if game is over
					if(board.isGameOver()) {
						gameoverPane.setStyle("-fx-background-color: rgb(230, 242, 255, 0.7)");
						gOver.setOpacity(1);
					}
				}
			}
			// Check if user input is s
			else if(key == KeyCode.S) {
				// Save board
				try {
					board.saveBoard(outputBoard);
					System.out.println("Saving Board to " + outputBoard);
				} catch (IOException e) { 
					System.out.println("saveBoard threw an Exception");
				}
			}

		}
	
	}


    /** DO NOT EDIT BELOW */

    // The method used to process the command line arguments
    private void processArgs(String[] args)
    {
        String inputBoard = null;   // The filename for where to load the Board
        int boardSize = 0;          // The Size of the Board

        // Arguments must come in pairs
        if((args.length % 2) != 0)
        {
            printUsage();
            System.exit(-1);
        }

        // Process all the arguments 
        for(int i = 0; i < args.length; i += 2)
        {
            if(args[i].equals("-i"))
            {   // We are processing the argument that specifies
                // the input file to be used to set the board
                inputBoard = args[i + 1];
            }
            else if(args[i].equals("-o"))
            {   // We are processing the argument that specifies
                // the output file to be used to save the board
                outputBoard = args[i + 1];
            }
            else if(args[i].equals("-s"))
            {   // We are processing the argument that specifies
                // the size of the Board
                boardSize = Integer.parseInt(args[i + 1]);
            }
            else
            {   // Incorrect Argument 
                printUsage();
                System.exit(-1);
            }
        }

        // Set the default output file if none specified
        if(outputBoard == null)
            outputBoard = "2048.board";
        // Set the default Board size if none specified or less than 2
        if(boardSize < 2)
            boardSize = 4;

        // Initialize the Game Board
        try{
            if(inputBoard != null)
                board = new Board(new Random(), inputBoard);
            else
                board = new Board(new Random(), boardSize);
        }
        catch (Exception e)
        {
            System.out.println(e.getClass().getName() + 
                               " was thrown while creating a " +
                               "Board from file " + inputBoard);
            System.out.println("Either your Board(String, Random) " +
                               "Constructor is broken or the file isn't " +
                               "formated correctly");
            System.exit(-1);
        }
    }

    // Print the Usage Message 
    private static void printUsage()
    {
        System.out.println("Gui2048");
        System.out.println("Usage:  Gui2048 [-i|o file ...]");
        System.out.println();
        System.out.println("  Command line arguments come in pairs of the "+ 
                           "form: <command> <argument>");
        System.out.println();
        System.out.println("  -i [file]  -> Specifies a 2048 board that " + 
                           "should be loaded");
        System.out.println();
        System.out.println("  -o [file]  -> Specifies a file that should be " + 
                           "used to save the 2048 board");
        System.out.println("                If none specified then the " + 
                           "default \"2048.board\" file will be used");  
        System.out.println("  -s [size]  -> Specifies the size of the 2048" + 
                           "board if an input file hasn't been"); 
        System.out.println("                specified.  If both -s and -i" + 
                           "are used, then the size of the board"); 
        System.out.println("                will be determined by the input" +
                           " file. The default size is 4.");
    }
}
