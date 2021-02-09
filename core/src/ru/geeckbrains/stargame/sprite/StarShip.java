package ru.geeckbrains.stargame.sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import ru.geeckbrains.stargame.base.Sprite;
import ru.geeckbrains.stargame.math.Rect;

public class StarShip extends Sprite {
  private final float K = 0.05f;

  private Vector2 touch = new Vector2();
  private Vector2 vectorV = new Vector2();
  private Vector2 tmp = new Vector2();
  private float v;

  public StarShip(Texture texture) {
    super(new TextureRegion(texture));
    vectorV.set(0.0f, 0.0f);
    touch.set(0, 0);
    v = 0.05f;
  }

  @Override
  public void resize(Rect worldBounds) {

    this.pos.set(worldBounds.pos);
    setHeightProportion(0.30f);

  }

  @Override
  public void update(float delta) {
    super.update(delta);

    uTurn();
    tmp.set(touch);
    if (tmp.sub(pos).len() > v * K) {

      this.pos.mulAdd(vectorV, delta);
    } else {
      pos.set(touch);
    }

  }

  private void uTurn() {
    angle = (float) ((Math.atan2(touch.x - pos.x, -(touch.y - pos.y)) * 180.0d / Math.PI) + 180.0f);
  }

  @Override
  public boolean touchDown(Vector2 touch, int pointer, int button) {
    vectorV.set(touch.cpy().sub(pos)).setLength(v);
    this.touch = touch;
    return super.touchDown(touch, pointer, button);
  }
}
