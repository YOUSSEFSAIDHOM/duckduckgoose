package io.github.duckduckgoose;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import org.lwjgl.glfw.GLFW;

public class Lwjgl3Launcher {
    public static void main(String[] args) {
        // Must be set before GLFW initializes, or it defaults to X11/XWayland,
        // which renders blurry on HiDPI Wayland compositors like Hyprland.
        GLFW.glfwInitHint(GLFW.GLFW_PLATFORM, GLFW.GLFW_PLATFORM_WAYLAND);
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("draft");
        config.setWindowedMode(800, 600);
        new Lwjgl3Application(new Main(), config);
    }
}
