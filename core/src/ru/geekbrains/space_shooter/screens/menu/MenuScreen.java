package ru.geekbrains.space_shooter.screens.menu;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import ru.geekuniversity.engine.Base2DScreen;
import ru.geekuniversity.engine.math.MatrixUtils;

/**
 * Created by agcheb on 23.08.17.
 */

public class MenuScreen extends Base2DScreen {

    private SpriteBatch batch;
    private Texture img;
    private Texture textureBackground;
    private Texture textureCircle;

    public MenuScreen(Game game) {
        super(game);
    }

    @Override
    public void show(){
        super.show();
        batch = new SpriteBatch();
        batch.getProjectionMatrix().idt().scale(0.1f,0.1f,1); //.mul(projection);
        //batch.setProjectionMatrix(projection);
//        img = new Texture("badlogic.jpg");
//        textureBackground = new Texture("bg.png");
        textureCircle = new Texture("circle.png");

    }


    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0.5f,0.5f,0.5f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
//        batch.draw(textureBackground,0,0);
//        batch.draw(img,0,0);
        batch.draw(textureCircle,-1,-1,2f,2f);
        batch.end();
//        game.setScreen();
    }

    public void dispose(){
        batch.dispose();
        img.dispose();
        textureBackground.dispose();
        textureCircle.dispose();
        super.dispose();
    }
}
