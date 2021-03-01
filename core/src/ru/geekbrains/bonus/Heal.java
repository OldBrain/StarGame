package ru.geekbrains.bonus;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import ru.geekbrains.math.Rect;
import ru.geekbrains.math.Rnd;

public class Heal extends Bonus {
  private static final float HEIGHT = 0.03f;
  private final float ROTATION_SPEED = 50f;

  private BonusPool bonusPool;
  protected Rect worldBounds;

  public Heal(TextureAtlas atlas, BonusPool bonusPool, Rect worldBounds) {
    super(atlas.findRegion("ap"));
    this.bonusPool = bonusPool;
    v = new Vector2(0.00f, -0.025f);
    this.worldBounds = worldBounds;
    setHeightProportion(HEIGHT);
    initCoordinate();
  }

  @Override
  public void update(float delta) {
    setAngle(getAngle() + delta * ROTATION_SPEED);
    pos.mulAdd(v, delta);
    checkBounds();
  }

  public void checkBounds() {
    if (getRight() < worldBounds.getLeft()) {
      bonusPool.bonusDeactivation();
    }
    if (getLeft() > worldBounds.getRight()) {
      bonusPool.bonusDeactivation();
    }
    if (getTop() < worldBounds.getBottom()) {
      bonusPool.bonusDeactivation();
    }
  }

  @Override
  public void initCoordinate() {
    setRight(Rnd.nextFloat(-0.3f, 0.3f));
    setBottom(0.5f);
  }
}
