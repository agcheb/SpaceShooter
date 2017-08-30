package ru.geekbrains.space_shooter.screens.menu;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

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


    private Sprite2DTexture textureBackGround;
    private TextureAtlas atlas;
    private BackGround backGround;
    private Star star;


    public MenuScreen(Game game) {
        super(game);
    }

    @Override
    public void show(){
        super.show();
        textureBackGround = new Sprite2DTexture("textures/bg.png");
        atlas = new TextureAtlas("textures/mainAtlas.pack");
        backGround = new BackGround(new TextureRegion(textureBackGround));
        TextureRegion regionStar = atlas.findRegion("star");
        float vx = Rnd.nextFloat(-0.005f,0.005f);
        float vy = Rnd.nextFloat(-0.05f,-0.01f);
        float starWidth = STAR_WIDTH * Rnd.nextFloat(0.75f,1f);
        star = new Star(regionStar, vx,vy,starWidth);
    }


    @Override
    protected void resize(Rect worldBounds) {
        backGround.resize(worldBounds);
        star.resize(worldBounds);
    }

    @Override
    public void render(float delta){
    update(delta);
       // checkCollision();
        draw();
    }

    private void update(float deltaTime){
        star.update(deltaTime);
    }

    private void draw(){
        Gdx.gl.glClearColor(0.5f,0.5f,0.5f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        backGround.draw(batch);
        star.draw(batch);
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
