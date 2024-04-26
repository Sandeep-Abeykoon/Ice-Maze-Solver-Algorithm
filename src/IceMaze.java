import java.util.*;
import java.io.IOException;

/**
 * This class represents a maze-solving algorithm using BFS.
 */
public class IceMaze {

    /**
     * Point class to represent coordinates and path information
     */
    static class Point {
        int x, y, dist;
        String path;

        /**
         * Constructor for Point class
         * @param x X-coordinate
         * @param y Y-coordinate
         * @param dist Distance
         * @param path Path
         */
        public Point(int x, int y, int dist, String path) {
            this.x = x;
            this.y = y;
            this.dist = dist;
            this.path = path;
        }
    }

    /**
     * Method to solve the maze
     * @param maze The maze represented as a 2D array of characters
     * @param startX Starting X-coordinate
     * @param startY Starting Y-coordinate
     * @param endX Ending X-coordinate
     * @param endY Ending Y-coordinate
     * @param rows Number of rows in the maze
     * @param cols Number of columns in the maze
     */
    public static void solveMaze(char[][] maze, int startX, int startY, int endX, int endY, int rows, int cols) {
        // Create a boolean array to mark visited cells
        boolean[][] visited = new boolean[rows][cols];
        // Create a queue to store points for BFS traversal
        Queue<Point> queue = new LinkedList<>();
        // Add the start point to the queue
        queue.add(new Point(startX, startY, 0, ""));

        // Define the four possible directions (right, down, left, up)
        int[] dx = {0, 1, 0, -1};
        int[] dy = {1, 0, -1, 0};
        String[] dirNames = {"right", "down", "left", "up"};

        System.out.println("Shortest path:");

        int stepCount = 1;

        // Print the starting point
        System.out.println(stepCount + ". Start at (" + (startY + 1) + "," + (startX + 1) + ")");
        stepCount++;

        // Perform BFS traversal
        while (!queue.isEmpty()) {
            Point current = queue.poll();
            int x = current.x;
            int y = current.y;
            int dist = current.dist;
            String path = current.path;

            // If the end point is reached, print the path and exit
            if (x == endX && y == endY) {
                String[] steps = path.split(",");
                for (int i = 1; i < steps.length; i++) {
                    String[] coords = steps[i].trim().split(" ");
                    System.out.println(stepCount + ". Move " + dirNames[Integer.parseInt(coords[0])] +
                            " to (" + (Integer.parseInt(coords[2]) + 1) + "," + (Integer.parseInt(coords[1]) + 1) + ")");
                    stepCount++;
                }
                System.out.println(stepCount + ". Done!");
                return;
            }

            // Explore all four directions
            for (int i = 0; i < 4; i++) {
                int newX = x + dx[i];
                int newY = y + dy[i];

                // Continue in the same direction until hitting a boundary or obstacle
                while (newX >= 0 && newY >= 0 && newX < rows && newY < cols && maze[newX][newY] != '0') {
                    if (newX == endX && newY == endY) {
                        path += "," + i + " " + newX + " " + newY;
                        String[] steps = path.split(",");
                        for (int j = 1; j < steps.length; j++) {
                            String[] coords = steps[j].trim().split(" ");
                            System.out.println(stepCount + ". Move " + dirNames[Integer.parseInt(coords[0])] +
                                    " to (" + (Integer.parseInt(coords[2]) + 1) + "," + (Integer.parseInt(coords[1]) + 1) + ")");
                            stepCount++;
                        }
                        System.out.println(stepCount + ". Done!");
                        return;
                    }
                    newX += dx[i];
                    newY += dy[i];
                }

                // Backtrack to the last valid position
                newX -= dx[i];
                newY -= dy[i];

                // Add the new position to the queue if not visited
                if (!visited[newX][newY]) {
                    visited[newX][newY] = true;
                    queue.add(new Point(newX, newY, dist + 1, path + "," + i + " " + newX + " " + newY));
                }
            }
        }

        // If no path is found, print a message
        System.out.println("No path found!");
    }

    /**
     * Main method to read the maze from file and solve it
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        String filename = "test.txt";

        try {
            // Read the maze info from the file
            MazeInfo mazeInfo = MazeReader.readMazeFromFile(filename);
            char[][] maze = mazeInfo.maze;
            int startRow = mazeInfo.startRow;
            int startCol = mazeInfo.startCol;
            int endRow = mazeInfo.endRow;
            int endCol = mazeInfo.endCol;
            int numRows = mazeInfo.numRows;
            int numCols = mazeInfo.numCols;

            // Print the maze metadata
            System.out.println("Start point: (" + startRow + ", " + startCol + ")");
            System.out.println("End point: (" + endRow + ", " + endCol + ")");
            System.out.println("Number of rows: " + numRows);
            System.out.println("Number of columns: " + numCols);
            System.out.println();

            // Solve the maze
            solveMaze(maze, startRow, startCol, endRow, endCol, numRows, numCols);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
