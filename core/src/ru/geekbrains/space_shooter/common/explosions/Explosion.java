package ru.geekbrains.space_shooter.common.explosions;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import ru.geekuniversity.engine.sprites.Sprite;

/**
 * Created by agcheb on 11.09.17.
 */

public class Explosion extends Sprite {

    private float animateInterval = 0.017f;
    private float animateTimer;

    private final Sound sndExplosion;

    Explosion(TextureRegion region, int rows, int cols, int frames, Sound sndExplosion) {
        super(region, rows, cols, frames);
        this.sndExplosion = sndExplosion;
    }

    public void set(float height, Vector2 pos){
        frame = 0;
        this.pos.set(pos);
        setHeightProportion(height);
        if(sndExplosion.play()==-1) throw new RuntimeException("звука нет");
    }

    @Override
    public void update(float deltaTime) {
        animateTimer+=deltaTime;
        if (animateTimer>=animateInterval){
            animateTimer=0f;
            if(++frame == regions.length)destroy();
        }
    }
}
