import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.MinPQ;

public class Board {

    public Board(int[][] blocks) {
        // construct a board from an n-by-n array of blocks
        // (where blocks[i][j] = block in row i, column j)
        // suggestions for immutability in the Binary Heap video
    }

    public int dimension() {
        // board dimension n
        return 0;
    }

    public int hamming() {
        // number of blocks out of place
        return 0;
    }

    public int manhattan() {
        // sum of Manhattan distances between blocks and goal
        return 0;
    }

    public String toString() {
        // string representation of this board
        // for example
        // 3
        //  1 0 3
        //  4 2 5
        //  7 8 6
        return null;
    }

    public boolean equals(Object y) {
        // does this board equal y?
        return false;
    }

    public Board twin() {
        // a board that is obtained by exchanging any pair of blocks
        return null;
    }

    public boolean isGoal() {
        // is this board the goal board?
        return false;
    }

    public Iterable<Board> neighbors() {
        // all neighboring boards
        return null;
    }

    public static void main(String[] args) {
        // create initial board from file
        In in = new In("./8puzzle-test-files/puzzle3x3-07.txt");
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
