import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import java.util.*;

public class Board {

    private final int[][] tiles; // the board
    private final int n;         // size of board

    public Board(int[][] blocks) {
        // make a copy of the board so we don't mess up the original
        n = blocks.length;
        tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            System.arraycopy(blocks[i], 0, tiles[i], 0, n);
        }
    }

    public int dimension() {
        // just return the size
        return n;
    }

    public int hamming() {
        // count how many tiles are in wrong place
        int count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                // what number should be here
                int expected = i * n + j + 1;

                // blank doesn't count and has value 0
                if (tiles[i][j] != 0 && tiles[i][j] != expected) {
                    count++;
                }
            }
        }
        return count;
    }

    public int manhattan() {
        // sum up how far each tile is from where it should be
        int sum = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int value = tiles[i][j];

                // skip the blank
                if (value != 0) {
                    // figure out where it should be
                    int targetRow = (value - 1) / n;
                    int targetCol = (value - 1) % n;

                    // add up the distance (manhattan = |x1-x2| + |y1-y2|)
                    sum += Math.abs(i - targetRow) + Math.abs(j - targetCol);
                }
            }
        }
        return sum;
    }

    public String toString() {
        // make a string to print the board
        StringBuilder sb = new StringBuilder();
        sb.append(n).append("\n");

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                sb.append(" ").append(tiles[i][j]);
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public boolean equals(Object y) {
        // check if boards are the same
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;

        Board that = (Board) y;
        if (that.n != this.n) return false;

        // check each tile
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (this.tiles[i][j] != that.tiles[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    // make a copy of the board
    private int[][] copyBoard() {
        int[][] copy = new int[n][n];
        for (int i = 0; i < n; i++) {
            System.arraycopy(tiles[i], 0, copy[i], 0, n);
        }
        return copy;
    }

    public Board twin() {
        // make a board with two tiles swapped
        int[][] twinTiles = copyBoard();

        // find two tiles to swap (not the blank)
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n - 1; j++) {
                // check two adjacent tiles
                if (twinTiles[i][j] != 0 && twinTiles[i][j + 1] != 0) {
                    // swap them
                    int temp = twinTiles[i][j];
                    twinTiles[i][j] = twinTiles[i][j + 1];
                    twinTiles[i][j + 1] = temp;
                    return new Board(twinTiles);
                }
            }
        }
        return null; // should never get here
    }

    public boolean isGoal() {
        // check if board is solved
        // easy way: if hamming is 0, all tiles are in right place
        return hamming() == 0;
    }

    // find where the blank (0) is
    private int[] findBlank() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == 0) {
                    return new int[] {i, j};
                }
            }
        }
        return null; // should never happen
    }

    public Iterable<Board> neighbors() {
        // get all possible moves from current board
        ArrayList<Board> neighbors = new ArrayList<>();

        // find the blank
        int[] blank = findBlank();
        assert blank != null;
        int row = blank[0];
        int col = blank[1];

        // try all 4 directions: up, right, down, left
        int[][] moves = {
                {-1, 0},  // up
                {0, 1},   // right
                {1, 0},   // down
                {0, -1}   // left
        };

        for (int[] move : moves) {
            int newRow = row + move[0];
            int newCol = col + move[1];

            // make sure we're still on the board
            if (newRow >= 0 && newRow < n && newCol >= 0 && newCol < n) {
                // make a new board with the blank moved
                int[][] newTiles = copyBoard();

                // swap blank with tile
                newTiles[row][col] = newTiles[newRow][newCol];
                newTiles[newRow][newCol] = 0;

                neighbors.add(new Board(newTiles));
            }
        }

        return neighbors;
    }

    public static void main(String[] args) {
        // test with a puzzle file
        In in = new In("./8puzzle-test-files/puzzle3x3-07.txt");
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();

        Board initial = new Board(tiles);

        // solve it
        Solver solver = new Solver(initial);

        // show the solution
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("min number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}