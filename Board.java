//Daniela Molina
//cs8bwans
//Jan 23, 2018
//
//The Board class is intended as the fundamental board where tiles will be
//placed and moved around. The Board.java file is intended to handle all of the
//board operations. It first constructs a Board object either from scratch or
//from loading a file. Methods include to save the board, to add a random tile
//in the board, to check if the board can move in a certain direction, to move
//the board in a certain direction, and to check if the game is over.

/**
 * Sample Board
 * <p/>
 * 0   1   2   3
 * 0   -   -   -   -
 * 1   -   -   -   -
 * 2   -   -   -   -
 * 3   -   -   -   -
 * <p/>
 * The sample board shows the index values for the columns and rows
 * Remember that you access a 2D array by first specifying the row
 * and then the column: grid[row][column]
 */

import java.util.*;
import java.io.*;

public class Board {
    public final int NUM_START_TILES = 2; 
    public final int TWO_PROBABILITY = 90;
    public final int GRID_SIZE;


    private final Random random; // a reference to the Random object, passed in 
                                 // as a parameter in Boards constructors
    private int[][] grid;  // a 2D int array, its size being boardSize*boardSize
    private int score;     // the current score, incremented as tiles merge 


    //Constructs a fresh board with random tiles
    //param@ Random object and int object for the size of the board
    public Board(Random random, int boardSize) {
        this.random = random; //initialize random to parameter random
        GRID_SIZE = boardSize; //initialize GRID_SIZE to parameter int
        grid = new int[GRID_SIZE][GRID_SIZE]; //create grid
	score = 0;  //initialize score to zero
	//NUM_START_TILES (2)
        this.addRandomTile();
	this.addRandomTile();
    }

   
    //Construct a board based off of an input file
    //Assume board is valid
    //param@ Random object and String of the filename of the input board
    public Board(Random random, String inputBoard) throws IOException {
        this.random = random; //initialize random to parameter random
	//construct a scanner that reads in words from a file
	Scanner input = new Scanner( new File(inputBoard));
	String gridSize = input.next();
	//change gridSize String to an int value
	GRID_SIZE = Integer.parseInt(gridSize);
	String scoreString = input.next();
	//change score String to int value
	score = Integer.parseInt(scoreString);
	grid = new int[GRID_SIZE][GRID_SIZE]; //create grid
	//starting tiles. loop around grid and set value
	for(int y = 0; y < GRID_SIZE; y++) {
	    for(int x = 0; x < GRID_SIZE; x++) {
		String tileValue = input.next();
		grid[y][x] = Integer.parseInt(tileValue);
	    }
	}  
    }

    
    // Saves the current board to a file
    // param@ String of name of file the board will be saved to
    // return@ Nothing
    public void saveBoard(String outputBoard) throws IOException {
        //make PrintWriter object
        PrintWriter pw = new PrintWriter(outputBoard);
	//print grid size
	pw.println(GRID_SIZE);
	//print score
	pw.println(score);
	//print tile values
	for(int y = 0; y < GRID_SIZE; y++) {
	    for(int x = 0; x < GRID_SIZE; x++) {
		//get each tile value
		int tileValue = grid[y][x];
		//print each tile value
		pw.print(tileValue + " ");
	    }
	    //make new line
	    pw.println();
	}
	//close file
	pw.close();
    }

   
    //Adds a random tile (of value 2 or 4) to a
    //random empty space on the board
    //param@ nothing
    //return@ nothing
    public void addRandomTile() {
	//initialize variables
	int count = 0;
	int tileValue = 0;

	//count how many empty spaces there are in the grid
	for(int y = 0; y < GRID_SIZE; y++) {
	    for(int x = 0; x < GRID_SIZE; x++) {
		//empty spaces in grid have value of 0
		if( grid[y][x] == 0) {
		    count++;
		}
	    }
	}
	//get a random location in grid that is empty
	int location = random.nextInt(count); //between 0 and count - 1
	int value = random.nextInt(100); //between 0 and 99
	//if there are no empty spaces left
	if(count == 0) {
	    return;
	} else { //if there are empty spaces
	    //loop through grid to find empty spot location
	    for(int y = 0; y < GRID_SIZE; y++) {
	        for(int x = 0; x < GRID_SIZE; x++) {
		    //if the space is open
		    if( grid[y][x] == 0) {
			//if there are no spots left to look for
		        if(location == 0) {
			    //generate either 2 or 4
			    if(value < TWO_PROBABILITY) {
				tileValue = 2;
			    } else {
				tileValue = 4;
			    }
		   	    //insert tileValue
		   	    grid[y][x] = tileValue;
			    //stop method
			    return;
		        }
			//when you find an open space decrease location
			location--;
		    }
	        }
	    }
	}
    }

