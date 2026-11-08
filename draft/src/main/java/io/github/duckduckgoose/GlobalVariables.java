package io.github.duckduckgoose;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GlobalVariables {
    public static final float gooseDamage = 0.2f;
    public static final float playerHealth = 100f;

    public static final float playerRadius = 0.5f;
    public static final float gooseRadius = 0.5f;

    public static final int gooseSpeed = 5;
    public static final int playerSpeed = 9;
    public static final int playerSprintSpeed = 12;

    public static final float lerpFactor = 0.05f;
    // The higher the lerp factor the faster the camera pans

    public static final int inventorySize = 5;

    public static final int geeseQuantity = 100;

    public static final List<String> mapList = List.of(
            "ver_3/maps/main_path.tmx",
            "ver_3/maps/langwith.tmx",
            "ver_3/maps/bus_stop.tmx"
    );
}
