import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import java.util.*;

public class Solver {

    // this is a nested class for the search nodes in the game tree
    private static class SearchNode implements Comparable<SearchNode> {
        private final Board board;        // current board state
        private final int moves;          // number of moves to reach this board
        private final SearchNode previous; // previous search node
        private final int priority;       // priority value for A* algorithm

        public SearchNode(Board board, int moves, SearchNode previous) {
            this.board = board;
            this.moves = moves;
            this.previous = previous;
            // using manhattan priority function (manhattan distance + moves)
            this.priority = board.manhattan() + moves;

            // note: could also use hamming priority but manhattan works better
            // this.priority = board.hamming() + moves;
        }

        // this lets the MinPQ compare search nodes
        @Override
        public int compareTo(SearchNode that) {
            // lower priority value comes first
            return this.priority - that.priority;
        }
    }

    private SearchNode solution; // will store the solution when found
    private boolean solvable;    // tracks if puzzle can be solved

    public Solver(Board initial) {
        // find a solution to the initial board (using the A* algorithm)
        if (initial == null) {
            throw new IllegalArgumentException("board cannot be null");
        }

        // need two priority queues - one for the original board
        // and one for the twin board to detect unsolvable puzzles
        MinPQ<SearchNode> pq = new MinPQ<>();
        MinPQ<SearchNode> twinPq = new MinPQ<>();

        // insert starting positions
        pq.insert(new SearchNode(initial, 0, null));
        twinPq.insert(new SearchNode(initial.twin(), 0, null));

        // run A* algorithm on both puzzles at the same time
        while (!pq.isEmpty() && !twinPq.isEmpty()) {
            // get the next board with minimum priority
            SearchNode node = pq.delMin();
            SearchNode twinNode = twinPq.delMin();

            // check if we reached goal in original puzzle
            if (node.board.isGoal()) {
                solvable = true;
                solution = node;
                break;
            }

            // check if twin reached goal (means original is unsolvable)
            if (twinNode.board.isGoal()) {
                solvable = false;
                solution = null;
                break;
            }

            // get all neighbors of current board and add to queue
            for (Board neighbor : node.board.neighbors()) {
                // don't revisit the previous board (critical optimization)
                if (node.previous != null && neighbor.equals(node.previous.board)) {
                    continue;
                }
                // add this neighbor to the queue
                pq.insert(new SearchNode(neighbor, node.moves + 1, node));
            }

            // do the same for twin puzzle
            for (Board neighbor : twinNode.board.neighbors()) {
                if (twinNode.previous != null && neighbor.equals(twinNode.previous.board)) {
                    continue;
                }
                twinPq.insert(new SearchNode(neighbor, twinNode.moves + 1, twinNode));
            }
        }
    }

    public boolean isSolvable() {
        // is the initial board solvable?
        return solvable;
    }

    public int moves() {
        // min number of moves to solve initial board; -1 if unsolvable
        if (!isSolvable()) {
            return -1; // return -1 if puzzle can't be solved
        }
        return solution.moves;
    }

    public Iterable<Board> solution() {
        // sequence of boards in a shortest solution; null if unsolvable
        if (!isSolvable()) {
            return null;
        }

        // build the solution path by working backwards from goal
        LinkedList<Board> path = new LinkedList<>();
        SearchNode current = solution;

        // add each board to the front of the list to get right order
        while (current != null) {
            path.addFirst(current.board);
            current = current.previous;
        }

        return path;
    }

    public static void main(String[] args) {
        // solve a slider puzzle from a file
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

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