package niazi.BOARD;

import niazi.PIECES.*;

public class Board implements Cloneable{
	
	private ChessPiece[][] board;
	
	public Board() {
		this.board = setUp();
	}
	
	@ Override
	public boolean equals(Object e) {
		Board b = (Board) e;
		ChessPiece[][] board = b.getBoard();
		
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				if(this.board[i][j] == null && board[i][j] == null) {
					continue;                  // consider the null case
				}
				if(this.board[i][j] == null) {
					if(!board[i][j].equals(this.board[i][j])) {
						return false;
					}
				}
				else {
					if(!this.board[i][j].equals(board[i][j])) {
						return false;
					}
				}
				
			}
		}
		
		return true;
		
	}
	@ Override
	public Board clone() throws CloneNotSupportedException {
		Board clone = new Board();
		
		ChessPiece[][] clonedBoard = clone.getBoard();
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				
				if(this.getBoard()[i][j] == null) {
					clonedBoard[i][j] = null;
				}
				else {
					clonedBoard[i][j] = (ChessPiece) this.getBoard()[i][j].clone();
				}
			}
		}
		clone.setBoard(clonedBoard);
		return clone;
	}
	private ChessPiece[][] setUp(){
		ChessPiece[][] Board = new ChessPiece[8][8];
		
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				
				int[] currentPos = {i, j};
				ChessPiece cp = null;
				
				// generate white major and minor pieces
				if(i == 0) {
					if(j == 0 || j == 7){
						cp = new Rook("w");
					}
					else if(j == 1 || j == 6) {
						cp = new Knight("w");
				}
					else if(j == 2 || j == 5) {
						cp = new Bishop("w");
					}
					else if(j == 3) {
						cp = new Queen("w");
					}
					else {
						cp = new King("w");
					}
					cp.setLocation(currentPos);

				}
				// generate white pawns
				else if(i == 1) {
					cp = new Pawn("w");
					cp.setLocation(currentPos);
				}
				// generate black pawns
				else if(i == 6) {
					cp = new Pawn("b");
					cp.setLocation(currentPos);
				}
				// generate black major and minor pieces
				else if(i == 7) {
					if(j == 0 || j == 7){
						cp = new Rook("b");
					}
					else if(j == 1 || j == 6) {
						cp = new Knight("b");
					}
					else if(j == 2 || j == 5) {
						cp = new Bishop("b");
					}
					else if(j == 3) {
						cp = new Queen("b");
					}
					else {
						cp = new King("b");
					}
					cp.setLocation(currentPos);
				}
				
				Board[i][j] = cp;
			}
		}
		return Board;
	}
	/* display the current board state
	 * Notes: we want the first rank on the bottom, so reverse the board,
	 * if there isn't a piece on the square, set its type to "-"
	 */
	public void displayBoard(String turnColor) {
		
		
		if(turnColor.equals("w")) {
		ChessPiece[][] temp = new ChessPiece[8][];
		for(int i = 0; i < 8; i++) {
			temp[i] = this.board[7-i];
		}
		// display the row header 
		System.out.println("Rank #");
		
		// display the board state
		System.out.println("__________________________________________________________________________");
		
		// use a counter to display row headers
		int counter = 7;
		for(ChessPiece[] rank: temp) {
			System.out.print( (counter + 1) + "\t| ");
			for(ChessPiece piece: rank) {
				String type = ""; 
				
				if(piece == null) {
					type = "-";
				}
				else {
					type = piece.getColor() + piece.getType();
				}
				System.out.print(type + "\t ");
			}
			System.out.print("|");
			System.out.println();
			
			if(counter > 0) {
			System.out.println("\t| \t \t \t \t \t \t \t \t |");
			}
			counter--;
		}
		
		System.out.println("__________________________________________________________________________");
		
		// display the column headers
		System.out.print("File \t  ");
		
		System.out.print("a\t b\t c\t d\t e\t f\t g\t h\n ");

		// print a line break so scanner input prompt prints on its own line
		System.out.println();
	}
		else {
			ChessPiece[][] board = this.board;
			
			System.out.println("Rank #");
			System.out.println("__________________________________________________________________________");
			int counter = 0;
			for(int i = 0; i < 8; i++) {
				System.out.print( (counter + 1) + "\t| ");
				for(int j = 0; j < 8; j++) {
					
					ChessPiece piece = board[i][7-j];
					String type = "";
					
					if(piece == null) {
						type = "-";
					}
					else {
						type = piece.getColor() + piece.getType();
					}
					System.out.print(type + "\t ");
				}
				System.out.print("|");
				System.out.println();
				
				if(counter < 7) {
					System.out.println("\t| \t \t \t \t \t \t \t \t |");
					}
					counter++;
			}
			
			System.out.println("__________________________________________________________________________");
			System.out.print("File \t  ");
			System.out.print("h\t g\t f\t e\t d\t c\t b\t a\n ");
			System.out.println();
			
		}
}
	public ChessPiece[][] getBoard() {
		return board;
	}

	public void setBoard(ChessPiece[][] board) {
		this.board = board;
	}


	
}
