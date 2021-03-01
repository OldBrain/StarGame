package ru.geekbrains.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import ru.geekbrains.base.Fon;
import ru.geekbrains.math.Rnd;

public class Asteroid extends Fon {

  private final float ROTATION_SPEED=50f;
  private final float INCREASE_RATE = 50f;
  private final float INITIAL_SIZE=0.01f;

  public Asteroid(TextureAtlas atlas) {
      super(atlas.findRegion(new StringBuffer().append("ast").append((int) Rnd.nextFloat(0,3)).toString()));
    setHeightProportion(INITIAL_SIZE);
  }

  @Override
  public void update(float delta) {
    super.update(delta);
    setAngle(getAngle()+delta*ROTATION_SPEED);
    setHeightProportion(getWidth()+delta/INCREASE_RATE);
  }

  @Override
  public void refreshPosition() {
    super.refreshPosition();
    setAngle(Rnd.nextFloat(0,360));
    setHeightProportion(INITIAL_SIZE);
  }
}

