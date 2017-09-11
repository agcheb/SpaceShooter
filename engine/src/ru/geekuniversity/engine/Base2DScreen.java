package ru.geekuniversity.engine;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;

import ru.geekuniversity.engine.math.MatrixUtils;
import ru.geekuniversity.engine.math.Rect;

/**
 * Created by agcheb on 23.08.17.
 */

public class Base2DScreen implements Screen, InputProcessor {

    protected static final float WORLD_HEIGHT = 1f;

    protected final Game game;

    protected final Rect screenBounds = new Rect(); //граница области рисования в px
    protected final Rect worldBounds = new Rect(); // желаемая граница проекций мировых координат
    protected final Rect glBounds = new Rect(0f,0f,1f,1f); //дефолтные границы проекции мир - gl

    /**
     * Эта матрица используется SpriteBatch.
     * С помощью нее, он наши мировые координаты переводит в GL(-1,1,-1,1) для последующей отрисовки
     * SpriteBatcher умеет работать только с Матрицей 4х4
     */

    protected final Matrix4 matWorldToGL = new Matrix4();


    /**
     * Эту матрицу мы будем использовать чтобы переводить тачи из экранных координат в мировые
     * Здесь лучше использовать матрицу 3х3, так как класс Vector2 умеет умножаться на нее
     */

    protected final Matrix3 matScreenToWorld = new Matrix3();

    // С помощью батчера будет рисовать спрайты в наследниках
    protected  SpriteBatch batch;

    public Base2DScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        System.out.println("Screen show");
        Gdx.input.setInputProcessor(this);
        if(batch!= null) throw new RuntimeException("batch!= null, повторная установка screen без dispose");
        batch = new SpriteBatch();
    }



    @Override
    public void resize(int width, int height) {
        System.out.println("resize: width = " + width + " height = " + height);
        screenBounds.setSize(width,height);
        screenBounds.setLeft(0);
        screenBounds.setBottom(0);

        float aspect =  width/(float)height;
        worldBounds.setHeight(WORLD_HEIGHT);
        worldBounds.setWidth(WORLD_HEIGHT * aspect);

        // РАссчитываем матрицу перехода  Мир-GL
        MatrixUtils.calcTransitionMatrix(matWorldToGL,worldBounds,glBounds);
        // И устанавливаем ее батчеру. В общем-то она нам больше не нужна
        batch.setProjectionMatrix(matWorldToGL);
        // Рассчитываем матрицу перехода Экран-Мир
        MatrixUtils.calcTransitionMatrix(matScreenToWorld,screenBounds,worldBounds);
        resize(worldBounds);
    }

    protected void resize(Rect worldBounds){

    }

    @Override
    public void render(float delta) {

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

    // Для перехвата тачей оверрайдим эти методы!
    protected void touchDown(Vector2 touch,int pointer){
    }

    protected void touchUp(Vector2 touch,int pointer){

    }

    protected void touchDragged(Vector2 touch,int pointer){

    }


    // Override all InputProcessor methods

    // Эти методы НЕ оверрайдим НИКОГДА

    private final Vector2 touch = new Vector2(); //Вектор для принятия/перевода/передачи тачей
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        touch.set(screenX, screenBounds.getHeight() - 1f - screenY).mul(matScreenToWorld);
        touchDown(touch,pointer);

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        touch.set(screenX, screenBounds.getHeight() - 1f - screenY).mul(matScreenToWorld);
        touchUp(touch,pointer);
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        touch.set(screenX, screenBounds.getHeight() - 1f - screenY).mul(matScreenToWorld);
        touchDragged(touch,pointer);
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


}
