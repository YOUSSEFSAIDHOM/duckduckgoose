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
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import io.github.duckduckgoose.collision.CollisionHandler;
import io.github.duckduckgoose.entities.Goose;
import io.github.duckduckgoose.entities.Player;
import io.github.duckduckgoose.events.EventsHandler;
import io.github.duckduckgoose.items.AbstractItem;
import io.github.duckduckgoose.items.BirdSeeds;
import io.github.duckduckgoose.items.EnergyDrink;
import io.github.duckduckgoose.mapping.GameMap;
import io.github.duckduckgoose.ui.GameHUD;
import io.github.duckduckgoose.utils.GlobalVariables;
import io.github.duckduckgoose.utils.ItemStack;

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
    ArrayList<AbstractItem> pickableItems = new ArrayList<>();

    CollisionHandler collisionHandler;
    EventsHandler eventsHandler;

    Vector2 spawn;
    SpriteBatch batch;

    Random random = new Random();

    // TargetX and TargetY are for camera
    float targetX; float targetY;

    private GameHUD gameHud;
    private Stage stage;

    public Vector2 randomGooseSpawn() { return gameMap.getGeeseSpawn().get(random.nextInt(gameMap.getGeeseSpawn().size())); }

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

        // Create the controls UI
        stage = new Stage(new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));

        Gdx.input.setInputProcessor(stage);

        // Setting up before starting game
        if (gameMap != null) {
            gameMap.dispose();
        } gameMap = new GameMap(GlobalVariables.mapPath);

        spawn = gameMap.getPlayerSpawn();
        player = new Player(spawn.x, spawn.y);
        gameHud = new GameHUD(stage, player.getHotbar().getTable());

        startGame();
    }

    public void startGame() {

        // Parameter order is : x, y, size, healt

        // Initialise the items in the hotbar
        player.pickUpItem(new EnergyDrink(0,0));
        player.pickUpStack(new ItemStack(new BirdSeeds(0,0), GlobalVariables.geeseQuantity + 20));

        // Instantiating geese
        geese.clear();
        for (int i = 0; i < GlobalVariables.geeseQuantity; i++) {
            // Parameter order is : x, y
            Vector2 gooseSpawn = randomGooseSpawn();
            geese.add(
                    new Goose(gooseSpawn.x, gooseSpawn.y)
            );
        }

        // Adding items for testing
        Vector2 itemSpawn = randomGooseSpawn();
        pickableItems.add(
                new EnergyDrink(itemSpawn.x, itemSpawn.y)
        );

        // Testing


        // Once everythings been instantiated we can create a collision handler
        collisionHandler = new CollisionHandler(gameMap.getWalls(), geese, player, pickableItems, gameHud);
        eventsHandler = new EventsHandler(player.getDroppedSeeds(), geese, gameMap.getGooseStampede(), gameMap.getStatue());
    }

    @Override
    public void render () {
        /**
         * The render function is part of the boiler plate as well,
         * This acts as our game loop.
         */


        deltaTime = Gdx.graphics.getDeltaTime();
        gameHud.tickCooldown(deltaTime);

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

        // ----

        shape.setProjectionMatrix(uiCamera.combined);
        batch.setProjectionMatrix(camera.combined);
        gameMap.renderBelow(camera);

        // Check from the HUD if player pressed on the start button
        if (gameHud.isGameStarted()) {

            batch.begin();

            // Updates
            if (player.alive) {
                if (gameHud.tickTimer(deltaTime)) {
                    player.alive = false;
                }
                collisionHandler.update();
                eventsHandler.update(deltaTime);
                player.update(deltaTime);
                updateItems();
                updateGeese();
                player.draw(batch);

            } else {
                player.getHotbar().clearItems();
                // Press space to restart game
                if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
                    resetGame();
                }
            }

            batch.end();
            gameMap.renderAbove(camera);

            // Healthbar
            shape.begin(ShapeRenderer.ShapeType.Filled);
            displayHealthBar(shape);
            shape.end();

            // Final check if the player won or not

            if (checkWin()) {
                player.alive = false;
                gameHud.showFinalScore();
            }
        }

        stage.getViewport().apply();
        stage.act();
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {

        viewport.update(width, height);
        uiCamera.setToOrtho(false, width, height);
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        stage.dispose();
        gameHud.dispose();
        gameMap.dispose();
    }

    // Custom methods here

    public void displayHealthBar(ShapeRenderer shape) {
        shape.setColor(Color.RED);
        shape.rect(0, Gdx.graphics.getHeight()-20, player.getHealth(), 20);
    }

    public void updateGeese() {
        for (Goose goose : geese) {
            goose.update(player, player.getDroppedSeeds(), deltaTime);
            goose.draw(batch);
        }
    }

    public boolean checkWin() {
        for (Goose goose : geese) {
            if (goose.isChasing()) {
                return false;
            }
        } return true;
    }

    public void updateItems() {
        /**
         * Used to update items that can be picked up + bird seeds
         */
        for (AbstractItem item : pickableItems) {
            item.update(deltaTime, player);
            item.draw(batch);
        }

        for (BirdSeeds seeds : player.getDroppedSeeds()) {
            seeds.update(deltaTime, player);
            seeds.draw(batch);
        }
    }

    private void resetGame() {
        player.resetHealth();
        gameHud.reset();
        // have a position so this is one call (abstract entity)
        player.setX(spawn.x);
        player.setY(spawn.y);
        startGame();
    }
}