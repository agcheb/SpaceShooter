package ru.geekbrains.space_shooter.pools;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.geekbrains.space_shooter.screens.Explosion;
import ru.geekuniversity.engine.pool.SpritesPool;

/**
 * Created by agcheb on 11.09.17.
 */

public class ExplosionPool extends SpritesPool<Explosion> {

    private final TextureRegion explosionRegion;

    public ExplosionPool(TextureAtlas atlas) {
        String regionName = "explosion";
        explosionRegion = atlas.findRegion(regionName);
        if (explosionRegion == null)throw  new RuntimeException("регион " + regionName + " не найден.");
    }


    @Override
    protected Explosion newObject() {
        return new Explosion(explosionRegion,9,9,74);
    }

    @Override
    protected void debugLog() {
        System.out.println("ExplosionPool change active/free: " + activeObjects.size() + "/" + freeObjects.size());
    }
}
