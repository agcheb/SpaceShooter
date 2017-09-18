package ru.geekuniversity.engine.utils;

import com.badlogic.gdx.utils.StringBuilder;

/**
 * Created by agcheb on 18.09.17.
 */

public class StrBuilder extends StringBuilder {
    public StrBuilder clear(){
        setLength(0);
        return this;
    }
}
