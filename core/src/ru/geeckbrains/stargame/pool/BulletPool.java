package ru.geeckbrains.stargame.pool;

import ru.geeckbrains.stargame.base.SpritesPool;
import ru.geeckbrains.stargame.sprite.Bullet;

public class BulletPool extends SpritesPool<Bullet> {

    @Override
    protected Bullet newObject() {
        return new Bullet();
    }
}
