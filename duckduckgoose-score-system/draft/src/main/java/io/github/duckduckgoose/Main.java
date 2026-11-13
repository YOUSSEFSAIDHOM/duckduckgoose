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
import com.badlogic.gdx.scenes.scene2d.ui.*;
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
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.Actor;
import io.github.duckduckgoose.utils.GlobalVariables;

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

    float targetX; float targetY;

    private Stage stage;
    private Skin skin;
    private boolean gameStarted = false;

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
        skin = new Skin(Gdx.files.internal("ver_3/ui/glassy-ui.json"));

        stage = new Stage(new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        Gdx.input.setInputProcessor(stage);

        Table mainTable = new Table();
        mainTable.setFillParent(true);
        stage.addActor(mainTable);

        Label movement_label = new Label("W = Up, S = Down, A = Left, D = Right", skin);
        mainTable.add(movement_label).width(100).height(100).left().expand().padLeft(10);
        mainTable.row();

        Label items_label = new Label("1 = Use item 1, 2 = Use item 2", skin);
        mainTable.add(items_label).width(100).height(100).left().expand().padLeft(10);
        mainTable.row();

        Label restart_label = new Label("Spacebar = Restart", skin);
        mainTable.add(restart_label).width(100).height(100).left().expand().padLeft(10);
        mainTable.row();

        TextButton textButton = new TextButton("Start!", skin);
        mainTable.add(textButton).expand().bottom().width(300).height(100);

        textButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameStarted = true;
                mainTable.setVisible(false);
            }
        });

        startGame();
    }

    public void startGame() {
        // Parameter order is : x, y, size, health


        if (gameMap != null) {
            gameMap.dispose();
        } gameMap = new GameMap(GlobalVariables.mapPath);

        spawn = gameMap.getPlayerSpawn();
        player = new Player(spawn.x, spawn.y);
        player.pickUpItem(new EnergyDrink(0,0));
        player.pickUpItem(new BirdSeeds(0, 0));
        stage.addActor(player.getHotbar().getTable());

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
        collisionHandler = new CollisionHandler(gameMap.getWalls(), geese, player, pickableItems);
        eventsHandler = new EventsHandler(player.getDroppedSeeds(), gameMap.getGooseStampede(), gameMap.getStatue());
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

        if (gameStarted) {

            batch.begin();

            // Updates
            if (player.alive) {
                collisionHandler.update();
                eventsHandler.update(deltaTime);
                player.update(deltaTime);
                updateGeese();
                updateItems();
                player.draw(batch);
            } else {
                player.getHotbar().clearItems();
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
        skin.dispose();

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

    public Vector2 randomGooseSpawn() { return gameMap.getGeeseSpawn().get(random.nextInt(gameMap.getGeeseSpawn().size())); }
}