package niazi.PIECES;

import java.util.ArrayList;
import java.util.Arrays;

public class Knight extends ChessPiece {
	
	public Knight(String color) {
		super("N", color);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected ArrayList<int[]> generateMoves(ChessPiece[][] board){
		ArrayList<int[]> moves = new ArrayList<int[]>();
		
		int[] location = this.getLocation();
		int rank = location[0];
		int file = location[1];
		
		// rightward moves
		int[] move1 = {rank + 2, file + 1};
		int[] move2 = {rank + 1, file + 2};
		int[] move3 = {rank - 1, file + 2};
		int[] move4 = {rank - 2, file + 1};
		
		// leftward moves
		int[] move5 = {rank + 2, file - 1};
		int[] move6 = {rank + 1, file - 2};
		int[] move7 = {rank - 1, file - 2};
		int[] move8 = {rank - 2, file - 1};
		
		moves.add(move1);
		moves.add(move2);
		moves.add(move3);
		moves.add(move4);
		moves.add(move5);
		moves.add(move6);
		moves.add(move7);
		moves.add(move8);
		
		ArrayList<int[]> temp = (ArrayList<int[]>) moves.clone();
		
		for(int[] move: temp) {
			int moveRank = move[0];
			int moveFile = move[1];
			
			if( (moveRank < 0 || moveRank > 7) || (moveFile < 0 || moveFile > 7) ) {
				moves.remove(move);
			}
			else {
				ChessPiece cp = board[moveRank][moveFile];
				if(cp != null && cp.getColor().equals(this.getColor()) ) {
					moves.remove(move);
				}
			}
		}
		
		return moves;
	}
	public ArrayList <int[]> getPossibleMoves(ChessPiece[][] board, boolean makingAMove){
		ArrayList<int[]> possibleMoves = new ArrayList<int[]>();
		possibleMoves = this.generateMoves(board);
		if(makingAMove) {
			try {
				possibleMoves = this.checkForPins(possibleMoves, board);
			} catch(Exception e) {}
		}
		return possibleMoves;
	}
	
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
	public Knight clone() throws CloneNotSupportedException {
		Knight clone = (Knight) super.clone();
		return clone;
	}
}
