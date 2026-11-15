package io.github.duckduckgoose.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.util.List;

public class GlobalVariables {

    // Damage + Health stuff
    public static final float gooseDamage = 0.2f;
    public static final float playerHealth = 200f;

    // Size stuff
    public static final float playerRadius = 0.5f;
    public static final float gooseRadius = 0.4f;
    public static final float itemRadius = 0.5f;

    // Speed stuff
    public static final int gooseSpeed = 8;
    public static final int  playerSpeed = 9;
    public static final int playerSprintSpeed = 12;
    public static float playerSpeedMultiplier = 1f;
    public static final float defaultSpeedMultiplier = 1f;
    public static float speedEffectMultiplier = 1.4f;

    // Item stats
    public static final float energyDrinkDuration = 3f;

    // Misc...
    public static final float lerpFactor = 0.05f; // The higher the lerp factor the faster the camera pans
    public static final int inventorySize = 5;
    public static final int geeseQuantity = 30;
    public static final int eventCountDown = 3;

    public static final String mapPath = "ver_3/maps/campus_east.tmx";
    public static final Skin skin = new Skin(Gdx.files.internal("ver_3/ui/glassy-ui.json"));
    // LibGdx has its own preferences module that allows you to save temp data like high scores for example
    public static final Preferences preferences = Gdx.app.getPreferences("duckduckgoose");

    public static final float gameDuration = 300f; // --> 5 minutes
    public static final float scoreCooldown = 0.5f;

}
