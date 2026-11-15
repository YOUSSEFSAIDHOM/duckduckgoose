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

/**
 * Establish the Goose entity, it's characteristics and methods
 */
public class Goose extends AbstractEntity {
    float speed = GlobalVariables.gooseSpeed;
    int feedNum = GlobalVariables.gooseMaxFeed;
    int points; // The number of points the player gets after feeding a goose

    // Goose animation
    protected Texture animationSheet;
    protected Animation<TextureRegion> gooseAnimation;

    private float animTime = 0f;
    private float xVelocity;

    Color colour = new Color(Color.WHITE);
    boolean chasing = true;

    public boolean isChasing() { return this.chasing; }

    public void setChasing(boolean chasing) {
        this.chasing = chasing;
    }

    protected String getAnimationPath() {
        return GlobalVariables.gooseSheetPath;
    }

    public int getPoints() { return points; }

    /**
     * Constructor method for the Goose object
     *
     * @param x x co-ordinate of the goose
     * @param y y co-ordinate of the goose
     */
    public Goose(float x, float y) {
        super(x, y);
        this.size = GlobalVariables.gooseRadius;
        this.animationSheet = new Texture(getAnimationPath());
        this.points = 20;
        this.bounds = new Rectangle(x - 1f / 2f, y - 2f / 2f, 2 * size, 2 * size);
        createAnimation();
    }

    /**
     * Determines what the Goose should chase and the speed to do it
     *
     * @param deltaTime time between frames rendering
     * @param target the target for the goose to chase
     */
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

    /**
     * Update which target the goose is chasing after
     *
     * @param player the player object
     * @param droppedSeeds an array of all the seeds active in the game
     * @param deltaTime time between frames rendering
     */
    public void update(Player player, ArrayList<BirdSeeds> droppedSeeds, float deltaTime) {
        Vector2 nearestTarget = findNearestTarget(droppedSeeds, player);
        if (chasing) { chaseTarget(deltaTime, nearestTarget); }

        updateBounds();
        animTime += deltaTime;
    }

    public void eatSeeds() {
        feedNum--;
        if (feedNum == 0) {
            chasing = false;
        }
    }

    /**
     * Finds the nearest entity for the goose to target
     *
     * @param birdSeeds an array of all the seeds active in the game
     * @param player the player object
     * @return the nearest entity to the goose
     */
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

    /**
     * Draw the specific sprite/animation depending on the gooses direction
     *
     * @param batch method for drawing sprites
     */
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

    /**
     * Create the animation frame for the goose
     */
    private void createAnimation() {
        TextureRegion[][] tmp =
                TextureRegion.split(animationSheet, 64, 64);

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