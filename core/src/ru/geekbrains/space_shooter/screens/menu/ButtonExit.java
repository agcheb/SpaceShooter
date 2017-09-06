package ru.geekbrains.space_shooter.screens.menu;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.geekuniversity.engine.math.Rect;
import ui.ActionListener;
import ui.ScaledTouchUpButton;

/**
 * Created by agcheb on 06.09.17.
 */

public class ButtonExit extends ScaledTouchUpButton {

    public ButtonExit(TextureAtlas atlas, ActionListener listener, float pressScale) {
        super(atlas.findRegion("btExit"), listener, pressScale);
    }

    @Override
    public void resize(Rect worldBounds) {

        setRight(worldBounds.getRight());
        setBottom(worldBounds.getBottom());

    }
}
