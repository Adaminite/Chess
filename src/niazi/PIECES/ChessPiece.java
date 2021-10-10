package niazi.PIECES;

import java.util.Arrays;
import java.util.Iterator;
import java.util.ArrayList;

import niazi.INTERFACES.*;
import niazi.MAIN.Chess;

public abstract class ChessPiece implements Movable, Cloneable {
	
	private String type;
	private String color;
	private int[] location;
	
	public ChessPiece(String type, String color) {
		this.type = type;
		this.color = color;
	}
	
	// method for all classes to check for pins
	protected ArrayList<int[]> checkForPins(ArrayList <int[]> moves, ChessPiece[][] board) throws CloneNotSupportedException {
		
		int[] currLocation = this.getLocation();
		
		Iterator<int[]> movesIT = moves.iterator();
		while(movesIT.hasNext()) {
			
			int[] move = movesIT.next();
			
			ChessPiece[][] boardCopy = Chess.createBoardCopy(board);
			King king = Chess.findKings(boardCopy, this.getColor());
			
			boardCopy[move[0]][move[1]] = boardCopy[currLocation[0]][currLocation[1]];
			boardCopy[currLocation[0]][currLocation[1]] = null;
			
			if(king.isInCheck(boardCopy)) {
				movesIT.remove();
			}
		}
		
		return moves;
	}
	// abstract method
	protected abstract ArrayList<int[]> generateMoves(ChessPiece[][] board);
	
	// getters
	public String getType() {
		return type;
	}

	public String getColor() {
		return color;
	}
	
	public int[] getLocation() {
		return location;
	}
	
	public void setLocation(int[] location) {
		this.location = location;
	}

	private void setType(String type) {
		this.type = type;
	}

	private void setColor(String color) {
		this.color = color;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ChessPiece other = (ChessPiece) obj;
		if (color == null) {
			if (other.color != null)
				return false;
		} else if (!color.equals(other.color))
			return false;
		if (!Arrays.equals(location, other.location))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}
	
	@Override
	public ChessPiece clone() throws CloneNotSupportedException {
		ChessPiece clone = (ChessPiece) super.clone();
		
		clone.setColor(this.color);
		clone.setType(this.type);
		clone.setLocation(this.location);
		
		return clone;
	}
	
	
	
	
}
