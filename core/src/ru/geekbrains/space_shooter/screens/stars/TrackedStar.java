package ru.geekbrains.space_shooter.screens.stars;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.space_shooter.screens.game.MainShip;

/**
 * Created by agcheb on 07.09.17.
 */

public class TrackedStar extends Star {

    private MainShip ship;
        public TrackedStar(TextureAtlas atlas,MainShip ship, float vx, float vy, float height) {
        super(atlas.findRegion("star"), vx, vy, height);
        this.ship = ship;
    }

    @Override
    public void update(float deltaTime) {
        pos.mulAdd(v,deltaTime);
        pos.mulAdd(ship.getV(),-deltaTime);
        checkAndHandleBounds();
    }
}
