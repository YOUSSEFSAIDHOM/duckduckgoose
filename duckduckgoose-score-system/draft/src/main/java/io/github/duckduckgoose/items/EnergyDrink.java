package io.github.duckduckgoose.items;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import io.github.duckduckgoose.effects.EffectsHandler;
import io.github.duckduckgoose.effects.SpeedEffect;
import io.github.duckduckgoose.entities.Player;
import io.github.duckduckgoose.utils.GlobalVariables;

/**
 * Establishes the appearance and methods of the energy drink item
 */
public class EnergyDrink extends AbstractItem {

    public static final float speedMultiplier = GlobalVariables.speedEffectMultiplier;// seconds
    public static final Texture texture = new Texture("ver_3/assets/items/energyDrink.png");
    public static final TextureRegion textureRegion = new TextureRegion(texture);

    @Override
    public TextureRegion getIcon() {
        return textureRegion;
    }

    @Override
    public String getName() {
        return "Energy Drink";
    }

    /**
     * Constructor method for energy drink
     *
     * @param x x co-ordinate of the energy drink
     * @param y y co-ordinate of the energy drink
     */
    public EnergyDrink(float x, float y) {
        super(x, y);
    }

    /**
     * Establishes what happens once the energy drink has been used
     *
     * @param effectsHandler handles the state and duration of an effect
     * @param player the player object
     */
    @Override
    public void use(EffectsHandler effectsHandler, Player player) {
        SpeedEffect speedEffect = new SpeedEffect(GlobalVariables.energyDrinkDuration, GlobalVariables.speedEffectMultiplier);
        speedEffect.applyEffect();
        effectsHandler.addEffect(speedEffect);
    }

    /**
     * Draw energy drink on the map that hasn't been collected
     *
     * @param batch used to draw sprites
     */
    public void draw(SpriteBatch batch) {
        if (collected) {
            return;
        }
        batch.draw(textureRegion, x, y, 0.5f, 0.5f);
    }
}
