package ru.geeckbrains.stargame.sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import ru.geeckbrains.stargame.base.Sprite;
import ru.geeckbrains.stargame.math.Rect;

import javax.swing.plaf.synth.Region;

public class Ship extends Sprite {

  private static final float HEIGHT = 0.2f;
  private Vector2 v;
  private Rect worldBounds;

  public Ship(TextureAtlas atlas) {
    super(new TextureRegion(atlas.findRegion("main_ship"),
        0, 0,
        atlas.findRegion("main_ship").originalWidth/2,
        atlas.findRegion("main_ship").originalHeight));
    v = new Vector2(0, 0);
  }

  @Override
  public void update(float dt) {
    pos.mulAdd(v, dt);
    super.update(dt);
  }

  @Override
  public void resize(Rect worldBounds) {
    this.worldBounds = worldBounds;
    setHeightProportion(HEIGHT);
    float x = worldBounds.getLeft()-getLeft();
    float y = worldBounds.getBottom()-getBottom()-getBottom()/10;
    pos.set(x, y);
  }
}
