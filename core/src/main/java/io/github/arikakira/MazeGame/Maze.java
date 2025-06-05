package io.github.arikakira.MazeGame;

public class Maze {
    private int currentRow;
    private int currentCol;
    private int endRow = 13;
    private int endCol = 5;
    private int coins = 0;
    private int health = 4;
    private int roll = 0;
    private boolean gamblingTime = false;
    private boolean canSeeStatus = false;
    private boolean gameStarted = false;
    private boolean ranIntoMonster = false;
    private boolean gotCoin = false;
    private boolean broke = true;

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
            if(coins >= 1) {
                broke = false;
                coins--;
                randomEvent();
            } else {
                broke = true;
            }
        } else {
            if(maze[r][c].equals(" o ")) {
                coins++;
                gotCoin = true;
            }
            if(maze[r][c].equals(">-<")) {
                health--;
                ranIntoMonster = true;
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

    public boolean ranIntoMonster() {
        return ranIntoMonster;
    }

    public void setRanIntoMonster(boolean r) {
        ranIntoMonster = r;
    }

    public boolean gotCoin() {
        return gotCoin;
    }

    public void setGotCoin(boolean c) {
        gotCoin = c;
    }

    public boolean isBroke() {
        return broke;
    }

    public void randomEvent() {
        int random = (int) (Math.random() * 2) + 8;
        switch(random) {      // teleport to random spot
            case 1:
                System.out.println("rolled a 1");
                roll = 1;
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
                break;
            case 2:
                System.out.println("rolled a 2");
                roll = 2;
                coins--;
                break;
            case 3:
                System.out.println("rolled a 3");
                roll = 3;
                coins = coins + 2;
                break;
            case 4:
                System.out.println("rolled a 4");
                roll = 4;
                health--;
                break;
            case 5:
                System.out.println("rolled a 5");
                roll = 5;
                health = health + 2;
                break;
            case 6:
                System.out.println("rolled a 6");
                roll = 6;
                coins = coins + 3;
                break;
            case 7:
                System.out.println("rolled a 7");
                roll = 7;
                canSeeStatus = true;
                break;
            case 8:
                System.out.println("rolled a 8");       // horizontal direction for exit
                roll = 8;
                break;
            case 9:
                System.out.println("rolled a 9");       // vertical direction for exit
                roll = 9;
                break;
            case 10:
                System.out.println("rolled a 10");
                roll = 10;
                break;
        }
    }
    
    public int getRoll() {
        return roll;
    }

    public String hExitDirection() {
        if(currentCol<endCol) {
            return "right";
        } else {
            return "left";
        }
    }

    public String vExitDirection() {
        if(currentRow<endRow) {
            return "down";
        } else {
            return "up";
        }
    }
}
