package game.draft;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;


public class Player {

    float speed = 4f;
    private final Rectangle bounds;
    Texture playerSheet;
    TextureRegion idlePlayer;

    public Player(float spawnX, float spawnY){

        bounds = new Rectangle(spawnX, spawnY,0.5f, 0.5f);
        playerSheet = new Texture("ver_3/assets/Farmer_1_32x32.png");
        idlePlayer = new TextureRegion(playerSheet,96,0,32,64);

    }

    public void draw(SpriteBatch batch) {
        float drawX = bounds.x + bounds.width / 2 - 0.5f;
        float drawY = bounds.y;


        batch.draw(idlePlayer, drawX, drawY, 1, 2);
    }

`    public void move(float dirX, float dirY,  float delta, ArrayList<Rectangle> walls) {
`        float dx = dirX * speed * delta;

        float dy = dirY * speed * delta;

        bounds.x += dx;
        for (Rectangle wall : walls) {
            if (bounds.overlaps(wall)) {
                bounds.x -= dx;
                break;
            }
        }

        bounds.y += dy;
        for (Rectangle wall : walls) {
            if (bounds.overlaps(wall)) {
                bounds.y -= dy;
                break;

            }
        }
    }
    public Rectangle getBounds(){
        return bounds;
    }

    public void dispose() {
        playerSheet.dispose();
    }
}
