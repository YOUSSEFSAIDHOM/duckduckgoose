package io.github.duckduckgoose.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

// This is for the colour don't get confused with the other one cuz intellij just imports some bs
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import io.github.duckduckgoose.GlobalVariables;
import io.github.duckduckgoose.items.AbstractItem;


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
    AbstractItem hotbar [] = new AbstractItem[GlobalVariables.inventorySize];
    AbstractItem currentItem;

    final Texture playerSheet = new Texture("ver_3/assets/Farmer_1_32x32.png");
    final TextureRegion idlePlayer = new TextureRegion(playerSheet,96,0,32,64);

    // Pre-set values
    Color takeDamageColour = new Color(Color.CORAL);

    public Player(float x, float y) {
        /**
         * Player is able to move around, with W, A, S, D keys and use items,
         * specify its x and y to indicate starting position
         *
         * @param speed is the regular speed at which the player runs at
         * @param sprintSpeed when left shift is held down you can change the speed to this
         * @param size is radius, entity collisions work by treating player/geese as circles
         */
        this.x = x; this.y = y;
        this.bounds = new Rectangle(this.x, this.y, 0.5f, 0.5f);
        this.size = GlobalVariables.playerRadius;
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
        if(Gdx.input.isKeyPressed(Input.Keys.W)) {
            yVelocity = currentSpeed;
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            yVelocity = -currentSpeed;
        } else {
            yVelocity = 0;
        }

        // X Velocity (A and D keys)
        if(Gdx.input.isKeyPressed(Input.Keys.D)) {
            xVelocity = currentSpeed;
        } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            xVelocity = -currentSpeed;
        } else {
            xVelocity = 0;
        }

        // Updating x and y coordinates
        x += xVelocity; y+= yVelocity;

        // Updating bounds
        bounds.x = x; bounds.y = y;
    }

    public void draw(SpriteBatch batch) {
        float drawX = bounds.x + bounds.width / 2 - 0.5f;
        float drawY = bounds.y;

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

    public void pickupItem(AbstractItem item) {
        // Check if hotbar is full before picking up item

    }

    public void holdItem() {
        // use number system and set currentItem to one of hotbar
    }

    public void useItem() {
        // currentItem.use()
        // ..
    }

}
