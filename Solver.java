package sudokuGame;

public class Solver implements SudokuSolver {

	private int[][] board;
	private int[][] boardCopy; // Om man behöver ursprungsboard efter att ha ändrat i board.

	public Solver() {
		board = new int[9][9];
		boardCopy = new int[9][9];
	}

	public Solver(int[][] solveBoard) {
		this.board = solveBoard;
		this.boardCopy = solveBoard; // Om man behöver ursprungsboard efter att ha ändrat i board.
	}

	/**
	 * Tries to fill the empty square with a legal number
	 * 
	 * @param row The row to start at
	 * @param col The column to start at
	 * @return false if the empty square cannot be filled with a legal number
	 */
	private boolean solve(int row, int col) {
		int newX = row;
		int newY;
		if (col != 8) {
			newY = col + 1;
		} else {
			newY = 0;
			newX = row + 1;
		}
		if (row == 9) {
			return true;
		}
		if (boardCopy[row][col] == 0) {
			for (int number = 1; number < 10; number++) {
				if (isValid(row, col, number)) {
					board[row][col] = number;
					if (solve(newX, newY)) {
						return true;
					} else {
						board[row][col] = 0;
					}
				}
			}
			return false;
		}
		return solve(newX, newY);
	}

	/**
	 * Checks if the row contains the number
	 * 
	 * @param row    The row
	 * @param number The number
	 * @return if the row contains number
	 */
	private boolean rowContains(int row, int number) {
		for (int col = 0; col < 9; col++) {
			if (board[row][col] == number) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks if the column contains the number
	 * 
	 * @param col    The column
	 * @param number The number
	 * @return if column contains number
	 */
	private boolean colContains(int col, int number) {
		for (int row = 0; row < 9; row++) {
			if (board[row][col] == number) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks if the 3x3 box that the row and column is within contains the number
	 * 
	 * @param row    The row
	 * @param col    The column
	 * @param number The number
	 * @return true if the 3x3 box that row and col is in contains number
	 */
	private boolean boxContains(int row, int col, int number) {
		int r = row - row % 3;
		int c = col - col % 3;
		for (int i = r; i < (r + 3); i++) {
			for (int j = c; j < (c + 3); j++) {
				if (board[i][j] == number) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Checks if the starting numbers are legal according to the Sudoku rules
	 * 
	 * @return true if the board is legal, false if the board is illegal.
	 */
	@Override
	public boolean isAllValid() {
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				int number = board[row][col];
				if (number != 0) {
					board[row][col] = 0;
					if (!isValid(row, col, number)) {
						board[row][col] = number;
						return false;
					} else {
						board[row][col] = number;
					}
				}
			}
		}
		return true;
	}

	/**
	 * Empties all boxes in the sudoku.
	 * 
	 */
	@Override
	public void clear() { // Sätter alla rutor till 0
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				board[row][col] = 0;
				boardCopy[row][col] = 0; // Hur representeras en tom ruta?
			}
		}

	}

	/**
	 * Sets the digit number in the box row, col.
	 * 
	 * @param row    The row
	 * @param col    The column
	 * @param number The digit to insert
	 * @throws IllegalArgumentException if number not in [1..9] or row or col is
	 *                                  outside the allowed range
	 */
	@Override
	public void setNumber(int row, int col, int number) {
		if (row < 0 || row > 8 || col < 0 || col > 8 || number < 0 || number > 9) {
			throw new IllegalArgumentException();
		}
		if (number > 0 && number < 10) {
			board[row][col] = number;
			boardCopy[row][col] = number;
		} else
			return;
	}

	/**
	 * Checks if the value nbr in the box r, c is ok according to the sudoku rules.
	 * 
	 * @param row    The row
	 * @param col    The column
	 * @param number The digit to insert
	 * @return true if it, according to the rules of Sudoku, is possible to put
	 *         number at row, col, else false
	 * @throws IllegalArgumentException if number not in [1..9] or row or col is
	 *                                  outside the allowed range
	 */
	@Override
	public boolean isValid(int row, int col, int number) {
		if (row < 0 || row > 8 || col < 0 || col > 8 || number < 0 || number > 9) {
			throw new IllegalArgumentException();
		}
		if (board[row][col] != 0) {
			return false;
		} else {
			return !(rowContains(row, number) || colContains(col, number) || boxContains(row, col, number));
		}

	}

	/**
	 * Returns the number in box r, c. If the box i empty 0 is returned.
	 * 
	 * @param row The row
	 * @param col The column
	 * @return int of box with row row and col col
	 * @throws IllegalArgumentException if row or col is outside the allowed range
	 */
	@Override
	public int getNumber(int row, int col) {
		if (row < 0 || row > 8 || col < 0 || col > 8) {
			throw new IllegalArgumentException();
		}
		return board[row][col];
	}

	/**
	 * Tries to read the sudoku, returns true if ok, else false.
	 * 
	 * @return true if solvable, else false
	 */
	@Override
	public boolean solve() {
		if (isAllValid()) {
			if (solve(0, 0)) {
				return true;
			}
			return false;
		} else {
			return false;
		}

	}

	/**
	 * Empties the box r, c.
	 * 
	 * @param r The row
	 * @param c The column
	 * @throws IllegalArgumentException if r or c is outside [0..getDimension()-1]
	 */
	@Override
	public void clearNumber(int row, int col) {
		if (row < 0 || row > 8 || col < 0 || col > 8) {
			throw new IllegalArgumentException();
		}
		board[row][col] = 0;
		boardCopy[row][col] = 0;

	}

	/**
	 * Returns the numbers in the grid. An empty box i represented by the value 0.
	 * 
	 * @return the numbers in the grid
	 */
	@Override
	public int[][] getMatrix() {
		int[][] tempReturn = new int[9][9];
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				tempReturn[row][col] = board[row][col];
			}
		}
		return tempReturn;
	}

	/**
	 * Fills the grid with the numbers in nbrs.
	 * 
	 * @param nbrs the matrix with the numbers to insert
	 * @throws IllegalArgumentException if nbrs have wrong dimension or containing
	 *                                  values not in [0..9]
	 */
	@Override
	public void setMatrix(int[][] nbrs) {
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				if (nbrs[row][col] < 0 || nbrs[row][col] > 9) {
					throw new IllegalArgumentException();
				}
				board[row][col] = nbrs[row][col];
				boardCopy[row][col] = nbrs[row][col];
			}
		}

	}
}
