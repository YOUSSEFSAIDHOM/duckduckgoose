package io.github.duckduckgoose.entities;

import com.badlogic.gdx.graphics.Texture;
import io.github.duckduckgoose.utils.GlobalVariables;

public class DuckBoss extends Goose {

    protected String getAnimationPath() {
        return GlobalVariables.duckSheetPath;
    }

    public DuckBoss(float x, float y) {
        super(x, y);
        // it needs to be fed 12 times
        this.feedNum = GlobalVariables.duckMaxFeed;
        this.points = 250;
    }
}
