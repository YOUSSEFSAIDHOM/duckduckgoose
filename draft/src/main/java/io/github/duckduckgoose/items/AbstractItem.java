package io.github.duckduckgoose.items;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import io.github.duckduckgoose.entities.Player;

public abstract class AbstractItem {

    boolean equipped;

    public void dispose() {

    }

    // Keep this empty
    public void use() {

    }

    public void setEquipped() {
        this.equipped = true;
    }

    public void update(Player player) {

    }

    public void draw(ShapeRenderer shape) {
        //
    }

}
