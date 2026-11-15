package io.github.duckduckgoose.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import io.github.duckduckgoose.utils.GlobalVariables;

public class GameHUD {
    /**
     * Takes care of all things HUD related in the game
     *
     */

    private final Stage stage;
    private final Skin skin;

    // Tables
    private Table mainTable;
    private Table hotbarTable;
    private Table eventCountdownTable;
    private Table winTable;

    //Labels
    private Label scoreLabel;
    private Label timerLabel;
    private Label eventCountdownLabel;
    private Label finalScoreLabel;
    private Label winLabel;

    private int score = 0;
    private float scoreCooldown = 0f;
    private boolean gameStarted = false;
    private float timeRemaining = GlobalVariables.gameDuration;

    public Stage getStage() { return stage; }

    public boolean isGameStarted() { return gameStarted; }

    public GameHUD(Stage stage, Table hotbarTable) {
        this.stage = stage;
        this.hotbarTable = hotbarTable;
        this.skin = GlobalVariables.skin;

        // Adding the hotbar, set the visibility intially to false so it doesn't overlap the start button
        this.hotbarTable.setVisible(gameStarted);
        this.stage.addActor(hotbarTable);

        buildStartMenu();
        buildScore();
        buildTimer();
        buildEventCountdown();
        buildWinScreen();
    }

    private void buildStartMenu() {
        mainTable = new Table();
        mainTable.setFillParent(true);
        stage.addActor(mainTable);

        Label movement_label = new Label("WASD movement controls, Space to restart if you lose", skin);
        mainTable.add(movement_label).width(100).height(100).left().expand().padLeft(10);
        mainTable.row();

        Label items_label = new Label("Equip items by pressing 1-5 on the number keys, E to use items", skin);
        mainTable.add(items_label).width(100).height(100).left().expand().padLeft(10);
        mainTable.row();

        Label restart_label = new Label("Survive 5 minutes or feed all the geese to win, you may trigger hidden events to try and get extra points!!", skin);
        mainTable.add(restart_label).width(100).height(100).left().expand().padLeft(10);
        mainTable.row();

        TextButton textButton = new TextButton("Start!", skin);
        mainTable.add(textButton).expand().bottom().width(300).height(100);

        textButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameStarted = true;
                mainTable.setVisible(false);
                hotbarTable.setVisible(gameStarted);
            }
        });
    }

    private void buildScore() {
        Table scoreTable = new Table();
        scoreTable.setFillParent(true);
        stage.addActor(scoreTable);

        scoreLabel = new Label("Score: 0", skin);
        scoreLabel.setFontScale(2);
        scoreTable.add(scoreLabel).top().right().pad(20).expand();
    }

    private void buildTimer() {
        Table timerTable = new Table();
        timerTable.setFillParent(true);
        // timerTable.top().padTop(12);
        stage.addActor(timerTable);

        timerLabel = new Label("5:00", skin);
        timerLabel.setFontScale(2);
        timerTable.add(timerLabel).top().pad(20).expand();
    }

    private void buildWinScreen() {
        winTable = new Table();
        winTable.setFillParent(true);
        winTable.setVisible(false);
        stage.addActor(winTable);

        winLabel = new Label("", skin);
        winLabel.setFontScale(2);
        winTable.add(winLabel).expand().row();

        finalScoreLabel = new Label("Final score:", skin);
        finalScoreLabel.setFontScale(2);
        winTable.add(finalScoreLabel).expand();
    }

    // Event countdown

    public void buildEventCountdown() {
        eventCountdownTable = new Table();
        eventCountdownTable.setFillParent(true);
        eventCountdownTable.setVisible(false);
        stage.addActor(eventCountdownTable);

        eventCountdownLabel = new Label("", skin);
        eventCountdownLabel.setFontScale(3);
        eventCountdownTable.add(eventCountdownLabel).expand();
    }

    public void showEventCountdown(String eventName, int countdown) {
        eventCountdownLabel.setText(eventName+" activating in " + countdown);
        eventCountdownTable.setVisible(true);
    }

    public void hideEventCountdown() {
        eventCountdownTable.setVisible(false);
    }

    // Score functionality

    public void addScore(int points) {
        if (scoreCooldown <= 0) {
            score += points;
            scoreLabel.setText("Score: "+ score);
            scoreCooldown = GlobalVariables.scoreCooldown;
        }
    }

    public void tickCooldown(float delta) {
        if (scoreCooldown > 0) {
            scoreCooldown -= delta;
        }
    }

    public void showFinalScore(String message) {
        winLabel.setText(message);
        // Your final score is your in game points + time remaining
        int finalScore = (int) (score + timeRemaining);
        // Default value of high score is 0 if its not found
        int highScore = GlobalVariables.preferences.getInteger("highscore", 0);

        if (finalScore > highScore) {
            highScore = finalScore;
            GlobalVariables.preferences.putInteger("highscore", highScore);
        }
        finalScoreLabel.setText(score + " + " + (int) timeRemaining + " seconds " + " = " + finalScore + ", \nHigh score : " + highScore);
        winTable.setVisible(true);
    }

    public boolean tickTimer(float delta) {
        /**
         * Only ticks time if it is above 0,
         * and returns true if time remaining is finished
         */
        if (timeRemaining <= 0) {
            return true;
        }
        timeRemaining -= delta;
        updateTimerLabel();
        return timeRemaining == 0;
    }

    private void updateTimerLabel() {
        int minutes = (int) timeRemaining / 60;
        int seconds = (int) timeRemaining % 60;
        timerLabel.setText(String.format("%d:%02d", minutes, seconds));
    }

    public void dispose() {
        skin.dispose();
    }

    public void reset() {
        score = 0;
        scoreLabel.setText("Score: 0");
        timeRemaining = GlobalVariables.gameDuration;
        winTable.setVisible(false);
        updateTimerLabel();
    }
}
