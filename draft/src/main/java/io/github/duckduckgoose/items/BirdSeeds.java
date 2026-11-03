package io.github.duckduckgoose.items;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import io.github.duckduckgoose.entities.Player;

public class BirdSeeds extends AbstractItem {
    float x; float y;

    Color colour = new Color(Color.GREEN);

    public BirdSeeds(float x, float y) {
        this.x = x; this.y = y;
    }

    @Override
    public void use() {
    }

    @Override
    public void dispose(){

    }

    @Override
    public void update(Player player) {
        if (equipped) {
            this.x = player.getX();
            this.y = player.getY();
        }
    }

    @Override
    public void draw(ShapeRenderer shape) {
        shape.setColor(colour);
        shape.rect(x, y, 10, 10);
    }
}
