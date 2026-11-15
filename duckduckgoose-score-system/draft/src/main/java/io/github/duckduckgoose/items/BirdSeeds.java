package io.github.duckduckgoose.items;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import io.github.duckduckgoose.effects.EffectsHandler;
import io.github.duckduckgoose.entities.Player;
import io.github.duckduckgoose.utils.GlobalVariables;

/**
 * Establishes the appearance and methods of the bird seed item
 */
public class BirdSeeds extends AbstractItem {

    // Bird seeds has 2 textures one which is a bag and one which is just
    public static final Texture texture = new Texture("ver_3/assets/items/birdSeeds.png");
    public static final TextureRegion textureRegion = new TextureRegion(texture);

    @Override
    public TextureRegion getIcon() {
        return textureRegion;
    }

    @Override
    public String getName() {
        return "Bird seeds";
    }

    /**
     * Constructor method for bird seeds
     *
     * @param x x co-ordinate of the bird seed
     * @param y y co-ordinate of the bird seed
     */
    public BirdSeeds(float x, float y) {
        super(x, y);
    }

    /**
     * Establishes what happens once the bird seed has been used
     *
     * @param effectsHandler handles the state and duration of an effect
     * @param player the player object
     */
    @Override
    public void use(EffectsHandler effectsHandler, Player player) {
        player.dropSeed(new BirdSeeds(player.getX(), player.getY()));
    }

    /**
     * Draw bird seed on the map that hasn't been collected
     *
     * @param batch used to draw sprites
     */
    @Override
    public void draw(SpriteBatch batch) {
        if (collected) {
            return;
        }
        batch.draw(textureRegion, x, y, 0.5f, 0.5f);
    }

}
