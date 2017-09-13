package ru.geekbrains.space_shooter.common.enemies;

import ru.geekbrains.space_shooter.common.bullets.BulletPool;
import ru.geekbrains.space_shooter.common.explosions.ExplosionPool;
import ru.geekbrains.space_shooter.screens.game_screen.MainShip;
import ru.geekuniversity.engine.math.Rect;
import ru.geekuniversity.engine.pool.SpritesPool;

/**
 * Created by agcheb on 11.09.17.
 */

public class EnemyPool extends SpritesPool<Enemy> {




    private final BulletPool bulletPool;
    private final ExplosionPool explosionPool;
    private final Rect worldBounds;
    private final MainShip mainShip;

    public EnemyPool( BulletPool bulletPool, ExplosionPool explosionPool, Rect worldBounds,MainShip mainShip) {
        this.mainShip = mainShip;
        this.bulletPool = bulletPool;
        this.worldBounds = worldBounds;
        this.explosionPool = explosionPool;
    }


    @Override
    protected Enemy newObject() {
        return new Enemy(bulletPool,explosionPool,worldBounds,mainShip);
    }

    @Override
    protected void debugLog() {
        System.out.println("EnemyPool change active/free: " + activeObjects.size() + "/" + freeObjects.size());
    }
}
