package niazi.PIECES;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


public class Pawn extends ChessPiece {
	private boolean hasMoved;
	
	public Pawn(String color) {
		super("P", color);
		this.hasMoved = false;
	}
	
	private ArrayList <int[]> validateMoves(ArrayList <int[]> moves, ChessPiece[][] board) {
		
		// if the pawn has alr moved, then check if there is any piece in front of it
		// if there is, then remove it from the list
		if(moves.size() == 1) {
			int[] move = moves.get(0);
			ChessPiece cp = board[move[0]][move[1]];
			
			if(cp != null) {
				moves.remove(move);
			}
		}
		// if the pawn hasn't moved, then check if there is any piece in front of it
		// if there is one directly in front, then the pawn can't move forward so empty arraylist
		// if there is one 2 away, then only remove the two squares move 
		if(moves.size() == 2) {
			int[] move = moves.get(0);
			ChessPiece cp = board[move[0]][move[1]];
			
			if(cp != null) {
				moves.clear();
			}
			else {
				int[] secondMove = moves.get(1);
				ChessPiece cp2 = board[secondMove[0]][secondMove[1]];
				
				if(cp2 != null) {
					moves.remove(secondMove);
				}
			}
		}
		return moves;
	}
	
	@SuppressWarnings("unchecked")
	private ArrayList <int[]> validateCaptures(ArrayList <int[]> moves, ChessPiece[][] board){
		
		// create a shallow copy, so we can remove the moves
		// from the original while keeping the temp the same
		ArrayList<int[]> temp = new ArrayList<int[]>();
		temp = (ArrayList<int[]>) moves.clone();
		
		
		// check if there is a white piece or if there is no piece 
		// on those squares
		// if either above condition is met, then the capture is not valid
		for(int[] move: temp) {
			ChessPiece cp = board[move[0]][move[1]];
		
			if( (cp == null) || (cp.getColor().equals(this.getColor())) ) {
				moves.remove(move);
			}
		}
		return moves;
	}
	
	protected ArrayList<int[]> generateMoves(ChessPiece[][] board){
		int[] curr = this.getLocation();
		String color = this.getColor();
		
		ArrayList<int[]> standardMoves = new ArrayList <int[]>();
		ArrayList<int[]> captures = new ArrayList <int[]>();
		ArrayList<int[]> possibleMoves = new ArrayList<int[]>();
		
		if(color.equals("w")) {
		// standard 1 square up
		int[] normalMove = {curr[0] + 1, curr[1]};
		standardMoves.add(normalMove);
		
		// if it hasnt moved, then pawn can move up 2 squares
		if(!hasMoved) {
			int[] firstMove = {curr[0] + 2, curr[1]};
			standardMoves.add(firstMove);
		}
		
		ArrayList<int[]> possibleStandardMoves = validateMoves(standardMoves, board);
		
		// now consider the diagonal captures 
		if(curr[1] > 0) {
			int[] leftCapture = {curr[0] + 1, curr[1] - 1};
			captures.add(leftCapture);
		}
		if(curr[1] < 7) {
			int[] rightCapture = {curr[0] + 1, curr[1] + 1};
			captures.add(rightCapture);
		}
		ArrayList<int[]> possibleCaptures = validateCaptures(captures, board);
		
	
		possibleMoves.addAll(possibleStandardMoves);
		possibleMoves.addAll(possibleCaptures);
	}
		else {
			int[] normalMove = {curr[0] - 1, curr[1]};
			standardMoves.add(normalMove);
			
			if(!hasMoved) {
				int[] firstMove = {curr[0] - 2, curr[1]};
				standardMoves.add(firstMove);
			}
			
			ArrayList<int[]> possibleStandardMoves = validateMoves(standardMoves,board);
			
			if(curr[1] > 0) {
				int[] leftCapture = {curr[0] - 1, curr[1] - 1};
				captures.add(leftCapture);
			}
			if(curr[1] < 7) {
				int[] rightCapture = {curr[0] - 1, curr[1] + 1};
				captures.add(rightCapture);
			}
			ArrayList<int[]> possibleCaptures = validateCaptures(captures, board);
			
		
			possibleMoves.addAll(possibleStandardMoves);
			possibleMoves.addAll(possibleCaptures);
		}

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

	@ Override
	public ChessPiece[][] move (int[] location, ChessPiece[][] board) {
		ArrayList <int[]> possibleMoves = this.getPossibleMoves(board, true);
		
		for(int[] move: possibleMoves) {
			if(Arrays.equals(move, location)) {
				int[] oldLocation = this.getLocation();
				this.setLocation(location);
				board[location[0]][location[1]] = this;
				board[oldLocation[0]][oldLocation[1]] = null;
				this.hasMoved = true;
				
				if(this.checkForPromotion()) {
					board = this.promote(board);		
				}
				break;
			}
		}
		return board;
	}
	// check if a white pawn is on the 8th rank or if a black pawn is on the 1st rank
	// that is the condition for promotion
	private boolean checkForPromotion() {
		int[] location = this.getLocation();
		String color = this.getColor();
		if( (color.equals("b") && location[0] == 0) || (color.equals("w") && location[0] == 7) ) {
			return true;
		}
		return false;
		
	}
	// ask user what they want to promote to, 
	// set that board position to the input piece type,
	// and then return the changed board position
	@SuppressWarnings("resource")
	private ChessPiece[][] promote(ChessPiece[][] board) {
		
		Scanner input = new Scanner(System.in);
		String p = "";
		do {
		System.out.println("Which piece would you like promote to ('Q' for Queen, 'R' for Rook, 'N' for Knight, 'B' for Bishop)?");
		p = input.next();
		} while( !(p.equals("Q") || p.equals("R") || p.equals("N") || p.equals("B") ));
		
		String color = this.getColor();
		int[] location = this.getLocation();
		int x = location[0];
		int y = location[1];
		
		if(p.equals("Q")) {
			Queen q = new Queen(color);
			q.setLocation(location);
			board[x][y] = q;
		}
		else if(p.equals("R")) {
			Rook r = new Rook(color);
			r.setLocation(location);
			board[x][y] = r;
		}
		else if(p.equals("N")) {
			Knight n = new Knight(color);
			n.setLocation(location);
			board[x][y] = n;
		}
		else {
			Bishop b = new Bishop(color);
			b.setLocation(location);
			board[x][y] = b;
		}
		
		return board;
	}
	@Override
	public Pawn clone() throws CloneNotSupportedException {
		Pawn clone = (Pawn) super.clone();
		clone.setHasMoved(this.hasMoved);
		return clone;
	}

	private void setHasMoved(boolean hasMoved) {
		this.hasMoved = hasMoved;
	}

}
