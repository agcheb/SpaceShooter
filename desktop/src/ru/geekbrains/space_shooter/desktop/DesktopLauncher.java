package ru.geekbrains.space_shooter.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import ru.geekbrains.space_shooter.StarGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		float aspect = 9f/16f;
		config.width = 450;
		config.height = (int) (config.width / aspect);
		new LwjglApplication(new StarGame(), config);
	}
}
