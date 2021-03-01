package ru.geekbrains.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import ru.geekbrains.base.Fon;
import ru.geekbrains.math.Rnd;

public class Star3d extends Fon {

  public Star3d(TextureAtlas atlas) {
    super(atlas.findRegion("star"));
    setHeightProportion(Rnd.nextFloat(0.005f, 0.007f));
  }
}
