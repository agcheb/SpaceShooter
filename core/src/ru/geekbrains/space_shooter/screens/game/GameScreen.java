package ru.geekbrains.space_shooter.screens.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.space_shooter.screens.BackGround;
import ru.geekbrains.space_shooter.screens.stars.TrackingStar;
import ru.geekuniversity.engine.Base2DScreen;
import ru.geekuniversity.engine.Sprite2DTexture;
import ru.geekuniversity.engine.math.Rect;
import ru.geekuniversity.engine.math.Rnd;

/**
 * Created by agcheb on 06.09.17.
 */

public class GameScreen extends Base2DScreen {

    private static final float STAR_HEIGHT = 0.01f;
    private static final int STARS_COUNT = 50;

    private TrackingStar[] stars = new TrackingStar[STARS_COUNT];
    private Sprite2DTexture textureBackGround;
    private TextureAtlas atlas;
    private BackGround backGround;
    private MainShip mainShip;

    public GameScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        textureBackGround = new Sprite2DTexture("textures/bgmain.png");
        atlas = new TextureAtlas("textures/mainAtlas.tpack");
        backGround = new BackGround(new TextureRegion(textureBackGround));

        mainShip = new MainShip(atlas);

        for (int i = 0; i < stars.length; i++) {
            float vx = Rnd.nextFloat(-0.005f,0.005f);
            float vy = Rnd.nextFloat(-0.05f,-0.01f);
            float starHeight = STAR_HEIGHT * Rnd.nextFloat(0.75f,1f);
            stars[i] = new TrackingStar(atlas,mainShip, vx,vy,starHeight);
        }
    }

    @Override
    protected void resize(Rect worldBounds) {
        backGround.resize(worldBounds);
        mainShip.resize(worldBounds);
        for (int i = 0; i < stars.length ; i++) {
            stars[i].resize(worldBounds);
        }

    }

    @Override
    public void render(float delta) {
        update(delta);
        checkCollisions();
        deleteAllDestroyed();
        draw();
    }

    private  void update(float deltaTime){
        mainShip.update(deltaTime);
        for (int i = 0; i < stars.length ; i++) {
            stars[i].update(deltaTime);
        }

    }

    private void checkCollisions(){

    }

    private  void  deleteAllDestroyed(){

    }

    private void draw(){
        Gdx.gl.glClearColor(0.5f,0.5f,0.5f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        backGround.draw(batch);
        for (int i = 0; i < stars.length; i++) {
            stars[i].draw(batch);
        }
        mainShip.draw(batch);
        batch.end();

    }
    @Override
    public void dispose() {
        textureBackGround.dispose();
        atlas.dispose();
        super.dispose();
    }

    @Override
    protected void touchDown(Vector2 touch, int pointer) {
        mainShip.touchDown(touch, pointer);
    }

    @Override
    protected void touchUp(Vector2 touch, int pointer) {
        mainShip.touchUp(touch, pointer);
    }

    @Override
    public boolean keyDown(int keycode) {
        mainShip.keyDown(keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        mainShip.keyUp(keycode);
        return false;
    }
}
