package ru.geeckbrains.stargame.sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import ru.geeckbrains.stargame.base.BaseButton;
import ru.geeckbrains.stargame.math.Rect;
import ru.geeckbrains.stargame.sound.StarMusic;

public class ButtonMusic extends BaseButton {
  private static final float HEIGHT = 0.1f;
  private static final float PADDING = 0.12f;
  private TextureRegion on;
  private TextureRegion off;
  private boolean isMusicON;
  private StarMusic music;
//  private TextureRegion region;

  public ButtonMusic(Texture texture) {
    super(new TextureRegion(texture, 255, 144),1,2,2);
    on = new TextureRegion(regions[0]);
    off = new TextureRegion(regions[1]);
    music = new StarMusic("sounds/music.mp3");
  }

  @Override
  public void draw(SpriteBatch batch) {
    super.draw(batch);
//    batch.draw(regions[1],0.1f,0.1f);

  }

  public TextureRegion getOn() {
    return on;
  }

  public TextureRegion getOff() {
    return off;
  }

  public void resize(Rect worldBounds) {
    setHeightProportion(HEIGHT);
    setBottom(worldBounds.getTop()-PADDING);
    setRight(worldBounds.getLeft() + PADDING);
  }

  public boolean isMusicON() {
    return isMusicON;
      }

  public void update(float dt) {
    if (isMusicON) {

    }
  }
  public void dispose() {
    music.dispose();
      }

  @Override
  public void action() {
    music.musicOnOff();
    if (isMusicON) {
      isMusicON = false;
    } else {
      isMusicON = true;
    }
  }
}
