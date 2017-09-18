package ru.geekbrains.space_shooter.common.stars;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.space_shooter.screens.game_screen.MainShip;

/**
 * Created by agcheb on 07.09.17.
 */

public class TrackingStar extends ru.geekbrains.space_shooter.common.stars.Star {

    private Vector2 shipV;
        public TrackingStar(TextureAtlas atlas, Vector2 shipV, float vx, float vy, float height) {
        super(atlas.findRegion("star"), vx, vy, height);
        this.shipV = shipV;
    }

    @Override
    public void update(float deltaTime) {
        pos.mulAdd(v,deltaTime);
        pos.mulAdd(shipV,-0.2f*deltaTime);
        checkAndHandleBounds();
    }
}
