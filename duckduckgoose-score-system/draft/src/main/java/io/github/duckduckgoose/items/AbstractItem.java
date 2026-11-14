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

    public AbstractItem(float x, float y) {
        this.x = x;
        this.y = y;
        this.bounds = new Rectangle(x - size, y - size, 2 * size, 2 * size);

    }

    // Functions to be overridden per item
    public abstract void use(EffectsHandler effectsHandler, Player player);
    public abstract void draw(SpriteBatch batch);

    public void update(float delta, Player player) {
        if (collected) {
            this.x = player.getX();
            this.y = player.getY();
            updateBounds();
        }
    }

    public void updateBounds() {
        bounds.x = x - size;
        bounds.y = y + size;
    }

    public boolean isCollected() {
        return collected;
    }

}
