package io.github.some_example_name.Player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Ball {

    float x;
    float y;

    int size;

    float xSpeed;
    float ySpeed;
    float speedFactor;

    Color colour = Color.WHITE;


    public Ball(float x, float y, int size, float xSpeed, float ySpeed, float speedFactor) {
        this.x = x;
        this.y = y;

        this.size = size;

        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.speedFactor = speedFactor;
    }

    public void increaseSpeed() {
        xSpeed *= speedFactor;
        ySpeed *= speedFactor;
    }

    public void checkCollision(Bat bat) {
        if(collidesWith(bat)){
            ySpeed *= -1;
            increaseSpeed();
        }
    }

    public void checkCollision(Block block) {
        if (collidesWith(block)) {
            ySpeed *= -1;
            block.destroyed = true;
        }
    }

    public boolean collidesWith(Bat bat) {
        Rectangle batRect = new Rectangle(bat.getX(), bat.getY(), bat.getLength(), bat.getHeight());
        Circle ballCircle = new Circle(x, y, size);
        return Intersector.overlaps(ballCircle, batRect);
    }

    public boolean collidesWith(Block block) {
        Rectangle batRect = new Rectangle(block.getX(), block.getY(), block.getLength(), block.getHeight());
        Circle ballCircle = new Circle(x, y, size);
        return Intersector.overlaps(ballCircle, batRect);
    }

    public void update() {
        x += xSpeed;
        y += ySpeed;

        if (x < 0 || x > Gdx.graphics.getWidth()) {
            // increaseSpeed();
            xSpeed = -xSpeed;
        }
        if (y < 0 || y > Gdx.graphics.getHeight()) {
            // increaseSpeed();
            ySpeed = -ySpeed;
        }
    }

    public void draw(ShapeRenderer shape) {
        shape.setColor(colour);
        shape.circle(x, y, size);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getSize() {
        return size;
    }
}
