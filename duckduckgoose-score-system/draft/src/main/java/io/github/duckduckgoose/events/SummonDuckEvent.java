package io.github.duckduckgoose.events;

import com.badlogic.gdx.math.Rectangle;
import io.github.duckduckgoose.entities.DuckBoss;
import io.github.duckduckgoose.entities.Goose;

import java.util.ArrayList;

/**
 * Creates and event to summon a duck boss.
 */
public class SummonDuckEvent extends AbstractEvent {
    ArrayList<Goose> geese;
    Rectangle duckBossSpawn;

    /**
     * Activated if the player places seeds near the 'long boi' statue.
     *
     * @param geese array of all active geese
     * @param duckBossSpawn area where the duck boss will spawn in
     */
    public SummonDuckEvent(ArrayList<Goose> geese, Rectangle duckBossSpawn) {
        this.geese = geese;
        this.duckBossSpawn = duckBossSpawn;
        this.eventName = "Long boy";
    }

    /**
     * Establishes the trigger for the event and spawns the duck boss in the map.
     */
    @Override
    void trigger() {
        activated = true;
        geese.add(new DuckBoss(duckBossSpawn.x, duckBossSpawn.y));
    }

}
