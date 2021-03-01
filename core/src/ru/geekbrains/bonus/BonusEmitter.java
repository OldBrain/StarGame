package ru.geekbrains.bonus;

public class BonusEmitter {

  private BonusPool bonusPool;
  private float generateInterval = 15f;
  private float generateTimer;


  public BonusEmitter(BonusPool bonusPool) {
    this.bonusPool = bonusPool;
  }

  public void generate(float delta) {
    generateTimer += delta;
    if (generateTimer >= generateInterval) {
      generateTimer = 0f;
      bonusPool.bonusActivate();
    }
  }
}
