package ru.geekbrains.space_shooter.screens.menu;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekuniversity.engine.Base2DScreen;
import ru.geekuniversity.engine.math.MatrixUtils;

/**
 * Created by agcheb on 23.08.17.
 */

public class MenuScreen extends Base2DScreen {


    private Texture img;
    private Texture textureBackground;
    private Texture textureCircle;
    private TextureRegion textureRegion;

    public MenuScreen(Game game) {
        super(game);
    }

    @Override
    public void show(){
        super.show();

//        batch.setProjectionMatrix(projection);
//        img = new Texture("badlogic.jpg");
//        textureBackground = new Texture("bg.png");
        textureCircle = new Texture("circle.png");
        textureRegion = new TextureRegion(textureCircle,10,10,20,20);

    }


    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0.5f,0.5f,0.5f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
//        batch.draw(textureBackground,0,0);
//        batch.draw(img,0,0);
        batch.draw(textureRegion,-0.5f,-0.5f,1f,1f);
        batch.end();
//        game.setScreen();
    }

    public void dispose(){
//        img.dispose();
//        textureBackground.dispose();
        textureCircle.dispose();
        super.dispose();
    }

    @Override
    protected void touchDown(Vector2 touch, int pointer) {
        System.out.println("touchdown " +touch);
    }

    @Override
    protected void touchUp(Vector2 touch, int pointer) {
        System.out.println("touchup " +touch);
    }

    @Override
    protected void touchDragged(Vector2 touch, int pointer) {
        System.out.println("dragged " +touch);
    }
}
