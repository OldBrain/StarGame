package ru.geekbrains.math;

import com.badlogic.gdx.math.Vector2;

public class ThreeD {

  public Vector2 getCoordinate(float z, Vector2 pos) {
    pos.x = pos.x / z;
    pos.y = pos.y / z;
    return pos;
  }
}
