package ru.geekbrains.space_shooter.common;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.space_shooter.common.bullets.BulletPool;
import ru.geekbrains.space_shooter.common.bullets.Bullet;
import ru.geekbrains.space_shooter.common.explosions.ExplosionPool;
import ru.geekuniversity.engine.math.Rect;
import ru.geekuniversity.engine.sprites.Sprite;

/**
 * Created by agcheb on 11.09.17.
 */

public class Ship extends Sprite {

    protected final Vector2 v = new Vector2();
    protected Rect worldBounds;

    protected BulletPool bulletPool;
    protected TextureRegion bulletRegion;
    protected Sound bulletSound;
    protected final ExplosionPool explosionPool;
    protected int hp;

    public Ship( BulletPool bulletPool , ExplosionPool explosionPool, Rect worldBounds) {
//        this.bulletSound = bulletSound;
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        this.worldBounds = worldBounds;
    }

    public Ship(TextureRegion region, int rows, int cols, int frames,  BulletPool bulletPool , ExplosionPool explosionPool, Rect worldBounds) {
        super(region, rows, cols, frames);

//        this.bulletSound = bulletSound;
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        this.worldBounds = worldBounds;

    }

    @Override
    public void update(float deltaTime) {
        pos.mulAdd(v,deltaTime);
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
    }

    protected float bulletHeight;
    protected final Vector2 bulletV = new Vector2();
    protected int bulletDamage;

    protected float reloadInterval;
    protected float reloadTimer;

    protected void shoot(){
        Bullet bullet = bulletPool.obtain();
        bullet.set(this,bulletRegion, pos, bulletV, bulletHeight,worldBounds, bulletDamage);
        if(bulletSound.play(0.1f) ==-1) throw new RuntimeException();
    }
}
