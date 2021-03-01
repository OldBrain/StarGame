package ru.geekbrains.base;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import ru.geekbrains.math.Rect;
import ru.geekbrains.math.Rnd;
import ru.geekbrains.math.ThreeD;

public abstract class Fon extends Sprite  {
  ThreeD threeD;
  protected Rect worldBounds;
  private final float DISTANCE = -1f;
  float z = DISTANCE;
  private final float speed = 0.9955f;


  public Fon(TextureRegion region) {
    super(region);
    if (region == null) {
      throw new RuntimeException("Region is null");
    }
    threeD = new ThreeD();
    worldBounds = new Rect();
  }

  @Override
  public void update(float delta) {
    z = speed;
    pos.x = threeD.getCoordinate(z, pos).x;
    pos.y = threeD.getCoordinate(z, pos).y;
    checkBounds();
  }


  public void checkBounds() {
    if (getRight() < worldBounds.getLeft()) {
      refreshPosition();
    }
    if (getLeft() > worldBounds.getRight()) {
      refreshPosition();
    }
    if (getTop() < worldBounds.getBottom()) {
      refreshPosition();
    }
  }
    public void resize(Rect worldBounds) {
      this.worldBounds = worldBounds;
      float x = Rnd.nextFloat(worldBounds.getLeft(), worldBounds.getRight());
      float y = Rnd.nextFloat(worldBounds.getBottom(), worldBounds.getTop());
      pos.set(x, y);
  }

    public void refreshPosition() {
      pos.y = Rnd.nextFloat(worldBounds.getBottom(), worldBounds.getTop());
      pos.x = Rnd.nextFloat(worldBounds.getLeft(), worldBounds.getRight());
  }
}
