package ru.geekuniversity.engine;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;

import ru.geekuniversity.engine.math.MatrixUtils;
import ru.geekuniversity.engine.math.Rect;

/**
 * Created by agcheb on 23.08.17.
 */

public class Base2DScreen implements Screen, InputProcessor {

    private static final float WORLD_HEIGHT = 1f;

    protected  SpriteBatch batch;
    protected final Game game;

    private final Rect screenBounds = new Rect(); //граница области рисования в px
    private final Rect worldBounds = new Rect(); // желаемая граница проекций мировых координат
    private final Rect glBounds = new Rect(0f,0f,1f,1f); //дефолтные границы проекции мир - gl
    protected final Matrix4 matWorldToGL = new Matrix4();

    public Base2DScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        System.out.println("show");
        Gdx.input.setInputProcessor(this);
        if(batch!= null) throw new RuntimeException("batch!= null, повторная установка screen без dispose");
        batch = new SpriteBatch();
    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {
        System.out.println("resize: width = " + width + " height = " + height);
        screenBounds.setSize(width,height);
        screenBounds.setLeft(0f);
        screenBounds.setBottom(0f);

        worldBounds.setHeight(WORLD_HEIGHT);

        float aspect =  width/(float)height;
        worldBounds.setWidth(WORLD_HEIGHT * aspect);

        MatrixUtils.calcTransitionMatrix(matWorldToGL,worldBounds,glBounds);
        batch.setProjectionMatrix(matWorldToGL);
        resize(worldBounds);
    }

    protected void resize(Rect worldBounds){

    }

    @Override
    public void pause() {
        System.out.println("pause");
    }

    @Override
    public void resume() {
        System.out.println("resume");
    }

    @Override
    public void hide() {
        System.out.println("hide");
        dispose();
    }

    @Override
    public void dispose() {
        System.out.println("dispose");
        batch.dispose();
        batch = null;
    }



    // Override all InputProcessor methods
    @Override
    public boolean keyDown(int keycode) {
        System.out.println("keyDown keycode = " + keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        System.out.println("keyUp keycode = " + keycode);
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        System.out.println("keyTyped symbol = " + character);
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        System.out.println("touchdown x = "+ screenX + " y = "+ screenY);

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        System.out.println("touchup x = "+ screenX + " y = "+ screenY);
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        System.out.println("touchdragged x = "+ screenX + " y = "+ screenY);
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        //System.out.println("mousemoved x = "+ screenX + " y = "+ screenY);
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        System.out.printf("scrolled amount" + amount);
        return false;
    }
}
