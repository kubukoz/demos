{
  inputs.nixpkgs.url = "github:nixos/nixpkgs";
  inputs.flake-utils.url = "github:numtide/flake-utils";

  outputs = { self, nixpkgs, flake-utils, ... }@inputs:
    flake-utils.lib.eachDefaultSystem (
      system:
      let
        pkgs = import nixpkgs { inherit system; };
      in
      {
        devShells.default = pkgs.clangStdenv.mkDerivation {
          name = "clang-nix-shell";
          shellHook = ''
            # Works on Mac
            export PLAYDATE_SDK_PATH=~/Developer/PlaydateSDK
            # Works for me.
            export PLAYDATE_DEVICE_PATH=/dev/cu.usbmodemPDU1_Y0669441
          '';
        };
        devShells.arm = pkgs.pkgsCross.arm-embedded.mkShell {
          nativeBuildInputs = [
            pkgs.clang
          ];
        };
      }
    );
}

