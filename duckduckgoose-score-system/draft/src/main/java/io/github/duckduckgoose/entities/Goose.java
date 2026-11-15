package io.github.duckduckgoose.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.utils.Array;

// This is for the colour don't get confused with the other one cuz intellij just imports some bs
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import io.github.duckduckgoose.items.BirdSeeds;
import io.github.duckduckgoose.utils.GlobalVariables;

import java.util.ArrayList;


public class Goose extends AbstractEntity {
    float speed = GlobalVariables.gooseSpeed;
    protected float drawSize = 2f;

    // Goose animation
    static Texture gooseSheet = new Texture("ver_3/assets/sprites/goose_walk.png");
    static Animation<TextureRegion> gooseAnimation;

    private float animTime = 0f;
    private float xVelocity;

    Color colour = new Color(Color.WHITE);
    boolean chasing = true;

    public boolean isChasing() { return this.chasing; }
    public void setChasing(boolean chasing) {
        this.chasing = chasing;
    }

    public void ateSeed() {
        setChasing(false);
    }

    public Goose(float x, float y) {
        /**
         * Geese chase player and can deal supposed damage to the player
         *
         * @param speed how many pixels the goose covers per tick.
         * @param size is radius, entity collisions work by treating player/geese as circles
         */
        super(x, y);
        this.size = GlobalVariables.gooseRadius;
        this.bounds = new Rectangle(x - size, y - size, 2 * size, 2 * size);

        if (gooseAnimation == null) {
            createAnimation();
        }
    }

    public void chaseTarget(float deltaTime, Vector2 target) {
        float distance = (float) Math.sqrt(target.x * target.x + target.y * target.y);

        if (distance > 0) {
            x += (target.x / distance) * speed * deltaTime;
            y += (target.y / distance) * speed * deltaTime;

            xVelocity = (target.x / distance) * speed;
        }
        else {
            xVelocity = 0;
        }
    }

    public void update(Player player, ArrayList<BirdSeeds> droppedSeeds, float deltaTime) {
        Vector2 nearestTarget = findNearestTarget(droppedSeeds, player);
        if (chasing) { chaseTarget(deltaTime, nearestTarget); }

        updateBounds();
        animTime += deltaTime;
    }

    private Vector2 findNearestTarget(ArrayList<BirdSeeds> birdSeeds, Player player) {

        float playerDistance = (float) Math.sqrt(
                (player.getX() - x) * (player.getX() - x) +
                        (player.getY() - y) * (player.getY() - y)
        );

        float seedDistance = Float.POSITIVE_INFINITY;
        float tempDistance = 0;
        BirdSeeds closestSeed = null;

        for (BirdSeeds seeds : birdSeeds) {
            tempDistance = (float) Math.sqrt(
                    (seeds.getX() - x) * (seeds.getX() - x) +
                            (seeds.getY() - y) * (seeds.getY() - y)
            );

            if (tempDistance < seedDistance) {
                seedDistance = tempDistance;
                closestSeed = seeds;
            }
        }
        
        if (playerDistance <= seedDistance) {
            return new Vector2(player.getX() - x, player.getY() - y);
        } return new Vector2(closestSeed.getX() - x, closestSeed.getY() - y);
    }

    // Animations

    public void draw(SpriteBatch batch) {
        float drawWidth = drawSize;
        float drawHeight = drawSize;

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
            batch.draw(currentFrame, drawX, drawY, drawSize, drawSize);
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