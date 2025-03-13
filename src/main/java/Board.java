import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import java.util.*;

public class Board {

    private final int[][] tiles;
    private final int n;

    public Board(int[][] blocks) {
        // construct a board from an n-by-n array of blocks
        // (where blocks[i][j] = block in row i, column j)
        // suggestions for immutability in the Binary Heap video
        this.n = blocks.length;
        this.tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            this.tiles[i] = Arrays.copyOf(blocks[i], n);
        }
    }

    public int dimension() {
        // board dimension n
        return n;
    }

    public int hamming() {
        // number of blocks out of place
        int count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int expected = i * n + j + 1;
                if (tiles[i][j] != 0 && tiles[i][j] != expected) {
                    count++;
                }
            }
        }
        return count;
    }

    public int manhattan() {
        // sum of Manhattan distances between blocks and goal
        int sum = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int value = tiles[i][j];
                if (value != 0) {
                    int targetRow = (value - 1) / n;
                    int targetCol = (value - 1) % n;
                    sum += Math.abs(i - targetRow) + Math.abs(j - targetCol);
                }
            }
        }
        return sum;
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
        if (y == this) {
            return true;
        }
        if (y == null || y.getClass() != this.getClass()){
            return false;
        }
        Board that = (Board) y;
        return Arrays.deepEquals(this.tiles, that.tiles);
    }

    private int[][] copyTiles() {
        int[][] copy = new int[n][n];
        for (int i = 0; i < n; i++) {
            copy[i] = Arrays.copyOf(tiles[i], n);
        }
        return copy;
    }

    public Board twin() {
        // a board that is obtained by exchanging any pair of blocks
        int[][] twinTiles = copyTiles();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n - 1; j++) {
                if (twinTiles[i][j] != 0 && twinTiles[i][j + 1] != 0) {
                    int temp = twinTiles[i][j];
                    twinTiles[i][j] = twinTiles[i][j + 1];
                    twinTiles[i][j + 1] = temp;
                    return new Board(twinTiles);
                }
            }
        }
        return null;
    }

    public boolean isGoal() {
        // is this board the goal board?
        return hamming() == 0;
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
