package io.github.some_example_name.Player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Bat {
    float y;
    float x;
    int length;
    int height;


    public Bat(float x, float y, int length, int height) {
        this.x = x;
        this.y = y;
        this.length = length;
        this.height = height;
    }

    public void update(float x, float y) {
        this.x = x;
        // this.y = y;
    }

    public void draw(ShapeRenderer shape) {
        shape.rect(x, y, length, height);
    }

    public float getY() {
        return y;
    }

    public float getX() {
        return x;
    }

    public int getLength() {
        return length;
    }

    public int getHeight() {
        return height;
    }
}
