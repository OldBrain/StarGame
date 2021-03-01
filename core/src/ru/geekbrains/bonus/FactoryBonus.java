package ru.geekbrains.bonus;


import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class FactoryBonus {

  public FactoryBonus() {
  }

  public Bonus greatBonus(BonusType typeBonus, TextureAtlas atlas, BonusPool bonusPool) {
    switch (typeBonus) {
      case SHUT:
        break;
      case HEAL:
        return new Heal(atlas, bonusPool, bonusPool.worldBounds);
    }
    return null;
  }
}
