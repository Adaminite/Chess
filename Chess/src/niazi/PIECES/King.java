package niazi.PIECES;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import niazi.MAIN.Chess;

public class King extends ChessPiece {
	
	private boolean hasMoved;
	private boolean canCastle;
	private boolean isInCheck;
	private boolean isMated;
	
	public King(String color) {
		super("K", color);
		this.hasMoved = false;
		this.canCastle = true;
		this.isInCheck = false;
		this.isMated = false;
	}
	
	
	private boolean isCastlingLegal (ChessPiece[][] board, String side){
		
		String color = this.getColor();
		int[] currLocation = this.getLocation();
		
		int[] rookSquare = new int[2];
		int[] kingSquare = new int[2];
		if(side.equals("King")){
			rookSquare[0] = currLocation[0];
			rookSquare[1] = currLocation[1] + 1;
			kingSquare[0] = currLocation[0];
			kingSquare[1] = currLocation[1] + 2;
		}
		else {
			rookSquare[0] = currLocation[0];
			rookSquare[1] = currLocation[1] - 1;
			kingSquare[0] = currLocation[0];
			kingSquare[1] = currLocation[1] - 2;
			
		}
		for(ChessPiece[] rank: board) {
			for(ChessPiece piece: rank) {
				if(piece == null) {
					continue;
				}
				if( !(piece.getColor().equals(color)) ) {
					ArrayList<int[]> attackedSquares = piece.getPossibleMoves(board, false);
					
					for(int[] square: attackedSquares) {
						if(Arrays.equals(rookSquare, square) || Arrays.equals(kingSquare, square)) {
							return false; }
					}
				}
			}
		}
		
		return true;
	}
	private ArrayList<int[]> getCastlingMoves(ChessPiece[][] board) {
		
		ArrayList<int[]> castlingMoves = new ArrayList<int[]> ();
		
		if(this.canCastle) {
			int[] curr = this.getLocation();
			int currRank = curr[0];
			int currFile = curr[1];
			
			try {
			ChessPiece rRook = board[currRank][currFile + 3];
			ChessPiece lRook = board[currRank][currFile - 4];
				if( !(rRook == null) ) {
					String pType = rRook.getType();
					boolean arePiecesInWay = false;
					for(int i = 1; i < 3; i++) {
						if(board[currRank][currFile + i] != null) {
							arePiecesInWay = true;
							break;
						}
					}
					if(pType.equals("R")) {
						if( ((Rook) rRook ).isCanCastle() && !(arePiecesInWay) && this.isCastlingLegal(board, "King")) {
							int[] move = {currRank, currFile + 2};
							castlingMoves.add(move);
						}
					}
					
				}
				if( !(lRook == null)) {
					String pType = lRook.getType();
					boolean arePiecesInWay = false;
					for(int i = 1; i < 4; i++) {
						if(board[currRank][currFile - i] != null) {
							arePiecesInWay = true;
							break;
						}
					}
					if(pType.equals("R")) {
						if( ((Rook) lRook ).isCanCastle() && !(arePiecesInWay) && this.isCastlingLegal(board, "Queen")) {
							int[] move = {currRank, currFile - 2};
							castlingMoves.add(move);
						}
					}
				}
			} catch (Exception e) {	}
		}
		
		return castlingMoves;
	}
	
	private ChessPiece[][] castle(int[] move, ChessPiece[][] board){
		int[] old = this.getLocation();
		
		if(move[1] == (old[1] + 2) ) {
			Rook rRook = (Rook) board[old[0]][old[1] + 3];
			int[] rookLoc = rRook.getLocation();
			int[] rookMove = {rookLoc[0], rookLoc[1] - 2};
			
			// changes the king location
			this.setLocation(move);
			board[old[0]][old[1]] = null;
			board[move[0]][move[1]] = this;
			
			//change the rook location
			rRook.setLocation(rookMove);
			rRook.setCanCastle(false);
			rRook.setHasMoved(true);
			board[rookLoc[0]][rookLoc[1]] = null;
			board[rookMove[0]][rookMove[1]] = rRook;
			
		}
		
		else {
			Rook lRook = (Rook) board[old[0]][old[1] - 4];
			int[] rookLoc = lRook.getLocation();
			int[] rookMove = {rookLoc[0], rookLoc[1] + 3};
			
			// changes the king location
			this.setLocation(move);
			board[old[0]][old[1]] = null;
			board[move[0]][move[1]] = this;
			
			//change the rook location
			lRook.setLocation(rookMove);
			lRook.setCanCastle(false);
			lRook.setHasMoved(true);
			
			board[rookLoc[0]][rookLoc[1]] = null;
			board[rookMove[0]][rookMove[1]] = lRook;
		}
		
		return board;
	}
	@SuppressWarnings("unchecked")
	protected ArrayList<int[]> generateMoves(ChessPiece[][] board){
		ArrayList<int[]> moves = new ArrayList<int[]>();
		
		int[] location = this.getLocation();
		int rank = location[0];
		int file = location[1];
		
		// rightward moves
		int[] move1 = {rank + 1, file + 1};
		int[] move2 = {rank, file + 1};
		int[] move3 = {rank - 1, file + 1};
		
		// vertical moves
		int[] move4 = {rank + 1, file};
		int[] move5 = {rank + - 1, file};
		
		// leftward moves
		int[] move6 = {rank + 1, file - 1};
		int[] move7 = {rank , file - 1};
		int[] move8 = {rank - 1, file - 1};
		
		moves.add(move1);
		moves.add(move2);
		moves.add(move3);
		moves.add(move4);
		moves.add(move5);
		moves.add(move6);
		moves.add(move7);
		moves.add(move8);
		
		// add possible castling moves
		moves.addAll(this.getCastlingMoves(board));
		
		ArrayList<int[]> temp = (ArrayList<int[]>) moves.clone();
		
		
		for(int[] move: temp) {
			int moveRank = move[0];
			int moveFile = move[1];
			// remove moves that put king off of the board
			if( (moveRank < 0 || moveRank > 7) || (moveFile < 0 || moveFile > 7) ) {
				moves.remove(move);
			}
			// remove moves that have king replace own pieces
			else {
				ChessPiece cp = board[moveRank][moveFile];
				if(cp != null && cp.getColor().equals(this.getColor()) ) {
					moves.remove(move);
			}
		}
	}

		return moves;
	}
	
