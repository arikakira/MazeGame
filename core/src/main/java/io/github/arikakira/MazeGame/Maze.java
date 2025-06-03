package io.github.arikakira.MazeGame;

public class Maze {
    private int currentRow;
    private int currentCol;
    private int endRow = 13;
    private int endCol = 5;
    private int coins = 0;
    private int health = 4;
    private boolean gamblingTime = false;
    private boolean canSeeStatus = true;
    private boolean gameStarted = false;

    private String[][] maze = 
    {
        {"XXX","XXX","XXX","XXX","XXX","XXX","XXX","XXX","XXX","XXX","XXX","XXX","XXX","XXX","XXX"},
        {"XXX","   ","   "," o ","XXX","   ","   "," o ","   "," o ","777","XXX","   ","777","XXX"},
        {"XXX","777","XXX",">-<","XXX","   ","XXX","XXX","   ","XXX","XXX","XXX","   ","XXX","XXX"},
        {"XXX","XXX","XXX","   "," o ",">-<"," o ","XXX","   ","   ",">-<","XXX"," o ",">-<","XXX"},
        {"XXX","777","XXX","   ","XXX","XXX","   ","XXX","XXX","   "," o ","   ","   ","   ","XXX"},
        {"XXX","   "," o ","   ","XXX","777","   ","   ","   ","   ","XXX","   ","XXX","777","XXX"},
        {"XXX","   ","XXX","XXX","XXX","XXX","XXX","XXX",">-<","XXX","XXX"," o ","XXX","XXX","XXX"},
        {"XXX","   ","XXX"," o ",">-<","XXX","777","   "," o ","XXX",">-<","   ","XXX","777","XXX"},
        {"XXX"," o ","   ","   ","   ","XXX","XXX","   ","   ","   ","   ","   ","XXX","   ","XXX"},
        {"XXX","   ","777","XXX","   ","XXX"," o ","   ","XXX",">-<","XXX","   "," o ","   ","XXX"},
        {"XXX",">-<","XXX","XXX"," o ","   ","   ",">-<","XXX","XXX","XXX","XXX",">-<","XXX","XXX"},
        {"XXX","   "," o ","XXX","   ","XXX","XXX"," o ","XXX",">-<","XXX","   "," o ","777","XXX"},
        {"XXX","XXX","   ","XXX","   ","   ","XXX","   ","XXX","777","   "," o ","XXX","XXX","XXX"},
        {"XXX","777","   ","XXX","XXX","^v^","XXX","777","XXX",">-<","XXX","   "," o ","777","XXX"},
        {"XXX","XXX","XXX","XXX","XXX","XXX","XXX","XXX","XXX","XXX","XXX","XXX","XXX","XXX","XXX"}
    };

     private String[] spaces = {"   ", "^v^", "777", " o ", ">-<"};
    // 777 is a wheel that gives a random chance of getting these:
    // teleport to random spot, inverted controls but dont say directly, trivia minigame?
    // seeing whole maze for a few sec, move 2 spaces, bomb to break walls but can destroy items too, know which direction exit is

    public Maze(int r, int c) {
        maze[r][c] = "0-0";
        currentRow = r;
        currentCol = c;
    }

    public boolean isStarted() {
        return gameStarted;
    }

    public void setStarted(boolean start) {
        gameStarted = start;
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
        return checkAvail(currentRow, currentCol+1);
    }

    public boolean leftAvail() {
        return checkAvail(currentRow, currentCol-1);
    }

    public boolean upAvail() {
        return checkAvail(currentRow-1, currentCol);
    }

    public boolean downAvail() {
        return checkAvail(currentRow+1, currentCol);
    }

    public boolean checkAvail(int r, int c) {
        for(String space : spaces) {
            if(maze[r][c] == space) {
                return true;
            }
        }
        return false;
    }

    public boolean rHasCoin() {
        return maze[currentRow][currentCol+1].equals(" o ");
    }

    public boolean lHasCoin() {
        return maze[currentRow][currentCol-1].equals(" o ");
    }

    public boolean uHasCoin() {
        return maze[currentRow-1][currentCol].equals(" o ");
    }

    public boolean dHasCoin() {
        return maze[currentRow+1][currentCol].equals(" o ");
    }

    public boolean reachEnd() {
        return (currentRow==endRow && currentCol == endCol);
    }

    public boolean isDead() {
        return (health <= 0);
    }

    public void move(String input) {
        if(input.equals("right")) {
            if(rightAvail()) {
                moveSpace(currentRow, currentCol+1);
            }
        }
        if(input.equals("left")) {
            if(leftAvail()) {
                moveSpace(currentRow, currentCol-1);
            }
        }
        if(input.equals("up")) {
            if(upAvail()) {
                moveSpace(currentRow-1, currentCol);
            }
        }
        if(input.equals("down")) {
            if(downAvail()) {
                moveSpace(currentRow+1, currentCol);
            }
        }
    }

    public void moveSpace(int r, int c) {
        if(maze[r][c].equals("777")) {
            gamblingTime = true;
            randomEvent();
        } else {
            if(maze[r][c].equals(" o ")) {
                coins++;
            }
            if(maze[r][c].equals(">-<")) {
                health--;
            }
            maze[currentRow][currentCol] = "   ";
            maze[r][c] = "0-0";
            currentRow = r;
            currentCol = c;
        }
    }

    public int getCoins() {
        return coins;
    }

    public boolean isGamblingTime() {
        return gamblingTime;
    }

    public void setGamblingTime(boolean g) {
        gamblingTime = g;
    }

    public int getHealth() {
        return health;
    }

    public boolean seeMazeStatus() {
        return canSeeStatus;
    }

    public void randomEvent() {
        int random = (int) (Math.random() * 7) + 1;
        if(random==1) {      // teleport to random spot
            System.out.println("rolled a 1");
            int randomRow = 0;
            int randomCol = 0;
            while(maze[randomRow][randomCol] != "   ") {
                randomRow = (int)(Math.random() * (maze.length - 2)) + 1; // Avoid first and last row
                randomCol = (int)(Math.random() * (maze[0].length - 2)) + 1; // Avoid first and last column
            }
            maze[currentRow][currentCol] = "   ";
            maze[randomRow][randomCol] = "0-0";
            currentRow = randomRow;
            currentCol = randomCol;
        }
        if(random==2) {
            System.out.println("rolled a 2");
            coins--;
        }
        if(random==3) {
            System.out.println("rolled a 3");
            coins = coins + 2;
        }
        if(random==4) {
            System.out.println("rolled a 4");
            health--;
        }
        if(random==5) {
            System.out.println("rolled a 5");
            health = health + 2;
        }
        if(random==6) {
            System.out.println("rolled a 6");
            coins = coins + 3;
        }
        if(random==7) {
            System.out.println("rolled a 7");
            canSeeStatus = true;
        }
        if(random==8) {
            System.out.println("rolled a 8");
        }
        if(random==9) {
            System.out.println("rolled a 9");
        }
        if(random==10) {
            System.out.println("rolled a 10");
        }
    }
}
