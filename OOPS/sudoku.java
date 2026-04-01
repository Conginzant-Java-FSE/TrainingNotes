public class SudokuSolver {

    private static final int SIZE = 9;
    private static final int EMPTY = 0;

    public static void main(String[] args) {
        int[][] board = {
            {5, 3, 0, 0, 7, 0, 0, 0, 0},
            {6, 0, 0, 1, 9, 5, 0, 0, 0},
            {0, 9, 8, 0, 0, 0, 0, 6, 0},
            {8, 0, 0, 0, 6, 0, 0, 0, 3},
            {4, 0, 0, 8, 0, 3, 0, 0, 1},
            {7, 0, 0, 0, 2, 0, 0, 0, 6},
            {0, 6, 0, 0, 0, 0, 2, 8, 0},
            {0, 0, 0, 4, 1, 9, 0, 0, 5},
            {0, 0, 0, 0, 8, 0, 0, 7, 9}
        };

        System.out.println("Puzzle:");
        printBoard(board);

        if (solve(board)) {
            System.out.println("\nSolved:");
            printBoard(board);
        } else {
            System.out.println("No solution exists.");
        }
    }

    // Core backtracking solver
    public static boolean solve(int[][] board) {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (board[row][col] == EMPTY) {
                    for (int num = 1; num <= SIZE; num++) {
                        if (isValid(board, row, col, num)) {
                            board[row][col] = num;           // place

                            if (solve(board)) return true;  // recurse

                            board[row][col] = EMPTY;        // backtrack
                        }
                    }
                    return false; // no valid number found — trigger backtrack
                }
            }
        }
        return true; // no empty cell found — puzzle is solved
    }

    // Check if placing 'num' at (row, col) is valid
    public static boolean isValid(int[][] board, int row, int col, int num) {
        // Check row
        for (int c = 0; c < SIZE; c++) {
            if (board[row][c] == num) return false;
        }

        // Check column
        for (int r = 0; r < SIZE; r++) {
            if (board[r][col] == num) return false;
        }

        // Check 3×3 box
        int boxRowStart = (row / 3) * 3;
        int boxColStart = (col / 3) * 3;
        for (int r = boxRowStart; r < boxRowStart + 3; r++) {
            for (int c = boxColStart; c < boxColStart + 3; c++) {
                if (board[r][c] == num) return false;
            }
        }

        return true;
    }

    // Pretty-print the board
    public static void printBoard(int[][] board) {
        for (int row = 0; row < SIZE; row++) {
            if (row % 3 == 0 && row != 0) {
                System.out.println("------+-------+------");
            }
            for (int col = 0; col < SIZE; col++) {
                if (col % 3 == 0 && col != 0) System.out.print("| ");
                int val = board[row][col];
                System.out.print((val == EMPTY ? "." : val) + " ");
            }
            System.out.println();
        }
    }
}
