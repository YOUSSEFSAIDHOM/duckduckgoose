package io.github.duckduckgoose.entities;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import io.github.duckduckgoose.items.AbstractItem;

/**
 * An abstract class which is the basis of the player, geese and item entities
 */
public class AbstractEntity {
    protected float x; protected float y; protected float size;
    protected Rectangle bounds;

    public float getX() { return x; }

    public float getY() {
        return y;
    }

    public float getSize() { return size; }

    public Rectangle getBounds() { return bounds; }

    public void setX(float x) {  this.x = x; }

    public void setY(float y) {  this.y = y; }

    public void updateBounds() {
        bounds.x = x - size;
        bounds.y = y - size;
    }

    public AbstractEntity(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * check what the collision of a wall is
     *
     * @param wall the wall to check
     * @return the collision of the wall
     */
    public boolean checkWallCollision(Rectangle wall) {
        return this.bounds.overlaps(wall);
    }

    /**
     * check what the collision of an entity is
     *
     * @param entity the item to check
     * @return the collision of the entity
     */
    public boolean checkEntityCollision(AbstractEntity entity) {
        // Use circles for this instead of bounds to make the collision smoother
        if (this == entity) {
            return false;
        }

        Circle thisEntity = new Circle(this.getX(), this.getY(), this.getSize());
        Circle otherEntity = new Circle(entity.getX(), entity.getY(), entity.getSize());
        return thisEntity.overlaps(otherEntity);
    }

    /**
     * check what the collision of an item is
     *
     * @param item the item to check
     * @return the collision of the item
     */
    public boolean checkItemCollision(AbstractItem item) {
        Circle thisEntity = new Circle(x, y, size);
        Circle itemCircle = new Circle(item.getX(), item.getY(), item.getSize());
        return thisEntity.overlaps(itemCircle);
    }
}
