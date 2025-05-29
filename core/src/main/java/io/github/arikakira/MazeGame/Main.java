package io.github.arikakira.MazeGame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

import com.badlogic.gdx.graphics.Color;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture image;
    private Texture rArrowTexture;
    private Texture lArrowTexture;
    private Texture uArrowTexture;
    private Texture dArrowTexture;

    private SpriteBatch spriteBatch;
    private FitViewport viewport;

    @Override
    public void create() {
        batch = new SpriteBatch();
        image = new Texture("libgdx.png");

        spriteBatch = new SpriteBatch();
        rArrowTexture = new Texture("rightArrow.png");
        lArrowTexture = new Texture("leftArrow.png");
        uArrowTexture = new Texture("upArrow.png");
        dArrowTexture = new Texture("downArrow.png");
        viewport = new FitViewport(9, 7);
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

    }

    public void logic() {
        
    }
    
    public void draw() {
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();
        spriteBatch.draw(rArrowTexture, 7,3,2,1);
        spriteBatch.draw(lArrowTexture, 0,3,2,1);
        spriteBatch.draw(uArrowTexture, 4,5,1,2);
        spriteBatch.draw(dArrowTexture, 4,0,1,2);
        spriteBatch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        image.dispose();
    }
}
