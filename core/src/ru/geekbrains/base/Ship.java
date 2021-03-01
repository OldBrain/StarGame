package ru.geekbrains.base;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import ru.geekbrains.math.Rect;
import ru.geekbrains.pool.BulletPool;
import ru.geekbrains.pool.ExplosionPool;
import ru.geekbrains.screen.GameScreen;
import ru.geekbrains.sprite.Bullet;
import ru.geekbrains.sprite.Explosion;
import ru.geekbrains.sprite.HpBar;

public class Ship extends Sprite {

  private static final float DAMAGE_ANIMATE_INTERVAL = 0.1f;
  private static final float DAMAGE_TEXT_INTERVAL = 0.5f;

  protected Vector2 v0;
  protected Vector2 v;

  protected Rect worldBounds;
  protected ExplosionPool explosionPool;
  protected BulletPool bulletPool;
  protected TextureRegion bulletRegion;
  protected Vector2 bulletV;
  protected Vector2 bulletPos;
  protected float bulletHeight;
  protected int damage;

  protected float reloadInterval;
  protected float reloadTimer;

  protected int hp;

  protected Sound sound;
  private float damageAnimateTimer = DAMAGE_ANIMATE_INTERVAL;
  private float damageTextTimer = DAMAGE_TEXT_INTERVAL;

  protected StringBuilder dmg = new StringBuilder();
  protected float acceptedDamage;

  protected HpBar hpBar;
  protected float barWidth;

  public Ship() {
  }

  public Ship(TextureRegion region, int rows, int cols, int frames) {
    super(region, rows, cols, frames);
    }


  @Override
  public void update(float delta) {
    pos.mulAdd(v, delta);
    reloadTimer += delta;
    if (reloadTimer >= reloadInterval) {
      reloadTimer = 0f;
      shoot();
    }
    damageAnimateTimer += delta;
    if (damageAnimateTimer >= DAMAGE_ANIMATE_INTERVAL) {
      frame = 0;
    }
    damageTextTimer += delta;
    if (damageTextTimer >= DAMAGE_TEXT_INTERVAL) {
      acceptedDamage = 0;
    }
      }

  @Override
  public void destroy() {
    super.destroy();
    boom();
  }

  @Override
  public void draw(SpriteBatch batch) {
    super.draw(batch);
    if (acceptedDamage != 0) {
      showDamage(batch);
    }
  }

  private void showDamage(SpriteBatch batch) {
    dmg.setLength(0);
    if (acceptedDamage > 0) {
      dmg.append("+");
    } else {
      dmg.append("-");
    }
    dmg.append(acceptedDamage);
    GameScreen.font.draw(
        batch,
        dmg,
        pos.x + getHalfWidth(),
        pos.y - getHalfHeight(),
        Align.right
    );
  }

  public void damage(int damage) {
    acceptedDamage = -damage;
    hp -= damage;
    if (hp <= 0) {
      hp = 0;
      destroy();
    }
    frame = 1;
    damageAnimateTimer = 0f;
    damageTextTimer = 0f;
  }

  public int getDamage() {
    return damage;
  }

  public int getHp() {
    return hp;
  }

  private void shoot() {
    Bullet bullet = bulletPool.obtain();
    bullet.set(this, bulletRegion, bulletPos, bulletV, bulletHeight, worldBounds, damage);
//    sound.play();
  }

  private void boom() {
    Explosion explosion = explosionPool.obtain();
    explosion.set(getHeight(), pos);
  }
}

