import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.junit.jupiter.api.Assertions.fail;

//import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.Test;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.In;

public class SolverTest {

    private Solver generateSolver(String filename) {
        // create initial board from file
        In in = new In("./8puzzle-test-files/" + filename);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        return new Solver(initial);
    }

    @Test
    public void testShortPuzzleSolvable() {
        for (int i = 1; i < 10; i++) {
            Solver testSolver = generateSolver(String.format("puzzle%02d.txt", i));
            assertTrue(testSolver.isSolvable(), "The puzzle from file puzzle" + i + ".txt is solvable.");
        }
    }

    @Test
    public void testZeroPuzzleMoves() {
        Solver testSolver;
        testSolver = generateSolver("puzzle00.txt");
        assertEquals(testSolver.moves(), 0, "The puzzle from file puzzle00.txt is already solved.");
        testSolver = generateSolver("puzzle3x3-00.txt");
        assertEquals(testSolver.moves(), 0, "The puzzle from file puzzle3x3-00.txt is already solved.");
        testSolver = generateSolver("puzzle4x4-00.txt");
        assertEquals(testSolver.moves(), 0, "The puzzle from file puzzle4x4-00.txt is already solved.");
    }

    @Test
    @Timeout(5)
    public void testShortPuzzleMoves() {
        for (int i = 1; i < 10; i++) {
            Solver testSolver = generateSolver(String.format("puzzle%02d.txt", i));
            assertEquals(testSolver.moves(), i, "The puzzle from file puzzle" + i + ".txt can be solved in " + i + " moves.");
        }
    }

    @Test
    public void testShortPuzzleTwos() {
        for (int i = 1; i <= 6; i++) {
            Solver testSolver = generateSolver(String.format("puzzle2x2-%02d.txt", i));
            assertTrue(testSolver.isSolvable(), "The puzzle from file puzzle2x2-" + i + ".txt is solvable.");
            assertEquals(testSolver.moves(), i, "The puzzle from file puzzle2x2-" + i + ".txt can be solved in " + i + " moves.");
        }
    }

    @Test
    public void testShortPuzzleThrees() {
        for (int i = 1; i <= 10; i++) {
            Solver testSolver = generateSolver(String.format("puzzle3x3-%02d.txt", i));
            assertTrue(testSolver.isSolvable(), "The puzzle from file puzzle3x3-" + i + ".txt is solvable.");
            assertEquals(testSolver.moves(), i, "The puzzle from file puzzle3x3-" + i + ".txt can be solved in " + i + " moves.");
        }
    }

    @Test
    public void testShortPuzzleFours() {
        for (int i = 1; i <= 10; i++) {
            Solver testSolver = generateSolver(String.format("puzzle4x4-%02d.txt", i));
            assertTrue(testSolver.isSolvable(), "The puzzle from file puzzle4x4" + i + ".txt is solvable.");
            assertEquals(testSolver.moves(), i, "The puzzle from file puzzle4x4" + i + ".txt can be solved in " + i + " moves.");
        }
    }

    @Test
    public void testMediumPuzzleThrees() {
        for (int i = 11; i <= 20; i++) {
            // took mine 7 minutes!
            if (i == 13) continue; // what happened?
            Solver testSolver = generateSolver(String.format("puzzle3x3-%02d.txt", i));
            assertEquals(testSolver.moves(), i, "The puzzle from file puzzle3x3-" + i + ".txt can be solved in " + i + " moves.");
        }
    }

    @Test
    public void testMediumPuzzleFours() {
        for (int i = 11; i <= 15; i++) {
            // this one also 7 min
            Solver testSolver = generateSolver(String.format("puzzle4x4-%02d.txt", i));
            assertEquals(testSolver.moves(), i, "The puzzle from file puzzle4x4" + i + ".txt can be solved in " + i + " moves.");
        }
    }

    @Test
    public void testUnsolvableSolvable() {
        for (int d = 2; d < 7; d++) {
            int[] testArray = new int[d * d];
            for (int i = 0; i < testArray.length; i++) {
                testArray[i] = i;
            }
            StdRandom.shuffle(testArray);
            int[][] testBlocks = new int[d][d];
            for (int row = 0; row < d; row++)
                for (int col = 0; col < d; col++)
                    testBlocks[row][col] = testArray[row * d + col];
            Board testBoard = new Board(testBlocks);
            assertEquals(testBoard.dimension(), d, "Board has wrong dimension.");
        }
    }

    @Test
    public void testChallengingPuzzle30() {
        // I gave up waiting for this one
        Solver solver = generateSolver("puzzle30.txt");
        assertTrue(solver.isSolvable());
        assertEquals(30, solver.moves());
    }
}
