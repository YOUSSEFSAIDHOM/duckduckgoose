package io.github.duckduckgoose.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import io.github.duckduckgoose.utils.GlobalVariables;

/**
 * Takes care of all things HUD related in the game
 */
public class GameHUD {

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

    /**
     * Constructor for the main HUD for the game.
     *
     * @param stage where all the UI elements are placed upon
     * @param hotbarTable the table used to lay out the hotbar UI
     */
    public GameHUD(Stage stage, Table hotbarTable) {
        this.stage = stage;
        this.hotbarTable = hotbarTable;
        this.skin = GlobalVariables.skin;

        // Adding the hotbar, set the visibility initially to false so it doesn't overlap the start button
        this.hotbarTable.setVisible(gameStarted);
        this.stage.addActor(hotbarTable);

        buildStartMenu();
        buildScore();
        buildTimer();
        buildEventCountdown();
        buildWinScreen();
    }

    /**
     * Creates the start menu UI (explains controls and has the 'start' button).
     */
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

    /**
     * Creates the UI for the score display in-game.
     */
    private void buildScore() {
        Table scoreTable = new Table();
        scoreTable.setFillParent(true);
        stage.addActor(scoreTable);

        scoreLabel = new Label("Score: 0", skin);
        scoreLabel.setFontScale(2);
        scoreTable.add(scoreLabel).top().right().pad(20).expand();
    }

    /**
     * Creates the UI for the timer display in-game.
     */
    private void buildTimer() {
        Table timerTable = new Table();
        timerTable.setFillParent(true);
        // timerTable.top().padTop(12);
        stage.addActor(timerTable);

        timerLabel = new Label("5:00", skin);
        timerLabel.setFontScale(2);
        timerTable.add(timerLabel).top().pad(20).expand();
    }

    /**
     * Creates the UI for the win screen for when the timer runs out.
     */
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

    /**
     * Creates the UI for a countdown before a hidden event begins.
     */
    public void buildEventCountdown() {
        eventCountdownTable = new Table();
        eventCountdownTable.setFillParent(true);
        eventCountdownTable.setVisible(false);
        stage.addActor(eventCountdownTable);

        eventCountdownLabel = new Label("", skin);
        eventCountdownLabel.setFontScale(3);
        eventCountdownTable.add(eventCountdownLabel).expand();
    }

    /**
     * Displays the event countdown once hidden trigger is active.
     *
     * @param eventName the event that has been triggered
     * @param countdown the time to countdown from
     */
    public void showEventCountdown(String eventName, int countdown) {
        eventCountdownLabel.setText(eventName+" activating in " + countdown);
        eventCountdownTable.setVisible(true);
    }

    /**
     * Hides the event timer once it's at 0.
     */
    public void hideEventCountdown() {
        eventCountdownTable.setVisible(false);
    }

    // Score functionality

    /**
     * Adds points to the player's total score.
     *
     * @param points the number of points to add to the score
     */
    public void addScore(int points) {
        if (scoreCooldown <= 0) {
            score += points;
            scoreLabel.setText("Score: "+ score);
            scoreCooldown = GlobalVariables.scoreCooldown;
        }
    }

    /**
     * Cooldown for score allocation to prevent rapid increases.
     *
     * @param delta difference in time between frames
     */
    public void tickCooldown(float delta) {
        if (scoreCooldown > 0) {
            scoreCooldown -= delta;
        }
    }

    /**
     * Displays the final score that the player has achieved.
     *
     * @param message a formatted string message to be display the score
     */
    public void showFinalScore(String message) {
        winLabel.setText(message);
        // Your final score is your in game points + time remaining
        int finalScore = (int) (score + timeRemaining);
        // Default value of high score is 0 if it's not found
        int highScore = GlobalVariables.preferences.getInteger("highscore", 0);

        if (finalScore > highScore) {
            highScore = finalScore;
            GlobalVariables.preferences.putInteger("highscore", highScore);
        }
        finalScoreLabel.setText(score + " + " + (int) timeRemaining + " seconds " + " = " + finalScore + ", \nHigh score : " + highScore);
        winTable.setVisible(true);
    }

    /**
     * Only ticks time if it is above 0, and returns true if time remaining is finished.
     *
     * @param delta time difference between frames
     * @return determines whether time remaining is 0 or not
     */
    public boolean tickTimer(float delta) {
        if (timeRemaining <= 0) {
            return true;
        }
        timeRemaining -= delta;
        updateTimerLabel();
        return timeRemaining == 0;
    }

    /**
     * Updates the timer to keep counting down.
     */
    private void updateTimerLabel() {
        int minutes = (int) timeRemaining / 60;
        int seconds = (int) timeRemaining % 60;
        timerLabel.setText(String.format("%d:%02d", minutes, seconds));
    }

    public void dispose() {
        skin.dispose();
    }

    /**
     * Resets the timer and score display once the game restarts.
     */
    public void reset() {
        score = 0;
        scoreLabel.setText("Score: 0");
        timeRemaining = GlobalVariables.gameDuration;
        winTable.setVisible(false);
        updateTimerLabel();
    }
}
