package ru.geekbrains.space_shooter.screens.menu;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekuniversity.engine.Base2DScreen;
import ru.geekuniversity.engine.Sprite2DTexture;
import ru.geekuniversity.engine.math.MatrixUtils;
import ru.geekuniversity.engine.sprites.Sprite;

/**
 * Created by agcheb on 23.08.17.
 */

public class MenuScreen extends Base2DScreen {


    private Sprite2DTexture textureCircle;
    private TextureRegion textureRegion;
    private Sprite circle;

    public MenuScreen(Game game) {
        super(game);
    }

    @Override
    public void show(){
        super.show();

//        batch.setProjectionMatrix(projection);
//        img = new Texture("badlogic.jpg");
//        textureBackground = new Texture("bg.png");
        textureCircle = new Sprite2DTexture("circle.png");
        circle = new Sprite(new TextureRegion(textureCircle));
        circle.setWidthProportion(0.67f);

    }


    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0.5f,0.5f,0.5f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        circle.draw(batch);
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
