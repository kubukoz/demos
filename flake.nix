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
        devShells.default = pkgs.mkShell { };
        devShells.arm = pkgs.pkgsCross.arm-embedded.mkShell {
          nativeBuildInputs = [
            pkgs.clang
          ];
        };
      }
    );
}
