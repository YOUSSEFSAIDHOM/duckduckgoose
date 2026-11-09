package io.github.duckduckgoose;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
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
import io.github.duckduckgoose.items.BirdSeeds;
import io.github.duckduckgoose.mapping.GameMap;

import java.util.ArrayList;
import java.util.Random;

public class Main extends ApplicationAdapter {
    ShapeRenderer shape;
    GameMap gameMap;
    OrthographicCamera uiCamera;
    OrthographicCamera camera;
    FitViewport viewport;
    private float deltaTime;

    Player player;
    ArrayList<Goose> geese = new ArrayList<>();
    ArrayList<AbstractItem> items = new ArrayList<>();
    CollisionHandler collisionHandler;
    Vector2 spawn;
    SpriteBatch batch;
    BirdSeeds seeds;

    Random random = new Random();

    float targetX; float targetY;

    GlobalVariables variables = new GlobalVariables();


    @Override
    public void create () {
        /**
         * The create function is part of the boiler plate for LibGDX projects.
         * Anything that needs to be instantiated is here
         */

        // Initialise the UI Camera
        uiCamera = new OrthographicCamera();
        uiCamera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Create the map
        camera = new OrthographicCamera();
        viewport = new FitViewport(20, 12, camera);
        batch = new SpriteBatch();
        shape = new ShapeRenderer();

        startGame();
    }

    public void startGame() {
        // Parameter order is : x, y, size, health

        if (gameMap != null) {
            gameMap.dispose();
        } gameMap = new GameMap(randomMap());

        spawn = gameMap.getPlayerSpawn();
        player = new Player(spawn.x, spawn.y);
        // Instantiating geese
        geese.clear();
        for (int i = 0; i < GlobalVariables.geeseQuantity; i++) {
            // Parameter order is : x, y, speed, size
            geese.add(
                    new Goose(
                            random.nextFloat(gameMap.getWidth()),
                            random.nextFloat(gameMap.getHeight())
                    )
            );
        }
        seeds = new BirdSeeds(spawn.x, spawn.y, geese);
        seeds.setEquipped();

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

        // Updating Camera
        targetX = player.getX(); targetY = player.getY();
        // using linear interpolation to add some smooth factor to the trackinge = Gdx.graphics.getDeltaTime();
        float smooth = 1f - (float) Math.pow(1f - GlobalVariables.lerpFactor, deltaTime * 60f);
        camera.position.x = MathUtils.lerp(camera.position.x, targetX, smooth);
        camera.position.y = MathUtils.lerp(camera.position.y, targetY, smooth);

        float halfWidth = camera.viewportWidth / 2;
        float halfHeight = camera.viewportHeight / 2;

        camera.position.x =  MathUtils.clamp(camera.position.x, halfWidth, gameMap.getWidth() - halfWidth);
        camera.position.y = MathUtils.clamp(camera.position.y, halfHeight, gameMap.getHeight() - halfHeight);
        camera.update();

        ScreenUtils.clear(0, 0, 0, 1);
        gameMap.render(camera);

        // ----

        shape.setProjectionMatrix(uiCamera.combined);
        batch.setProjectionMatrix(camera.combined);

        batch.begin();

        // Updates
        if (player.alive) {
            collisionHandler.update();
            player.update(deltaTime);
            updateGeese();
            player.draw(batch);

            seeds.update(player);
            if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
                seeds.use();
            }

        } else {
            // Press space to restart game
            if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
                startGame();
            }
        }

        batch.end();

        // Healthbar
        shape.begin(ShapeRenderer.ShapeType.Filled);
        displayHealthBar(shape);
        shape.end();

    }

    @Override
    public void resize(int width, int height) {

        viewport.update(width, height);
        uiCamera.setToOrtho(false, width, height);
    }

    @Override
    public void dispose() {
        gameMap.dispose();
    }

    // Custom methods here

    public void displayHealthBar(ShapeRenderer shape) {
        shape.setColor(Color.RED);
        shape.rect(0, Gdx.graphics.getHeight()-20, player.getHealth(), 20);
    }

    public void updateGeese() {
        for (Goose goose : geese) {
            goose.update(player, deltaTime);
            goose.draw(batch);
        }
    }

    public String randomMap() {
        return GlobalVariables.mapList.get(random.nextInt(GlobalVariables.mapList.size()));
    }
}