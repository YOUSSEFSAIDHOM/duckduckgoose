package game.draft;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class PlayerInput {
    private float dirX = 0;
    private float dirY = 0;

    public void update() {
        dirX = 0;
        dirY = 0;

        // isKeyPressed = "is this key held down RIGHT NOW?"
        if (Gdx.input.isKeyPressed(Input.Keys.A)) dirX -= 1;   // left
        if (Gdx.input.isKeyPressed(Input.Keys.D)) dirX += 1;   // right
        if (Gdx.input.isKeyPressed(Input.Keys.S)) dirY -= 1;   // down
        if (Gdx.input.isKeyPressed(Input.Keys.W)) dirY += 1;   // up
    }

    public float getDirX() {
        return dirX;
    }
    public float getDirY() {
        return dirY;
    }

}
