package niazi.PIECES;

import java.util.ArrayList;
import java.util.Arrays;

public class Bishop extends ChessPiece {
	
	public Bishop(String color) {
		super("B", color);
	}
	
	// find all possible moves in right diagonal directions
	  private ArrayList<int[]> getRightMoves(ChessPiece[][] board){
		ArrayList <int[]> moves = new ArrayList<int[]>();
		int[] location = this.getLocation();
		int rank = location[0];
		int file = location[1];
		
		// check how far a bishop can go in either direction
		int horizontalDist = 7 - file;
		int verticalDist = 7 - rank;
	
		// determine the limiting direction
		int limiter = 0;
		if(horizontalDist < verticalDist) {
			limiter = horizontalDist;
		}
		else {
			limiter = verticalDist;
		}
		
		// iterate through each possible move in the upper right diag direction
		for(int i = 1; i <= limiter; i++) {
			int newRank = rank + i;
			int newFile = file + i;
			
			ChessPiece cp = board[newRank][newFile];
			
			// if there isn't a piece there, keep going
			if(cp == null) {
				int[] possibleMove = {newRank, newFile};
				moves.add(possibleMove);
			}
			// if a piece of the same color is there, break (can't go further)
			else if( cp.getColor().equals(this.getColor()) ) {
				break;
			}
			// if a piece of the opposite color is there, add that move, and then break (can't go further)
			else {
				int[] possibleMove = {newRank, newFile};
				moves.add(possibleMove);
				break;
			}		
		}
		
		// now compute limiter for the lower right direction
		// note that the horizontalDist is the same
		horizontalDist = 7 - file;
		verticalDist = 0 + rank;
		
		if(horizontalDist < verticalDist) {
			limiter = horizontalDist;
		}
		else {
			limiter = verticalDist;
		}
		// do the same thing for the lower right diag direction
		for(int i = 1; i <= limiter; i++) {
			int newRank = rank - i;
			int newFile = file + i;
			
			ChessPiece cp = board[newRank][newFile];
			
			if(cp == null) {
				int[] possibleMove = {newRank, newFile};
				moves.add(possibleMove);
			}
			else if( cp.getColor().equals(this.getColor()) ) {
				break;
			}
			else {
				int[] possibleMove = {newRank, newFile};
				moves.add(possibleMove);
				break;
			}		
		}
		return moves;
	}
	// find all possible moves in left diagonal directions
	  private ArrayList<int[]> getLeftMoves(ChessPiece[][] board){
		ArrayList<int[]> moves = new ArrayList<int[]> ();
		int[] location = this.getLocation();
		
		int rank = location[0];
		int file = location[1];
		
		// check how far a bishop can go in either direction
		int horizontalDist = 0 + file;
		int verticalDist = 7 - rank;
	
		// determine the limiting direction (for upper left direction)
		int limiter = 0;
		if(horizontalDist < verticalDist) {
			limiter = horizontalDist;
		}
		else {
			limiter = verticalDist;
		}
		
		// iterate through each possible move in the upper left diag direction
		for(int i = 1; i <= limiter; i++) {
			int newRank = rank + i;
			int newFile = file - i;
			
			ChessPiece cp = board[newRank][newFile];
			
			// if there isn't a piece there, keep going
			if(cp == null) {
				int[] possibleMove = {newRank, newFile};
				moves.add(possibleMove);
			}
			// if a piece of the same color is there, break (can't go further)
			else if( cp.getColor().equals(this.getColor()) ) {
				break;
			}
			// if a piece of the opposite color is there, add that move, and then break (can't go further)
			else {
				int[] possibleMove = {newRank, newFile};
				moves.add(possibleMove);
				break;
			}		
		}
		// adjust limiters for the lower left direction
		horizontalDist = 0 + file;
		verticalDist = 0 + rank;
	
		if(horizontalDist < verticalDist) {
			limiter = horizontalDist;
		}
		else {
			limiter = verticalDist;
		}
		
		// do the same thing for the lower left diag direction
		for(int i = 1; i <= limiter; i++) {
			int newRank = rank - i;
			int newFile = file - i;
			
			ChessPiece cp = board[newRank][newFile];
			
			if(cp == null) {
				int[] possibleMove = {newRank, newFile};
				moves.add(possibleMove);
			}
			else if( cp.getColor().equals(this.getColor()) ) {
				break;
			}
			else {
				int[] possibleMove = {newRank, newFile};
				moves.add(possibleMove);
				break;
			}		
		}
		return moves;
	}
	
	@Override
	protected ArrayList<int[]> generateMoves(ChessPiece[][] board) {
		ArrayList <int[]> possibleMoves = new ArrayList<int[]>();
		
		possibleMoves.addAll(this.getRightMoves(board));
		possibleMoves.addAll(this.getLeftMoves(board));
		
		return possibleMoves;
	}
	
	public ArrayList<int[]> getPossibleMoves(ChessPiece[][] board, boolean makingAMove){
		ArrayList <int[]> possibleMoves = this.generateMoves(board);
		
		if(makingAMove) {
			try {
				possibleMoves = this.checkForPins(possibleMoves, board);
			} catch(Exception e) {}
		}

		return possibleMoves;
	}
	
	//follow same procedure as the pawn class
	public ChessPiece[][] move (int[] newLocation, ChessPiece[][] board) {
		
		ArrayList <int[]> possibleMoves = this.getPossibleMoves(board, true);
		
		for(int[] move: possibleMoves) {
			if(Arrays.equals(move, newLocation)) {
				int[] oldLocation = this.getLocation();
				this.setLocation(newLocation);
				
				board[newLocation[0]][newLocation[1]] = this;
				board[oldLocation[0]][oldLocation[1]] = null;
				
				break;
			}
		}
		
		return board;
	}
	
	@Override
	public Bishop clone() throws CloneNotSupportedException {
		Bishop clone = (Bishop) super.clone();
		return clone;
	}

}
