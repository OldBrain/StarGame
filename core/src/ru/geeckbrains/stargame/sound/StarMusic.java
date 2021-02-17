package ru.geeckbrains.stargame.sound;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class StarMusic {
  Music music;

  public StarMusic(String path) {
    music = Gdx.audio.newMusic(Gdx.files.internal(path));
    music.setLooping(true);
    music.pause();
  }

  public void musicOnOff() {
    if (!music.isPlaying()) {
      music.play();
    } else {
      music.pause();
    }
  }

  public void dispose() {
    music.dispose();
  }
}
