package com;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

// This is for the colour don't get confused with the other one cuz intellij just imports some bs
import com.badlogic.gdx.graphics.Color;


public class Player {
    float x, y;
    int size;
    float speed;
    float sprintSpeed;
    float currentSpeed;

    float xVelocity = 0;
    float yVelocity = 0;

    Color colour = new Color(Color.CORAL);

    public float getX() { return x; }

    public float getY() {
        return y;
    }

    public int getSize() { return size; }

    public Player(float x, float y, float speed, float sprintSpeed, int size) {
        /**
         * Player is able to move around, with W, A, S, D keys and use items,
         * specify its x and y to indicate starting position
         *
         * @param speed is the regular speed at which the player runs at
         * @param sprintSpeed when left shift is held down you can change the speed to this
         * @param size is radius, atm the player is just a circle (placeholder)
         */
        this.x = x; this.y = y;
        this.speed = speed;
        this.sprintSpeed = sprintSpeed;
        this.size = size;
    }

    public void update() {

        // -=-=-=-=-=-=-=-=-=
        //    Movement
        // =-=-=-=-=-=-=-=-=-

        // Sprinting mechanism
        if (Gdx.input.isKeyPressed(Input.Keys.META_SHIFT_LEFT_ON)) {
            currentSpeed = sprintSpeed;
        } else { currentSpeed = speed; }

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
    }

    public void draw(ShapeRenderer shape) {
        /**
         * @param shape for this class to call any drawing functionality,
         *              we need an instance of a libGDX ShapeRenderer object
         */
        // For now let's just make this player a circle
        shape.setColor(colour);
        shape.circle(x, y, size);
    }
}
