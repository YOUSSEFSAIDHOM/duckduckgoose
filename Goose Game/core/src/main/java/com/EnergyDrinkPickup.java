package com;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class EnergyDrinkPickup {
    private final float x, y;
    private final int size;
    private boolean collected = false;

    public EnergyDrinkPickup(float x, float y, int size) {
        this.x = x;
        this.y = y;
        this.size = size;
    }

    public boolean tryCollect(Player player) {
        if (collected) {
            return false;
        }
        float dx = player.getX() - x;
        float dy = player.getY() - y;
        float Distance = player.getSize() + size;
        if (dx * dx + dy * dy <= Distance * Distance) {
            collected = true;
            player.useEnergyDrink();
            return true;
        }
        return false;
    }

    public void draw(ShapeRenderer shape) {
       if (collected) {
            return;
        }
        shape.setColor(Color.YELLOW);
        shape.circle(x, y, size);
    }

    public boolean isCollected() {
        return collected;
    }
}