    //Determines whether the board can move left
    //param@ nothing
    //return@ true if such a move is possible, if not then false
    private boolean canMoveLeft() {
	//iterate through the entire grid
	for(int y = 0; y < GRID_SIZE; y++) {
	    //start x at 1 to make sure that tile is not on the leftmost column
	    for(int x = 1; x < GRID_SIZE; x++) {
		//if tile on left is equal to tile
		if(grid[y][x-1] == grid[y][x]) {
		    //if neither tile equals 0
		    if(grid[y][x] != 0) {
			return true;
		    }
		}
		//if tile on left is empty
		if(grid[y][x-1] == 0) {
		    if(grid[y][x] != 0) {
			return true;
		    }
		}
	    }
	}
	return false;
    }
    
    //Determines whether the board can move left
    //param@ nothing
    //return@ true if such a move is possible, if not then false
    private boolean canMoveRight() {
	//iterate through the entire grid
	for(int y = 0; y < GRID_SIZE; y++) {
	    //x is less than the rightmost column
	    for(int x = 0; x < GRID_SIZE - 1; x++) {
	        //if tile on right is equal to tile
		if(grid[y][x+1] == grid[y][x]) {
		    //if neither tile equals 0
		    if(grid[y][x] != 0) {
			return true;
		    }
		}
		//if tile on right is empty
		if(grid[y][x+1] == 0) {
		    if(grid[y][x] != 0) {
			return true;
		    }
		}
	    }
	}
	return false;
    }

   //Determines whether the board can move up
   //param@ nothing
   //return@ true if such a move is possible, if not then false
   private boolean canMoveUp() {
	//iterate through the entire grid
	//y starts at 1 so it is not the highest row
	for(int y = 1; y < GRID_SIZE; y++) {
	    for(int x = 0; x < GRID_SIZE; x++) {
		//if tile above is equal to tile
		if(grid[y-1][x] == grid[y][x]) {
		    //if neither tile equals 0
		    if(grid[y][x] != 0) {
			return true;
		    }
		}
		//if tile above is empty
		if(grid[y-1][x] == 0) {
		    if(grid[y][x] != 0) {
			return true;
		    }
		}
	    }
	}
	return false;
    }

    //Determines whether the board can move down
    //param@ nothing
    //return@ true if such a move is possible, if not then false
    private boolean canMoveDown() {
	//iterate through the entire grid
	//y is never the bottom row
	for(int y = 0; y < GRID_SIZE - 1; y++) {
	    for(int x = 0; x < GRID_SIZE; x++) {
		//if tile under is equal to tile
		if(grid[y+1][x] == grid[y][x]) {
		    //if neither tile equals 0
		    if(grid[y][x] != 0) {
			return true;
		    }
		}
		//if tile under is empty
		if(grid[y+1][x] == 0) {
		    if(grid[y][x] != 0) {
			return true;
		    }
		}
	    }
	}
	return false;
    }

    //Determines whether the board can move in a certain direction
    //param@ direction
    //return@ boolean, true if such a move is possible; false if no tiles can
    //move in that direction
    public boolean canMove(Direction direction){
	//check if parameter direction is left
	if(direction.equals(Direction.LEFT)) {
	    if(canMoveLeft()) {
		return true;
	    }
	//check if parameter direction is right
	} else if(direction.equals(Direction.RIGHT)) {
	    if(canMoveRight()) {
		return true;
	    }
	}
	//check if parameter direction is up
	else if(direction.equals(Direction.UP)) {
	    if(canMoveUp()) {
		return true;
	    }
	}
	//check if parameter direction is down
	else if(direction.equals(Direction.DOWN)) {
	    if(canMoveDown()) {
		return true;
	    }
	}
        return false;
    }

    
    //Helper method for moveLeft()
    //Shifts the board left
    //@param nothing
    //return@ nothing
    private void shiftLeft() {
	//loop through entire grid
	for(int y = 0; y < GRID_SIZE; y++) {
	    for(int x = 0; x < GRID_SIZE; x++) {
		//find first zero location
		if(grid[y][x] == 0) {
		    //make a check for later
		    boolean flag = true;
		    //find first nonzero number after first zero
		    for(int i = x+1; i < GRID_SIZE; i++) {
			if(grid[y][i] != 0 && flag) {
			    //move first nonzero to location of first zero
			    grid[y][x] = grid[y][i];
			    //change first nonzero value to 0
			    grid[y][i] = 0;
			    //stop if statement from looping again
			    flag = false;
			}
		    }
		}
	    }
	}
    }