	private ArrayList <int[]> checkForIllegal(ArrayList<int[]> moves, ChessPiece[][] board) throws CloneNotSupportedException {
		
		int[] currLocation = this.getLocation();
		
		Iterator<int[]> movesIT = moves.iterator();
		while(movesIT.hasNext()) {
			
			int[] move = movesIT.next();
			
			ChessPiece[][] boardCopy = Chess.createBoardCopy(board);
			King king = (King) boardCopy[currLocation[0]][currLocation[1]];
			
			boardCopy[move[0]][move[1]] = king;
			king.setLocation(move);
			boardCopy[currLocation[0]][currLocation[1]] = null;
			
			if(king.isInCheck(boardCopy)) {
				movesIT.remove();
			}
		}
		
		return moves;
	}
	public ArrayList <int[]> getPossibleMoves(ChessPiece[][] board, boolean makingAMove)  {
		ArrayList<int[]> possibleMoves = new ArrayList<int[]>();
		possibleMoves.addAll(this.generateMoves(board));
		

		
		if(makingAMove) {
			try {
			possibleMoves = this.checkForIllegal(possibleMoves, board); }
			catch(CloneNotSupportedException e) {}
		}
		
		return possibleMoves;
	}
	
	public ChessPiece[][] move (int[] newLocation, ChessPiece[][] board) {
		ArrayList <int[]> possibleMoves = this.getPossibleMoves(board, true);
		
		for(int[] move: possibleMoves) {
			
			if(Arrays.equals(move, newLocation)) {
				int[] oldLocation = this.getLocation();
				
				
				// check if they input a castling move
				int[] kingsideCastle = {oldLocation[0],oldLocation[1] + 2};
				int[] queensideCastle = {oldLocation[0], oldLocation[1] - 2};
				
				if(Arrays.equals(newLocation, kingsideCastle) || Arrays.equals(newLocation, queensideCastle)) {
					board = this.castle(newLocation, board);
				}
				// if they didn't wanna castle, perform standard procedure
				else {
					this.setLocation(newLocation);
					
					board[newLocation[0]][newLocation[1]] = this;
					board[oldLocation[0]][oldLocation[1]] = null;	
				}
				
				// change king's attributes
				this.hasMoved = true;
				this.canCastle = false;
				
				break;
			}
				
		}
		return board;
	}
	

	public boolean isInCheck(ChessPiece[][] board) {
		String color = this.getColor();
		int[] currLocation = this.getLocation();
		
		for(ChessPiece[] rank: board) {
			for(ChessPiece piece: rank) {
				if(piece == null) {
					continue;
				}
				if( !(piece.getColor().equals(color)) ) {
					ArrayList<int[]> attackedSquares = piece.getPossibleMoves(board, false);
					
					for(int[] square: attackedSquares) {
						if(Arrays.equals(currLocation, square)) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	public boolean isMated(ChessPiece[][] board) {
		String color = this.getColor();
		
		for(ChessPiece[] rank: board) {
			for(ChessPiece piece: rank) {
				if(piece == null) {
					continue;
				}
				
				if(piece.getColor().equals(color)) {
					ArrayList<int[]> possibleMoves = piece.getPossibleMoves(board, true);
					
					if(!possibleMoves.isEmpty()) {
						return false;
					}
				}
			}
		}
		
		return true;
	}
	@Override
	public King clone() throws CloneNotSupportedException {
		King clone = (King) super.clone();
		clone.setCanCastle(this.canCastle);
		clone.setHasMoved(this.hasMoved);
		clone.setInCheck(this.isInCheck);
		return clone;
	}

	private void setHasMoved(boolean hasMoved) {
		this.hasMoved = hasMoved;
	}

	private void setCanCastle(boolean canCastle) {
		this.canCastle = canCastle;
	}

	private void setInCheck(boolean isInCheck) {
		this.isInCheck = isInCheck;
	}
	
	public void setMated(boolean isMated) {
		this.isMated = isMated;
	}
}
