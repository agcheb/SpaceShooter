package ru.geekbrains.space_shooter.common.bullets;

import ru.geekuniversity.engine.pool.SpritesPool;

/**
 * Created by agcheb on 11.09.17.
 */

public class BulletPool extends SpritesPool<Bullet> {
    @Override
    protected Bullet newObject() {
        return new Bullet();
    }

    @Override
    protected void debugLog() {
        System.out.println("BulletPool change active/free: " + activeObjects.size() + "/" + freeObjects.size());
    }
}
