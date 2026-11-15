package io.github.duckduckgoose.items;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import io.github.duckduckgoose.effects.EffectsHandler;
import io.github.duckduckgoose.effects.SpeedEffect;
import io.github.duckduckgoose.entities.Player;
import io.github.duckduckgoose.utils.GlobalVariables;

import java.util.ArrayList;

/**
 * Abstract class to be adapted for various items
 */
public abstract class AbstractItem {
    protected float x, y;
    protected final float size = GlobalVariables.itemRadius;
    protected boolean collected = false;
    Rectangle bounds;

    public abstract TextureRegion getIcon();

    public abstract String getName();

    public float getX() { return x; }

    public float getY() {
        return y;
    }

    public float getSize() { return size; }

    public Rectangle getBounds() { return bounds; }

    public void getCollected() {
        this.collected = true;
    }

    /**
     * Constructor method for an abstract item
     *
     * @param x x co-ordinate of the item
     * @param y y co-ordinate of the item
     */
    public AbstractItem(float x, float y) {
        this.x = x;
        this.y = y;
        this.bounds = new Rectangle(x - size, y - size, 2 * size, 2 * size);

    }

    // Functions to be overridden per item
    public abstract void use(EffectsHandler effectsHandler, Player player);
    public abstract void draw(SpriteBatch batch);

    /**
     * check whether an item has been collected
     *
     * @param delta change in time
     * @param player the player object
     */
    public void update(float delta, Player player) {
        if (collected) {
            this.x = player.getX();
            this.y = player.getY();
            updateBounds();
        }
    }

    /**
     * Update the boundaries of the item
     */
    public void updateBounds() {
        bounds.x = x - size;
        bounds.y = y + size;
    }

    public boolean isCollected() {
        return collected;
    }

}
