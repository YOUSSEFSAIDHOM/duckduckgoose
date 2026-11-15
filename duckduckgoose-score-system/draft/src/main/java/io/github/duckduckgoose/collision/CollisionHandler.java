package io.github.duckduckgoose.collision;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import io.github.duckduckgoose.entities.Goose;
import io.github.duckduckgoose.entities.Player;
import io.github.duckduckgoose.items.AbstractItem;
import io.github.duckduckgoose.items.BirdSeeds;
import io.github.duckduckgoose.ui.GameHUD;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Checks points of collision to determine specific interactions with the player/enemies/items
 */
public class CollisionHandler {

    ArrayList<Rectangle> walls;
    ArrayList<Goose> geese;
    ArrayList<AbstractItem> pickableItems;
    Player player;
    GameHUD gameHud;

    /**
     * Constructor method for the collision handler
     *
     * @param walls array of all the wall collisions
     * @param geese array of all the geese collisions
     * @param player the player's collision
     * @param pickableItems array of all the item collisions
     */
    public CollisionHandler(ArrayList<Rectangle> walls, ArrayList<Goose> geese, Player player, ArrayList<AbstractItem> pickableItems, GameHUD gameHud) {
        this.walls = walls;
        this.geese = geese;
        this.player = player;
        this.pickableItems = pickableItems;
        this.gameHud = gameHud;
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

    /**
     * Checks if item is picked up and removes it from pickableItems
     * Done with iterators
     */
    public void resolveItems() {
        Iterator<AbstractItem> itemIterator = pickableItems.iterator();
        while(itemIterator.hasNext()) {
            AbstractItem item = itemIterator.next();
            if (player.checkItemCollision(item)) {
                player.pickUpItem(item);
                itemIterator.remove();
            }
        }
    }

    /**
     * Iterates through each goose and checks any collisions with player, walls, seeds or other geese.
     */
    public void resolveGeese() {
        for (Goose gooseA : geese) {
            Vector2 pushVector = new Vector2();

            if (gooseA.checkEntityCollision(player)) {
                if (gooseA.isChasing()) {
                    player.decreaseHealth();
                }
                pushVector.add(CollisionUtils.push(gooseA, player));
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
                    gooseA.eatSeeds();
                    droppedSeedIterator.remove();
                    if (!gooseA.isChasing()) {
                        gameHud.addScore(gooseA.getPoints());
                    }
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

    /**
     * updates all checks for collision detection.
     */
    public void update() {
        resolveGeese(); resolveWalls(); resolveItems();
    }

}
