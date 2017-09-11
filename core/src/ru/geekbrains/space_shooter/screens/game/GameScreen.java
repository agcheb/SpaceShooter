package ru.geekbrains.space_shooter.screens.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.space_shooter.pools.BulletPool;
import ru.geekbrains.space_shooter.pools.ExplosionPool;
import ru.geekbrains.space_shooter.screens.BackGround;
import ru.geekbrains.space_shooter.screens.Explosion;
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


    private final BulletPool bulletPool= new BulletPool();
    private ExplosionPool explosionPool;

    private TrackingStar[] stars = new TrackingStar[STARS_COUNT];
    private Sprite2DTexture textureBackGround;
    private TextureAtlas atlas;
    private BackGround backGround;
    private MainShip mainShip;

    private Sound sndExplosion;

    private Music menuMusic;

    public GameScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        textureBackGround = new Sprite2DTexture("textures/bgmain.png");
        atlas = new TextureAtlas("textures/mainAtlas.tpack");
        backGround = new BackGround(new TextureRegion(textureBackGround));


        menuMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/menumsc.mp3"));
        menuMusic.setLooping(true);
        menuMusic.setVolume(0.7f);
        menuMusic.play();

        mainShip = new MainShip(atlas, bulletPool);

        sndExplosion = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav"));

        explosionPool = new ExplosionPool(atlas,sndExplosion);



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
        for (int i = 0; i < stars.length ; i++) {
            stars[i].resize(worldBounds);
        }
        mainShip.resize(worldBounds);

    }

    @Override
    public void render(float delta) {
        update(delta);
        checkCollisions();
        deleteAllDestroyed();
        draw();
    }

//    private float explosionInterval = 3f;
//    private float explosionTimer;



    private  void update(float deltaTime){

//        explosionTimer+=deltaTime;
//        if (explosionTimer>=explosionInterval){
//            explosionTimer=0f;
//            float posX = Rnd.nextFloat(worldBounds.getLeft(),worldBounds.getRight());
//            float posY = Rnd.nextFloat(worldBounds.getBottom(),worldBounds.getTop());
//            Explosion explosion = explosionPool.obtain();
//            Vector2 pos = new Vector2(posX,posY);
//            explosion.set(0.2f,pos);
//        }


        mainShip.update(deltaTime);
        for (int i = 0; i < stars.length ; i++) {
            stars[i].update(deltaTime);
        }
        bulletPool.updateActiveSprites(deltaTime);
        explosionPool.updateActiveSprites(deltaTime);

    }

    private void checkCollisions(){

    }

    private  void  deleteAllDestroyed(){
        bulletPool.freeAllDestroyedActiveObjects();
        explosionPool.freeAllDestroyedActiveObjects();
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
        bulletPool.drawActiveSprites(batch);
        explosionPool.drawActiveSprites(batch);
        batch.end();

    }
    @Override
    public void dispose() {
        explosionPool.dispose();
        textureBackGround.dispose();
        atlas.dispose();
        bulletPool.dispose();
        sndExplosion.dispose();
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
