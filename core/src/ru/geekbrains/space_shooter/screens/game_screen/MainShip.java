package ru.geekbrains.space_shooter.screens.game_screen;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.space_shooter.common.Ship;
import ru.geekbrains.space_shooter.common.bullets.BulletPool;
import ru.geekbrains.space_shooter.common.explosions.ExplosionPool;
import ru.geekuniversity.engine.math.Rect;

/**
 * Created by agcheb on 06.09.17.
 */

public class MainShip extends Ship {

    private static final float SHIP_HEIGHT = 0.15f;
    private static final float BOTTOM_MARGIN = 0.05f;

    private final Vector2 v0 = new Vector2(0.5f,0f);


    MainShip(TextureAtlas atlas, BulletPool bulletPool, ExplosionPool explosionPool, Rect worldBounds, Sound bulletSound) {
        super(atlas.findRegion("main_ship"),1,2,2,bulletPool,explosionPool,worldBounds);
        bulletRegion = atlas.findRegion("bulletMainShip");

        this.bulletSound = bulletSound;

        setHeightProportion(SHIP_HEIGHT);

        setToNewGame();

    }

    void setToNewGame(){
        pos.x = worldBounds.pos.x;

        bulletHeight = 0.01f;
        bulletV.set(0f,0.5f);
        bulletDamage = 1;
        reloadInterval = 0.15f;

        hp = 100;
        flushDestroy();

    }

    private final float hpRegenInterval = 1f;
    private float hpRegenTimer;

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        reloadTimer+= deltaTime;
        hpRegenTimer+=deltaTime;
        if(hpRegenTimer>=hpRegenInterval){
            hpRegenTimer=0f;
            if(hp<100)hp+=1;
        }
        if(reloadTimer>=reloadInterval){
            reloadTimer=0f;
            shoot();
        }
        checkAndHandleBounds();
    }

    @Override
    public void resize(Rect worldBounds) {
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
            case Input.Keys.UP: shoot(); break;
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
            case Input.Keys.UP:  break;
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
     int getHP(){
         return hp;
     }

     Vector2 getV(){
         return v;
     }

    public boolean isBulletCollision(Rect bullet){

        return  !(bullet.getRight() < getLeft() || bullet.getLeft() > getRight() || bullet.getTop() < pos.y || bullet.getBottom() > getTop());
    }

}
