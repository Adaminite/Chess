package niazi.MAIN;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import niazi.BOARD.Board;
import niazi.PIECES.*;

public class Chess {
	
	
	public static Scanner input = new Scanner(System.in);
	
	
	public static ChessPiece[][] createBoardCopy(ChessPiece[][] board) throws CloneNotSupportedException {
		ChessPiece[][] clonedBoard = new ChessPiece[8][8];
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				if(board[i][j] == null) {
					clonedBoard[i][j] = null;
				}
				else {
					clonedBoard[i][j] = (ChessPiece) board[i][j].clone();
				}
			}
		}
		return clonedBoard;
	}
	public static ChessPiece choosePiece(ChessPiece[][] board, String turnColor) {
		ChessPiece piece;
		int[] pieceSquare = new int[2];
		int squareRank = 0;
		int squareFile = 0;
		do {
		while(true) {
			System.out.println("Enter piece to move (e.g. e2): ");
			String inputPiece = input.nextLine();
			inputPiece = inputPiece.trim();
			
			if(inputPiece.length() == 2) {
				String[] square = inputPiece.split("");
				int rank = Integer.parseInt(square[1]);
				String file = square[0];
				
				int fileNum = 0;
				
				switch(file) {
				case "a":
					fileNum = 1;
					break;
				case "b":
					fileNum = 2;
					break;
				case "c":
					fileNum = 3;
					break;
				case "d":
					fileNum = 4;
					break;
				case "e":
					fileNum = 5;
					break;
				case "f":
					fileNum = 6;
					break;
				case "g":
					fileNum = 7;
					break;
				case "h":
					fileNum = 8;
					break;
				default:
					fileNum = 0;
					break;
				}
				if(fileNum != 0 && (rank > 0 && rank < 9)) {
					pieceSquare[0]  = rank - 1;	
					pieceSquare[1] = fileNum - 1;
					break;
				}
			}
		}
			squareRank = pieceSquare[0];
			squareFile = pieceSquare[1];
			piece = board[squareRank][squareFile];
		} while( (piece == null) || (!piece.getColor().equals(turnColor)) || (piece.getPossibleMoves(board, true).isEmpty() ));
		
		return piece;
	}
	
	public static int[] chooseMove(ChessPiece[][] board, ChessPiece piece) {
		int[] move = new int[2];
		boolean isNotValid = true;
		do {
			while(true) {
				System.out.println("Enter your move (e.g. e4): ");
				String inputMove = input.nextLine();
				inputMove = inputMove.trim();
				
				if(inputMove.length() == 2) {
					String[] Move = inputMove.split("");
					int rank = Integer.parseInt(Move[1]);
					String file = Move[0];
					
					int fileNum = 0;
					
					switch(file) {
					case "a":
						fileNum = 1;
						break;
					case "b":
						fileNum = 2;
						break;
					case "c":
						fileNum = 3;
						break;
					case "d":
						fileNum = 4;
						break;
					case "e":
						fileNum = 5;
						break;
					case "f":
						fileNum = 6;
						break;
					case "g":
						fileNum = 7;
						break;
					case "h":
						fileNum = 8;
						break;
					default:
						fileNum = 0;
						break;
					}
					if(fileNum != 0 && (rank > 0 && rank < 9)) {
						move[0]  = rank - 1;	
						move[1] = fileNum - 1;
						break;
					}
				}
			}
			
		ArrayList<int[]> possibleMoves = piece.getPossibleMoves(board, true);
		
		for(int[] possMove: possibleMoves) {
			
			if(Arrays.equals(possMove, move)) {
				isNotValid = false;
			}
		}
		if(isNotValid) {
			System.out.println("Invalid move. Try again");
		}
	} while(isNotValid);
	
	return move;
}
	
	public static ChessPiece[][] makeMove(ChessPiece[][] board, ChessPiece piece, int[] move){
		ChessPiece[][] newBoard = piece.move(move, board);
		return newBoard;
}
	
	public static ChessPiece[][] executeTurn (ChessPiece[][] board, String turnColor) {
		
		if(turnColor.equals("w")) {
			System.out.println("White to Move");
		}
		else {
			System.out.println("Black to Move");
		}
		ChessPiece piece = choosePiece(board, turnColor);
		int[] move = chooseMove(board, piece);
		ChessPiece[][] newBoard = makeMove(board, piece, move);
		
		return newBoard;
}
	
	public static King findKings(ChessPiece[][] board, String color) {
		King king = null;
		for(ChessPiece[] rank: board) {
			for(ChessPiece piece: rank) {
				if(piece == null) {
					continue;
				}
				if(piece.getColor().equals(color) && piece.getType().equals("K")) {
					king = (King) piece;
				}
			}
		}
		return king;
	}
	
	public static void main(String[] args) throws CloneNotSupportedException {

		Board board = new Board();
			
		King whiteKing = findKings(board.getBoard(), "w");
		King blackKing = findKings(board.getBoard(), "b");
		
		int turnCounter = 0;
	
			while(true) {
			String turnColor = "";
			if(turnCounter % 2 == 0) {
				turnColor = "w";
			}
			else {
				turnColor = "b";
			}
			
			board.displayBoard(turnColor);
			board.setBoard( executeTurn(board.getBoard(), turnColor) );
			
			System.out.println();
			
			
			whiteKing = findKings(board.getBoard(), "w");
			blackKing = findKings(board.getBoard(), "b");
			
			if(turnColor.equals("w")) {
				if(blackKing.isInCheck(board.getBoard()) && blackKing.isMated(board.getBoard())) {
						board.displayBoard(turnColor);
						System.out.println("Checkmate! White wins.");
						break;
					}
			}
			else {
				if(whiteKing.isInCheck(board.getBoard()) && whiteKing.isMated(board.getBoard())) {
					board.displayBoard(turnColor);
					System.out.println("Checkmate! Black wins.");
					break;
				}
			}
			turnCounter++;
		}
		input.close();	
	} 
	
}


