package ru.geekbrains.bonus;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.Rect;
import ru.geekbrains.math.Rnd;

import java.util.ArrayList;
import java.util.List;

public class BonusPool extends Sprite {
  public final int BONUS_QUANTITY = 10;

  List<Bonus> bonuses = new ArrayList<Bonus>();
  List<Bonus> activeBonuses = new ArrayList<Bonus>();

  TextureAtlas atlas;

  protected FactoryBonus factoryBonus = new FactoryBonus();
  protected Rect worldBounds;

  public BonusPool(TextureAtlas atlas, Rect worldBounds) {
    this.atlas = atlas;
    this.worldBounds = worldBounds;
    freeBonusGenerate();
  }

  public Bonus getAnyBonus(BonusPool bonusPool) {
    switch ((int) Rnd.nextFloat(1, 1)) {
      case 1:
        return factoryBonus.greatBonus(BonusType.HEAL, atlas, bonusPool);
      default:
        throw new RuntimeException("Неверный параметр");
    }
  }

  @Override
  public void update(float delta) {
    for (Bonus bon : bonuses) {
      super.update(delta);
      if (bon.isActive) {
        bon.update(delta);
      }
    }

  }

  @Override
  public void draw(SpriteBatch batch) {
    for (Bonus bon : bonuses) {
      if (bon.isActive) {
        bon.draw(batch);
      }
    }
  }

  public void freeBonusGenerate() {
    for (int i = 0; i < BONUS_QUANTITY; i++) {
      bonuses.add(getAnyBonus(this));
    }
  }

  public void bonusActivate() {
    for (int i = 0; i < bonuses.size(); i++) {
      if (!bonuses.get(i).isActive) {
        bonuses.get(i).isActive = true;
        bonuses.get(i).initCoordinate();
        break;
      }
    }
  }

  public void bonusDeactivation() {
    for (int i = 0; i < bonuses.size(); i++) {
      if (bonuses.get(i).isActive) {
        bonuses.get(i).isActive = false;
        System.out.println("Bonus delete");
        break;
      }
    }

  }

  public void bonusDeactivationAll() {
    for (int i = 0; i < bonuses.size(); i++) {
      if (bonuses.get(i).isActive) {
        bonuses.get(i).isActive = false;
      }
    }

  }

  public List<Bonus> getActiveBonuses() {
    activeBonuses.clear();
    for (Bonus bon : bonuses) {
      if (bon.isActive) {
        activeBonuses.add(bon);
      }
    }

    return activeBonuses;
  }

  public void dispose() {
    bonuses.clear();
    bonuses.clear();
  }


}
