package ru.geekuniversity.engine.pool;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

import ru.geekuniversity.engine.sprites.Sprite;

/**
 * Created by agcheb on 11.09.17.
 */

public abstract class SpritesPool<T extends Sprite> {

    protected final ArrayList<T>  activeObjects = new ArrayList<T>();
    protected final ArrayList<T>  freeObjects = new ArrayList<T>();

    protected abstract T newObject();

    public T obtain(){
        T object;
        if(freeObjects.isEmpty()){
            object = newObject();
        } else {
            object =freeObjects.remove(freeObjects.size() - 1);
        }
        activeObjects.add(object);
        debugLog();
        return object;
    }

    private void free(T object){
       if (!activeObjects.remove(object)) throw new RuntimeException("Попытка удаления несуществующего object = " + object);
        freeObjects.add(object);
        debugLog();
    }

    public void updateActiveSprites(float deltatime){
        final int cnt = activeObjects.size();
        for (int i = 0; i <cnt ; i++) {
            Sprite sprite = activeObjects.get(i);
            if(sprite.isDestroyed()) throw new RuntimeException("Update destroyed sprite");
            sprite.update(deltatime);
        }
    }

    public void freeAllDestroyedActiveObjects(){
        for (int i = 0; i < activeObjects.size() ; i++) {
            T sprite = activeObjects.get(i);
            if(sprite.isDestroyed()){
                free(sprite);
                i--;
                sprite.flushDestroy();
            }
        }
    }

    public void drawActiveSprites(SpriteBatch batch){
        final int cnt = activeObjects.size();
        for (int i = 0; i <cnt ; i++) {
            Sprite sprite = activeObjects.get(i);
            if(sprite.isDestroyed()) throw new RuntimeException("draw destroyed sprite");
            sprite.draw(batch);
        }
    }

    protected void debugLog(){

    }

    public ArrayList<T> getActiveObjects() {
        return activeObjects;
    }

    public void dispose(){
        activeObjects.clear();
        freeObjects.clear();
    }
}
