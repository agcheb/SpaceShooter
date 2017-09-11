package ru.geekbrains.space_shooter.screens.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.geekuniversity.engine.math.Rect;
import ru.geekuniversity.engine.sprites.Sprite;

/**
 * Created by agcheb on 06.09.17.
 */

public class MainShip extends Sprite {

    private static final float SHIP_HEIGHT = 0.15f;
    private static final float BOTTOM_MARGIN = 0.05f;

    private Rect worldBounds;
    private final Vector2 v0 = new Vector2(0.5f,0f);
    private final Vector2 v = new Vector2();

    MainShip(TextureAtlas atlas) {
        super(atlas.findRegion("main_ship"),1,2,2);
        setHeightProportion(SHIP_HEIGHT);
    }

    public Vector2 getV() {
        return v;
    }

    @Override
    public void update(float deltaTime) {
        pos.mulAdd(v,deltaTime);
        checkAndHandleBounds();
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        setBottom(worldBounds.getBottom()+BOTTOM_MARGIN);
    }


    //команды управления кораблем
    private void moveRight(){
        v.set(v0);       //реализация через флаги контакта с границей
    }


    private void moveLeft(){
        v.set(v0).rotate(180);//реализация через флаги контакта с границей
    }

    private void stop(){
        v.setZero();
    }
    //конец команд управения кораблем

    // события нажатия на экран

    private static final int INVALID_POINTER = -1;
    private int leftPointer = INVALID_POINTER;
    private int rightPointer = INVALID_POINTER;
    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        if(touch.x < worldBounds.pos.x){
            if(leftPointer !=INVALID_POINTER)return false;
            leftPointer = pointer;
            moveLeft();
        } else {
            if(rightPointer !=INVALID_POINTER)return false;
            rightPointer = pointer;
            moveRight();
        }
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        if(pointer == leftPointer){
            leftPointer = INVALID_POINTER;
            if(rightPointer != INVALID_POINTER) moveRight();else stop();
        } else if(pointer == rightPointer){
            rightPointer = INVALID_POINTER;
            if(leftPointer != INVALID_POINTER) moveLeft();else stop();

        }
        return false;
    }

    private boolean pressedLeft;
    private boolean pressedRight;

    void keyDown(int keycode) {
        switch (keycode){
            case Input.Keys.A:
            case Input.Keys.LEFT: pressedLeft = true; moveLeft();break;
            case Input.Keys.D:
            case Input.Keys.RIGHT: pressedRight = true; moveRight();break;
            case Input.Keys.UP: frame = 1; break;
        }
    }

    void keyUp(int keycode) {
        switch (keycode){
            case Input.Keys.A:
            case Input.Keys.LEFT:
                pressedLeft = false;
                if(pressedRight) moveRight(); else stop();
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                pressedRight = false;
                if(pressedLeft) moveLeft(); else stop();
                break;
            case Input.Keys.UP: frame = 0; break;
        }
    }
    // конец событий нажатия на экран

    // Проверка выхода за границу полета
    protected void checkAndHandleBounds(){
        // решение через аналог со звездами
//        if(getRight() < worldBounds.getLeft()) setRight(worldBounds.getRight());
//        if(getLeft() > worldBounds.getRight()) setLeft(worldBounds.getLeft());
        if(getLeft() < worldBounds.getLeft()){setLeft(worldBounds.getLeft());stop();}
        if(getRight() > worldBounds.getRight()){setRight(worldBounds.getRight());stop();}
     }
}
