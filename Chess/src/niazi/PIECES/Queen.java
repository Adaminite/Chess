package niazi.PIECES;

import java.util.ArrayList;
import java.util.Arrays;

public class Queen extends ChessPiece {
	public Queen(String color) {
		super("Q", color);
	}
	
	private ArrayList<int[]> getRightDiagonalMoves(ChessPiece[][] board){
		ArrayList <int[]> moves = new ArrayList<int[]>();
		
		int rank = this.getLocation()[0];
		int file = this.getLocation()[1];

		int horizontalDist = 7 - file;
		int verticalDist = 7 - rank;
	
		int limiter = 0;
		if(horizontalDist < verticalDist) {
			limiter = horizontalDist;
		}
		else {
			limiter = verticalDist;
		}

		for(int i = 1; i <= limiter; i++) {
			int newRank = rank + i;
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
		horizontalDist = 7 - file;
		verticalDist = 0 + rank;
		
		if(horizontalDist < verticalDist) {
			limiter = horizontalDist;
		}
		else {
			limiter = verticalDist;
		}
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
	
	private ArrayList<int[]> getLeftDiagonalMoves(ChessPiece[][] board){
		
		ArrayList<int[]> moves = new ArrayList<int[]> ();
		
		int rank = this.getLocation()[0];
		int file = this.getLocation()[1];
		
		int horizontalDist = 0 + file;
		int verticalDist = 7 - rank;
	

		int limiter = 0;
		if(horizontalDist < verticalDist) {
			limiter = horizontalDist;
		}
		else {
			limiter = verticalDist;
		}
		
		for(int i = 1; i <= limiter; i++) {
			int newRank = rank + i;
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
		
		horizontalDist = 0 + file;
		verticalDist = 0 + rank;
	
		if(horizontalDist < verticalDist) {
			limiter = horizontalDist;
		}
		else {
			limiter = verticalDist;
		}
		
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
	
	private ArrayList<int[]> getHorizontalMoves(ChessPiece[][] board){
		ArrayList <int[]> horizontalMoves = new ArrayList<int[]>();
		
		int[] location = this.getLocation();
		int rank = location[0];
		int file = location[1];
		
		int leftDist = 0 + file;
		int rightDist = 7 - file ;
		
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
	
	// same process as the rook
	private ArrayList<int[]> getVerticalMoves(ChessPiece[][] board){
		ArrayList <int[]> verticalMoves = new ArrayList<int[]>();
		
		int[] location = this.getLocation();
		int rank = location[0];
		int file = location[1];
		
		int upwardDist = 7 - rank;
		int downwardDist = 0 + rank;
		
		for(int i = 1; i <= upwardDist; i++) {
			int newRank = rank + i;
			
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
	@Override
	protected ArrayList<int[]> generateMoves(ChessPiece[][] board) {
		ArrayList<int[]> possibleMoves = new ArrayList<int[]>();
		
		possibleMoves.addAll(this.getHorizontalMoves(board));
		possibleMoves.addAll(this.getVerticalMoves(board));
		possibleMoves.addAll(this.getLeftDiagonalMoves(board));
		possibleMoves.addAll(this.getRightDiagonalMoves(board));
		
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
				
				break;
			}
		}
		return board;
	}

	@Override
	public Queen clone() throws CloneNotSupportedException {
		Queen clone = (Queen) super.clone();
		return clone;
	}
}
