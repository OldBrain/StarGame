package ru.geekbrains.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;

import java.util.List;

import ru.geekbrains.base.BaseScreen;
import ru.geekbrains.bonus.Bonus;
import ru.geekbrains.bonus.BonusEmitter;
import ru.geekbrains.bonus.BonusPool;
import ru.geekbrains.math.Rect;
import ru.geekbrains.pool.BulletPool;
import ru.geekbrains.pool.EnemyPool;
import ru.geekbrains.pool.ExplosionPool;
import ru.geekbrains.sprite.*;
import ru.geekbrains.utils.EnemyEmitter;
import ru.geekbrains.utils.Font;

public class GameScreen extends BaseScreen {

    private static final int STAR_COUNT = 180;
    private static final int ASTEROID_COUNT =10;
    private static final float FONT = 0.02f;
    private static final float PADDING = 0.01f;

    private static final String FRAGS = "Frags: ";
    private static final String HP = "HP: ";
    private static final String LEVEL = "Level: ";

    private enum State {PLAYING, GAME_OVER}

    private Star3d[] stars;
    private Asteroid[] asteroids;

    private BulletPool bulletPool;
    private ExplosionPool explosionPool;
    private EnemyPool enemyPool;

    private MainShip mainShip;

    private Music music;
    private Sound enemyBulletSound;
    private Sound explosionSound;

    private EnemyEmitter enemyEmitter;
    private BonusEmitter bonusEmitter;
    private State state;

    private GameOver gameOver;
    private NewGame newGame;

    public static Font font = new Font("font/font.fnt", "font/font.png");
    private StringBuilder sbFrags;
    private StringBuilder sbHp;
    private StringBuilder sbLevel;

    private int frags;

    private BonusPool bonusPool;
    private HpBar hpBar;

    @Override
    public void show() {
        super.show();
        bulletPool = new BulletPool();
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav"));
        explosionPool = new ExplosionPool(mainAtlas, explosionSound);
        enemyBulletSound = Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav"));
        hpBar = new HpBar(fonAtlas.findRegion("hpbar"));
        enemyPool = new EnemyPool(bulletPool, explosionPool, worldBounds, enemyBulletSound,hpBar);
        mainShip = new MainShip(mainAtlas, bulletPool, explosionPool,font,hpBar);
        gameOver = new GameOver(mainAtlas);
        newGame = new NewGame(mainAtlas, this);
        font.setSize(FONT);
        sbFrags = new StringBuilder();
        sbHp = new StringBuilder();
        sbLevel = new StringBuilder();
        asteroids = new Asteroid[ASTEROID_COUNT];
        stars = new Star3d[STAR_COUNT];
        for (int i = 0; i < STAR_COUNT; i++) {
            stars[i] = new Star3d(mainAtlas);
        }
        for (int i = 0; i < ASTEROID_COUNT; i++) {
            asteroids[i] = new Asteroid(fonAtlas);
        }
        enemyEmitter = new EnemyEmitter(mainAtlas, worldBounds, enemyPool);
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/music.mp3"));
        music.setLooping(true);
        music.play();
        state = State.PLAYING;
        bonusPool= new BonusPool(fonAtlas,worldBounds);
        bonusEmitter = new BonusEmitter(bonusPool);
       }

    public void startNewGame() {
        state = State.PLAYING;
        mainShip.startNewGame();
        frags = 0;
        bulletPool.freeAllActiveSprites();
        enemyPool.freeAllActiveSprites();
        explosionPool.freeAllActiveSprites();
    }

    @Override
    public void render(float delta) {
        update(delta);
        checkCollisions();
        freeAllDestroyed();
        draw();
    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        for (Star3d star : stars) {
            star.resize(worldBounds);
        }
        for (Asteroid a:asteroids ) {
            a.resize(worldBounds);
        }
        mainShip.resize(worldBounds);
        gameOver.resize(worldBounds);
        newGame.resize(worldBounds);
        hpBar.resize(worldBounds);
    }

