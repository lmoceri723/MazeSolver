/**
 * Solves the given maze using DFS or BFS
 * @author Ms. Namasivayam
 * @version 03/10/2023
 */

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class MazeSolver {
    private Maze maze;

    public MazeSolver() {
        this.maze = null;
    }

    public MazeSolver(Maze maze) {
        this.maze = maze;
    }

    public void setMaze(Maze maze) {
        this.maze = maze;
    }

    /**
     * Starting from the end cell, backtracks through
     * the parents to determine the solution
     * @return An arraylist of MazeCells to visit in order
     */
    public ArrayList<MazeCell> getSolution() {

        // Creates a stack representing all cells needed to be traversed through to reach the solution
        Stack<MazeCell> solutionCells = new Stack<MazeCell>();
        // Adds the end cell to the stack
        solutionCells.add(maze.getEndCell());
        while (solutionCells.peek() != maze.getStartCell())
        {
            solutionCells.push(solutionCells.peek().getParent());
        }
        // Adds the cells to an ArrayList, switching the order as to return them in the correct order
        ArrayList<MazeCell> output = new ArrayList<MazeCell>();
        int size = solutionCells.size();
        for (int i = 0; i < size; i++)
        {
            output.add(solutionCells.pop());
        }

        // Returns an ArrayList of cells leading to the solution
        return output;
    }

    /**
     * Performs a Depth-First Search to solve the Maze
     * @return An ArrayList of MazeCells in order from the start to end cell
     */
    public ArrayList<MazeCell> solveMazeDFS() {

        // Creates a stack to store the future cells to be explored
        Stack<MazeCell> toBeExplored = new Stack<MazeCell>();
        // Keeps track of the current cell being checked
        MazeCell currentCell = maze.getStartCell();

        // Runs until the current cell is set as the end cell
        while (currentCell != maze.getEndCell())
        {
            // Keeps track of the row + column of currentCell
            int row = currentCell.getRow(), col = currentCell.getCol();

            // Checks if each neighboring cell exists and is valid, then pushes it to toBeExplored and updates its info
            checkAndPush(toBeExplored, currentCell, row - 1, col);
            checkAndPush(toBeExplored, currentCell, row, col + 1);
            checkAndPush(toBeExplored, currentCell, row + 1, col);
            checkAndPush(toBeExplored, currentCell, row, col - 1);

            // Pops from toBeExplored and runs the loop again on the new cell
            currentCell = toBeExplored.pop();
        }

        // Returns the solution after a path has been marked in each cell's parent
        return getSolution();
    }

    /**
     * Performs a Breadth-First Search to solve the Maze
     * @return An ArrayList of MazeCells in order from the start to end cell
     */
    public ArrayList<MazeCell> solveMazeBFS() {
        // Creates a queue to store the future cells to be explored
        Queue<MazeCell> toBeExplored = new LinkedList<MazeCell>();
        // Keeps track of the current cell being checked
        MazeCell currentCell = maze.getStartCell();

        // Runs until the current cell is set as the end cell
        while (currentCell != maze.getEndCell())
        {
            // Keeps track of the row + column of currentCell
            int row = currentCell.getRow(), col = currentCell.getCol();

            // Checks if each neighboring cell exists and is valid, then pushes it to toBeExplored and updates its info
            checkAndPush(toBeExplored, currentCell, row - 1, col);
            checkAndPush(toBeExplored, currentCell, row, col + 1);
            checkAndPush(toBeExplored, currentCell, row + 1, col);
            checkAndPush(toBeExplored, currentCell, row, col - 1);

            // Removes from toBeExplored and runs the loop again on the new cell
            currentCell = toBeExplored.remove();
        }

        // Returns the solution after a path has been marked in each cell's parent
        return getSolution();
    }

    /* Checks if a cell at row, col exists and is valid, then adds it to toBeExplored,
    sets its parent, and marks it as explored */
    public void checkAndPush(Stack<MazeCell> toBeExplored, MazeCell parent, int row, int col)
    {
        // Checks if the cell exists
        if (maze.isValidCell(row, col))
        {
            // Creates a variable to store the cell
            MazeCell cell = maze.getCell(row, col);
            // Pushes it to toBeExplored
            toBeExplored.push(cell);
            // Sets its parent and its explored status to true
            cell.setParent(parent);
            cell.setExplored(true);
        }
    }

    // checkAndPush overloaded to accept a Queue instead of a Stack
    public void checkAndPush(Queue<MazeCell> toBeExplored, MazeCell parent, int row, int col)
    {
        if (maze.isValidCell(row, col))
        {
            MazeCell cell = maze.getCell(row, col);
            // Adds it to toBeExplored instead of pushing
            toBeExplored.add(cell);
            cell.setParent(parent);
            cell.setExplored(true);
        }
    }

    public static void main(String[] args) {
        // Create the Maze to be solved
        Maze maze = new Maze("Resources/maze3.txt");

        // Create the MazeSolver object and give it the maze
        MazeSolver ms = new MazeSolver();
        ms.setMaze(maze);

        // Solve the maze using DFS and print the solution
        ArrayList<MazeCell> sol = ms.solveMazeDFS();
        maze.printSolution(sol);

        // Reset the maze
        maze.reset();

        // Solve the maze using BFS and print the solution
        sol = ms.solveMazeBFS();
        maze.printSolution(sol);
    }
}
