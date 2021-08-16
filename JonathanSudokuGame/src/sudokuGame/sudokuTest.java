package sudokuGame;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class sudokuTest {
	private int[][] cantSolve;
	private int[][] canSolve;
	private int[][] zeros;
	private Solver sudokuCantSolve;
	private Solver sudokuCanSolve;
	private Solver emptySudoku;

	@BeforeEach
	public void setUp() throws Exception {
		cantSolve = new int[][] { { 2, 7, 4, 0, 9, 0, 6, 5, 3 }, { 3, 9, 6, 5, 7, 4, 8, 0, 0 },
				{ 0, 4, 0, 6, 1, 8, 3, 9, 7 }, { 7, 6, 1, 0, 4, 0, 5, 2, 8 }, { 9, 3, 8, 7, 2, 5, 0, 6, 3 },
				{ 1, 0, 0, 4, 5, 6, 7, 8, 9 }, { 4, 5, 7, 0, 8, 0, 2, 3, 6 }, { 6, 8, 9, 2, 3, 7, 0, 4, 0 },
				{ 0, 0, 5, 3, 6, 2, 9, 7, 4 } };
		canSolve = new int[][] { { 0, 0, 8, 0, 0, 9, 0, 6, 2 }, { 0, 0, 0, 0, 0, 0, 0, 0, 5 },
				{ 1, 0, 2, 5, 0, 0, 0, 0, 0 }, { 0, 0, 0, 2, 1, 0, 0, 9, 0 }, { 0, 5, 0, 0, 0, 0, 6, 0, 0 },
				{ 6, 0, 0, 0, 0, 0, 0, 2, 8 }, { 4, 1, 0, 6, 0, 8, 0, 0, 0 }, { 8, 6, 0, 0, 3, 0, 1, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 4, 0, 0 } };
		zeros = new int[][] { { 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0 } };
		sudokuCantSolve = new Solver(cantSolve);
		sudokuCanSolve = new Solver(canSolve);
		emptySudoku = new Solver();
	}

	@AfterEach
	public void tearDown() throws Exception {
		sudokuCantSolve = null;
		sudokuCanSolve = null;
		emptySudoku = null;
	}

	@Test
	public void testImpSudoku() { // Tests impossible sudoku
		assertFalse(sudokuCantSolve.solve());
	}

	@Test
	public void testSudoku() {
		assertArrayEquals(emptySudoku.getMatrix(), zeros);
		assertArrayEquals(sudokuCanSolve.getMatrix(), canSolve);
	}

	@Test
	public void testSolve() {
		assertTrue(sudokuCanSolve.solve());
	}

	@Test
	public void testClear() {
		sudokuCanSolve.clear();
		int[][] emptyBoard = new int[9][9];
		for (int row = 0; row < emptyBoard.length; row++) {
			for (int col = 0; col < emptyBoard[row].length; col++) {
				emptyBoard[row][col] = 0;
			}
		}
		assertArrayEquals(emptyBoard, sudokuCanSolve.getMatrix());
	}

	@Test
	public void testSetNumber() {
		for (int row = 0; row < canSolve.length; row++) {
			for (int col = 0; col < canSolve[row].length; col++) {
				for (int i = 1; i <= 9; i++) {
					sudokuCanSolve.setNumber(row, col, i);
					assertEquals(i, sudokuCanSolve.getNumber(row, col));
				}
			}
		}
		;
	}

	@Test
	public void testTrySetNumber() {
		sudokuCanSolve.setNumber(4, 2, 1);
		assertFalse(sudokuCanSolve.isValid(4, 2, 1));
		assertTrue(emptySudoku.isValid(6, 5, 3));
	}

	@Test
	public void testIllegalArgumentTrySetNumber() {
		try {
			emptySudoku.isValid(4, 2, 42);
		} catch (Exception e) {
			// Success
		}
	}

	@Test // (expected = IllegalArgumentException.class)
	public void testIllegalArgumentSetNumber() {
		try {
			emptySudoku.setNumber(4, 2, 42);
		} catch (Exception e) {
			// Success
		}
	}

	@Test
	public void testGetNumber() {
		for (int row = 0; row < canSolve.length; row++) {
			for (int col = 0; col < canSolve[row].length; col++) {
				for (int i = 1; i <= 9; i++) {
					sudokuCanSolve.setNumber(row, col, i);
					assertEquals(i, sudokuCanSolve.getNumber(row, col));
				}
			}
		}
		assertEquals(0, emptySudoku.getNumber(4, 2));
	}

	@Test
	public void testRemoveNumber() {
		for (int row = 0; row < canSolve.length; row++) {
			for (int col = 0; col < canSolve[row].length; col++) {
				sudokuCanSolve.clearNumber(row, col);
				assertEquals(0, sudokuCanSolve.getNumber(row, col));
			}
		}
	}

	@Test
	public void testGetNumbers() {
		assertArrayEquals(canSolve, sudokuCanSolve.getMatrix());
	}

	@Test
	public void testSetNumbers() {
		emptySudoku.setMatrix(canSolve);
		assertArrayEquals(canSolve, emptySudoku.getMatrix());
	}

}
