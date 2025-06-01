package io.github.arikakira.MazeGame;

public class Maze {
    private int currentRow;
    private int currentCol;
    private String wall = "XXX";
    private String emptySpace = "   ";
    private String wheel = " 7 ";
    private int endRow = 13;
    private int endCol = 5;
    private int coins = 0;
    private boolean gamblingTime = false;

    private String[][] maze = 
    {
        {"XXX","XXX","XXX","XXX","XXX","XXX","XXX","XXX","XXX","XXX","XXX","XXX","XXX","XXX","XXX"},
        {"XXX","   ","   "," o ","XXX","   ","   "," o ","   "," o ","777","XXX","   ","777","XXX"},
        {"XXX","777","XXX","   ","XXX","   ","XXX","XXX","   ","XXX","XXX","XXX","   ","XXX","XXX"},
        {"XXX","XXX","XXX","   "," o ","   "," o ","XXX","   ","   ","   ","XXX"," o ","   ","XXX"},
        {"XXX","777","XXX","   ","XXX","XXX","   ","XXX","XXX","   "," o ","   ","   ","   ","XXX"},
        {"XXX","   "," o ","   ","XXX","777","   ","   ","   ","   ","XXX","   ","XXX","777","XXX"},
        {"XXX","   ","XXX","XXX","XXX","XXX","XXX","XXX","   ","XXX","XXX"," o ","XXX","XXX","XXX"},
        {"XXX","   ","XXX"," o ","   ","XXX","777","   "," o ","XXX","   ","   ","XXX","777","XXX"},
        {"XXX"," o ","   ","   ","   ","XXX","XXX","   ","   ","   ","   ","   ","XXX","   ","XXX"},
        {"XXX","   ","777","XXX","   ","XXX"," o ","   ","XXX","   ","XXX","   "," o ","   ","XXX"},
        {"XXX","   ","XXX","XXX"," o ","   ","   ","   ","XXX","XXX","XXX","XXX","   ","XXX","XXX"},
        {"XXX","   "," o ","XXX","   ","XXX","XXX"," o ","XXX","   ","XXX","   ","   ","777","XXX"},
        {"XXX","XXX","   ","XXX","   ","   ","XXX","   ","XXX","777","   "," o ","XXX","XXX","XXX"},
        {"XXX","777","   ","XXX","XXX","^v^","XXX","777","XXX","   ","XXX","   ","   ","777","XXX"},
        {"XXX","XXX","XXX","XXX","XXX","XXX","XXX","XXX","XXX","XXX","XXX","XXX","XXX","XXX","XXX"}
    };

     private String[] spaces = {"   ", "^v^", "777", " o "};
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

    public boolean rightAvail() {
        for(String space : spaces) {
            if(maze[currentRow][currentCol+1] == space) {
                return true;
            }
        }
        return false;
    }

    public boolean leftAvail() {
        for(String space : spaces) {
            if(maze[currentRow][currentCol-1] == space) {
                return true;
            }
        }
        return false;
    }

    public boolean upAvail() {
        for(String space : spaces) {
            if(maze[currentRow-1][currentCol] == space) {
                return true;
            }
        }
        return false;
    }

    public boolean downAvail() {
        for(String space : spaces) {
            if(maze[currentRow+1][currentCol] == space) {
                return true;
            }
        }
        return false;
    }

    public boolean reachEnd() {
        return (currentRow==endRow && currentCol == endCol);
    }


    public boolean move(String input) {
        if(input.equals("right")) {
            if(rightAvail()) {
                if(maze[currentRow][currentCol+1].equals(" o ")) {
                    coins++;
                }
                if(maze[currentRow][currentCol+1].equals("777")) {
                    gamblingTime = true;
                    // Random chance to teleport to a random spot
                    int randomRow = (int)(Math.random() * (maze.length - 2)) + 1; // Avoid first and last row
                    int randomCol = (int)(Math.random() * (maze[0].length - 2)) + 1; // Avoid first and last column
                    maze[currentRow][currentCol] = "   ";
                    maze[randomRow][randomCol] = "0-0";
                    currentRow = randomRow;
                    currentCol = randomCol;
                    return true;
                }
                maze[currentRow][currentCol] = "   ";
                maze[currentRow][currentCol+1] = "0-0";
                currentCol = currentCol + 1;
                return true;
            }
            return false;
        }
        if(input.equals("left")) {
            if(leftAvail()) {
                if(maze[currentRow][currentCol-1].equals(" o ")) {
                    coins++;
                }
                maze[currentRow][currentCol] = "   ";
                maze[currentRow][currentCol-1] = "0-0";
                currentCol = currentCol - 1;
                return true;
            }
                return false;
        }
        if(input.equals("up")) {
            if(upAvail()) {
                if(maze[currentRow-1][currentCol].equals(" o ")) {
                    coins++;
                }
                maze[currentRow][currentCol] = "   ";
                maze[currentRow-1][currentCol] = "0-0";
                currentRow = currentRow - 1;
                return true;
            }
                return false;
        }
        if(input.equals("down")) {
            if(downAvail()) {
                if(maze[currentRow+1][currentCol].equals(" o ")) {
                    coins++;
                }
                maze[currentRow][currentCol] = "   ";
                maze[currentRow+1][currentCol] = "0-0";
                currentRow = currentRow + 1;
                return true;
            }
                return false;
        }
        return false;
    }

    public int getCoins() {
        return coins;
    }

    public boolean isGamblingTime() {
        return gamblingTime;
    }
}
