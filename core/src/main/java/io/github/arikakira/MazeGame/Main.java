package io.github.arikakira.MazeGame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import java.lang.Thread;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private Texture arrowTexture;
    private Texture chanceTexture;
    private Texture returnTexture;
    private Texture coinTexture;

    private SpriteBatch spriteBatch;
    private FitViewport viewport;

    private Sprite rArrowSprite;
    private Sprite lArrowSprite;
    private Sprite uArrowSprite;
    private Sprite dArrowSprite;
    private Sprite chanceSprite;
    private Sprite returnSprite;
    private Sprite coinSprite;

    private FreeTypeFontGenerator fontGenerator;
    private FreeTypeFontParameter fontParameter;
    private BitmapFont font;
    private SpriteBatch fontBatch;

    private int roll = 0;

    Maze game = new Maze(1, 6);

    @Override
    public void create() {
        spriteBatch = new SpriteBatch();
        arrowTexture = new Texture("arrow.png");
        chanceTexture = new Texture("placeholderChance.png");
        returnTexture = new Texture("returnButton.png");
        coinTexture = new Texture("coin.png");
        viewport = new FitViewport(1000, 800);

        rArrowSprite = new Sprite(arrowTexture);
        rArrowSprite.setSize(200, 100);
        lArrowSprite = new Sprite(arrowTexture);
        lArrowSprite.setSize(200, 100);
        uArrowSprite = new Sprite(arrowTexture);
        uArrowSprite.setSize(200, 100);
        dArrowSprite = new Sprite(arrowTexture);
        dArrowSprite.setSize(200, 100);
        chanceSprite = new Sprite(chanceTexture);
        chanceSprite.setSize(440, 644);
        returnSprite = new Sprite(returnTexture);
        returnSprite.setSize(270, 120);
        coinSprite = new Sprite(coinTexture);
        coinSprite.setSize(95, 117);

        fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("1942.ttf"));
        fontParameter = new FreeTypeFontParameter();
        fontParameter.size = 37;
        // fontParameter.borderWidth = 1;
        // fontParameter.borderColor = Color.RED;
        fontParameter.color = Color.WHITE;
        font = fontGenerator.generateFont(fontParameter); // font size 12 pixels
        fontBatch = new SpriteBatch();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true); // true centers the camera
    }

    @Override
    public void render() {
        if(!game.isStarted()) {
            startInstructions();
        } else {
            draw();
            input();
        }
    }

    public void startInstructions() {
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();
        fontBatch.begin();
        font.draw(fontBatch, "Welcome to the Maze Game!", 200, Gdx.graphics.getHeight()/2f+122);
        font.draw(fontBatch, "Use the arrows to move around.", 140, Gdx.graphics.getHeight()/2f+50);
        font.draw(fontBatch, "Reach the exit to win!", 240, Gdx.graphics.getHeight()/2f+18);
        font.draw(fontBatch, "Avoid enemies and collect coins.", 115, Gdx.graphics.getHeight()/2f-14);
        font.draw(fontBatch, "Click anywhere to start.", 225, Gdx.graphics.getHeight()/2f-86);
        spriteBatch.end();
        fontBatch.end();

        if(Gdx.input.isTouched()) {
            game.setStarted(true);
            game.getMaze();
            wait(100);
        }
    }

    public void input() {
        if(Gdx.input.getX() < rArrowSprite.getX() + rArrowSprite.getWidth() &&
           Gdx.input.getX() > rArrowSprite.getX() &&
           Gdx.input.getY() < rArrowSprite.getY() + rArrowSprite.getHeight() &&
           Gdx.input.getY() > rArrowSprite.getY()) {
            if(Gdx.input.isTouched() && game.rightAvail() && !game.isGamblingTime() && !game.isDead()) {
                game.getMaze();
                game.move("right");
                game.getMaze();
                wait(100);
            }
        }

        if(Gdx.input.getX() < lArrowSprite.getX() + lArrowSprite.getWidth() &&
           Gdx.input.getX() > lArrowSprite.getX() &&
           Gdx.input.getY() < lArrowSprite.getY() + lArrowSprite.getHeight() &&
           Gdx.input.getY() > lArrowSprite.getY()) {
            if(Gdx.input.isTouched() && game.leftAvail() && !game.isGamblingTime() && !game.isDead()) {
                game.getMaze();
                game.move("left");
                game.getMaze();
                wait(100);
            }
        }

        if(Gdx.input.getX() < uArrowSprite.getX() + uArrowSprite.getWidth() - 50 &&
           Gdx.input.getX() > uArrowSprite.getX() + 50 &&
           700-Gdx.input.getY() < uArrowSprite.getY() + uArrowSprite.getHeight() &&
           850-Gdx.input.getY() > uArrowSprite.getY()) {
            if(Gdx.input.isTouched() && game.upAvail() && !game.isGamblingTime() && !game.isDead()) {
                game.getMaze();
                game.move("up");
                game.getMaze();
                wait(100);
            }
        }

        if(Gdx.input.getX() < dArrowSprite.getX() + dArrowSprite.getWidth() - 50 &&
           Gdx.input.getX() > dArrowSprite.getX() + 50 &&
           750-Gdx.input.getY() < dArrowSprite.getY() + dArrowSprite.getHeight() &&
           850-Gdx.input.getY() > dArrowSprite.getY()) {
            if(Gdx.input.isTouched() && game.downAvail() && !game.isGamblingTime() && !game.isDead()) {
                game.getMaze();
                game.move("down");
                game.getMaze();
                wait(100);
            }
        }

        if(Gdx.input.getX() < returnSprite.getX() + returnSprite.getWidth() &&
           Gdx.input.getX() > returnSprite.getX() &&
           810-Gdx.input.getY() < returnSprite.getY() + returnSprite.getHeight() &&
           790-Gdx.input.getY() > returnSprite.getY()) {
            if(Gdx.input.isTouched() && game.isGamblingTime() && !game.isDead()) {
                game.getMaze();
                game.setGamblingTime(false);
                wait(100);
            }
        }

        // if(Gdx.input.isTouched() && game.ranIntoMonster() && !game.isDead() && !game.isGamblingTime()) {
        //     game.setRanIntoMonster(false);
        // }
    }
    
    public void draw() {
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();
        fontBatch.begin();

        if(!game.reachEnd()) {
            if(game.isDead()) {     // PLAYER IS DEAD
                font.draw(fontBatch, "You died!", 400, Gdx.graphics.getHeight()/2f+37);
            } else {
                if(!game.isGamblingTime()) {        // REGULAR MAZE MOVEMENT
                    if(game.rightAvail()) {
                        rArrowSprite.draw(spriteBatch);
                        rArrowSprite.setPosition(800, 350);
                        if(game.seeMazeStatus() && game.rHasCoin()) {
                            coinSprite.draw(spriteBatch);
                            coinSprite.setPosition(800, 250);
                        }
                    }
                    if(game.leftAvail()) {
                        lArrowSprite.draw(spriteBatch);
                        lArrowSprite.setOrigin(lArrowSprite.getWidth()/2f, lArrowSprite.getHeight()/2f);
                        lArrowSprite.setRotation(180);
                        lArrowSprite.setPosition(0, 350);
                        if(game.seeMazeStatus() && game.lHasCoin()) {
                            coinSprite.draw(spriteBatch);
                            coinSprite.setPosition(100, 250);
                        }
                    }
                    if(game.upAvail()) {
                        uArrowSprite.draw(spriteBatch);
                        uArrowSprite.setOrigin(uArrowSprite.getWidth()/2f, uArrowSprite.getHeight()/2f);
                        uArrowSprite.setRotation(90);
                        uArrowSprite.setPosition( 400, 650);
                        if(game.seeMazeStatus() && game.uHasCoin()) {
                            coinSprite.draw(spriteBatch);
                            coinSprite.setPosition(550, 600);
                        }
                    }
                    if(game.downAvail()) {
                        dArrowSprite.draw(spriteBatch);
                        dArrowSprite.setOrigin(dArrowSprite.getWidth()/2f, dArrowSprite.getHeight()/2f);
                        dArrowSprite.setRotation(270);
                        dArrowSprite.setPosition(400, 50);
                        if(game.seeMazeStatus() && game.dHasCoin()) {
                            coinSprite.draw(spriteBatch);
                            coinSprite.setPosition(350, 70);
                        }
                    }
                    if(game.ranIntoMonster()) {
                        font.draw(fontBatch, "You ran into a monster!", 230, Gdx.graphics.getHeight()/2f+30);
                        font.draw(fontBatch,"You lost 1 HP!", 330, Gdx.graphics.getHeight()/2f-7);
                    }
                } else {        // GAMBLING TIME
                    chanceSprite.draw(spriteBatch);
                    chanceSprite.setPosition(20, 20);
                    returnSprite.draw(spriteBatch);
                    returnSprite.setPosition(700, 20);
                    font.draw(fontBatch, "Gambling time!", 300, 50);
                    roll = game.getRoll();
                    displayRoll(roll);
                }       // DISPLAYING PLAYER STATS
                font.draw(fontBatch, "Coins: " + game.getCoins(), 50, Gdx.graphics.getHeight()-50); // graphics.getHeight is top of the screen
                font.draw(fontBatch, "HP: " + game.getHealth(), 800, Gdx.graphics.getHeight()-50);
            }
        } else {        // REACHED EXIT
            font.draw(fontBatch, "You won!", 400, Gdx.graphics.getHeight()/2f+37);
        }
        spriteBatch.end();
        fontBatch.end();
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        arrowTexture.dispose();
        fontBatch.dispose();
    }

    public static void wait(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    public void displayRoll(int r) {
        switch(r) {
            case 1:
                font.draw(fontBatch, "You teleported to", 470, 470);
                font.draw(fontBatch, "a random spot!", 500, 420);
                break;
            case 2:
                font.draw(fontBatch, "You lost a coin!", 470, 470);
                break;
            case 3:
                font.draw(fontBatch, "You gained 2 coins!", 470, 470);
                break;
            case 4:
                font.draw(fontBatch, "You lost 1 health!", 470, 470);
                break;
            case 5:
                font.draw(fontBatch, "You gained 2 health!", 470, 470);
                break;
            case 6:
                font.draw(fontBatch, "You gained 3 coins!", 470, 470);
                break;
            case 7:
                font.draw(fontBatch, "You can see what's in", 470, 470);
                font.draw(fontBatch, "the spaces around you!", 465, 420);
                break;
            case 8:
                font.draw(fontBatch, "You got information!", 470, 470);
                break;
            case 9:
                font.draw(fontBatch, "You got information!", 470, 470);
                break;
            case 10:
                font.draw(fontBatch, "You got a bomb!", 470, 500);
                break;
        }
    }
}
