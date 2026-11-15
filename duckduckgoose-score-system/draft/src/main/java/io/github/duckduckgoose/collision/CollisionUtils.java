package io.github.duckduckgoose.collision;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import io.github.duckduckgoose.entities.AbstractEntity;
import io.github.duckduckgoose.entities.Player;

/**
 * Utility methods to help establish collisions.
 */
public class CollisionUtils {

    /**
     * Establish the behaviour of two entities when they collide with each other
     *
     * @param entityA general entity, could be player, goose or item
     * @param entityB general entity, could be player, goose or item
     * @return vector to determine where the entities should be after the collision
     */
    public static Vector2 push(AbstractEntity entityA, AbstractEntity entityB) {
        // Uses circle as bounds so it looks smoother
        float ox = entityA.getX() - entityB.getX();
        float oy = entityA.getY() - entityB.getY();
        float distance = Math.max((float) Math.sqrt(ox*ox + oy*oy), 0.01F);
        float overlap = (entityA.getSize() + entityB.getSize() - distance) / 2f;

        if (overlap <= 0) { return new Vector2(0,0); } // The '.Zero' might be problematic, if stubborn just put Vector2(0,0);

        return new Vector2(ox / distance * overlap, oy / distance * overlap);

    }

    /**
     * Establish how the player interacts after colliding with the outer walls of the map
     *
     * @param player the player object and collision
     * @param wall the wall's collision area
     * @return a vector of where to push the player based on the wall position
     */
    public static Vector2 wallPush(AbstractEntity player, Rectangle wall) {
        Rectangle playerBounds = player.getBounds();

        Vector2 wallCentre = new Vector2(wall.x + wall.width / 2, wall.y + wall.height / 2 );
        Vector2 playerCentre = new Vector2(playerBounds.x + playerBounds.width / 2, playerBounds.y + playerBounds.height / 2 );

        float dx = playerCentre.x - wallCentre.x;
        float dy = playerCentre.y - wallCentre.y;

        float overlapX = (playerBounds.width / 2 + wall.width / 2) - Math.abs(dx);
        float overlapY = (playerBounds.height / 2 + wall.height / 2) - Math.abs(dy);

        if (overlapX <= 0 || overlapY <= 0) return new Vector2(0,0);

        // Push upwards
        if (overlapX > overlapY) {
            return new Vector2(0, (dy < 0 ? -1f : 1f) * overlapY);
        }
        // Else push sideways
        return new Vector2((dx < 0 ? -1f : 1f) * overlapX, 0);
    }
}
