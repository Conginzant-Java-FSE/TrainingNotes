public class Demo {
    private int n;
    private int[] board;

    public Demo(int n) {
        this.n = n;
        this.board = new int[n];
    }

    public void solve() {
        placeQueen(0);
    }

    private void placeQueen(int row) {
        if (row == n) {
            printBoard();
            return;
        }
        for (int col = 0; col < n; col++) {
            if (isSafe(row, col)) {
                board[row] = col;
                placeQueen(row + 1);
            }
        }
    }

    private boolean isSafe(int row, int col) {
        for (int i = 0; i < row; i++) {
            if (board[i] == col) return false;
            if (Math.abs(board[i] - col) == Math.abs(i - row)) return false;
        }
        return true;
    }

    private void printBoard() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(board[i] == j ? "Q " : ". ");
            }
            System.out.println();
            //blah blah dumb AI reviewer 
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int n = 8;
        // Demo demo = new Demo(n);
        System.out.println("Solutions for " + n + "-Queens Problem:");
        demo.solve();
    }
}