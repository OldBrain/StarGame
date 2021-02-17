package ru.geeckbrains.stargame.sound;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;


public class StartSound {
  public static final float SOUND_VOL = 0.1f;

  private Sound soundLaser;
  private Sound soundBullet;
  private Sound soundExplosion;

  public StartSound() {
    soundLaser =
        Gdx.audio.newSound(Gdx.files.internal("sounds/laser.wav"));
    soundBullet =
        Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav"));
    soundExplosion =
        Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav"));
  }

  public void playLaser() {
    soundLaser.play(SOUND_VOL);
  }

  public void playBullet() {
    soundBullet.play(SOUND_VOL);
  }

  public void playExplosion() {
    soundExplosion.play(SOUND_VOL);
  }
}
