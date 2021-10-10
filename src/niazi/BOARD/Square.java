package niazi.BOARD;

import niazi.PIECES.ChessPiece;

public class Square {
	
	
	private String color;
	private ChessPiece piece;
	
	// only initialize the square color and then just use a setter later to add a piece to it
	public Square(String color) {
		this.color = color;
	}
	
	
	// getters
	public String getColor() {
		return color;
	}
	
	public ChessPiece getPiece() {
		return piece;
	}


	// setter for setUp() method in Board class
	public void setPiece(ChessPiece piece) {
		this.piece = piece;
	}
	
	


	
	
	
	
	
	
	
	
}
