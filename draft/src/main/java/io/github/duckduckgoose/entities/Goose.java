package io.github.duckduckgoose.entities;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

// This is for the colour don't get confused with the other one cuz intellij just imports some bs
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import io.github.duckduckgoose.collision.CollisionUtils;

import java.util.List;


public class Goose extends AbstractEntity {
    float speed;
    float distance, dx, dy;

    Color colour = new Color(Color.WHITE);
    boolean chasing = true;

    public Goose(float x, float y, float speed, float size) {
        /**
         * Geese chase player and can deal supposed damage to the player
         *
         * @param speed how many pixels the goose covers per tick.
         * @param size is radius, atm the goose is just a circle (placeholder)
         */
        this.x = x; this.y = y;
        this.speed = speed;
        this.size = size;
        this.bounds = new Rectangle(this.x, this.y, 0.3f, 0.3f);
    }

    public void chasePlayer(float deltaTime) {
        if (distance > 1) {
            x += (dx / distance) * speed * deltaTime;
            y += (dy / distance) * speed * deltaTime;
        }
    }

    public boolean checkCollision(Player player) {
        Circle playerCircle = new Circle(player.getX(), player.getY(), player.getSize());
        Circle thisGoose = new Circle(x, y, size);
        return playerCircle.overlaps(thisGoose);
    }

    public boolean checkCollision(Goose goose) {
        if (this == goose) {
            return false;
        }

        Circle gooseCircle = new Circle(goose.getX(), goose.getY(), goose.getSize());
        Circle thisGoose = new Circle(x, y, size);
        return gooseCircle.overlaps(thisGoose);
    }

    public void update(Player player, List<Goose> geese, float deltaTime) {
        dx = player.getX() - x;
        dy = player.getY() - y;

        distance = (float) Math.sqrt(dx*dx + dy*dy);
        if (chasing) { chasePlayer(deltaTime); }
        // separate(geese, player);as
        bounds.x = x; bounds.y = y;
    }

    public void draw(ShapeRenderer shape) {
        /**
         * @param shape for this class to call any drawing functionality,
         *              we need an instance of a libGDX ShapeRenderer object
         */
        shape.setColor(colour);
        shape.circle(x, y, size);
    }
}
