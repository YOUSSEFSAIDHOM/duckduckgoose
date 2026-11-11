package io.github.duckduckgoose.items;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import io.github.duckduckgoose.effects.EffectsHandler;
import io.github.duckduckgoose.effects.SpeedEffect;
import io.github.duckduckgoose.entities.Player;
import io.github.duckduckgoose.utils.GlobalVariables;

import java.util.ArrayList;

public abstract class AbstractItem {
    protected float x, y;
    protected final float size = GlobalVariables.itemRadius;
    protected boolean collected = false;

    public abstract TextureRegion getIcon();
    public abstract String getName();

    public float getX() { return x; }

    public float getY() {
        return y;
    }

    public float getSize() { return size; }

    public void getCollected() {
        this.collected = true;
    }

    public AbstractItem(float x, float y) {
        this.x = x;
        this.y = y;
    }

    // Functions to be overridden per item
    public abstract void use(EffectsHandler effectsHandler, Player player);
    public abstract void draw(SpriteBatch batch);

    public void update(float delta, Player player) {
        if (collected) {
            this.x = player.getX();
            this.y = player.getY();
        }
    }

    public boolean isCollected() {
        return collected;
    }

}
