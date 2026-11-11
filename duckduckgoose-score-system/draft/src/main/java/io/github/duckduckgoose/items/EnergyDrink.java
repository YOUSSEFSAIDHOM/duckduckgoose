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

    public EnergyDrink(float x, float y) {
        super(x, y);
    }

    @Override
    public void use(EffectsHandler effectsHandler, Player player) {
        SpeedEffect speedEffect = new SpeedEffect(GlobalVariables.energyDrinkDuration, GlobalVariables.speedEffectMultiplier);
        speedEffect.applyEffect();
        effectsHandler.addEffect(speedEffect);
    }

    public void draw(SpriteBatch batch) {
        if (collected) {
            return;
        }
        batch.draw(textureRegion, x, y, 0.5f, 0.5f);
    }
}
