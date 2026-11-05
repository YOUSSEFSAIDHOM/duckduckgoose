package io.github.duckduckgoose.entities;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;

public class AbstractEntity {
    float x; float y; float size;
    Rectangle bounds;

    public float getX() { return x; }

    public float getY() {
        return y;
    }

    public float getSize() { return size; }

    public Rectangle getBounds() { return bounds; }

    public void setX(float x) {  this.x = x; }

    public void setY(float y) {  this.y = y; }

    public boolean checkWallCollision(Rectangle wall) {
        return this.bounds.overlaps(wall);
    }

    public boolean checkEntityCollision(AbstractEntity entity) {
        // Use circles for this instead of bounds to make the collision smoother
        if (this == entity) {
            return false;
        }

        Circle thisEntity = new Circle(this.getX(), this.getY(), this.getSize());
        Circle otherEntity = new Circle(entity.getX(), entity.getY(), entity.getSize());
        return thisEntity.overlaps(otherEntity);
    }
}
