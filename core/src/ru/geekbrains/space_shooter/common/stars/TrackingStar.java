package ru.geekbrains.space_shooter.common.stars;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.space_shooter.screens.game_screen.MainShip;

/**
 * Created by agcheb on 07.09.17.
 */

public class TrackingStar extends ru.geekbrains.space_shooter.common.stars.Star {

    private MainShip ship;
        public TrackingStar(TextureAtlas atlas, MainShip ship, float vx, float vy, float height) {
        super(atlas.findRegion("star"), vx, vy, height);
        this.ship = ship;
    }

    @Override
    public void update(float deltaTime) {
        pos.mulAdd(v,deltaTime);
        pos.mulAdd(ship.getV(),-0.2f*deltaTime);
        checkAndHandleBounds();
    }
}
