package ru.geekbrains.space_shooter.screens.menu;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.space_shooter.screens.game.GameScreen;
import ru.geekbrains.space_shooter.screens.stars.Star;
import ru.geekbrains.space_shooter.screens.BackGround;
import ru.geekuniversity.engine.Base2DScreen;
import ru.geekuniversity.engine.Sprite2DTexture;
import ru.geekuniversity.engine.math.Rect;
import ru.geekuniversity.engine.math.Rnd;
import ui.ActionListener;

/**
 * Created by agcheb on 23.08.17.
 */

public class MenuScreen extends Base2DScreen implements ActionListener {

    private static final float STAR_HEIGHT = 0.01f;
    private static final int STARS_COUNT = 250;

    private static final float BUTTONS_HEIGHT = 0.15f;
    private static final float BUTTONS_PRESS_SCALE = 0.9f;

    private Sprite2DTexture textureBackGround;
    private TextureAtlas atlas;
    private BackGround backGround;
    private Star[] stars = new Star[STARS_COUNT];
    private ButtonExit buttonExit;
    private ButtonNewGame buttonNewGame;

    private Music menuMusic;
//    private Button exitbtn;



    public MenuScreen(Game game) {
        super(game);
    }

    @Override
    public void show(){
        super.show();
        textureBackGround = new Sprite2DTexture("textures/bgmain.png");
        atlas = new TextureAtlas("textures/menuAtlas.tpack");

        //добавили музыку в меню
        menuMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/menumsc.mp3"));
        menuMusic.setLooping(true);
        menuMusic.setVolume(0.7f);
        menuMusic.play();

        backGround = new BackGround(new TextureRegion(textureBackGround));
        TextureRegion regionStar = atlas.findRegion("star");

        for (int i = 0; i < stars.length; i++) {
            float vx = Rnd.nextFloat(-0.005f,0.005f);
            float vy = Rnd.nextFloat(-0.05f,-0.01f);
            float starHeight = STAR_HEIGHT * Rnd.nextFloat(0.75f,1f);
            stars[i] = new Star(regionStar, vx,vy,starHeight);
        }
        buttonExit = new ButtonExit(atlas, this, BUTTONS_PRESS_SCALE);
        buttonExit.setHeightProportion(BUTTONS_HEIGHT);
        buttonNewGame = new ButtonNewGame(atlas, this, BUTTONS_PRESS_SCALE);
        buttonNewGame.setHeightProportion(BUTTONS_HEIGHT);


        }

        @Override
        public void actionPerformed(Object src){
            if(src == buttonExit){
                Gdx.app.exit();
            }
            else if(src == buttonNewGame){
                game.setScreen(new GameScreen(game));
            }
            else {
                throw new RuntimeException("неизвестное событие " + src);
            }
        }

    @Override
    protected void resize(Rect worldBounds) {
        backGround.resize(worldBounds);
        for (int i = 0; i < stars.length ; i++) {
            stars[i].resize(worldBounds);
        }
        buttonExit.resize(worldBounds);
        buttonNewGame.resize(worldBounds);

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
//        startbtn.draw(batch);
        buttonExit.draw(batch);
        buttonNewGame.draw(batch);
        batch.end();
    }
//    private void checkCollision(){
//
//    }

    @Override
    public void dispose(){

        textureBackGround.dispose();
        atlas.dispose();
        menuMusic.dispose();
        super.dispose();
    }

    @Override
    protected void touchDown(Vector2 touch, int pointer) {
        buttonNewGame.touchDown(touch, pointer);
        buttonExit.touchDown(touch, pointer);
    }

    @Override
    protected void touchUp(Vector2 touch, int pointer) {
        System.out.println("touchup " +touch);
        buttonNewGame.touchUp(touch, pointer);
        buttonExit.touchUp(touch, pointer);
    }

}
