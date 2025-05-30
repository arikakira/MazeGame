package io.github.arikakira.MazeGame;

public class Maze {
    private int currentRow;
    private int currentCol;
    private String wall = "XXX";
    private String emptySpace = "   ";
    private String wheel = " 7 ";
    private int endRow = 13;
    private int endCol = 5;

    private String[][] maze = 
    {
        {"XXX","XXX","XXX","XXX","XXX","XXX","XXX","XXX","XXX","XXX","XXX","XXX","XXX","XXX","XXX"},
        {"XXX","   ","   ","   ","XXX","   ","   ","   ","   ","   ","   ","XXX","   ","   ","XXX"},
        {"XXX","   ","XXX","   ","XXX","   ","XXX","XXX","   ","XXX","XXX","XXX","   ","XXX","XXX"},
        {"XXX","XXX","XXX","   ","   ","   ","   ","XXX","   ","   ","   ","XXX","   ","   ","XXX"},
        {"XXX","   ","XXX","   ","XXX","XXX","   ","XXX","XXX","   ","   ","   ","   ","   ","XXX"},
        {"XXX","   ","   ","   ","XXX","   ","   ","   ","   ","   ","XXX","   ","XXX","   ","XXX"},
        {"XXX","   ","XXX","XXX","XXX","XXX","XXX","XXX","   ","XXX","XXX","   ","XXX","XXX","XXX"},
        {"XXX","   ","XXX","   ","   ","XXX","   ","   ","   ","XXX","   ","   ","XXX","   ","XXX"},
        {"XXX","   ","   ","   ","   ","XXX","XXX","   ","   ","   ","   ","   ","XXX","   ","XXX"},
        {"XXX","   ","   ","XXX","   ","XXX","   ","   ","XXX","   ","XXX","   ","   ","   ","XXX"},
        {"XXX","   ","XXX","XXX","   ","   ","   ","   ","XXX","XXX","XXX","XXX","   ","XXX","XXX"},
        {"XXX","   ","   ","XXX","   ","XXX","XXX","   ","XXX","   ","XXX","   ","   ","   ","XXX"},
        {"XXX","XXX","   ","XXX","   ","   ","XXX","   ","XXX","   ","   ","   ","XXX","XXX","XXX"},
        {"XXX","   ","   ","XXX","XXX","^v^","XXX","   ","XXX","   ","XXX","   ","   ","   ","XXX"},
        {"XXX","XXX","XXX","XXX","XXX","XXX","XXX","XXX","XXX","XXX","XXX","XXX","XXX","XXX","XXX"}
    };

     private String[] spaces = {"   ", "^v^", "777", "x-x"};
    // 777 is a wheel that gives a random chance of getting these:
    // teleport to random spot, inverted controls but dont say directly, trivia minigame?
    // seeing whole maze for a few sec, move 2 spaces, bomb to break walls but can destroy items too, know which direction exit is

    public Maze(int r, int c) {
        maze[r][c] = "0-0";
        currentRow = r;
        currentCol = c;
    }

    public void getMaze() {
        for(int r=0; r<maze.length; r++) {
            for(int c=0; c<maze[r].length; c++) {
                System.out.print(maze[r][c]);
            }
            System.out.println();
        }
    }
}
