package io.github.duckduckgoose.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

// This is for the colour don't get confused with the other one cuz intellij just imports some bs
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import io.github.duckduckgoose.GlobalVariables;
import io.github.duckduckgoose.collision.CollisionUtils;

import java.util.List;


public class Goose extends AbstractEntity {
    float speed = GlobalVariables.gooseSpeed;
    float distance, dx, dy;

    // Temp
    Texture playerSheet = new Texture("ver_3/assets/Duck_White_32x32.png");
    TextureRegion idlePlayer = new TextureRegion(playerSheet,96,0,32,64);

    boolean chasing = true;

    public Goose(float x, float y) {
        /**
         * Geese chase player and can deal supposed damage to the player
         *
         * @param speed how many pixels the goose covers per tick.
         * @param size is radius, entity collisions work by treating player/geese as circles
         */
        this.x = x; this.y = y;
        this.bounds = new Rectangle(this.x, this.y, 0.3f, 0.3f);
        this.size = GlobalVariables.gooseRadius;
    }

    private void chasePlayer(float deltaTime) {
        if (distance > 1) {
            x += (dx / distance) * speed * deltaTime;
            y += (dy / distance) * speed * deltaTime;
        }
    }

    public void update(Player player, float deltaTime) {
        dx = player.getX() - x;
        dy = player.getY() - y;

        distance = (float) Math.sqrt(dx*dx + dy*dy);
        if (chasing) { chasePlayer(deltaTime); }
        bounds.x = x; bounds.y = y;
    }

    public void draw(SpriteBatch batch) {
        float drawX = bounds.x + bounds.width / 2 - 0.5f;
        float drawY = bounds.y;
        batch.draw(idlePlayer, drawX, drawY, 1, 2);
    }
}
