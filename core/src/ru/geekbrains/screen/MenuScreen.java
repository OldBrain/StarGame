package ru.geekbrains.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.BaseScreen;
import ru.geekbrains.math.Rect;
import ru.geekbrains.sprite.*;

public class MenuScreen extends BaseScreen {

    private static final int STAR_COUNT = 256;
    private static final int ASTEROID_COUNT =15;

    private final Game game;

    private Star3d[] stars;
    private Asteroid[] asteroids;

    private ButtonExit buttonExit;
    private ButtonPlay buttonPlay;

    public MenuScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        super.show();
        stars = new Star3d[STAR_COUNT];
        asteroids = new Asteroid[ASTEROID_COUNT];
        for (int i = 0; i < STAR_COUNT; i++) {
            stars[i] = new Star3d(atlas);
        }
        for (int i = 0; i < ASTEROID_COUNT; i++) {
            asteroids[i] = new Asteroid(fonAtlas);
        }

        buttonExit = new ButtonExit(atlas);
        buttonPlay = new ButtonPlay(atlas, game);
    }

    @Override
    public void render(float delta) {
        update(delta);
        draw();
    }

    @Override
    public void dispose() {
        bg.dispose();
        atlas.dispose();
        fonAtlas.dispose();
        super.dispose();
    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        for (Star3d star : stars) {
            star.resize(worldBounds);
        }
        for (Asteroid a:asteroids ) {
            a.resize(worldBounds);
        }

        buttonExit.resize(worldBounds);
        buttonPlay.resize(worldBounds);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        buttonExit.touchDown(touch, pointer, button);
        buttonPlay.touchDown(touch, pointer, button);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        buttonExit.touchUp(touch, pointer, button);
        buttonPlay.touchUp(touch, pointer, button);
        return false;
    }

    private void update(float delta) {
        for (Star3d star : stars) {
            star.update(delta);
            }
        for (Asteroid a:asteroids ) {
            a.update(delta);
        }

    }

    private void draw() {
        Gdx.gl.glClearColor(0.3f, 0.6f, 0.4f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        for (Star3d star : stars) {
            star.draw(batch);
        }
        for (Asteroid a:asteroids ) {
            a.draw(batch);
        }
        buttonExit.draw(batch);
        buttonPlay.draw(batch);
        batch.end();
    }
}
