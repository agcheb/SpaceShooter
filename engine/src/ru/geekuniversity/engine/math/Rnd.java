package ru.geekuniversity.engine.math;

import java.util.Random;

/**
 * Created by agcheb on 30.08.17.
 */

public class Rnd {

    private static final Random rnd = new Random();

    public static float nextFloat(float min, float max){
        return rnd.nextFloat() * (max-min) +min;
    }
}