    @Override
    public void dispose() {
        mainAtlas.dispose();
        bulletPool.dispose();
        explosionPool.dispose();
        enemyPool.dispose();
        music.dispose();
        enemyBulletSound.dispose();
        explosionSound.dispose();
        mainShip.dispose();
        font.dispose();
        bonusPool.dispose();
        super.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        if (state == State.PLAYING) {
            mainShip.keyDown(keycode);
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (state == State.PLAYING) {
            mainShip.keyUp(keycode);
        }
        return false;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        if (state == State.PLAYING) {
            mainShip.touchDown(touch, pointer, button);
        } else if (state == State.GAME_OVER) {
            newGame.touchDown(touch, pointer, button);
        }
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        if (state == State.PLAYING) {
            mainShip.touchUp(touch, pointer, button);
        } else if (state == State.GAME_OVER) {
            newGame.touchUp(touch, pointer, button);
        }
        return false;
    }

    private void update(float delta) {
        for (Star3d star : stars) {
            star.update(delta);
        }
        for (Asteroid a:asteroids ) {
            a.update(delta);
        }
        explosionPool.updateActiveSprites(delta);
        if (state == State.PLAYING) {
            mainShip.update(delta);
            bulletPool.updateActiveSprites(delta);
            enemyPool.updateActiveSprites(delta);
            enemyEmitter.generate(delta, frags);
            bonusEmitter.generate(delta);
            bonusPool.update(delta);
            hpBar.update(delta);
        } else {
            bonusPool.bonusDeactivationAll();
        }

    }

    private void checkCollisions() {
        if (state == State.GAME_OVER) {
            return;
        }
        List<EnemyShip> enemyShipList = enemyPool.getActiveObjects();
        List<Bullet> bulletList = bulletPool.getActiveObjects();
        List<Bonus> bonusList = bonusPool.getActiveBonuses();

        for (Bonus bon:bonusList) {
            float minDist = bon.getHalfWidth() + mainShip.getHalfWidth();
            if (bon.pos.dst(mainShip.pos) < minDist) {
                bon.setActive(false);
                mainShip.heal(bon.getHP_HEAL());
            }
        }



        for (EnemyShip enemyShip : enemyShipList) {
            if (enemyShip.isDestroyed()) {
                continue;
            }
            float minDist = enemyShip.getHalfWidth() + mainShip.getHalfWidth();
            if (enemyShip.pos.dst(mainShip.pos) < minDist) {
                enemyShip.destroy();
                mainShip.damage(enemyShip.getDamage());
            }
        }
        for (Bullet bullet : bulletList) {
            if (bullet.isDestroyed()) {
                continue;
            }
            if (bullet.getOwner() != mainShip) {
                if (mainShip.isBulletCollision(bullet)) {
                    mainShip.damage(bullet.getDamage());
                    bullet.destroy();
                }
                continue;
            }
            for (EnemyShip enemyShip : enemyShipList) {
                if (enemyShip.isDestroyed()) {
                    continue;
                }
                if (enemyShip.isBulletCollision(bullet)) {
                    enemyShip.damage(bullet.getDamage());
                    bullet.destroy();
                    if (enemyShip.isDestroyed()) {
                        frags++;
                    }
                }
            }
        }
        if (mainShip.isDestroyed()) {
            state = State.GAME_OVER;
        }
    }



    private void freeAllDestroyed() {
        bulletPool.freeAllDestroyedActiveSprites();
        explosionPool.freeAllDestroyedActiveSprites();
        enemyPool.freeAllDestroyedActiveSprites();
    }

    private void draw() {
        Gdx.gl.glClearColor(0.3f, 0.6f, 0.4f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        for (Star3d star : stars) {
            star.draw(batch);
        }
        for (Asteroid a:asteroids ) {
            a.draw(batch);
        }
        if (state == State.PLAYING) {
            mainShip.draw(batch);
            bulletPool.drawActiveSprites(batch);
            enemyPool.drawActiveSprites(batch);
            bonusPool.draw(batch);
            hpBar.draw(batch);
        } else if (state == State.GAME_OVER) {
            gameOver.draw(batch);
            newGame.draw(batch);
           }
        explosionPool.drawActiveSprites(batch);
        printInfo();
        batch.end();
    }

    private void printInfo() {
        sbFrags.setLength(0);
        sbHp.setLength(0);
        sbLevel.setLength(0);
        font.draw(
                batch,
                sbFrags.append(FRAGS).append(frags),
                worldBounds.getLeft() + PADDING,
                worldBounds.getTop() - PADDING
        );
        font.draw(
                batch,
                sbHp.append(HP).append(mainShip.getHp()),
                worldBounds.pos.x,
                worldBounds.getTop() - PADDING,
                Align.center
        );
        font.draw(
                batch,
                sbLevel.append(LEVEL).append(enemyEmitter.getLevel()),
                worldBounds.getRight() - PADDING,
                worldBounds.getTop() - PADDING,
                Align.right
        );
    }

}
