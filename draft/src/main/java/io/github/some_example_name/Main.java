package io.github.some_example_name;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.GL20;
import io.github.some_example_name.Player.Ball;
import io.github.some_example_name.Player.Bat;
import io.github.some_example_name.Player.Block;

import java.util.ArrayList;
import java.util.Random;

public class Main extends ApplicationAdapter {
    ShapeRenderer shape;
    Ball ball;
    Bat bat;

    ArrayList<Block> blocks = new ArrayList<>();

    @Override
    public void create () {
        shape = new ShapeRenderer();
        ball = new Ball(400, 300, 25, 5, 5, 1.08F);
        bat = new Bat(0, 50, 40, 15);

        // Generating a bunch of blocks
        int blockWidth = 60;
        int blockHeight = 20;
        for (int y = Gdx.graphics.getHeight()/2; y < Gdx.graphics.getHeight(); y += blockHeight + 10) {
            for (int x = 0; x < Gdx.graphics.getWidth(); x+= blockWidth+10) {
                blocks.add(new Block(x, y, blockWidth, blockHeight));
            }
        }
    }

    @Override
    public void render () {
        // Clear the screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        ball.update();
        bat.update(Gdx.input.getX(), Gdx.graphics.getHeight()-Gdx.input.getY());

        ball.checkCollision(bat);

        shape.begin(ShapeRenderer.ShapeType.Filled);

        ball.draw(shape);
        bat.draw(shape);
        for (Block block : blocks) {
            block.draw(shape);
            ball.checkCollision(block);
        }
        cleanUp();
        shape.end();
    }

    public void cleanUp() {
        for (int i = 0; i < blocks.size(); i++) {
            Block b = blocks.get(i);
            if (b.destroyed) {
                blocks.remove(b);
                i--;
            }
        }
    }
}