    //Helper method for move method
    //Move the board left
    //param@ nothing
    //return@ true if moving left is successful
    private void moveLeft() {
	//ADDING TILES//
	//loop through board
	for(int y = 0; y < GRID_SIZE; y++) {
	    for(int x = 0; x < GRID_SIZE - 1; x++) {
		//find first nonzero number in row
		if(grid[y][x] != 0) {
	    	    //make a check for later
	  	    boolean flag = true;
		    //index of the tile on the right
		    int i = x + 1;
		    //if tile and tile on right are equal add them
		    if(grid[y][x] == grid[y][i]) {
			grid[y][x] += grid[y][i];
			//change right tile value to 0
			grid[y][i] = 0;
			//update score
			score += grid[y][x];
		    //check if right tile is empty
		    } else if(grid[y][x] != grid[y][i] && grid[y][i] == 0
				&& flag) {
			//sX means searching x
			//loop through the remaining row to find a match
			for(int sX = i + 1; sX < GRID_SIZE; sX++) {
			    //if the next tile is not 0
			    if(grid[y][sX] != 0) {
				flag = false;
				//if tiles are equal add them
				if(grid[y][x] == grid[y][sX]) {
				    grid[y][x] += grid[y][sX];
				    //make right tile value 0
				    grid[y][sX] = 0;
				    //update score
				    score += grid[y][x];
				}
			    }
			}
		    }
		}
	    }
	}
	//SHIFTING TILES//
	shiftLeft();
    }

    //Helper method for moveRight()
    //Shifts the board right
    //@param nothing
    //return@ nothing
    private void shiftRight() {
	//loop through entire grid
	for(int y = 0; y < GRID_SIZE; y++) {
	    for(int x = GRID_SIZE - 1; x >= 0; x--) {
		//find first zero location
		if(grid[y][x] == 0) {
		    //make a check for later
		    boolean flag = true;
		    //find first nonzero number after first zero
		    for(int i = x-1; i >= 0; i--) {
			if(grid[y][i] != 0 && flag) {
			    //move first nonzero to location of first zero
			    grid[y][x] = grid[y][i];
			    //change first nonzero value to 0
			    grid[y][i] = 0;
			    //stop if statement from looping again
			    flag = false;
			}
		    }
		}
	    }
	}
    }

    //Helper method for move method
    //Move the board right
    //param@ nothing
    //return@ true if moving right is successful
    private void moveRight() {
	//ADDING TILES//
	//loop through board
	for(int y = 0; y < GRID_SIZE; y++) {
	    for(int x = GRID_SIZE - 1; x > 0; x--) {
		//find first nonzero number in row
		if(grid[y][x] != 0) {
	    	    //make a check for later
	  	    boolean flag = true;
		    //index of the left tile
		    int i = x - 1;
		    //if tile and tile on left are equal add them
		    if(grid[y][x] == grid[y][i]) {
			grid[y][x] += grid[y][i];
			//change left tile value to 0
			grid[y][i] = 0;
			//update score
			score += grid[y][x];
		    //check if left tile is empty
		    } else if(grid[y][x] != grid[y][i] && grid[y][i] == 0
			&& flag) {
			//sX means searching x
			//loop through the remaining row to find match
			for(int sX = i - 1; sX >= 0; sX--) {
			    //if the next tile is not 0
			    if(grid[y][sX] != 0) {
				flag = false;
				//if tiles are equal add them
				if(grid[y][x] == grid[y][sX]) {
				    grid[y][x] += grid[y][sX];
				    //make left tile value 0
				    grid[y][sX] = 0;
				    //update score
				    score += grid[y][x];
				}
			    }
			}
		    }
		}
	    }
	}
	//SHIFTING TILES//
	shiftRight();
    }

    //Helper method for moveUp()
    //Shifts the board up
    //@param nothing
    //return@ nothing
    private void shiftUp() {
	//loop through entire grid
	for(int x = 0; x < GRID_SIZE; x++) {
	    for(int y = 0; y < GRID_SIZE; y++) {
		//find first zero location
		if(grid[y][x] == 0) {
		    //make a check for later
		    boolean flag = true;
		    //find first nonzero number after first zero
		    for(int i = y+1; i < GRID_SIZE; i++) {
			if(grid[i][x] != 0 && flag) {
			    //move first nonzero to location of first zero
			    grid[y][x] = grid[i][x];
			    //change first nonzero value to 0
			    grid[i][x] = 0;
			    //stop if statement from looping again
			    flag = false;
			}
		    }
		}
	    }
	}
    }

