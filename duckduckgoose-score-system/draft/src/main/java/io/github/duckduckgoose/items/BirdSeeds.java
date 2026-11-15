package io.github.duckduckgoose.items;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import io.github.duckduckgoose.effects.EffectsHandler;
import io.github.duckduckgoose.entities.Player;
import io.github.duckduckgoose.utils.GlobalVariables;

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

    public BirdSeeds(float x, float y) {
        super(x, y);
    }

    @Override
    public void use(EffectsHandler effectsHandler, Player player) {
        player.dropSeed(new BirdSeeds(player.getX(), player.getY()));
    }

    @Override
    public void draw(SpriteBatch batch) {
        if (collected) {
            return;
        }
        batch.draw(textureRegion, x, y, 0.5f, 0.5f);
    }

}
