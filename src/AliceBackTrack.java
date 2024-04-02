import java.util.ArrayList;
import java.util.List;

public class AliceBackTrack {
	private Board board;
	private Piece currentPiece = null;

	public AliceBackTrack(Board board) {
		this.board = board;
	}

	public ArrayList<Piece> solve() {
		int bottomRow = board.getHeight() - 1;
		; // bottom row
		int column = 0;
		Piece scanPiece;
		// we are looking through the bottom row to get
		while (column < board.getWidth()) {
			scanPiece = board.getPiece(bottomRow, column);
			if (scanPiece != null) {
				currentPiece = scanPiece;
			}
			column++;

		}
		// no starting piece possible, alice is stuck
		if (currentPiece == null) {
			return null;
		}

		// Use an ArrayList to store possible paths
		ArrayList<ArrayList<Piece>> paths = new ArrayList<>();

		// Use an ArrayList to track an individual path
		ArrayList<Piece> path = new ArrayList<>();
		path.add(currentPiece);
		paths.add(path);

		while (!paths.isEmpty()) {
			path = paths.remove(0);

			// final piece of the path is at the top, we are done
			Piece finalPiece = path.get(path.size() - 1);
			//this checks if we are at the top and path.size = contain all the pieces possible on the board
			if (finalPiece.getRow() == 0 && path.size() == board.countPieces(null)) {
				return path;
			}
			// if not then we need newPath to find ways to get there.

			ArrayList<Piece> newPath = new ArrayList<>(path); //copies path into newPath, 
			newPath = getPossibleMoves(finalPiece, path);

			for (Piece minipath : newPath) {
				// for all possible moves, we add it to paths, we go back and take it out
				// if it is at the top we are done, if not it will continue to look.
				
				ArrayList<Piece> nextPath = new ArrayList<>(path); 
				nextPath.add(minipath);
				paths.add(nextPath);
			}

		}
		return null;
	}

	public ArrayList<Piece> getPossibleMoves(Piece piece, List<Piece> path) {
		ArrayList<Piece> moves = new ArrayList<>();
		int row = piece.getRow();
		int column = piece.getColumn();
		// loop through the entire board 2 dimensional array to find piece, add it to
		// moves
		for (int i = 0; i < board.getHeight(); i++) {
			for (int j = 0; j < board.getWidth(); j++) {
				// if the piece can move there, and there is another piece there, then add it to
				// the moves
				if (board.isValidMove(null, row, column, i, j) && board.getPiece(i, j) != null && !path.contains(board.getPiece(i, j))) {
					moves.add(board.getPiece(i, j));
				}

			}

		}
		return moves;
	}

}