    //Helper method for move method
    //Move the board up
    //param@ nothing
    //return@ true if moving up is successful
    private void moveUp() {
	//ADDING TILES//
	//loop through board
	for(int x = 0; x < GRID_SIZE; x++) {
	    for(int y = 0; y < GRID_SIZE - 1; y++) {
		//find first nonzero number in row
		if(grid[y][x] != 0) {
	    	    //make a check for later
	  	    boolean flag = true;
		    //index of the bottom tile
		    int i = y + 1;
		    //if tile and tile below are equal add them
		    if(grid[y][x] == grid[i][x]) {
			grid[y][x] += grid[i][x];
			//change lower tile value to zero
			grid[i][x] = 0;
			//update score
			score += grid[y][x];
		    //check if bottom tile is empty
		    } else if(grid[y][x] != grid[i][x] && grid[i][x] == 0
				&& flag) {
			//sY means searching y
			//loop through the remaining column
			for(int sY = i + 1; sY < GRID_SIZE; sY++) {
			    //if the next tile is not 0
			    if(grid[sY][x] != 0) {
				flag = false;
				//if tiles are equal add them
				if(grid[y][x] == grid[sY][x]) {
				    grid[y][x] += grid[sY][x];
				    //change lower tile to 0
				    grid[i][x] = 0;
				    //update score
				    score += grid[y][x];
				}
			    }
			}
		    }
		}
	    }
	}
	//SHIFTING TILES//
	shiftUp();
    }

    //Helper method for moveDown()
    //Shifts the board down
    //@param nothing
    //return@ nothing
    private void shiftDown() {
	//loop through entire grid
	for(int x = 0; x < GRID_SIZE; x++) {
	    for(int y = GRID_SIZE - 1; y >= 0; y--) {
		//find first zero location
		if(grid[y][x] == 0) {
		    //make a check for later
		    boolean flag = true;
		    //find first nonzero number after first zero
		    for(int i = y-1; i >= 0; i--) {
			if(grid[i][x] != 0 && flag) {
			    //move first nonzero to location of first zero
			    grid[y][x] = grid[i][x];
			    //change first nonzero value to 0
			    grid[i][x] = 0;
			    //stop if statement from looping again
			    flag = false;
			}
		    }
		}
	    }
	}
    }

    //Helper method for move method
    //Move the board down
    //param@ nothing
    //return@ true if moving down is successful
    private void moveDown() {
	//ADDING TILES//
	//loop through board
	for(int x = 0; x < GRID_SIZE; x++) {
	    for(int y = GRID_SIZE - 1; y > 0; y--) {
		//find first nonzero number in row
		if(grid[y][x] != 0) {
	    	    //make a check for later
	  	    boolean flag = true;
		    //index of tile above
		    int i = y - 1;
		    //if tile is equal to tile above add them
		    if(grid[y][x] == grid[i][x]) {
			grid[y][x] += grid[i][x];
			//change upper tile to 0
			grid[i][x] = 0;
			//update score
			score += grid[y][x];
		    //check if upper tile is empty
		    } else if(grid[y][x] != grid[i][x] && grid[i][x] == 0
				&& flag) {
			//sY means searching y
			//loop through the remaining column
			for(int sY = i - 1; sY >= 0; sY--) {
			    //if the next tile is not 0
			    if(grid[sY][x] != 0) {
				flag = false;
				//if tiles are equal add them
				if(grid[y][x] == grid[sY][x]) {
				    grid[y][x] += grid[sY][x];
				    //make upper tile 0
				    grid[sY][x] = 0;
				    //update score
				    score += grid[y][x];
				}
			    }
			}
	  	    }
		}
	    }
	}
	//SHIFTING TILES//
	shiftDown();
    }


    //Move the board in a certain direction
    //param@ Direction direction to move
    //return@ true if such a move is successful
    public boolean move(Direction direction) {
	//check if board can move in that direction
	if(canMove(direction)) {
	    //if direction is left
	    if(direction.equals(Direction.LEFT)) {
		moveLeft();
		return true;
	    }
	    //if direction is right
	    if(direction.equals(Direction.RIGHT)) {
		moveRight();
		return true;
	    }
	    //if direction is up
	    if(direction.equals(Direction.UP)) {
		moveUp();
		return true;
	    }
	    //if direction is down
	    if(direction.equals(Direction.DOWN)) {
		moveDown();
		return true;
	    }
	}
        return false;
    }

    // No need to change this for PSA3
    // Check to see if we have a game over
    public boolean isGameOver() {
	if(canMove(Direction.LEFT)) {
	    return false;
	} else if(canMove(Direction.RIGHT)) {
	    return false;
	} else if(canMove(Direction.UP)) {
	    return false;
	} else if(canMove(Direction.DOWN)) {
	    return false;
	} else {
	    return true;
	}
    }

    // Return the reference to the 2048 Grid
    public int[][] getGrid() {
        return grid;
    }

    // Return the score
    public int getScore() {
        return score;
    }

    @Override
    public String toString() {
        StringBuilder outputString = new StringBuilder();
        outputString.append(String.format("Score: %d\n", score));
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int column = 0; column < GRID_SIZE; column++)
                outputString.append(grid[row][column] == 0 ? "    -" :
                        String.format("%5d", grid[row][column]));

            outputString.append("\n");
        }
        return outputString.toString();
    }
}
