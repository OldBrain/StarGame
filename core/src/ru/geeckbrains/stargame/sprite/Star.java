package ru.geeckbrains.stargame.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import ru.geeckbrains.stargame.base.Sprite;
import ru.geeckbrains.stargame.math.Rect;
import ru.geeckbrains.stargame.math.Rnd;

public class Star extends Sprite {
  private static final float HEIGHT = 0.01f;
  private Vector2 v;
  private Rect worldBounds;
  private float heightProportion;

  public Star(TextureAtlas atlas) {
    super(atlas.findRegion("star"));
    float x = Rnd.nextFloat(-0.005f, 0.005f);
//    float y = Rnd.nextFloat(-0.2f, -0.01f);
    v = new Vector2(x, 0);
  }

  @Override
  public void update(float dt) {
    pos.mulAdd(v, dt);
    if (getRight() < worldBounds.getLeft()) {
      setLeft(worldBounds.getRight());
    }
    if (getLeft() > worldBounds.getRight()) {
      setRight(worldBounds.getLeft());
    }
    if (getTop() < worldBounds.getBottom()) {
      setBottom(worldBounds.getTop());
    }
  }

  @Override
  public void resize(Rect worldBounds) {
    this.worldBounds = worldBounds;
    heightProportion = Rnd.nextFloat(HEIGHT, 0.002f);
    setHeightProportion(heightProportion);
    v.y = heightProportion * (-4);
    float x = Rnd.nextFloat(worldBounds.getLeft(), worldBounds.getRight());
    float y = Rnd.nextFloat(worldBounds.getBottom(), worldBounds.getTop());
    pos.set(x, y);
  }
}
