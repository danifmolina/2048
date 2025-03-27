import java.util.*;
import java.io.*;
 
 public class GameManager {
      // Instance variables
      private Board board; // The actual 2048 board
      private String outputBoard; // File to save the board to when exiting
  
      /*ec*/
      private String outputRecord; // file to save the record file, format: [s    ize] wasdwasdwasdawsd
      StringBuilder history = new StringBuilder(); // a string of commands his    tory
      /*ce*/
  
  
      //This constructor will create a new board with a grid size correspondin    g
      //to the value passed in the parameter boardSize
      //param@ outputBoard String object, boardSize int, and random Random obj    ect
      public GameManager(String outputBoard, int boardSize, Random random) {
        //initialize outputBoard
	this.outputBoard = outputBoard;
        //create a new board
        board = new Board(random, boardSize);
        }
    
        //This constructor will load a board using the filename passed in via th    e
        //inputBoard parameter
        //param@ inputBoard and outputBoard String objects and random Random obj    ect
        public GameManager(String inputBoard, String outputBoard, Random random)     throws IOException {
        //initialize outputBoard
        this.outputBoard = outputBoard;
        //create a board from inputBoard file
        board = new Board(random, inputBoard);
        }
   public void play() throws Exception {
        //print the controls of the game
        printControls();
        //print out board
        System.out.println(board);
        //make sure the process repeats
        boolean t = true;
        while(t) {
            //set up scanner for user input
            Scanner s = new Scanner(System.in);
            //create a String to store user input
            String input = s.next();
            //check what the user input is
            //if input is w, move up and add random tile
            if(input.equals("w")) {
            if(board.move(Direction.UP)) {
                board.addRandomTile();
            }
            System.out.println(board);
            //if input is s, move down and add random tile
            } else if(input.equals("s")) {
            if(board.move(Direction.DOWN)) {
                board.addRandomTile();
}
            System.out.println(board);
            //if input is a, move left and add random tile
            } else if(input.equals("a")) {
            if(board.move(Direction.LEFT)) {
                board.addRandomTile();
            }
            System.out.println(board);
            //if input is d, move right and add random tile
            } else if(input.equals("d")) {
            if(board.move(Direction.RIGHT)) {
                board.addRandomTile();
            }
            System.out.println(board);
            //if input is q, save board and quit method
            } else if(input.equals("q")) {
            board.saveBoard(outputBoard);
            return;
            //if input is anything else print out controls for game
            } else {
            printControls();
            System.out.println(board);
            }
	    //check if board cannot move any more
            if(board.isGameOver()) {
            //if true print out
            System.out.println("Game Over!");
            //save board
            board.saveBoard(outputBoard);
            //end method
            return;
            }
        }
        }
    
        // Print the Controls for the Game
        private void printControls() {
        System.out.println("  Controls:");
            System.out.println("    w - Move Up");
            System.out.println("    s - Move Down");
            System.out.println("    a - Move Left");
            System.out.println("    d - Move Right");
            System.out.println("    q - Quit and Save Board");
            System.out.println();
        }
    }
