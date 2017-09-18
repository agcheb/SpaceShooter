package ru.geekbrains.space_shooter.screens.game_screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.StringBuilder;

import java.util.ArrayList;

import ru.geekbrains.space_shooter.common.bullets.Bullet;
import ru.geekbrains.space_shooter.common.bullets.BulletPool;
import ru.geekbrains.space_shooter.common.enemies.EnemiesEmitter;
import ru.geekbrains.space_shooter.common.enemies.Enemy;
import ru.geekbrains.space_shooter.common.enemies.EnemyPool;
import ru.geekbrains.space_shooter.common.explosions.ExplosionPool;
import ru.geekbrains.space_shooter.common.BackGround;
import ru.geekbrains.space_shooter.common.stars.TrackingStar;
import ru.geekbrains.space_shooter.screens.game_screen.ui.MessageGameOver;
import ru.geekuniversity.engine.Base2DScreen;
import ru.geekuniversity.engine.Font;
import ru.geekuniversity.engine.Sprite2DTexture;
import ru.geekuniversity.engine.math.Rect;
import ru.geekuniversity.engine.math.Rnd;
import ru.geekuniversity.engine.utils.StrBuilder;

/**
 * Created by agcheb on 06.09.17.
 */

public class GameScreen extends Base2DScreen {

    private enum State{ PLAYING, GAME_OVER}


    private static final float STAR_HEIGHT = 0.01f;
    private static final int STARS_COUNT = 50;
    private static final float FONT_SIZE = 0.02f;

    private final BulletPool bulletPool = new BulletPool();
    private ExplosionPool explosionPool;
    private EnemyPool enemyPool;

    private TrackingStar[] stars = new TrackingStar[STARS_COUNT];
    private Sprite2DTexture textureBackGround;
    private TextureAtlas atlas;
    private BackGround backGround;
    private MainShip mainShip;
    private MessageGameOver messageGameOver;
    private EnemiesEmitter enemiesEmitter;

    private State state;
    private Sound sndExplosion;
    private Music music;
    private Sound sndBullet;
    private Sound sndLaser;

    private Font font;

    public GameScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();


        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/music.mp3"));
        sndBullet = Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav"));
        sndLaser = Gdx.audio.newSound(Gdx.files.internal("sounds/laser.wav"));
        sndExplosion = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav"));


        textureBackGround = new Sprite2DTexture("textures/bgmain.png");
        atlas = new TextureAtlas("textures/mainAtlas.tpack");
        backGround = new BackGround(new TextureRegion(textureBackGround));



        explosionPool = new ExplosionPool(atlas,sndExplosion);
        mainShip = new MainShip(atlas, bulletPool,explosionPool,worldBounds,sndLaser);
        enemyPool = new EnemyPool(bulletPool,explosionPool,worldBounds,mainShip);

        enemiesEmitter = new EnemiesEmitter(enemyPool,worldBounds,atlas,sndBullet);







        for (int i = 0; i < stars.length; i++) {
            float vx = Rnd.nextFloat(-0.005f,0.005f);
            float vy = Rnd.nextFloat(-0.05f,-0.01f);
            float starHeight = STAR_HEIGHT * Rnd.nextFloat(0.75f,1f);
            stars[i] = new TrackingStar(atlas,mainShip.getV(), vx,vy,starHeight);
        }

        font = new Font("fonts/font.fnt", "fonts/font.png");
        font.setWorldSize(FONT_SIZE);


        music.setLooping(true);
        music.setVolume(0.7f);
        music.play();

        startNewGame();
    }

    private void startNewGame(){
        state = State.PLAYING;
        frags = 0;
        mainShip.setToNewGame();
        enemiesEmitter.setToNewGame();


        bulletPool.freeAllActiveObjects();
        enemyPool.freeAllActiveObjects();
        explosionPool.freeAllActiveObjects();

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


        enemiesEmitter.generateEnemies(deltaTime);
        mainShip.update(deltaTime);
        for (int i = 0; i < stars.length ; i++) {
            stars[i].update(deltaTime);
        }
        bulletPool.updateActiveSprites(deltaTime);
        enemyPool.updateActiveSprites(deltaTime);
        explosionPool.updateActiveSprites(deltaTime);

    }

    private void checkCollisions(){
        ArrayList<Enemy> enemies = enemyPool.getActiveObjects();
        final int enemyCount = enemies.size();
        ArrayList<Bullet> bullets = bulletPool.getActiveObjects();
        final int bulletCount = bullets.size();

        for (int i = 0; i < enemyCount; i++) {
            Enemy enemy = enemies.get(i);
            if(enemy.isDestroyed())continue;
            float minDist = enemy.getHalfWidth()+mainShip.getHalfWidth();
            if(enemy.pos.dst2(mainShip.pos) < minDist * minDist){
                enemy.boom();
                enemy.destroy();
                mainShip.boom();
                mainShip.destroy();
                state = State.GAME_OVER;
                return;
            }
        }

            for (int j = 0; j < bulletCount; j++) {
                Bullet bullet = bullets.get(j);
                if(bullet.getOwner() == mainShip || bullet.isDestroyed())continue;
                if(mainShip.isBulletCollision(bullet)){
                    mainShip.damage(bullet.getDamage());
                    bullet.destroy();
                }
            }


        for (int i = 0; i < enemyCount ; i++) {
            Enemy enemy = enemies.get(i);
            if(enemy.isDestroyed())continue;
            for (int j = 0; j < bulletCount; j++) {
                Bullet bullet = bullets.get(j);
                if(bullet.getOwner() != mainShip || bullet.isDestroyed())continue;
                if(enemy.isBulletCollision(bullet)){
                    enemy.damage(bullet.getDamage());
                    bullet.destroy();
                    if(enemy.isDestroyed()){
                        enemy.boom();
                        frags++;
                        break;
                    }
                }
            }
        }
    }

    private  void  deleteAllDestroyed(){
        bulletPool.freeAllDestroyedActiveObjects();
        explosionPool.freeAllDestroyedActiveObjects();
        enemyPool.freeAllDestroyedActiveObjects();
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

        printInfo();
        bulletPool.drawActiveSprites(batch);
        enemyPool.drawActiveSprites(batch);
        explosionPool.drawActiveSprites(batch);
        batch.end();

    }

    private int frags;
    private StrBuilder sbFrags = new StrBuilder();
    private static final String STR_FRAGS = "FRAGS: ";
    private StrBuilder sbHP = new StrBuilder();
    private static final String STR_HP = "HP: ";

    private StrBuilder sbStage = new StrBuilder();
    private static final String STR_STAGE = "STAGE: ";
    private void printInfo(){

        font.draw(batch,sbFrags.clear().append(STR_FRAGS).append(frags),worldBounds.getLeft(),worldBounds.getTop());
        font.draw(batch,sbHP.clear().append(STR_HP).append(mainShip.getHP()),worldBounds.pos.x,worldBounds.getTop(), Align.center);
        font.draw(batch,sbStage.clear().append(STR_STAGE).append(enemiesEmitter.getStage()),worldBounds.getRight(),worldBounds.getTop(),Align.right);
    }

    @Override
    public void dispose() {
        music.dispose();
        sndBullet.dispose();
        sndLaser.dispose();
        sndExplosion.dispose();


        bulletPool.dispose();
        explosionPool.dispose();
        textureBackGround.dispose();
        atlas.dispose();
        font.dispose();
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
