import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class AliceChessSolver {
	private String[] pieceNames = { "P", "B", "R", "N", "Q", "K" };

	public static ArrayList<Board> boards = new ArrayList<>();

	public static void main(String[] args) {
		// reading and creating the boards
		try {
			File inputFile = new File("src/input.txt");
			Scanner scanner = new Scanner(inputFile);

			int numInstances = scanner.nextInt();

			scanner.nextLine(); // Consume newline

			for (int i = 1; i <= numInstances; i++) {
				Board board = new Board();

				for (int j = 0; j < board.getHeight(); j++) {
					String line = scanner.nextLine();

					String[] pieces = null;
					pieces = line.split(" ");
					PieceColor color = null;

					for (int k = 0; k < pieces.length; k++) {
						switch (pieces[k]) {
						case "K":
							board.setPiece(j, k, new King(j,k));
							break;
						case "Q":
							board.setPiece(j, k, new Queen(j,k));
							break;
						case "B":
							board.setPiece(j, k, new Bishop(j,k));
							break;
						case "R":
							board.setPiece(j, k, 	new Rook(j,k));
							break;
						case "N":
							board.setPiece(j, k, new Knight(j,k));
							break;
						case "P":
							board.setPiece(j, k, new Pawn(j,k));
							break;

						default:
							board.setPiece(j, k, null);
						}
					}
				}
				boards.add(board);
				scanner.nextLine();
			}

			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		// Solving the boards using solve method from AliceBackTrack
		int boardNumber = 1;
		for (Board board : boards) {
			board.print();
			AliceBackTrack alice = new AliceBackTrack(board);

			ArrayList<Piece> path = alice.solve();

			if (path != null) {
				System.out.print("Board " + boardNumber + ": ");
				for (int n = 0; n < path.size(); n++) {
					System.out.print(path.get(n).getSymbol());
				}
				
				
			}
			else {
				System.out.println("Board " + boardNumber + ": Alice is stuck!");
			}
			boardNumber++;
			System.out.println();

		}
	}
}
