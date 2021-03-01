package ru.geekbrains.sprite;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.Rect;

public class HpBar extends Sprite {
  private float width;
  private static final float PADDING = 0.005f;

  protected Rect worldBounds;

  public HpBar(TextureRegion region) {
    super(region);
//    setHeight(0.007f);

  }

  @Override
  public void resize(Rect worldBounds) {
    this.worldBounds = worldBounds;
    super.resize(worldBounds);
    setHeightProportion(0.0075f);
    angle = 90;
    halfHeight = 0.002f;
    halfWidth = width;
  }

  @Override
  public void draw(SpriteBatch batch) {
    super.draw(batch);
  }

  @Override
  public void update(float delta) {
    super.update(delta);
  }

  public void setPosition(float x, float y) {
    this.pos.x = x + PADDING;
    this.pos.y = y - PADDING;
  }

  public void setBarWidth(float width) {
    this.width = width * 0.6f;
  }
}
