package io.github.duckduckgoose;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import org.lwjgl.glfw.GLFW;

public class Lwjgl3Launcher {
    public static void main(String[] args) {
        // Must be set before GLFW initializes, or it defaults to X11/XWayland,
        // which renders blurry on HiDPI Wayland compositors like Hyprland.
        // Only forced when a Wayland session exists: GLFW fails to start if the
        // requested platform is unavailable (Windows, macOS, X11-only Linux).
        if (isWaylandSession()) {
            GLFW.glfwInitHint(GLFW.GLFW_PLATFORM, GLFW.GLFW_PLATFORM_WAYLAND);
        }
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("draft");
        config.setWindowedMode(800, 600);
        new Lwjgl3Application(new Main(), config);
    }

    private static boolean isWaylandSession() {
        return System.getProperty("os.name").toLowerCase().contains("linux")
                && System.getenv("WAYLAND_DISPLAY") != null;
    }
}
