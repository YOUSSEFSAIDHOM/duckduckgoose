package io.github.duckduckgoose.collision;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import io.github.duckduckgoose.entities.Goose;
import io.github.duckduckgoose.entities.Player;

import java.util.ArrayList;

public class CollisionHandler {

    ArrayList<Rectangle> walls;
    ArrayList<Goose> geese;
    Player player;

    public CollisionHandler(ArrayList<Rectangle> walls, ArrayList<Goose> geese, Player player) {
        this.walls = walls;
        this.geese = geese;
        this.player = player;
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

    public void resolveGeese() {

        // This is for the geese
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
        resolveGeese(); resolveWalls();
    }

}
