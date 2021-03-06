package ru.geekuniversity.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

/**
 * Created by agcheb on 18.09.17.
 */

public class Font extends BitmapFont {

    public Font(String fontFile, String imageFile) {
        super(Gdx.files.internal(fontFile), Gdx.files.internal(imageFile), false, false);
        getRegion().getTexture().setFilter(Texture.TextureFilter.Linear,Texture.TextureFilter.Linear);
    }

    public void setWorldSize(float worldSize){
        getData().setScale(worldSize / getCapHeight());
    }


    public GlyphLayout draw(Batch batch, CharSequence str, float x, float y, int halign) {
        return draw(batch, str, x, y, 0f, halign, false);
    }
}
