package ru.geekbrains.space_shooter.screens.buttons;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekuniversity.engine.math.Rect;
import ru.geekuniversity.engine.sprites.Sprite;

/**
 * Created by agcheb on 31.08.17.
 */

public class Button extends Sprite {

    private float width;

    public Button(TextureRegion region, float width) {
        super(region);
        this.width = width;
        setWidthProportion(width);
    }

    @Override
    public void resize(Rect worldBounds) {
        pos.set(worldBounds.getLeft()+halfWidth,worldBounds.getBottom()+halfHeight);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        if(isMe(touch)){
            setWidthProportion(width/1.4f);
            return  true;
        }
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        if(isMe(touch)){
          return  true;
        }

        setWidthProportion(width);
        return false;
    }

    @Override
    public boolean touchDragged(Vector2 touch, int pointer) {
        return super.touchDragged(touch, pointer);
    }
}
