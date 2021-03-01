package ru.geekbrains.pool;

import com.badlogic.gdx.audio.Sound;

import ru.geekbrains.base.SpritesPool;
import ru.geekbrains.math.Rect;
import ru.geekbrains.sprite.EnemyShip;
import ru.geekbrains.sprite.HpBar;

public class EnemyPool extends SpritesPool<EnemyShip> {

    private BulletPool bulletPool;
    private ExplosionPool explosionPool;
    private Rect worldBounds;
    private Sound sound;
    private HpBar hpBar;

    public EnemyPool(BulletPool bulletPool,
                     ExplosionPool explosionPool,
                     Rect worldBounds,
                     Sound sound,
                     HpBar hpBar) {
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        this.worldBounds = worldBounds;
        this.sound = sound;
        this.hpBar = hpBar;
    }

    @Override
    protected EnemyShip newObject() {
        return new EnemyShip(bulletPool,
            explosionPool, worldBounds,
            sound, hpBar);
    }
}
