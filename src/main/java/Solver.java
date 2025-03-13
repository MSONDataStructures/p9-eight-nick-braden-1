import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import java.util.*;

public class Solver {

    // class for game nodes
    private static class SearchNode implements Comparable<SearchNode> {
        private final Board board;
        private final int moves;
        private final SearchNode previous;
        private final int priority;

        public SearchNode(Board board, int moves, SearchNode previous) {
            this.board = board;
            this.moves = moves;
            this.previous = previous;
            // manhattan seems to work better
            this.priority = board.manhattan() + moves;
        }

        public int compareTo(SearchNode that) {
            return this.priority - that.priority;
        }
    }

    private SearchNode solution;
    private boolean solvable;

    public Solver(Board initial) {
        // throw error if board is null
        if (initial == null) {
            throw new IllegalArgumentException("board cannot be null");
        }

        // need two queues for original and twin
        MinPQ<SearchNode> pq = new MinPQ<>();
        MinPQ<SearchNode> twinPq = new MinPQ<>();

        // start the search
        pq.insert(new SearchNode(initial, 0, null));
        twinPq.insert(new SearchNode(initial.twin(), 0, null));

        // keep searching until we find a solution
        while (!pq.isEmpty() && !twinPq.isEmpty()) {
            // get next boards to check
            SearchNode node = pq.delMin();
            SearchNode twinNode = twinPq.delMin();

            // check if we solved it
            if (node.board.isGoal()) {
                solvable = true;
                solution = node;
                break;
            }

            // check if twin is solved (means original can't be solved)
            if (twinNode.board.isGoal()) {
                solvable = false;
                solution = null;
                break;
            }

            // try all possible next moves
            for (Board neighbor : node.board.neighbors()) {
                // don't go back to previous board
                if (node.previous != null && neighbor.equals(node.previous.board)) {
                    continue;
                }
                pq.insert(new SearchNode(neighbor, node.moves + 1, node));
            }

            // do same for twin
            for (Board neighbor : twinNode.board.neighbors()) {
                if (twinNode.previous != null && neighbor.equals(twinNode.previous.board)) {
                    continue;
                }
                twinPq.insert(new SearchNode(neighbor, twinNode.moves + 1, twinNode));
            }
        }
    }

    // check if puzzle can be solved
    public boolean isSolvable() {
        return solvable;
    }

    // get number of moves needed
    public int moves() {
        if (!isSolvable()) {
            return -1; // can't be solved
        }
        return solution.moves;
    }

    // get the steps to solve it
    public Iterable<Board> solution() {
        if (!isSolvable()) {
            return null;
        }

        // make a list of all the boards in the solution
        LinkedList<Board> path = new LinkedList<>();
        SearchNode current = solution;

        // add boards in the right order
        while (current != null) {
            path.addFirst(current.board);
            current = current.previous;
        }

        return path;
    }

    // main function to run the program
    public static void main(String[] args) {
        // get puzzle from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve it
        Solver solver = new Solver(initial);

        // show the answer
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}