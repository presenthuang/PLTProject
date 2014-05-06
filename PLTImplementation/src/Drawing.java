import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * Defination of drawing, to draw the graph of the chess board just in time.
 * 
 * @author Presenthuang
 * 
 */
public class Drawing {
	public static void drawBoard(BoardGUI board, ArrayList<Player> pl,
			String[] icons) {
		Slot[][] slots = Board.getBoardSlots();
		int numOfRows = Board.getRow();
		int numOfColumns = Board.getCol();

		for (int i = 0; i < numOfRows; i++) {
			for (int j = 0; j < numOfColumns; j++) {
				try {
					add(board, i + "" + j, icons[slots[i][j].Player().getId()]);
				} catch (NullPointerException e) {

				}
			}
		}
	}

	public static BoardGUI drawInitialBoard() {
		try {
			Image whiteBlock = ImageIO.read(new File("wood.jpg"));
			BoardGUI board = new BoardGUI(whiteBlock);
			return board;
		} catch (IOException ex) {
			ex.printStackTrace();
			return null;
		}

	}

	public static void add(BoardGUI board, String position, String iconName) {
		// add pieces to board
		board.addPiece(new ImageIcon(iconName), position);

	}
}
