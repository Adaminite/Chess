package niazi.PIECES;

import java.util.ArrayList;
import java.util.Arrays;

public class Rook extends ChessPiece {
	
	private boolean hasMoved;
	private boolean canCastle;
	
	public Rook(String color) {
		super("R", color);
		this.hasMoved = false;
		this.canCastle = true;
	}
	
	private ArrayList<int[]> getVerticalMoves(ChessPiece[][] board){
		ArrayList <int[]> verticalMoves = new ArrayList<int[]>();
		
		int[] location = this.getLocation();
		int rank = location[0];
		int file = location[1];
		
		// compute how many ranks the rook can go up or down
		int upwardDist = 7 - rank;
		int downwardDist = 0 + rank;
		
		// iterate thru upward possibilities
		for(int i = 1; i <= upwardDist; i++) {
			int newRank = rank + i;
			
			ChessPiece cp = board[newRank][file];
			// if a square is empty, it is a possible move
			if(cp == null) {
				int[] move = {newRank, file};
				verticalMoves.add(move);
			}
			// if a piece of the same color is there, break
			else if(cp.getColor().equals(this.getColor())) {
				break;
			}
			// if a piece of the opposite color is there, add the move and then break
			else {
				int[] move = {newRank, file};
				verticalMoves.add(move);
				break;
			}
		}
		
		// now iterate thru downward possibilities
		// follow same procedure as above
		for(int j = 1; j <= downwardDist; j++) {
			int newRank = rank - j;
			
			ChessPiece cp = board[newRank][file];
	
			if(cp == null) {
				int[] move = {newRank, file};
				verticalMoves.add(move);
			}
			else if(cp.getColor().equals(this.getColor())) {
				break;
			}
			else {
				int[] move = {newRank, file};
				verticalMoves.add(move);
				break;
		}
		
	}
		return verticalMoves;
}
	private ArrayList <int[]> getHorizontalMoves(ChessPiece[][] board){
		ArrayList <int[]> horizontalMoves = new ArrayList<int[]>();
		
		int[] location = this.getLocation();
		int rank = location[0];
		int file = location[1];
		
		int leftDist = 0 + file;
		int rightDist = 7 - file ;
		
		// find all rightward moves
		for(int i = 1; i <= rightDist; i++) {
			int newFile = file + i;
			
			ChessPiece cp = board[rank][newFile];
			
			if(cp == null) {
				int[] move = {rank, newFile};
				horizontalMoves.add(move);
			}
			else if(cp.getColor().equals(this.getColor())) {
				break;
			}
			else {
				int[] move = {rank, newFile};
				horizontalMoves.add(move);
				break;
			}
		}
		// find all leftward moves
		for(int j = 1; j <= leftDist; j++) {
			int newFile = file - j;
			
			ChessPiece cp = board[rank][newFile];
			
			if(cp == null) {
				int[] move = {rank, newFile};
				horizontalMoves.add(move);
			}
			else if(cp.getColor().equals(this.getColor())) {
				break;
			}
			else {
				int[] move = {rank, newFile};
				horizontalMoves.add(move);
				break;
			}
		}		
		return horizontalMoves;
	}
	
	@Override
	protected ArrayList<int[]> generateMoves(ChessPiece[][] board) {
		ArrayList<int[]> possibleMoves = new ArrayList<int[]>();
		
		possibleMoves.addAll(getHorizontalMoves(board));
		possibleMoves.addAll(getVerticalMoves(board));
		
		return possibleMoves;
	}
	public ArrayList <int[]> getPossibleMoves(ChessPiece[][] board, boolean makingAMove){
		ArrayList<int[]> possibleMoves = this.generateMoves(board);
		
		if(makingAMove) {
			try {
				possibleMoves = this.checkForPins(possibleMoves, board);
			} catch(Exception e) {}
		}
		
		return possibleMoves;
	}	
	
	public ChessPiece[][] move (int[] newLocation, ChessPiece[][] board) {
		
		ArrayList<int[]> possibleMoves = this.getPossibleMoves(board, true);
		
		for(int[] move: possibleMoves) {
			if(Arrays.equals(newLocation, move)) {
				int[] oldLocation = this.getLocation();
				this.setLocation(newLocation);
				
				board[newLocation[0]][newLocation[1]] = this;
				board[oldLocation[0]][oldLocation[1]] = null;
				
				this.hasMoved = true;
				this.canCastle = false;
				
				break;
			}
		}
		return board;
	}

	public boolean isHasMoved() {
		return hasMoved;
	}

	public boolean isCanCastle() {
		return canCastle;
	}

	protected void setHasMoved(boolean hasMoved) {
		this.hasMoved = hasMoved;
	}

	protected void setCanCastle(boolean canCastle) {
		this.canCastle = canCastle;
	}
	
	@Override
	public Rook clone() throws CloneNotSupportedException {
		Rook clone = (Rook) super.clone();
		clone.setCanCastle(this.canCastle);
		clone.setHasMoved(this.hasMoved);
		return clone;
	}


	
	
}
