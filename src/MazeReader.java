import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Class to hold information about the maze
 */
class MazeInfo {
    char[][] maze;
    int startRow;
    int startCol;
    int endRow;
    int endCol;
    int numRows;
    int numCols;

    /**
     * Constructor for MazeInfo class
     * @param maze The maze represented as a 2D array of characters
     * @param startRow Starting row index
     * @param startCol Starting column index
     * @param endRow Ending row index
     * @param endCol Ending column index
     * @param numRows Number of rows in the maze
     * @param numCols Number of columns in the maze
     */
    public MazeInfo(char[][] maze, int startRow, int startCol, int endRow, int endCol, int numRows, int numCols) {
        this.maze = maze;
        this.startRow = startRow;
        this.startCol = startCol;
        this.endRow = endRow;
        this.endCol = endCol;
        this.numRows = numRows;
        this.numCols = numCols;
    }
}

/**
 * Class to read a maze from a file
 */
public class MazeReader {
    /**
     * Reads a maze from a file and returns MazeInfo object
     * @param filename The name of the file containing the maze
     * @return MazeInfo object containing the maze information
     * @throws IOException If an I/O error occurs while reading the file
     */
    public static MazeInfo readMazeFromFile(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        int numRows = 0;
        int numCols = 0;
        int startRow = -1;
        int startCol = -1;
        int endRow = -1;
        int endCol = -1;

        // Determine the number of rows and columns in the maze
        while ((line = reader.readLine()) != null) {
            if (!line.trim().isEmpty()) { // Skip empty lines
                numRows++;
                numCols = Math.max(numCols, line.length());
            }
        }
        reader.close();

        char[][] maze = new char[numRows][numCols];

        // Populate the maze array
        reader = new BufferedReader(new FileReader(filename));
        int row = 0;
        while ((line = reader.readLine()) != null) {
            if (!line.trim().isEmpty()) { // Skip empty lines
                for (int col = 0; col < line.length(); col++) {
                    char cell = line.charAt(col);
                    maze[row][col] = cell;
                    if (cell == 'S') {
                        startRow = row;
                        startCol = col;
                    } else if (cell == 'F') {
                        endRow = row;
                        endCol = col;
                    }
                }
                // Fill remaining columns with '0'
                for (int col = line.length(); col < numCols; col++) {
                    maze[row][col] = '0';
                }
                row++;
            }
        }
        reader.close();

        return new MazeInfo(maze, startRow, startCol, endRow, endCol, numRows, numCols);
    }
}
