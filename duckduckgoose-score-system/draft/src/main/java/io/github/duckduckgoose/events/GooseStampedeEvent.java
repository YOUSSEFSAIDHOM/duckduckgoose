package io.github.duckduckgoose.events;

import com.badlogic.gdx.math.Rectangle;
import io.github.duckduckgoose.entities.Goose;
import io.github.duckduckgoose.utils.GlobalVariables;

import java.util.ArrayList;
import java.util.Random;

/**
 * Creates an event to summon a stampede of geese.
 */
public class GooseStampedeEvent extends AbstractEvent{
    ArrayList<Goose> geese;
    Rectangle gooseStampedeSpawn;

    /**
     * Activated once the seeds are dropped at far north area of the map.
     *
     * @param geese array of all active geese
     * @param gooseStampedeSpawn area where the stampede of geese spawn from
     */
    public GooseStampedeEvent(ArrayList<Goose> geese, Rectangle gooseStampedeSpawn) {
        this.geese = geese;
        this.gooseStampedeSpawn = gooseStampedeSpawn;
        this.eventName = "Goose stampede";
    }

    /**
     * Establishes how the event should be triggered + how many geese to spawn.
     */
    @Override
    void trigger() {
        // Spawns twice as many geese in the region
        Random random = new Random();
        activated = true;
        for (int i = 0; i < GlobalVariables.geeseQuantity; i++) {
            geese.add(
                    new Goose(
                            random.nextFloat(gooseStampedeSpawn.x, gooseStampedeSpawn.x+gooseStampedeSpawn.width),
                            gooseStampedeSpawn.y
                    )
            );
        }
    }
}
