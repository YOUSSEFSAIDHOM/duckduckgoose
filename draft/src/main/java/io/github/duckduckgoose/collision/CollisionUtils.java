package io.github.duckduckgoose.collision;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import io.github.duckduckgoose.entities.AbstractEntity;
import io.github.duckduckgoose.entities.Player;

public class CollisionUtils {

    // To replace the push functionality here

    public static Vector2 push(AbstractEntity entityA, AbstractEntity entityB) {
        float ox = entityA.getX() - entityB.getX();
        float oy = entityA.getY() - entityB.getY();
        float distance = Math.max((float) Math.sqrt(ox*ox + oy*oy), 0.01F);
        float overlap = (entityA.getSize() + entityB.getSize() - distance) / 2f;

        if (overlap <= 0) { return Vector2.Zero; } // The '.Zero' might be problematic, if stubborn just put Vector2(0,0);

        return new Vector2(ox / distance * overlap, oy / distance * overlap);
    }

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
