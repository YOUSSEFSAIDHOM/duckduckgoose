package io.github.duckduckgoose.events;

import com.badlogic.gdx.math.Rectangle;
import io.github.duckduckgoose.entities.Goose;
import io.github.duckduckgoose.utils.GlobalVariables;

import java.util.ArrayList;

public class GooseStampedeEvent extends AbstractEvent{
    ArrayList<Goose> geese;
    Rectangle gooseStampedeSpawn;

    public GooseStampedeEvent(ArrayList<Goose> geese, Rectangle gooseStampedeSpawn) {
        this.geese = geese;
        this.gooseStampedeSpawn = gooseStampedeSpawn;
    }


    @Override
    void trigger() {
        activated = true;
        for (int i = 0; i < GlobalVariables.geeseQuantity * 10; i++) {
            geese.add(
                    new Goose(
                            gooseStampedeSpawn.x,
                            gooseStampedeSpawn.y
                    )
            );
        }
    }
}
