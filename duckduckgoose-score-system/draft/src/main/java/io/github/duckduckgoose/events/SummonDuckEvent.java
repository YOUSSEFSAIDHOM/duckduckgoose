package io.github.duckduckgoose.events;

import com.badlogic.gdx.math.Rectangle;
import io.github.duckduckgoose.entities.DuckBoss;
import io.github.duckduckgoose.entities.Goose;

import java.util.ArrayList;

public class SummonDuckEvent extends AbstractEvent {
    ArrayList<Goose> geese;
    Rectangle duckBossSpawn;

    public SummonDuckEvent(ArrayList<Goose> geese, Rectangle duckBossSpawn) {
        this.geese = geese;
        this.duckBossSpawn = duckBossSpawn;
        this.eventName = "Long boy";
    }
    @Override
    void trigger() {
        activated = true;
        geese.add(new DuckBoss(duckBossSpawn.x, duckBossSpawn.y));
    }

}
