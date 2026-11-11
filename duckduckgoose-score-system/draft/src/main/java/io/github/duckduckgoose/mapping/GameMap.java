package io.github.duckduckgoose.mapping;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class GameMap {

    private final float tile_size = 32f;
    private final TiledMap map;
    OrthogonalTiledMapRenderer renderer;
    Vector2 playerSpawn = new Vector2();
    ArrayList<Vector2> geeseSpawn = new ArrayList<>();
    private final float width;
    private final float height;


    ArrayList <Rectangle> walls = new ArrayList<>();

    public GameMap(String path){
        map = new TmxMapLoader().load(path);
        renderer = new OrthogonalTiledMapRenderer(map, 1/tile_size);

        MapProperties properties = map.getProperties();
        width = properties.get("width", Integer.class);
        height = properties.get("height", Integer.class);

        loadBorders();
        loadPlayerSpawn();
        loadGeeseSpawn();
    }

    private void loadPlayerSpawn() {
        MapObject object = map.getLayers().get("playerSpawn").getObjects().get("player");
        Rectangle spawn = ((RectangleMapObject) object ).getRectangle();
        playerSpawn.set(spawn.x/tile_size, spawn.y/tile_size);

    }

    private void loadGeeseSpawn() {
        MapLayer geeseLayer = map.getLayers().get("geeseSpawn");

        for (RectangleMapObject object : geeseLayer.getObjects().getByType(RectangleMapObject.class) ) {
            Rectangle gooseSpawn = object.getRectangle();
            geeseSpawn.add(new Vector2(gooseSpawn.x / tile_size, gooseSpawn.y / tile_size));
        }
    }

    private void loadBorders(){
        MapLayer layer = map.getLayers().get("collisions");

        for(RectangleMapObject object : layer.getObjects().getByType(RectangleMapObject.class) ){
            Rectangle border = object.getRectangle();

            walls.add(new Rectangle(
                    border.x/ tile_size,
                    border.y/ tile_size,
                    border.width / tile_size,
                    border.height / tile_size)
            );
        }
    }

    public ArrayList<Rectangle> getWalls(){
        return this.walls;
    }

    public Vector2 getPlayerSpawn() {
        return playerSpawn;
    }

    public ArrayList<Vector2> getGeeseSpawn() {
        return geeseSpawn;
    }

    public float getWidth()  {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public void render(OrthographicCamera camera){
        renderer.setView(camera);
        renderer.render();
    }

    public void dispose() {
        map.dispose();
        renderer.dispose();
    }
}