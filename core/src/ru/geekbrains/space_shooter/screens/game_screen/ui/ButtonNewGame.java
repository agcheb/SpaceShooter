package ru.geekbrains.space_shooter.screens.game_screen.ui;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.geekuniversity.engine.sprites.Sprite;
import ui.ActionListener;
import ui.ScaledTouchUpButton;

/**
 * Created by agcheb on 18.09.17.
 */

public class ButtonNewGame extends ScaledTouchUpButton {


    private final static float HEIGHT =0.05f;
    private final static float TOP = -0.012f;
    private final static float PRESS_SCALE = 0.9f;

    public ButtonNewGame(TextureAtlas atlas, ActionListener listener) {
        super(atlas.findRegion("button_new_game"), listener, PRESS_SCALE);
        setHeightProportion(HEIGHT);
        setTop(TOP);
    }
}
