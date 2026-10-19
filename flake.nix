{
  description = "libGDX development shell for NixOS";

  inputs.nixpkgs.url = "github:NixOS/nixpkgs/nixos-unstable";

  outputs = { self, nixpkgs }:
    let
      system = "x86_64-linux";
      pkgs = nixpkgs.legacyPackages.${system};

      # Native libraries that LWJGL3 (GLFW, OpenGL, OpenAL) dlopens at runtime.
      runtimeLibs = with pkgs; [
        libGL
        xorg.libX11
        xorg.libXcursor
        xorg.libXrandr
        xorg.libXxf86vm
        xorg.libXi
        xorg.libXext
        xorg.libXrender
        libxkbcommon
        wayland
        alsa-lib
        libpulseaudio
        openal
        udev
        stdenv.cc.cc.lib
      ];
    in
    {
      devShells.${system}.default = pkgs.mkShell {
        packages = with pkgs; [
          jdk17
          gradle
        ];

        JAVA_HOME = "${pkgs.jdk17}";

        # /run/opengl-driver/lib holds your GPU driver's libGL on NixOS.
        LD_LIBRARY_PATH =
          "/run/opengl-driver/lib:" + pkgs.lib.makeLibraryPath runtimeLibs;
      };
    };
}
