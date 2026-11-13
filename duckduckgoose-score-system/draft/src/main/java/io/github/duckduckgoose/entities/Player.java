package io.github.duckduckgoose.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

// This is for the colour don't get confused with the other one cuz intellij just imports some bs
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import io.github.duckduckgoose.effects.EffectsHandler;
import io.github.duckduckgoose.items.BirdSeeds;
import io.github.duckduckgoose.ui.Hotbar;
import io.github.duckduckgoose.utils.GlobalVariables;
import io.github.duckduckgoose.items.AbstractItem;

import java.util.ArrayList;


public class Player extends AbstractEntity {
    // Core movement variables
    float xVelocity = 0;
    float yVelocity = 0;

    float health = GlobalVariables.playerHealth;
    float speed = GlobalVariables.playerSpeed;
    float sprintSpeed = GlobalVariables.playerSprintSpeed;
    float currentSpeed;

    public boolean alive = true;

    // Inventory
    Hotbar hotbar = new Hotbar(GlobalVariables.inventorySize);
    ArrayList<BirdSeeds> droppedSeeds = new ArrayList<>();
    AbstractItem currentItem;
    EffectsHandler effectsHandler = new EffectsHandler();
    int hotbarItemIndex = 0;

    // yeah this needs to change
    final Texture playerSheet = new Texture("ver_3/assets/sprites/Farmer_1_32x32.png");
    final TextureRegion idlePlayer = new TextureRegion(playerSheet,96,0,32,64);

    // Pre-set values
    Color takeDamageColour = new Color(Color.CORAL);

    public Hotbar getHotbar() { return hotbar; }

    public ArrayList<BirdSeeds> getDroppedSeeds() { return droppedSeeds; }

    public void dropSeed(BirdSeeds seeds) {
        droppedSeeds.add(seeds);
    }

    public Player(float x, float y) {
        super(x, y);
        /**
         * Player is able to move around, with W, A, S, D keys and use items,
         * specify its x and y to indicate starting position
         *
         * @param speed is the regular speed at which the player runs at
         * @param sprintSpeed when left shift is held down you can change the speed to this
         * @param size is radius, entity collisions work by treating player/geese as circles
         */
        this.size = GlobalVariables.playerRadius;
        this.bounds = new Rectangle(x - size, y - size, 2 * size, 2 * size);
    }

    public void update(float deltaTime) {

        // First check the player is still alive

        if (health <= 0) {
            alive = false;
        }

        // -=-=-=-=-=-=-=-=-=
        //    Movement
        // =-=-=-=-=-=-=-=-=-

        // Sprinting mechanism
        if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
            currentSpeed = sprintSpeed  * deltaTime;
        } else { currentSpeed = speed * deltaTime; }

        // Y Velocity (W and S keys)
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            yVelocity = currentSpeed * GlobalVariables.playerSpeedMultiplier;
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            yVelocity = -currentSpeed * GlobalVariables.playerSpeedMultiplier;
        } else {
            yVelocity = 0;
        }

        // X Velocity (A and D keys)
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            xVelocity = currentSpeed * GlobalVariables.playerSpeedMultiplier;
        } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            xVelocity = -currentSpeed * GlobalVariables.playerSpeedMultiplier;
        } else {
            xVelocity = 0;
        }

        // Use item when you press E, to avoid spamming E use isKeyJustPressed
        if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
            useItem();
        }

        // Testing bird seeds ignore this and delete it before submission
        if (Gdx.input.isKeyPressed(Input.Keys.F)) {
            pickUpItem(new BirdSeeds(0,0));
        }

        // Update hotbar
        hotbarItemIndex = getKeyNumber();
        holdItem(hotbarItemIndex);

        // Updating x and y coordinates
        x += xVelocity; y+= yVelocity;

        // And finally update the effectsHandler
        effectsHandler.updateEffects(deltaTime);

        // Updating bounds
        updateBounds();
    }

    public void draw(SpriteBatch batch) {
        float drawX = bounds.x; // + bounds.width / 2 - 0.5f;
        float drawY = bounds.y; // + bounds.height / 2 - 0.5f;

        batch.draw(idlePlayer, drawX, drawY, 1, 2);
    }

    // Health methods

    public float getHealth() {
        return health;
    }

    public void decreaseHealth() {
        this.health -= GlobalVariables.gooseDamage;
    }

    public void increaseHealth() {
        this.health++;
    }

    // Item management

    private int getKeyNumber() {
        // Controlling the hotbar with numbers 1-5 and returns an index to set currentItem with
        if (Gdx.input.isKeyPressed(Input.Keys.NUM_1)) return 0;
        if (Gdx.input.isKeyPressed(Input.Keys.NUM_2)) return 1;
        if (Gdx.input.isKeyPressed(Input.Keys.NUM_3)) return 2;
        if (Gdx.input.isKeyPressed(Input.Keys.NUM_4)) return 3;
        if (Gdx.input.isKeyPressed(Input.Keys.NUM_5)) return 4;
        return hotbarItemIndex;
    }

    public void pickUpItem(AbstractItem item) {
        hotbar.addItem(item);
    }

    private void holdItem(int index) {
        // use number system and set currentItem to one of hotbar
        currentItem = hotbar.getItem(index);
    }

    private void useItem() {
        if (currentItem != null) {
            currentItem.use(effectsHandler, this);
            hotbar.removeItem(hotbarItemIndex);
            currentItem = null;
        }
    }

}
