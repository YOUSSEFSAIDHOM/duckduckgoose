package io.github.some_example_name.Player;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

public class Block {
    float x, y;
    int length, height;
    public boolean destroyed = false;

    public Block(float x, float y, int length, int height) {
        this.x = x; this.y = y;
        this.length = length; this.height = height;
    }

    public void draw(ShapeRenderer shape) {
        shape.rect(x, y, length, height);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getLength() {
        return length;
    }

    public int getHeight() {
        return height;
    }
}
