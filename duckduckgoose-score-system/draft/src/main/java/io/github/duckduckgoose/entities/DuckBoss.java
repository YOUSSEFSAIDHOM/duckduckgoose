package io.github.duckduckgoose.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import io.github.duckduckgoose.items.BirdSeeds;
import io.github.duckduckgoose.utils.GlobalVariables;

import java.util.ArrayList;

public class DuckBoss extends Goose {

    private int feedCount = 0;
    private boolean fading = false;
    private float fade = 1f;

    public DuckBoss(float x, float y) {
        super(x, y);
        this.size = GlobalVariables.duckBossRadius;
        this.bounds = new Rectangle(x - size, y - size, 2 * size, 2 * size);
        this.drawSize = GlobalVariables.duckBossDrawSize;
        this.speed = GlobalVariables.duckBossSpeed;
    }

    @Override
    public void ateSeed() {
        if (fading) return;

        feedCount++;
        if (feedCount >= GlobalVariables.duckBossSeedsNeeded) {
            fading = true;
            setChasing(false);
        }
    }

    @Override
    public void update(Player player, ArrayList<BirdSeeds> droppedSeeds, float deltaTime) {
        if (fading) {
            fade -= deltaTime / GlobalVariables.duckBossFadeTime;
            if (fade < 0) fade = 0;
        }
        super.update(player, droppedSeeds, deltaTime);
    }

    @Override
    public void draw(SpriteBatch batch) {
        Color previous = batch.getColor().cpy();
        batch.setColor(previous.r, previous.g, previous.b, fade);
        super.draw(batch);
        batch.setColor(previous);
    }

    public boolean isFading() { return fading; }

    public boolean isGone() { return fading && fade <= 0; }

    public int getFeedCount() { return feedCount; }
}
