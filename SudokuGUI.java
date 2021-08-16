package sudokuGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.InputVerifier;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 * @author Jonathan Giegold
 */
public class SudokuGUI {
	public SudokuGUI(SudokuSolver sudoku) {
		SwingUtilities.invokeLater(() -> createWindow(sudoku));
	}

	/**
	 * Creates the window that displays the sudoku and the application starts
	 * 
	 * @param sudoku SudokuSolver object
	 */
	private void createWindow(SudokuSolver sudoku) {
		JFrame frame = new JFrame("Jonathans Sudoku Solver");
		Container container = frame.getContentPane();
		Dimension buttonDimension = new Dimension(330, 30);
		GridLayout gridLayout = new GridLayout(9, 9);
		JPanel segmentPanel = new JPanel();
		JPanel buttonPanel = new JPanel();
		JTextField[][] textFields = new JTextField[9][9];
		JButton solveButton = new JButton("SOLVE");
		JButton clearButton = new JButton("CLEAR");

		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setPreferredSize(new Dimension(680, 720));
		container.setPreferredSize(new Dimension(680, 680));
		segmentPanel.setPreferredSize(new Dimension(680, 655));
		solveButton.setPreferredSize(buttonDimension);
		clearButton.setPreferredSize(buttonDimension);

		container.setLayout(new BorderLayout());
		segmentPanel.setLayout(gridLayout);

		createTextFields(textFields, segmentPanel);
		updateTextFields(textFields, sudoku.getMatrix());

		solveButton.addActionListener(event -> {
			setTextFields(textFields, sudoku);
			if (sudoku.solve()) {
				updateTextFields(textFields, sudoku.getMatrix());
			} else {
				JOptionPane.showMessageDialog(null, "Unsolvable sudoku", "Could not solve", JOptionPane.ERROR_MESSAGE);
			}
		});
		clearButton.addActionListener(event -> {
			sudoku.clear();
			updateTextFields(textFields, sudoku.getMatrix());
		});

		buttonPanel.setLayout(new BorderLayout());
		buttonPanel.add(solveButton, BorderLayout.WEST);
		buttonPanel.add(clearButton, BorderLayout.EAST);

		container.add(segmentPanel, BorderLayout.NORTH);
		container.add(buttonPanel, BorderLayout.SOUTH);

		frame.pack();
		frame.setVisible(true);
	}

	/**
	 * Fills the array textFields with textFields and puts them in the segmentPanel
	 * 
	 * @param textFields   textFields to add
	 * @param segmentPanel JPanel to add textFields to
	 */
	private void createTextFields(JTextField[][] textFields, JPanel segmentPanel) {
		for (int row = 0; row < textFields.length; row++) {
			for (int col = 0; col < textFields[row].length; col++) {
				Dimension preferredSize = new Dimension(60, 60);

				JTextField textField = new JTextField();
				textField.setFont(new Font("Serif", Font.BOLD, 40));
				textField.setPreferredSize(preferredSize);
				textField.setHorizontalAlignment(0);
				textField.setVerifyInputWhenFocusTarget(true);

				textField.setInputVerifier(new InputVerifier() {
					@Override
					public boolean verify(JComponent input) {
						String text = ((JTextField) input).getText();
						if (text.equals("")) {
							return true;
						}
						return isDigit(text);
					}
				});

				textField.setBackground(Color.CYAN);
				if (col == (col % 3 + 3) && row != (row % 3 + 3)) {
					textField.setBackground(Color.white);
				} else if (col != (col % 3 + 3) && row == (row % 3 + 3)) {
					textField.setBackground(Color.white);
				}
				textField.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.lightGray));
				textFields[row][col] = textField;
				segmentPanel.add(textField);
			}
		}
	}

	/**
	 * Checks if text is a digit
	 * 
	 * @param text String containing user input
	 * @return true if text is a single digit
	 */
	private boolean isDigit(String text) {
		int text1;
		if (text.equals("")) {
			return false;
		}
		try {
			text1 = Integer.parseInt(text);
			if (text1 < 10 && text1 > 0) {
				return true;
			} else {
				JOptionPane.showMessageDialog(null, "Illegal input. Only numbers 1-9 are allowed. Try again");
				return false;
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Illegal input. Only numbers 1-9 are allowed. Try again");
			return false;
		}
	}

	/**
	 * Updates the text in the textFields to the values in grid
	 * 
	 * @param textFields textFields used in the visual representation
	 * @param grid       grid is a representation of the board
	 */
	private void updateTextFields(JTextField[][] textFields, int[][] grid) {
		for (int row = 0; row < textFields.length; row++) {
			for (int col = 0; col < textFields[row].length; col++) {
				textFields[row][col].setText(Integer.toString(grid[row][col]));
				if (grid[row][col] == 0) {
					textFields[row][col].setText("");
				}
			}
		}
	}

	/**
	 * Sets the values in the textFields to the sudoku
	 * 
	 * @param textFields textFields used in the visual representation
	 * @param sudoku     SudokuSolver object
	 */
	private void setTextFields(JTextField[][] textFields, SudokuSolver sudoku) {
		for (int row = 0; row < textFields.length; row++) {
			for (int col = 0; col < textFields[row].length; col++) {
				String text = textFields[row][col].getText();
				if (isDigit(text)) {
					sudoku.setNumber(row, col, Integer.parseInt(text));
				} else {
					sudoku.clearNumber(row, col);
				}
			}
		}
	}

	public static void main(String[] args) {
		SudokuSolver solveThis = new Solver();
		new SudokuGUI(solveThis);
	}
}
