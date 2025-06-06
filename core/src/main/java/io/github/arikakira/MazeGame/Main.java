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
    private Texture bombTexture;
    private Texture monsterTexture;
    private Texture slotTexture;
    private Texture dBoxTexture;

    private SpriteBatch spriteBatch;
    private FitViewport viewport;

    private Sprite rArrowSprite;
    private Sprite lArrowSprite;
    private Sprite uArrowSprite;
    private Sprite dArrowSprite;
    private Sprite chanceSprite;
    private Sprite returnSprite;
    private Sprite coinSprite;
    private Sprite bombSprite;
    private Sprite monsterSprite;
    private Sprite slotSprite;
    private Sprite dBoxSprite;

    private FreeTypeFontGenerator fontGenerator;
    private FreeTypeFontParameter fontParameter;
    private BitmapFont font;
    private SpriteBatch fontBatch;

    private int roll = 0;
    private Long messageEndTime = 0l;
    private boolean monsterMessageActive = false;
    private boolean coinMessageActive = false;

    Maze game = new Maze(1, 6);

    @Override
    public void create() {
        spriteBatch = new SpriteBatch();
        arrowTexture = new Texture("arrow.png");
        chanceTexture = new Texture("chance.png");
        returnTexture = new Texture("returnButton.png");
        coinTexture = new Texture("coin.png");
        bombTexture = new Texture("bombButton.png");
        monsterTexture = new Texture("monster.png");
        slotTexture = new Texture("slotMachine.png");
        dBoxTexture = new Texture("dialogueBox.png");
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
        chanceSprite.setSize(407, 599);
        returnSprite = new Sprite(returnTexture);
        returnSprite.setSize(270, 120);
        coinSprite = new Sprite(coinTexture);
        coinSprite.setSize(95, 117);
        bombSprite = new Sprite(bombTexture);
        bombSprite.setSize(221, 120);
        monsterSprite = new Sprite(monsterTexture);
        monsterSprite.setSize(186, 171);
        slotSprite = new Sprite(slotTexture);
        slotSprite.setSize(182, 112);
        dBoxSprite = new Sprite(dBoxTexture);
        dBoxSprite.setSize(710, 340);

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
                game.setGamblingTime(false);
                wait(100);
            }
        }

        if(Gdx.input.getX() < bombSprite.getX() + bombSprite.getWidth() &&
           Gdx.input.getX() > bombSprite.getX() &&
           810-Gdx.input.getY() < bombSprite.getY() + bombSprite.getHeight() &&
           790-Gdx.input.getY() > bombSprite.getY()) {
            if(Gdx.input.isTouched() && game.hasBomb() && !game.isDead()) {
                game.useBomb();
                game.getMaze();
                game.setHasBomb(false);
                wait(100);
            }
        }

        if(game.ranIntoMonster() && !monsterMessageActive) {
            messageEndTime = System.currentTimeMillis() + 2000;
            monsterMessageActive = true;
        }

        if(game.gotCoin() && !coinMessageActive) {
            messageEndTime = System.currentTimeMillis() + 1000;
            coinMessageActive = true;
        }
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
                        if(game.seeMazeStatus()) {
                            if(game.rHasCoin()) {
                                coinSprite.draw(spriteBatch);
                                coinSprite.setPosition(800, 250);
                            }
                            if(game.rHasMonster()) {
                                monsterSprite.draw(spriteBatch);
                                monsterSprite.setPosition(760, 200);
                            }
                            if(game.rHasGambling()) {
                                slotSprite.draw(spriteBatch);
                                slotSprite.setPosition(780, 220);
                            }
                        }
                    }
                    if(game.leftAvail()) {
                        lArrowSprite.draw(spriteBatch);
                        lArrowSprite.setOrigin(lArrowSprite.getWidth()/2f, lArrowSprite.getHeight()/2f);
                        lArrowSprite.setRotation(180);
                        lArrowSprite.setPosition(0, 350);
                        if(game.seeMazeStatus()) {
                            if(game.lHasCoin()) {
                                coinSprite.draw(spriteBatch);
                                coinSprite.setPosition(100, 250);  
                            }
                            if(game.lHasMonster()) {
                                monsterSprite.draw(spriteBatch);
                                monsterSprite.setPosition(90, 200);
                            }
                            if(game.lHasGambling()) {
                                slotSprite.draw(spriteBatch);
                                slotSprite.setPosition(90, 230);
                            }
                        }
                    }
                    if(game.upAvail()) {
                        uArrowSprite.draw(spriteBatch);
                        uArrowSprite.setOrigin(uArrowSprite.getWidth()/2f, uArrowSprite.getHeight()/2f);
                        uArrowSprite.setRotation(90);
                        uArrowSprite.setPosition( 400, 650);
                        if(game.seeMazeStatus()) {
                            if(game.uHasCoin()) {
                                coinSprite.draw(spriteBatch);
                                coinSprite.setPosition(550, 600);
                            }
                            if(game.uHasMonster()) {
                                monsterSprite.draw(spriteBatch);
                                monsterSprite.setPosition(550, 600);
                            }
                            if(game.uHasGambling()) {
                                slotSprite.draw(spriteBatch);
                                slotSprite.setPosition(550, 620);
                            }
                        }
                    }
                    if(game.downAvail()) {
                        dArrowSprite.draw(spriteBatch);
                        dArrowSprite.setOrigin(dArrowSprite.getWidth()/2f, dArrowSprite.getHeight()/2f);
                        dArrowSprite.setRotation(270);
                        dArrowSprite.setPosition(400, 50);
                        if(game.seeMazeStatus()) {
                            if(game.dHasCoin()) {
                                coinSprite.draw(spriteBatch);
                                coinSprite.setPosition(350, 70);
                            }
                            if(game.dHasMonster()) {
                                monsterSprite.draw(spriteBatch);
                                monsterSprite.setPosition(290, 30);
                            }
                            if(game.dHasGambling()) {
                                slotSprite.draw(spriteBatch);
                                slotSprite.setPosition(260, 20);
                            }
                        }
                    }
                    if(game.hasBomb()) {
                        bombSprite.draw(spriteBatch);
                        bombSprite.setPosition(20, 20);
                    }
                    if(game.ranIntoMonster() && System.currentTimeMillis() < messageEndTime) {      // display monster message
                        font.draw(fontBatch, "You ran into a monster!", 230, Gdx.graphics.getHeight()/2f+20);
                        font.draw(fontBatch,"You lost 1 HP!", 330, Gdx.graphics.getHeight()/2f-17);
                    } else if(game.ranIntoMonster()) {
                        game.setRanIntoMonster(false);
                        monsterMessageActive = false;
                    }
                    if(game.gotCoin() && System.currentTimeMillis() < messageEndTime) {      // display coin message
                        font.draw(fontBatch, "You got a coin!", 310, Gdx.graphics.getHeight()/2f+10);
                    } else if(game.gotCoin()) {
                        game.setGotCoin(false);
                        coinMessageActive = false;
                    }
                } else {        // GAMBLING TIME
                    chanceSprite.draw(spriteBatch);
                    chanceSprite.setPosition(0, 50);
                    returnSprite.draw(spriteBatch);
                    returnSprite.setPosition(700, 20);
                    dBoxSprite.draw(spriteBatch);
                    dBoxSprite.setPosition(290, 335);
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
        if(game.isBroke()) {
            font.draw(fontBatch, "Sorry, why don't you", 430, 650);
            font.draw(fontBatch, "come back once you're", 430, 600);
            font.draw(fontBatch, "more... financially", 430, 550);
            font.draw(fontBatch, "secure?", 430, 500);
        } else {
            switch(r) {
                case 1:
                    font.draw(fontBatch, "You teleported to", 430, 270);
                    font.draw(fontBatch, "a random spot!", 430, 220);

                    font.draw(fontBatch, "Sorry man, this is", 430, 650);
                    font.draw(fontBatch, "probably the worst", 430, 600);
                    font.draw(fontBatch, "thing that could've", 430, 550);
                    font.draw(fontBatch, "happened to you other", 430, 500);
                    font.draw(fontBatch, "than dying.", 430, 450);
                    break;
                case 2:
                    font.draw(fontBatch, "You lost a coin!", 430, 270);

                    font.draw(fontBatch, "On the bright side,", 430, 650);
                    font.draw(fontBatch, "think about your", 430, 600);
                    font.draw(fontBatch, "contribution to the", 430, 550);
                    font.draw(fontBatch, "economy.", 430, 500);
                    break;
                case 3:
                    font.draw(fontBatch, "You gained 2 coins!", 430, 270);

                    font.draw(fontBatch, "Planning to save money?", 430, 650);
                    font.draw(fontBatch, "When you could generate", 430, 600);
                    font.draw(fontBatch, "more by gambling? How", 430, 550);
                    font.draw(fontBatch, "preposterous.", 430, 500);
                    break;
                case 4:
                    font.draw(fontBatch, "You lost 1 health!", 430, 270);

                    font.draw(fontBatch, "If you think there are", 430, 650);
                    font.draw(fontBatch, "health packs around,", 430, 600);
                    font.draw(fontBatch, "the developer forgot to", 430, 550);
                    font.draw(fontBatch, "put them in the game.", 430, 500);
                    break;
                case 5:
                    font.draw(fontBatch, "You gained 2 health!", 430, 270);

                    font.draw(fontBatch, "You couldn't get", 430, 650);
                    font.draw(fontBatch, "healing for such a", 430, 600);
                    font.draw(fontBatch, "low price anywhere", 430, 550);
                    font.draw(fontBatch, "else.", 430, 500);
                    break;
                case 6:
                    font.draw(fontBatch, "You gained 4 coins!", 430, 270);

                    font.draw(fontBatch, "Of course you got this.", 430, 650);
                    font.draw(fontBatch, "Chance is on your side", 430, 600);
                    font.draw(fontBatch, "after all.", 430, 550);
                    break;
                case 7:
                    font.draw(fontBatch, "You can see what's in", 430, 270);
                    font.draw(fontBatch, "the spaces around you!", 430, 220);

                    font.draw(fontBatch, "Congratulations, you", 430, 650);
                    font.draw(fontBatch, "got the most useful", 430, 600);
                    font.draw(fontBatch, "effect! It was all", 430, 550);
                    font.draw(fontBatch, "worth it, wasn't it?", 430, 500);
                    font.draw(fontBatch, "Never stop gambling.", 430, 450);
                    break;
                case 8:
                    font.draw(fontBatch, "You got information!", 430, 270);
                    String vExitDirection = game.vExitDirection();
                    font.draw(fontBatch, "I'm willing to bet the", 430, 650);
                    font.draw(fontBatch, "exit is " + vExitDirection + ".", 430, 600);
                    break;
                case 9:
                    font.draw(fontBatch, "You got information!", 430, 270);
                    String hExitDirection = game.hExitDirection();
                    font.draw(fontBatch, "I'm willing to bet the", 430, 650);
                    font.draw(fontBatch, "exit is " + hExitDirection + ".", 430, 600);
                    break;
                case 10:
                    font.draw(fontBatch, "You got a bomb!", 430, 270);

                    font.draw(fontBatch, "This destroys anything", 430, 650);
                    font.draw(fontBatch, "within 2 tiles of you.", 430, 600);
                    font.draw(fontBatch, "Careful though, the", 430, 550);
                    font.draw(fontBatch, "explosion will hurt.", 430, 500);
                    font.draw(fontBatch, "And, I definitely won't", 430, 450);
                    font.draw(fontBatch, "be around to help.", 430, 400);
                    break;
            }
        }
    }
}
