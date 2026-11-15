package io.github.duckduckgoose.mapping;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
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
    int belowLayers[];
    int aboveLayers[];

    Rectangle statue, gooseStampede;

    public GameMap(String path){
        map = new TmxMapLoader().load(path);
        renderer = new OrthogonalTiledMapRenderer(map, 1/tile_size);

        MapProperties properties = map.getProperties();
        width = properties.get("width", Integer.class);
        height = properties.get("height", Integer.class);

        computeRenderOrder();
        loadBorders();
        loadPlayerSpawn();
        loadGeeseSpawn();
        loadEventsLayer();
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

    private void loadEventsLayer() {
        MapObject object = map.getLayers().get("events").getObjects().get("gooseStampede");
        Rectangle temp = ((RectangleMapObject) object ).getRectangle();
        gooseStampede = new Rectangle(
                temp.x / tile_size,
                temp.y / tile_size,
                temp.width / tile_size,
                temp.height / tile_size
        );

        object = map.getLayers().get("events").getObjects().get("statue");
        temp = ((RectangleMapObject) object ).getRectangle();
        statue = new Rectangle(
                temp.x / tile_size,
                temp.y / tile_size,
                temp.width / tile_size,
                temp.height / tile_size
        );
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

    public Rectangle getStatue() {
        return statue;
    }

    public Rectangle getGooseStampede() {
        return gooseStampede;
    }

    public float getWidth()  {
        return width;
    }

    public float getHeight() {
        return height;
    }

    // Creating depth in the game by rendering layers separately

    private void computeRenderOrder() {
        MapLayers layers = map.getLayers();
        int buildingsIndex = layers.getIndex("building");
        int treesIndex = layers.getIndex("trees");

        ArrayList<Integer> tempAbove = new ArrayList<>();
        ArrayList<Integer> tempBelow = new ArrayList<>();

        if (buildingsIndex < 0) {
            return;

            // Amazing potential for exceptions here
        }

        for (int i = 0; i < layers.getCount(); i++) {
            if (layers.get(i) instanceof TiledMapTileLayer) {
                if (i == buildingsIndex || i == treesIndex) {
                    tempAbove.add(i);
                } else tempBelow.add(i);
            }
        }

        belowLayers = toIntArray(tempBelow);
        aboveLayers = toIntArray(tempAbove);
    }

    private int[] toIntArray(ArrayList<Integer> temp) {
        int[] arr = new int[temp.size()];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = temp.get(i);
        } return arr;
    }

    public void renderAbove(OrthographicCamera camera) {
        renderer.setView(camera);
        renderer.render(aboveLayers);
    }

    public void renderBelow(OrthographicCamera camera) {
        renderer.setView(camera);
        renderer.render(belowLayers);
    }


    public void dispose() {
        map.dispose();
        renderer.dispose();
    }
}