package io.github.duckduckgoose.collision;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import io.github.duckduckgoose.effects.AbstractEffect;
import io.github.duckduckgoose.entities.Goose;
import io.github.duckduckgoose.entities.Player;
import io.github.duckduckgoose.items.AbstractItem;
import io.github.duckduckgoose.items.BirdSeeds;

import java.util.ArrayList;
import java.util.Iterator;

public class CollisionHandler {

    ArrayList<Rectangle> walls;
    ArrayList<Goose> geese;
    ArrayList<AbstractItem> pickableItems;
    Player player;

    public CollisionHandler(ArrayList<Rectangle> walls, ArrayList<Goose> geese, Player player, ArrayList<AbstractItem> pickableItems) {
        this.walls = walls;
        this.geese = geese;
        this.player = player;
        this.pickableItems = pickableItems;
    }

    public void resolveWalls() {
        Vector2 pushVector = new Vector2();

        for (Rectangle wall : walls) {
            if (player.checkWallCollision(wall)) {
                pushVector.add(CollisionUtils.wallPush(player, wall));
            }
        }

        player.setX(player.getX() + pushVector.x);
        player.setY(player.getY() + pushVector.y);
    }

    public void resolveItems() {
        /**
         * Checks if item is picked up and removes it from pickableItems
         * Done with iterators
         */
        Iterator<AbstractItem> itemIterator = pickableItems.iterator();
        while(itemIterator.hasNext()) {
            AbstractItem item = itemIterator.next();
            if (player.checkItemCollision(item)) {
                player.pickUpItem(item);
                itemIterator.remove();
            }
        }
    }

    public void resolveGeese() {
        /**
         * Iterates through each goose and checks any collisions with player, walls, seeds or other geese.
         */

        for (Goose gooseA : geese) {
            Vector2 pushVector = new Vector2();

            if (gooseA.checkEntityCollision(player)) {
                pushVector.add(CollisionUtils.push(gooseA, player));
                player.decreaseHealth();
            }

            for (Rectangle wall : walls) {
                if (gooseA.checkWallCollision(wall)) {
                    pushVector.add(CollisionUtils.wallPush(gooseA, wall));
                }
            }

            // Iterator used here so we can remove the seeds if a collision is detected
            Iterator<BirdSeeds> droppedSeedIterator = player.getDroppedSeeds().iterator();
            while (droppedSeedIterator.hasNext()) {
                BirdSeeds seeds = droppedSeedIterator.next();
                if (gooseA.checkItemCollision(seeds)) {
                    gooseA.setChasing(false);
                    droppedSeedIterator.remove();
                }
            }

            for (Goose gooseB : geese) {
                // The check if gooseA is the same instance of gooseB is already done in the checkCollision function
                if (gooseA.checkEntityCollision(gooseB)) {
                    pushVector.add(CollisionUtils.push(gooseA, gooseB));
                }
            }

            gooseA.setX(gooseA.getX() + pushVector.x);
            gooseA.setY(gooseA.getY() + pushVector.y);
        }
    }

    public void update() {
        resolveGeese(); resolveWalls(); resolveItems();
    }

}
