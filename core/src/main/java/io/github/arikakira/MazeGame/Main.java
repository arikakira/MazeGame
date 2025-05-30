package io.github.arikakira.MazeGame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private Texture arrowTexture;
    // private Texture lArrowTexture;
    // private Texture uArrowTexture;
    // private Texture dArrowTexture;

    private SpriteBatch spriteBatch;
    private FitViewport viewport;

    private Sprite rArrowSprite;
    private Sprite lArrowSprite;
    private Sprite uArrowSprite;
    private Sprite dArrowSprite;

    Vector2 touchPos;

    @Override
    public void create() {
        spriteBatch = new SpriteBatch();
        arrowTexture = new Texture("arrow.png");
        // lArrowTexture = new Texture("leftArrow.png");
        // uArrowTexture = new Texture("upArrow.png");
        // dArrowTexture = new Texture("downArrow.png");
        viewport = new FitViewport(9, 7);

        rArrowSprite = new Sprite(arrowTexture);
        rArrowSprite.setSize(2, 1);
        lArrowSprite = new Sprite(arrowTexture);
        lArrowSprite.setSize(2, 1);
        uArrowSprite = new Sprite(arrowTexture);
        uArrowSprite.setSize(2, 1);
        dArrowSprite = new Sprite(arrowTexture);
        dArrowSprite.setSize(2, 1);

        touchPos = new Vector2();
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
    }

    public void input() {
        if(Gdx.input.isTouched()) {
            // does something when user cicks the screen
        }
    }

    public void logic() {
        
    }
    
    public void draw() {
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();

        //spriteBatch.draw(rArrowTexture, 7,3,2,1);
        rArrowSprite.draw(spriteBatch);
        rArrowSprite.setPosition(7, 3);
        // spriteBatch.draw(lArrowTexture, 0,3,2,1);
        lArrowSprite.draw(spriteBatch);
        lArrowSprite.setOrigin(lArrowSprite.getWidth()/2f, lArrowSprite.getHeight()/2f);
        lArrowSprite.setRotation(180);
        lArrowSprite.setPosition(0, 3);
        //spriteBatch.draw(uArrowTexture, 4,5,1,2);
        uArrowSprite.draw(spriteBatch);
        uArrowSprite.setOrigin(uArrowSprite.getWidth()/2f, uArrowSprite.getHeight()/2f);
        uArrowSprite.setRotation(90);
        uArrowSprite.setPosition((float) 3.5, (float) 5.5);
        // spriteBatch.draw(dArrowTexture, 4,0,1,2);
        dArrowSprite.draw(spriteBatch);
        dArrowSprite.setOrigin(dArrowSprite.getWidth()/2f, dArrowSprite.getHeight()/2f);
        dArrowSprite.setRotation(270);
        dArrowSprite.setPosition((float) 3.5, (float) 0.5);

        spriteBatch.end();
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        arrowTexture.dispose();
    }
}
