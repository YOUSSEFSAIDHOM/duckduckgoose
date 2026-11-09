package com.badlogic.drop;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.GL20;
import com.EnergyDrink;
import com.Goose;
import com.Player;
import com.EnergyDrinkPickup;

import java.util.ArrayList;
import java.util.Random;

public class Main extends ApplicationAdapter {
    ShapeRenderer shape;
    Player player;
    ArrayList<Goose> geese = new ArrayList<>();


    EnergyDrinkPickup energyDrinkPickup;

    Random random = new Random();

    @Override
    public void create () {
        player = new Player(50, 50, 8, 13, 20);
        shape = new ShapeRenderer();

        // Create Energy Drink pickup
        energyDrinkPickup = new EnergyDrinkPickup(200, 200, 10);

        // Instantiating geese
        for (int i = 0; i < 30; i++) {
            geese.add(
                new Goose(
                    random.nextFloat(Gdx.graphics.getWidth()),
                    random.nextFloat(Gdx.graphics.getHeight()),
                    5,
                    10
                )
            );
        }
    }

    @Override
    public void render () {
        // Clear the screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Updates
        player.update();

        // Check if player collects Energy Drink
        energyDrinkPickup.tryCollect(player);

        // Rendering
        shape.begin(ShapeRenderer.ShapeType.Filled);
        // ...
        for (Goose goose : geese) {
            goose.update(player, geese);
            goose.draw(shape);
        }
        player.draw(shape);
        // Draw Energy Drink
        energyDrinkPickup.draw(shape);

        shape.end();
    }

    // Custom methods here
}
