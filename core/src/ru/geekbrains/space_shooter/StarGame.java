package ru.geekbrains.space_shooter;

import com.badlogic.gdx.Game;

import ru.geekbrains.space_shooter.screens.menu.MenuScreen;

/**
 * Created by agcheb on 23.08.17.
 */

public class StarGame extends Game {
    @Override
    public void create() {
        setScreen(new MenuScreen(this));
    }
}
