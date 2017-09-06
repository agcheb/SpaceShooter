package ru.geekbrains.space_shooter.screens.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.geekbrains.space_shooter.screens.BackGround;
import ru.geekuniversity.engine.Base2DScreen;
import ru.geekuniversity.engine.Sprite2DTexture;
import ru.geekuniversity.engine.math.Rect;

/**
 * Created by agcheb on 06.09.17.
 */

public class GameScreen extends Base2DScreen {

    private Sprite2DTexture textureBackGround;
    private BackGround backGround;

    public GameScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        textureBackGround = new Sprite2DTexture("textures/bgmain.png");
        backGround = new BackGround(new TextureRegion(textureBackGround));
    }

    @Override
    protected void resize(Rect worldBounds) {
        backGround.resize(worldBounds);
    }

    @Override
    public void render(float delta) {
        update(delta);
        checkCollisions();
        deleteAllDestroyed();
        draw();
    }

    private  void update(float deltaTime){
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
        batch.end();

    }
    @Override
    public void dispose() {
        textureBackGround.dispose();
        super.dispose();
    }
}
