import java.util.ArrayList;

/**
 * class for all functions in the program as well all the built-in functions
 * 
 * @author Presenthuang
 * 
 */

public class Functions {
	public static boolean add(Pos pos, Player currentowner, String ptype) {
		if (GameDesigner.add_res(ptype, new Pos(pos))) {
			Board.initSlot(pos);
			Board.getSlot(pos).setPiece(new Piece(currentowner, new Pos(pos)), currentowner);
			return true;
		} else {
			return false;
		}
	}

	public static boolean win(Pos pos, Player currentowner) {
		if (GameDesigner.win_res(new Pos(pos))) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isEmpty(Pos pos) {
		if (pos == null)
			return false;
		if (pos.getX() >= Board.getBoardSlots().length
				|| pos.getY() >= Board.getBoardSlots()[0].length
				|| pos.getX() < 0 || pos.getY() < 0)
			return false;
		Slot currentSlot = Board.getSlot(pos);
		if (currentSlot == null) {
			return true;
		} else {
			return false;
		}
	}

	public static int numberInRow(Pos pos) {
		Slot currentSlot = Board.getSlot(pos);
		if (currentSlot == null) {
			return 0;
		}
		// check horizontal line
		// check vertical line
		// check diagonal line
		int posx = pos.getX();
		int posy = pos.getY();
		int a = CountDiagonal(posx, posy, currentSlot);
		int b = CountHorizontal(posx, posy, currentSlot);
		int c = CountVertical(posx, posy, currentSlot);

		return Math.max(a, Math.max(b, c));
	}

	private static int CountHorizontal(int posx, int posy, Slot currentSlot) {
		int total = 1;
		for (int i = posy + 1; i < Board.getCol(); ++i) {
			if (Board.getBoardSlots()[posx][i] == null)
				break;
			if (Board.getBoardSlots()[posx][i].Player() == currentSlot.Player()) {
				total++;
			} else {
				break;
			}
		}
		for (int i = posy - 1; i >= 0; --i) {
			if (Board.getBoardSlots()[posx][i] == null)
				break;
			if (Board.getBoardSlots()[posx][i].Player() == currentSlot.Player()) {
				total++;
			} else {
				break;
			}
		}

		return total;
	}

	private static int CountVertical(int posx, int posy, Slot currentSlot) {
		int total = 1;
		for (int i = posx + 1; i < Board.getRow(); ++i) {
			if (Board.getBoardSlots()[i][posy] == null)
				break;
			if (Board.getBoardSlots()[i][posy].Player() == currentSlot.Player()) {
				total++;
			} else {
				break;
			}
		}
		for (int i = posx - 1; i >= 0; --i) {
			if (Board.getBoardSlots()[i][posy] == null)
				break;
			if (Board.getBoardSlots()[i][posy].Player() == currentSlot.Player()) {
				total++;
			} else {
				break;
			}
		}

		return total;
	}

	private static int CountDiagonal(int posx, int posy, Slot currentSlot) {
		int i = posx;
		int j = posy;
		int total1 = 1;
		int total2 = 1;

		i -= 1;
		j -= 1;
		while (i >= 0 && i < Board.getRow() && j >= 0 && j < Board.getCol()) {
			if (Board.getBoardSlots()[i][j] == null)
				break;
			if (Board.getBoardSlots()[i][j].Player() == currentSlot.Player()) {
				total1++;
			}
			i -= 1;
			j -= 1;
		}
		i = posx;
		j = posy;
		i += 1;
		j += 1;
		while (i >= 0 && i < Board.getRow() && j >= 0 && j < Board.getCol()) {
			if (Board.getBoardSlots()[i][j] == null)
				break;
			if (Board.getBoardSlots()[i][j].Player() == currentSlot.Player()) {
				total1++;
			}
			i += 1;
			j += 1;
		}

		i = posx;
		j = posy;
		i -= 1;
		j += 1;
		while (i >= 0 && i < Board.getRow() && j >= 0 && j < Board.getCol()) {
			if (Board.getBoardSlots()[i][j] == null)
				break;
			if (Board.getBoardSlots()[i][j].Player() == currentSlot.Player()) {
				total2++;
			}
			i -= 1;
			j += 1;
		}

		i = posx;
		j = posy;
		i += 1;
		j -= 1;
		while (i >= 0 && i < Board.getRow() && j >= 0 && j < Board.getCol()) {
			if (Board.getBoardSlots()[i][j] == null)
				break;
			if (Board.getBoardSlots()[i][j].Player() == currentSlot.Player()) {
				total2++;
			}
			i += 1;
			j -= 1;
		}

		return total1 > total2 ? total1 : total2;
	}

	public static String getPieceType(Piece p) {
		return p.piecetype();
	}

	public static Player getPiecePlayer(Piece p) {
		return p.owner;
	}

	public static ArrayList<Piece> getPiecefromPlayer(Player p, String type) {
		ArrayList<Piece> myList = new ArrayList<Piece>();
		for (Piece pi : p.pieceList) {
			if (pi.Type.equals(type))
				myList.add(pi);
		}
		return myList;
	}

	public static ArrayList<Piece> getPiecefromPlayer(Player p) {
		return p.pieceList;
	}

	public static Pos getPiecePos(Piece p) {
		return p.pos;
	}

	public static Piece getPiece(Pos po) {
		if (po.getX() < Board.getBoardSlots().length
				&& po.getY() < Board.getBoardSlots()[0].length) {
			Piece piece = Board.getBoardSlots()[po.getX()][po.getY()].Piece();
			return piece;
		}
		return null;
	}

	public static Piece getPiece(int i, int j) {
		// TODO Auto-generated method stub
		if (i < Board.getBoardSlots().length && j < Board.getBoardSlots()[0].length) {
			Piece piece = Board.getBoardSlots()[i][j].Piece();
			return piece;
		}
		return null;
	}

	public static int pieceCount(String piecetype) {
		int count = 0;
		for (int x = 0; x < Board.getBoardSlots().length; x++) {
			for (int y = 0; y < Board.getBoardSlots()[0].length; y++) {
				if (Board.getBoardSlots()[x][y] != null
						&& Board.getBoardSlots()[x][y].Piece() != null
						&& Board.getBoardSlots()[x][y].Piece().piecetype()
								.equals(piecetype))
					count++;
			}
		}
		return count;
	}

	public static boolean isBoardFull() {
		for (int x = 0; x < Board.getBoardSlots().length; x++) {
			for (int y = 0; y < Board.getBoardSlots()[0].length; y++) {
				if (Board.getBoardSlots()[x][y].available())
					return false;
			}
		}
		return true;
	}

	public static boolean remove(Pos po, ArrayList<Player> players) {
		Piece piece = Board.getBoardSlots()[po.getX()][po.getY()].Piece();
		if (piece == null) {
			return false;
		} else {
			Player owner = piece.owner;
			Board.getBoardSlots()[po.getX()][po.getY()].setPiece(null, null);
			// delete the piece from the player
			for (Player p : players) {
				if (p.getId() == owner.getId()) {
					p.removePiece(piece);
				}
			}
			return true;
		}
	}

	public static Piece findNextInRow(Pos pos, int mode) {
		int x = pos.getX();
		int y = pos.getY();
		Slot currentSlot = Board.getBoardSlots()[x][y];
		if (currentSlot == null || currentSlot.Piece() == null)
			return null;
		if (mode < 0 || mode > 7)
			return null;
		int x_min = 0;
		int y_min = 0;
		int x_max = Board.getBoardSlots().length - 1;
		int y_max = Board.getBoardSlots()[0].length - 1;

		while (x >= x_min && x <= x_max && y >= y_min && y <= y_max) {
			Slot slot = Board.getBoardSlots()[x][y];
			if (slot != null
					&& slot.Piece() != null
					&& slot.Piece().Type == currentSlot.Piece().piecetype()
					&& slot.Piece().owner.getId() == currentSlot.Piece().owner
							.getId())
				return Board.getBoardSlots()[x][y].Piece();
			if (mode <= 1 || mode == 7)
				x--;
			if (mode >= 3 && mode <= 5)
				x++;
			if (mode >= 5 && mode <= 7)
				y--;
			if (mode >= 1 && mode <= 3)
				y++;
		}
		return null;
	}

}
