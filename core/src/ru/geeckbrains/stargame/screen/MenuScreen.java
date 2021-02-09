package ru.geeckbrains.stargame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import ru.geeckbrains.stargame.base.BaseScreen;
import ru.geeckbrains.stargame.math.Rect;
import ru.geeckbrains.stargame.sprite.Background;
import ru.geeckbrains.stargame.sprite.StarShip;


public class MenuScreen extends BaseScreen {

    private Texture bg;
    private Background background;

    private Texture starShipTexture;
    private StarShip starShip;

    @Override
    public void show() {
        super.show();
        bg = new Texture("textures/bg.png");
        background = new Background(bg);

        starShipTexture = new Texture("textures/sh.png");
        starShip = new StarShip(starShipTexture);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.3f, 0.0f, 0.0f, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        starShip.draw(batch);
        batch.end();
        starShip.update(delta);
    }

    @Override
    public void dispose() {
        bg.dispose();
        starShipTexture.dispose();
        super.dispose();
    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);

        starShip.resize(worldBounds);

    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        starShip.touchDown(touch, pointer, button);
        return super.touchDown(touch, pointer, button);
    }
}
