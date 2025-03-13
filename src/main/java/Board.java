import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import java.util.*;

public class Board {

    private final int[][] tiles; // board configuration
    private final int n;         // board size

    public Board(int[][] blocks) {
        // construct a board from an n-by-n array of blocks
        // (where blocks[i][j] = block in row i, column j)
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
                // don't count the blank square
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
                // skip blank tile
                if (value != 0) {
                    // calculate where this tile should be
                    int targetRow = (value - 1) / n;
                    int targetCol = (value - 1) % n;
                    // add the manhattan distance
                    sum += Math.abs(i - targetRow) + Math.abs(j - targetCol);
                }
            }
        }
        return sum;
    }

    public String toString() {
        // this method makes a string that looks like:
        // 3
        //  1 0 3
        //  4 2 5
        //  7 8 6
        StringBuilder sb = new StringBuilder();
        // first line is board size
        sb.append(n).append("\n");
        // add each row of tiles
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                sb.append(" ").append(tiles[i][j]);
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public boolean equals(Object y) {
        // this checks if two boards are the same
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
        // helper to copy the tiles array
        int[][] copy = new int[n][n];
        for (int i = 0; i < n; i++) {
            copy[i] = Arrays.copyOf(tiles[i], n);
        }
        return copy;
    }

    public Board twin() {
        // a board that is obtained by exchanging any pair of blocks
        int[][] twinTiles = copyTiles();
        // just find the first row with 2 non-blank tiles and swap them
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n - 1; j++) {
                if (twinTiles[i][j] != 0 && twinTiles[i][j + 1] != 0) {
                    // found two adjacent non-blank tiles, swap them
                    int temp = twinTiles[i][j];
                    twinTiles[i][j] = twinTiles[i][j + 1];
                    twinTiles[i][j + 1] = temp;
                    return new Board(twinTiles);
                }
            }
        }
        return null; // should never happen with valid boards
    }

    public boolean isGoal() {
        // is this board the goal board?
        // the goal board has all tiles in order, so hamming distance is 0
        return hamming() == 0;
    }

    // helper method to find the blank tile
    private int[] findBlank() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == 0) {
                    return new int[] {i, j};
                }
            }
        }
        // this should never happen if the board is valid
        return null;
    }

    public Iterable<Board> neighbors() {
        // all neighboring boards
        ArrayList<Board> neighbors = new ArrayList<>();

        // find blank position first
        int[] blank = findBlank();
        int blankRow = blank[0];
        int blankCol = blank[1];

        // try moving in all four directions
        int[][] directions = {
                {-1, 0},  // up
                {0, 1},   // right
                {1, 0},   // down
                {0, -1}   // left
        };

        for (int[] dir : directions) {
            int newRow = blankRow + dir[0];
            int newCol = blankCol + dir[1];

            // check if the new position is within bounds
            if (newRow >= 0 && newRow < n && newCol >= 0 && newCol < n) {
                // create a new board with the blank moved
                int[][] newTiles = copyTiles();
                // swap the blank with the tile in the new position
                newTiles[blankRow][blankCol] = newTiles[newRow][newCol];
                newTiles[newRow][newCol] = 0;
                neighbors.add(new Board(newTiles));
            }
        }

        return neighbors;
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