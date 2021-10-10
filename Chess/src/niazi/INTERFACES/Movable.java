package niazi.INTERFACES;

import java.util.ArrayList;

import niazi.PIECES.*;

public interface Movable {
	public abstract ChessPiece[][] move(int[] location, ChessPiece[][] board);
	public abstract ArrayList <int[]> getPossibleMoves(ChessPiece[][] board, boolean makingAMove);
	//public abstract ChessPiece[][] capture();
}
