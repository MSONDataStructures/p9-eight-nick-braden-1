import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.junit.jupiter.api.Assertions.fail;

//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.Test;

//import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.In;

import java.util.Arrays;
import java.util.Scanner;

public class BoardTest {

    private Board generateBoard(String filename) {
        // read in the board specified in the filename
        In in = new In("8puzzle-test-files/" + filename);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tiles[i][j] = in.readInt();
            }
        }
        return new Board(tiles);
    }

    @Test
    public void testDimension() {
        int[] dimArray = {
                10, 2, 9, 2, 3, 3, 4, 3, 3, 4,
                9, 3, 5, 4, 7, 8, 10, 3, 3, 3,
                3, 3, 3, 3, 3, 3, 3, 3, 3, 3,
                3, 3, 4, 4, 4, 4, 4, 4, 4, 4,
                4, 5, 4, 4, 5, 4, 4, 4, 4, 4, 4
        };

        for (int i = 0; i < 10; i++) {
            Board test = generateBoard("puzzle0" + i + ".txt");
            assertEquals(dimArray[i], test.dimension(), "Board " + i + " has wrong dimension.");
        }
        for (int i = 10; i < 51; i++) {
            Board test = generateBoard("puzzle" + i + ".txt");
            assertEquals(dimArray[i], test.dimension(), "Board " + i + " has wrong dimension.");
        }
    }

    @Test
    public void testHamming() {
        int[] hammingArray = {
                0, 1, 2, 3, 4, 5, 6, 4, 6, 9,
                10, 6, 11, 13, 7, 15, 12, 8, 7, 6,
                7, 5, 8, 7, 8, 7, 8, 7, 7, 8,
                6, 7, 11, 10, 9, 13, 13, 14, 10, 13,
                13, 20, 14, 14, 21, 13, 15, 14, 15, 14, 14
        };

        for (int i = 0; i < 10; i++) {
            Board test = generateBoard("puzzle0" + i + ".txt");
            assertEquals(hammingArray[i], test.hamming(), "Board " + i + " has wrong hamming distance.");
        }
        for (int i = 10; i < 51; i++) {
            Board test = generateBoard("puzzle" + i + ".txt");
            assertEquals(hammingArray[i], test.hamming(), "Board " + i + " has wrong hamming distance.");
        }
    }

    @Test
    public void testManhattan() {
        int[] manhattanArray = {
                0, 1, 2, 3, 4, 5, 6, 7, 8, 9,
                10, 9, 12, 13, 8, 15, 14, 13, 12, 11,
                16, 11, 12, 15, 20, 15, 18, 17, 18, 21,
                16, 21, 16, 17, 20, 23, 20, 29, 22, 29,
                30, 33, 28, 33, 34, 33, 38, 31, 38, 35, 38
        };

        for (int i = 0; i < 10; i++) {
            Board test = generateBoard("puzzle0" + i + ".txt");
            assertEquals(manhattanArray[i], test.manhattan(), "Board " + i + " has wrong manhattan distance.");
        }
        for (int i = 10; i < 51; i++) {
            Board test = generateBoard("puzzle" + i + ".txt");
            assertEquals(manhattanArray[i], test.manhattan(), "Board " + i + " has wrong manhattan distance.");
        }
    }

    @Test
    public void testToString() {
        Board testBoard;
        int[][] expectedTiles;
        int[][] testTiles;
        String testString;
        String filename;

        filename = "puzzle04.txt";
        testBoard = generateBoard(filename);
        testString = testBoard.toString();
        expectedTiles = new int[][]{
                {0, 1, 3},
                {4, 2, 5},
                {7, 8, 6}
        };
        testTiles = stringToTiles(testString);
        assertTrue(Arrays.deepEquals(testTiles, expectedTiles), "Bad string for " + filename);

        filename = "puzzle00.txt";
        testBoard = generateBoard(filename);
        testString = testBoard.toString();
        expectedTiles = new int[][]{
                {1, 2, 3, 4, 5, 6, 7, 8, 9, 10},
                {11, 12, 13, 14, 15, 16, 17, 18, 19, 20},
                {21, 22, 23, 24, 25, 26, 27, 28, 29, 30},
                {31, 32, 33, 34, 35, 36, 37, 38, 39, 40},
                {41, 42, 43, 44, 45, 46, 47, 48, 49, 50},
                {51, 52, 53, 54, 55, 56, 57, 58, 59, 60},
                {61, 62, 63, 64, 65, 66, 67, 68, 69, 70},
                {71, 72, 73, 74, 75, 76, 77, 78, 79, 80},
                {81, 82, 83, 84, 85, 86, 87, 88, 89, 90},
                {91, 92, 93, 94, 95, 96, 97, 98, 99, 0}
        };
        testTiles = stringToTiles(testString);
        assertTrue(Arrays.deepEquals(testTiles, expectedTiles), "Bad string for " + filename);

        filename = "puzzle06.txt";
        testBoard = generateBoard(filename);
        testString = testBoard.toString();
        expectedTiles = new int[][]{
                {0, 1, 2, 3},
                {5, 6, 7, 4},
                {9, 10, 11, 8},
                {13, 14, 15, 12}
        };
        testTiles = stringToTiles(testString);
        assertTrue(Arrays.deepEquals(testTiles, expectedTiles), "Bad string for " + filename);

        filename = "puzzle09.txt";
        testBoard = generateBoard(filename);
        testString = testBoard.toString();
        expectedTiles = new int[][]{
                {2, 0, 3, 4},
                {1, 10, 6, 8},
                {5, 9, 7, 12},
                {13, 14, 11, 15}
        };
        testTiles = stringToTiles(testString);
        assertTrue(Arrays.deepEquals(testTiles, expectedTiles), "Bad string for " + filename);

        filename = "puzzle23.txt";
        testBoard = generateBoard(filename);
        testString = testBoard.toString();
        expectedTiles = new int[][]{
                {5, 0, 4},
                {2, 3, 8},
                {7, 1, 6}
        };
        testTiles = stringToTiles(testString);
        assertTrue(Arrays.deepEquals(testTiles, expectedTiles), "Bad string for " + filename);

        filename = "puzzle2x2-unsolvable1.txt";
        testBoard = generateBoard(filename);
        testString = testBoard.toString();
        expectedTiles = new int[][]{
                {1, 0},
                {2, 3}
        };
        testTiles = stringToTiles(testString);
        assertTrue(Arrays.deepEquals(testTiles, expectedTiles), "Bad string for " + filename);
    }

    @Test
    public void testEquals() {
        Board board1 = generateBoard("puzzle22.txt");
        Board board2 = generateBoard("puzzle22.txt");
        Board board3 = generateBoard("puzzle23.txt");
        assertTrue(board1.equals(board1));
        assertTrue(board1.equals(board2));
        assertFalse(board1.equals(board3));
        Board board4 = new Board(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}});
        int[][] grid5 = new int[3][3];
        grid5[0][0] = 1;
        grid5[0][1] = 2;
        grid5[0][2] = 3;
        grid5[1][0] = 4;
        grid5[1][1] = 5;
        grid5[1][2] = 6;
        grid5[2][0] = 7;
        grid5[2][1] = 8;
        grid5[2][2] = 9;
        Board board5 = new Board(grid5);
        assertEquals(board4, board5);
    }

    @Test
    public void testTwin() {
        String[] files = {
                "puzzle04.txt", "puzzle00.txt", "puzzle06.txt",
                "puzzle09.txt", "puzzle23.txt", "puzzle2x2-unsolvable1.txt"
        };
        for (String file : files) {
            Board test = generateBoard(file);
            int n = test.dimension();
            Board testTwin = test.twin();
            String testString = test.toString();
            String twinString = testTwin.toString();
            int[][] testTiles = stringToTiles(testString);
            int[][] twinTiles = stringToTiles(twinString);

            int diffCount = 0;
            int[] diffRow = new int[2];
            int[] diffCol = new int[2];
            for (int row = 0; row < n; row++) {
                for (int col = 0; col < n; col++) {
                    if (testTiles[row][col] != twinTiles[row][col]) {
                        if (diffCount < 2) {
                            diffRow[diffCount] = row;
                            diffCol[diffCount] = col;
                        }
                        diffCount++;
                    }
                }
            }

            assertEquals(2, diffCount, "Need to change only two tiles.");
            if (diffCount == 2) {
                assertNotEquals(0, testTiles[diffRow[0]][diffCol[0]], "Need to swap two non-zero tiles.");
                assertNotEquals(0, testTiles[diffRow[1]][diffCol[1]], "Need to swap two non-zero tiles.");
                assertNotEquals(0, twinTiles[diffRow[0]][diffCol[0]], "Need to swap two non-zero tiles.");
                assertNotEquals(0, twinTiles[diffRow[1]][diffCol[1]], "Need to swap two non-zero tiles.");
                assertEquals(twinTiles[diffRow[0]][diffCol[0]], testTiles[diffRow[1]][diffCol[1]], "Tiles need to be swapped");
                assertEquals(twinTiles[diffRow[1]][diffCol[1]], testTiles[diffRow[0]][diffCol[0]], "Tiles need to be swapped");
            }
        }
    }

    @Test
    public void testIsGoal() {
        assertTrue(generateBoard("puzzle00.txt").isGoal());
        assertFalse(generateBoard("puzzle04.txt").isGoal());
        assertFalse(generateBoard("puzzle16.txt").isGoal());
        assertFalse(generateBoard("puzzle06.txt").isGoal());
        assertFalse(generateBoard("puzzle09.txt").isGoal());
        assertFalse(generateBoard("puzzle23.txt").isGoal());
        assertFalse(generateBoard("puzzle2x2-unsolvable1.txt").isGoal());
        assertFalse(generateBoard("puzzle3x3-unsolvable1.txt").isGoal());
        assertTrue(generateBoard("puzzle3x3-00.txt").isGoal());
        assertTrue(generateBoard("puzzle4x4-00.txt").isGoal());
    }

    @Test
    public void testNeighbors() {
        Board testBoard;
        Board neighbor1;
        Board neighbor2;
        Board neighbor3;
        Board neighbor4;
        boolean n1Found;
        boolean n2Found;
        boolean n3Found;
        boolean n4Found;
        int count;
        Iterable<Board> testNeighbors;

        testBoard = generateBoard("puzzle3x3-00.txt");
        neighbor1 = new Board(new int[][] {
                {1, 2, 3},
                {4, 5, 0},
                {7, 8, 6}
        });
        n1Found = false;
        neighbor2 = new Board(new int[][] {
                {1, 2, 3},
                {4, 5, 6},
                {7, 0, 8}
        });
        n2Found = false;
        count = 0;

        testNeighbors = testBoard.neighbors();
        for (Board nb : testNeighbors) {
            if (nb.equals(neighbor1)) {
                n1Found = true;
            } else if (nb.equals(neighbor2)) {
                n2Found = true;
            }
            count++;
        }
        assertEquals(2, count);
        assertTrue(n1Found);
        assertTrue(n2Found);

        testBoard = generateBoard("puzzle3x3-01.txt");
        neighbor1 = new Board(new int[][] {
                {1, 2, 0},
                {4, 5, 3},
                {7, 8, 6}
        });
        n1Found = false;
        neighbor2 = new Board(new int[][] {
                {1, 2, 3},
                {4, 0, 5},
                {7, 8, 6}
        });
        n2Found = false;
        neighbor3 = new Board(new int[][] {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 0}
        });
        n3Found = false;
        count = 0;

        testNeighbors = testBoard.neighbors();
        for (Board nb : testNeighbors) {
            if (nb.equals(neighbor1)) {
                n1Found = true;
            } else if (nb.equals(neighbor2)) {
                n2Found = true;
            } else if (nb.equals(neighbor3)) {
                n3Found = true;
            }
            count++;
        }
        assertEquals(3, count);
        assertTrue(n1Found);
        assertTrue(n2Found);
        assertTrue(n3Found);

        testBoard = generateBoard("puzzle3x3-02.txt");
        neighbor1 = new Board(new int[][] {
                {1, 0, 3},
                {4, 2, 5},
                {7, 8, 6}
        });
        n1Found = false;
        neighbor2 = new Board(new int[][] {
                {1, 2, 3},
                {0, 4, 5},
                {7, 8, 6}
        });
        n2Found = false;
        neighbor3 = new Board(new int[][] {
                {1, 2, 3},
                {4, 5, 0},
                {7, 8, 6}
        });
        n3Found = false;
        neighbor4 = new Board(new int[][] {
                {1, 2, 3},
                {4, 8, 5},
                {7, 0, 6}
        });
        n4Found = false;
        count = 0;

        testNeighbors = testBoard.neighbors();
        for (Board nb : testNeighbors) {
            if (nb.equals(neighbor1)) {
                n1Found = true;
            } else if (nb.equals(neighbor2)) {
                n2Found = true;
            } else if (nb.equals(neighbor3)) {
                n3Found = true;
            } else if (nb.equals(neighbor4)) {
                n4Found = true;
            }
            count++;
        }
        assertEquals(4, count);
        assertTrue(n1Found);
        assertTrue(n2Found);
        assertTrue(n3Found);
        assertTrue(n4Found);
    }

    private int[][] stringToTiles(String input) {
        // helper method converts string representation of a board
        // into a 2D array on ints with the tiles from the board
        // sample input
        // 3
        //  1 0 3
        //  4 2 5
        //  7 8 6
        Scanner testScan = new Scanner(input);
        // check first line for dimension
        if (!testScan.hasNextLine()) {
            throw new IllegalArgumentException("Input string is empty.");
        }
        Scanner dimLine = new Scanner(testScan.nextLine().trim());
        if (!dimLine.hasNextInt()) {
            throw new IllegalArgumentException("First line should have dimension.");
        }
        int dim = Integer.parseInt(dimLine.nextLine().trim());
        int[][] result = new int[dim][dim];

        // check board for correct tiles
        for (int row = 0; row < dim; row++) {
            if (!testScan.hasNextLine()) {
                throw new IllegalArgumentException("Input does not have enough lines.");
            }
            Scanner lineScan = new Scanner(testScan.nextLine());
            for (int col = 0; col < dim; col++) {
                if (!lineScan.hasNextInt()) {
                    throw new IllegalArgumentException("Input row " + row + " does not have enough columns.");
                }
                result[row][col] = lineScan.nextInt();
            }
        }
        return result;
    }
}
