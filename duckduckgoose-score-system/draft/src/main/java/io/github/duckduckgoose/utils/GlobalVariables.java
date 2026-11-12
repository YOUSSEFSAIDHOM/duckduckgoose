package io.github.duckduckgoose.utils;

import com.badlogic.gdx.graphics.Color;

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
    public static final int gooseSpeed = 5;
    public static final int  playerSpeed = 9;
    public static final int playerSprintSpeed = 12;
    public static float playerSpeedMultiplier = 1f;
    public static final float defaultSpeedMultiplier = 1f;
    public static float speedEffectMultiplier = 2f;

    // Item stats
    public static final float energyDrinkDuration = 2f;

    // Misc...
    public static final float lerpFactor = 0.05f; // The higher the lerp factor the faster the camera pans
    public static final int inventorySize = 5;
    public static final int geeseQuantity = 50;
    public static final float GAME_DURATION = 5 * 60f;

    public static final List<String> mapList = List.of(
            "ver_3/maps/main_path.tmx",
            "ver_3/maps/langwith.tmx",
            "ver_3/maps/bus_stop.tmx"
    );
}
