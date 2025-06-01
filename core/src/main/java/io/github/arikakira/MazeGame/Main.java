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
    // private Texture lArrowTexture;
    // private Texture uArrowTexture;
    // private Texture dArrowTexture;
    private Texture chanceTexture;

    private SpriteBatch spriteBatch;
    private FitViewport viewport;

    private Sprite rArrowSprite;
    private Sprite lArrowSprite;
    private Sprite uArrowSprite;
    private Sprite dArrowSprite;
    private Sprite chanceSprite;

    private FreeTypeFontGenerator fontGenerator;
    private FreeTypeFontParameter fontParameter;
    private BitmapFont font;
    private SpriteBatch fontBatch;

    Maze game = new Maze(1, 6);

    @Override
    public void create() {
        spriteBatch = new SpriteBatch();
        arrowTexture = new Texture("arrow.png");
        chanceTexture = new Texture("placeholderChance.png");
        // lArrowTexture = new Texture("leftArrow.png");
        // uArrowTexture = new Texture("upArrow.png");
        // dArrowTexture = new Texture("downArrow.png");
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
        chanceSprite.setSize(437, 708);

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
        input();
        logic();
        draw();

        // ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        // batch.begin();
        // batch.draw(image, 200, 210);    // this moves the image
        // batch.end();

        // System.out.println(rArrowSprite.getX() + " " + rArrowSprite.getY());
        // System.out.println(Gdx.input.getX() + " " + Gdx.input.getY());
        // System.out.println(dArrowSprite.getX() + dArrowSprite.getWidth() + " " + uArrowSprite.getX() + " " + uArrowSprite.getY() + uArrowSprite.getHeight() + " " + uArrowSprite.getY());
    }

    public void input() {
        if(Gdx.input.getX() < rArrowSprite.getX() + rArrowSprite.getWidth() &&
           Gdx.input.getX() > rArrowSprite.getX() &&
           Gdx.input.getY() < rArrowSprite.getY() + rArrowSprite.getHeight() &&
           Gdx.input.getY() > rArrowSprite.getY()) {
            if(Gdx.input.isTouched() && game.rightAvail()) {
                System.out.println("Right arrow pressed");
                game.getMaze();
                game.move("right");
                game.getMaze();
                wait(200);
            }
        }

        if(Gdx.input.getX() < lArrowSprite.getX() + lArrowSprite.getWidth() &&
           Gdx.input.getX() > lArrowSprite.getX() &&
           Gdx.input.getY() < lArrowSprite.getY() + lArrowSprite.getHeight() &&
           Gdx.input.getY() > lArrowSprite.getY()) {
            if(Gdx.input.isTouched() && game.leftAvail()) {
                System.out.println("Left arrow pressed");
                game.getMaze();
                game.move("left");
                game.getMaze();
                wait(200);
            }
        }

        if(Gdx.input.getX() < uArrowSprite.getX() + uArrowSprite.getWidth() - 50 &&
           Gdx.input.getX() > uArrowSprite.getX() + 50 &&
           700-Gdx.input.getY() < uArrowSprite.getY() + uArrowSprite.getHeight() &&
           850-Gdx.input.getY() > uArrowSprite.getY()) {
            if(Gdx.input.isTouched() && game.upAvail()) {
                System.out.println("Up arrow pressed");
                game.getMaze();
                game.move("up");
                game.getMaze();
                wait(200);
            }
        }

        if(Gdx.input.getX() < dArrowSprite.getX() + dArrowSprite.getWidth() - 50 &&
           Gdx.input.getX() > dArrowSprite.getX() + 50 &&
           750-Gdx.input.getY() < dArrowSprite.getY() + dArrowSprite.getHeight() &&
           850-Gdx.input.getY() > dArrowSprite.getY()) {
            if(Gdx.input.isTouched() && game.downAvail()) {
                System.out.println("Down arrow pressed");
                game.getMaze();
                game.move("down");
                game.getMaze();
                wait(200);
            }
        }
    }

    public void logic() {
        
    }
    
    public void draw() {
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();
        fontBatch.begin();

        if(!game.reachEnd()) {
            if(!game.isGamblingTime()) {
                if(game.rightAvail()) {
                    rArrowSprite.draw(spriteBatch);
                    rArrowSprite.setPosition(800, 350);
                }
                // spriteBatch.draw(lArrowTexture, 0,3,2,1);
                if(game.leftAvail()) {
                    lArrowSprite.draw(spriteBatch);
                    lArrowSprite.setOrigin(lArrowSprite.getWidth()/2f, lArrowSprite.getHeight()/2f);
                    lArrowSprite.setRotation(180);
                    lArrowSprite.setPosition(0, 350);
                }
        
                //spriteBatch.draw(uArrowTexture, 4,5,1,2);
                if(game.upAvail()) {
                    uArrowSprite.draw(spriteBatch);
                    uArrowSprite.setOrigin(uArrowSprite.getWidth()/2f, uArrowSprite.getHeight()/2f);
                    uArrowSprite.setRotation(90);
                    uArrowSprite.setPosition( 400, 650);
                }
            
                // spriteBatch.draw(dArrowTexture, 4,0,1,2);
                if(game.downAvail()) {
                    dArrowSprite.draw(spriteBatch);
                    dArrowSprite.setOrigin(dArrowSprite.getWidth()/2f, dArrowSprite.getHeight()/2f);
                    dArrowSprite.setRotation(270);
                    dArrowSprite.setPosition(400, 50);
                }
            } else {
                chanceSprite.draw(spriteBatch);
                chanceSprite.setPosition(20, 20);
                font.draw(fontBatch, "Gambling time!", 300, 50);
            }
            font.draw(fontBatch, "Coins: " + game.getCoins(), 50, Gdx.graphics.getHeight()-50); // graphics.getHeight is top of the screen
        } else {
            font.draw(fontBatch, "You won!", 400, Gdx.graphics.getHeight()/2f+37);
        }
        //spriteBatch.draw(rArrowTexture, 7,3,2,1);
        
        
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
}
