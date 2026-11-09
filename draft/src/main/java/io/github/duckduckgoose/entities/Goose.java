package io.github.duckduckgoose.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.utils.Array;
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
    float distance, targetX, targetY, dx, dy;

    // Goose animation
    static Texture gooseSheet = new Texture("ver_3/assets/goose_walk.png");
    static Animation<TextureRegion> gooseAnimation;

    private float animTime = 0f;
    private float xVelocity;

    Color colour = new Color(Color.WHITE);
    boolean chasing = true;

    public Goose(float x, float y) {
        /**
         * Geese chase player and can deal supposed damage to the player
         *
         * @param speed how many pixels the goose covers per tick.
         * @param size is radius, entity collisions work by treating player/geese as circles
         */
        this.x = x; this.y = y;
        this.targetX = x; this.targetY = y;
        this.bounds = new Rectangle(this.x, this.y, 0.3f, 0.3f);
        this.size = GlobalVariables.gooseRadius;

        if (gooseAnimation == null) {
            createAnimation();
        }
    }

    public void chasePlayer(float deltaTime) {
        if (distance > 1) {
            x += (dx / distance) * speed * deltaTime;
            y += (dy / distance) * speed * deltaTime;

            xVelocity = (dx / distance) * speed;
        }
        else {
            xVelocity = 0;
        }
    }

    public void update(Player player, float deltaTime) {
        dx = player.getX() - x;
        dy = player.getY() - y;

        distance = (float) Math.sqrt(dx*dx + dy*dy);
        if (chasing) { chasePlayer(deltaTime); }
        bounds.x = x; bounds.y = y;

        animTime += deltaTime;
    }

    public void draw(SpriteBatch batch) {
        float drawWidth = 2f;
        float drawHeight = 2f;

        float drawX = bounds.x + bounds.width / 2 - drawWidth / 2;
        float drawY = bounds.y;

        // Get current animation frame
        TextureRegion currentFrame =
                gooseAnimation.getKeyFrame(animTime);

        // Is the goose moving left
        boolean movingLeft = xVelocity < 0;

        if (movingLeft) {
            // Flip da goose
            batch.draw(
                    currentFrame,
                    drawX, drawY,
                    drawWidth / 2, 0f,
                    drawWidth, drawHeight,
                    -1f, 1f,
                    0f
            );
        }

        else {
            // Dont flip
            batch.draw(currentFrame, drawX, drawY, 2f, 2f);
        }
    }

    private static void createAnimation() {
        TextureRegion[][] tmp =
                TextureRegion.split(gooseSheet, 64, 64);
        
        Array<TextureRegion> frames = new Array<>();

        for (TextureRegion frame : tmp[0]) {
            frames.add(frame);
        }
        
        gooseAnimation = new Animation<>(
                0.3f, frames
        );
        
        gooseAnimation.setPlayMode(Animation.PlayMode.LOOP);
    }
}
