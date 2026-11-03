package io.github.duckduckgoose;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import io.github.duckduckgoose.collision.CollisionHandler;
import io.github.duckduckgoose.entities.Goose;
import io.github.duckduckgoose.entities.Player;
import io.github.duckduckgoose.items.AbstractItem;
import io.github.duckduckgoose.mapping.GameMap;

import java.util.ArrayList;
import java.util.Random;

public class Main extends ApplicationAdapter {
    ShapeRenderer shape;
    GameMap gameMap;
    OrthographicCamera camera;
    FitViewport viewport;
    private float deltaTime;

    Player player;
    ArrayList<Goose> geese = new ArrayList<>();
    ArrayList<AbstractItem> items = new ArrayList<>();
    CollisionHandler collisionHandler;
    Vector2 spawn;
    SpriteBatch batch;

    Random random = new Random();
    private final String mapPath = "ver_3/maps/main_path.tmx";

    @Override
    public void create () {
        /**
         * The create function is part of the boiler plate for LibGDX projects.
         * Anything that needs to be instantiated is here
         */

        // Create the map

        gameMap = new GameMap(mapPath);
        camera = new OrthographicCamera();
        viewport = new FitViewport(20, 12, camera);
        batch = new SpriteBatch();

        // Parameter order is : x, y, speed, sprintSpeed, size, health
        spawn = gameMap.getPlayerSpawn();
        player = new Player(spawn.x, spawn.y, 8, 12, 0.8f, 100);
        shape = new ShapeRenderer();
        // Instantiating geese
        for (int i = 0; i < 30; i++) {
            // Parameter order is : x, y, speed, size
            geese.add(
                    new Goose(
                            random.nextFloat(gameMap.getWidth()),
                            random.nextFloat(gameMap.getHeight()),
                            10,
                            0.3f
                    )
            );
        }

        // Once everythings been instantiated we can create a collision handler
        collisionHandler = new CollisionHandler(gameMap.getWalls(), geese, player);
    }

    @Override
    public void render () {
        /**
         * The render function is part of the boiler plate as well,
         * This acts as our game loop.
         */

        deltaTime = Gdx.graphics.getDeltaTime();

        // Updates
        player.update(deltaTime);

        // Updating Map
        camera.position.set(player.getX(), player.getY(), 0);

        float halfWidth = camera.viewportWidth / 2;
        float halfHeight = camera.viewportHeight / 2;

        camera.position.x =  MathUtils.clamp(camera.position.x, halfWidth, gameMap.getWidth() - halfWidth);
        camera.position.y = MathUtils.clamp(camera.position.y, halfHeight, gameMap.getHeight() - halfHeight);
        camera.update();

        ScreenUtils.clear(0, 0, 0, 1);
        gameMap.render(camera);
        // ----

        shape.setProjectionMatrix(camera.combined);
        shape.begin(ShapeRenderer.ShapeType.Filled);

        collisionHandler.update();
        updateGeese();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        player.draw(batch);
        batch.end();
        // ...
        shape.end();
    }

    public void updateGeese() {
        for (Goose goose : geese) {
            goose.update(player, geese, deltaTime);
            goose.draw(shape);
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void dispose() {
        gameMap.dispose();
//        shapes.dispose();
//        batch.dispose();
//        player.dispose();
    }
    // Custom methods here
}