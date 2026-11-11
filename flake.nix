{
  description = "Duck Duck Goose — libGDX (LWJGL3) desktop game";

  inputs.nixpkgs.url = "github:NixOS/nixpkgs/nixos-unstable";

  outputs = { self, nixpkgs }:
    let
      system = "x86_64-linux";
      pkgs = import nixpkgs { inherit system; };

      # The project targets Java 17 (see build.gradle). Gradle 9.3 (pinned by
      # the wrapper) runs fine on this JDK.
      jdk = pkgs.jdk17;

      # Native libraries that LWJGL3 / GLFW dlopen at runtime. The game ships
      # its own libglfw.so (gdx natives-desktop), but that in turn loads the
      # system GL and X11/Wayland libraries, which on NixOS are not on the
      # default loader path.
      nativeLibs = with pkgs; [
        libGL
        libglvnd
        libxkbcommon
        wayland
        libx11
        libxcursor
        libxrandr
        libxi
        libxxf86vm
        libxext
        libxrender
        libxfixes
      ];

      # /run/opengl-driver/lib holds the actual GPU driver (Mesa / NVIDIA) on
      # NixOS and MUST come first so GLX/EGL resolve the real driver rather
      # than a stub. Everything else comes from the pinned nixpkgs above, so
      # this stays correct across channel updates (no hardcoded store path).
      libraryPath = "/run/opengl-driver/lib:" + pkgs.lib.makeLibraryPath nativeLibs;
    in
    {
      devShells.${system}.default = pkgs.mkShell {
        packages = [ jdk pkgs.gradle ];

        JAVA_HOME = "${jdk}/lib/openjdk";
        LD_LIBRARY_PATH = libraryPath;

        # build.gradle's `run` task reads this to set the forked game JVM's
        # LD_LIBRARY_PATH explicitly, which survives the Gradle daemon.
        NIX_GAME_LD_LIBRARY_PATH = libraryPath;

        shellHook = ''
          echo "duckduckgoose dev shell — run the game with:  ./gradlew run"
        '';
      };

      # Convenience: `nix run` builds and launches the game with the correct
      # environment (uses --no-daemon so the native lib path always applies).
      apps.${system}.default = {
        type = "app";
        program = "${pkgs.writeShellScript "run-duckduckgoose" ''
          export JAVA_HOME=${jdk}/lib/openjdk
          export PATH=${jdk}/bin:$PATH
          export LD_LIBRARY_PATH=${libraryPath}
          export NIX_GAME_LD_LIBRARY_PATH=${libraryPath}
          exec ./gradlew --no-daemon run "$@"
        ''}";
      };
    };
}
