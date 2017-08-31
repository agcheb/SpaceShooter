package ru.geekbrains.space_shooter.screens.menu;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.space_shooter.screens.buttons.Button;
import ru.geekbrains.space_shooter.screens.stars.Star;
import ru.geekuniversity.engine.Base2DScreen;
import ru.geekuniversity.engine.Sprite2DTexture;
import ru.geekuniversity.engine.math.MatrixUtils;
import ru.geekuniversity.engine.math.Rect;
import ru.geekuniversity.engine.math.Rnd;
import ru.geekuniversity.engine.sprites.Sprite;

/**
 * Created by agcheb on 23.08.17.
 */

public class MenuScreen extends Base2DScreen {

    private static final float STAR_WIDTH = 0.01f;
    private static final float BTN_WIDTH = 0.2f;
    private static final int STARS_COUNT = 250;

    private Sprite2DTexture textureBackGround;
    private TextureAtlas atlas;
    private BackGround backGround;
    private Star[] stars = new Star[STARS_COUNT];
    private Button startbtn;
    private Button exitbtn;



    public MenuScreen(Game game) {
        super(game);
    }

    @Override
    public void show(){
        super.show();
        textureBackGround = new Sprite2DTexture("textures/bgmain.png");
        atlas = new TextureAtlas("textures/mainAtlas.pack");
        backGround = new BackGround(new TextureRegion(textureBackGround));
        TextureRegion regionStar = atlas.findRegion("star");
        TextureRegion regionPlay = atlas.findRegion("btPlay");
        TextureRegion regionExit = atlas.findRegion("btExit");

        for (int i = 0; i < stars.length; i++) {
            float vx = Rnd.nextFloat(-0.005f,0.005f);
            float vy = Rnd.nextFloat(-0.05f,-0.01f);
            float starWidth = STAR_WIDTH * Rnd.nextFloat(0.75f,1f);
            stars[i] = new Star(regionStar, vx,vy,starWidth);
        }

        startbtn = new Button(regionPlay,BTN_WIDTH);
        exitbtn = new Button(regionExit,BTN_WIDTH);
        }


    @Override
    protected void resize(Rect worldBounds) {
        backGround.resize(worldBounds);
        for (int i = 0; i < stars.length ; i++) {
            stars[i].resize(worldBounds);
        }

        startbtn.setLeft(worldBounds.getLeft());
        startbtn.setBottom(worldBounds.getBottom());



        exitbtn.setRight(worldBounds.getRight());
        exitbtn.setBottom(worldBounds.getBottom());
    }

    @Override
    public void render(float delta){
    update(delta);
       // checkCollision();
        draw();
    }

    private void update(float deltaTime){
        for (int i = 0; i < stars.length ; i++) {
            stars[i].update(deltaTime);
        }
    }

    private void draw(){
        Gdx.gl.glClearColor(0.5f,0.5f,0.5f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        backGround.draw(batch);
        for (int i = 0; i < stars.length; i++) {
            stars[i].draw(batch);
        }
        startbtn.draw(batch);
        exitbtn.draw(batch);
        batch.end();
    }
//    private void checkCollision(){
//
//    }

    public void dispose(){

        textureBackGround.dispose();
        atlas.dispose();
        super.dispose();
    }

    @Override
    protected void touchDown(Vector2 touch, int pointer) {
        startbtn.touchDown(touch, pointer);
        exitbtn.touchDown(touch, pointer);
    }

    @Override
    protected void touchUp(Vector2 touch, int pointer) {
        System.out.println("touchup " +touch);
        startbtn.touchUp(touch, pointer);
        exitbtn.touchUp(touch, pointer);
    }

    @Override
    protected void touchDragged(Vector2 touch, int pointer) {
        startbtn.touchDragged(touch, pointer);
        exitbtn.touchDragged(touch, pointer);
    }
}
