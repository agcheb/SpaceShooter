package ru.geekbrains.space_shooter.screens.game;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.geekuniversity.engine.sprites.Sprite;

/**
 * Created by agcheb on 06.09.17.
 */

public class MainShip extends Sprite {

    public MainShip(TextureAtlas atlas) {
        super(atlas.findRegion(""));
    }
}
