package game.draft;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;



public class TestGame extends ApplicationAdapter {


    GameMap gameMap;
    OrthographicCamera camera;

    private Player player;
    private PlayerInput playerInput;
    private ShapeRenderer shapes;
    private FitViewport viewport;
    private SpriteBatch batch;

    @Override
    public void create() {

        gameMap = new GameMap("ver_3/maps/main_path.tmx");

        camera = new OrthographicCamera();
        viewport = new FitViewport(20, 12, camera);


        Vector2 spawn = gameMap.getPlayerSpawn();
        player = new Player(spawn.x, spawn.y);
        playerInput = new PlayerInput();
        shapes = new ShapeRenderer();
        batch = new SpriteBatch();

    }

    @Override
    public void render() {

        playerInput.update();
        player.move(playerInput.getDirX(), playerInput.getDirY(),Gdx.graphics.getDeltaTime(), gameMap.getWalls());

        Rectangle playerBounds = player.getBounds();
        camera.position.set(playerBounds.x + playerBounds.width / 2, playerBounds.y + playerBounds.height / 2, 0);

        float halfWidth = camera.viewportWidth / 2;
        float halfHeight = camera.viewportHeight / 2;

        camera.position.x = MathUtils.clamp(camera.position.x, halfWidth, gameMap.getWidth() - halfWidth);
        camera.position.y = MathUtils.clamp(camera.position.y, halfHeight, gameMap.getHeight() - halfHeight);
        camera.update();

        ScreenUtils.clear(0, 0, 0, 1);
        gameMap.render(camera);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        player.draw(batch);
        batch.end();

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);

    }

    @Override
    public void dispose() {

        gameMap.dispose();
        shapes.dispose();
        batch.dispose();
        player.dispose();

    }
}
