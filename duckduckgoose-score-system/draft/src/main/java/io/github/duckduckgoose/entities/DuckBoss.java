package io.github.duckduckgoose.entities;

import com.badlogic.gdx.graphics.Texture;
import io.github.duckduckgoose.utils.GlobalVariables;

/**
 * Establishes the animation and unique stats of the duck boss
 */
public class DuckBoss extends Goose {

    protected String getAnimationPath() {
        return GlobalVariables.duckSheetPath;
    }

    /**
     * Defines unique stats of the duck boss
     *
     * @param x x co-ordinate of the duck boss
     * @param y y co-ordinate of the duck boss
     */
    public DuckBoss(float x, float y) {
        super(x, y);
        // it needs to be fed 12 times
        this.feedNum = GlobalVariables.duckMaxFeed;
        this.points = 250;
    }
}
