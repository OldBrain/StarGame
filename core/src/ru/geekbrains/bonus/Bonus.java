package ru.geekbrains.bonus;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import ru.geekbrains.base.Sprite;


public class Bonus extends Sprite {
  private static final float START_V_Y = -0.3f;
  private final int HP_HEAL = 20;

  protected TextureAtlas atlas;
  protected TextureRegion region;
  protected boolean isActive;
  protected Vector2 v;

  public Bonus(TextureRegion region) {
    super(region);
    if (region == null) {
      throw new RuntimeException("Region is null");
    }
  }

  public Vector2 getPos() {
    return pos;
  }

  public void setActive(boolean active) {
    isActive = active;
  }

  public int getHP_HEAL() {
    return HP_HEAL;
  }

  public void initCoordinate() {
  }
}
