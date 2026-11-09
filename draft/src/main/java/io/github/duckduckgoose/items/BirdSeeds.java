package io.github.duckduckgoose.items;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import io.github.duckduckgoose.entities.Player;
import io.github.duckduckgoose.GlobalVariables;
import io.github.duckduckgoose.entities.Goose;
import java.util.List;

public class BirdSeeds extends AbstractItem {
    float x; float y;
    List<Goose> geese;
    boolean used = false;

    boolean eaten = false;
    float eatTimer;


    Color colour = new Color(Color.GREEN);

    public BirdSeeds(float x, float y, List<Goose> geese) {
        this.x = x; this.y = y;
        this.geese = geese;
    }

    @Override
    public void use() {
        if (used) return;
        attractGeese();
        used = true;
    }
    private void attractGeese(){
        for (Goose goose : geese) {
            float dx = goose.getX() - x;
            float dy = goose.getY() - y;
            float distance = (float) Math.sqrt(dx * dx + dy * dy);

            if (distance < GlobalVariables.seedRadius && !goose.isDistracted()) {
                goose.distract(x, y, GlobalVariables.seedEatTime);
            }
        }

    }

    @Override
    public void dispose(){

    }

    @Override
    public void update(Player player) {
        if (equipped && !used) {
            this.x = player.getX();
            this.y = player.getY();
        }

        if (used && !eaten){
            
        }
    }

    @Override
    public void draw(ShapeRenderer shape) {
        shape.setColor(colour);
        shape.rect(x, y, 10, 10);
    }
}
