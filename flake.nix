{
  inputs.nixpkgs.url = "github:nixos/nixpkgs";
  inputs.flake-utils.url = "github:numtide/flake-utils";
  inputs.sbt-derivation.url = "github:zaninime/sbt-derivation";
  inputs.gitignore-source.url = "github:hercules-ci/gitignore.nix";
  inputs.gitignore-source.inputs.nixpkgs.follows = "nixpkgs";
  inputs.sn-bindgen.url = "github:indoorvivants/sn-bindgen";

  outputs = { self, nixpkgs, flake-utils, sbt-derivation, sn-bindgen, ... }@inputs:
    flake-utils.lib.eachDefaultSystem (
      system:
      let
        pkgs = import nixpkgs {
          inherit system;
          overlays = [
            sbt-derivation.overlays.default
            sn-bindgen.overlays.default
          ];
        };
        BINDGEN_PATH = "${pkgs.sn-bindgen}/bin/bindgen";
        PDAPI_PATH = "/Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api.h";
      in
      {
        devShells.default = pkgs.mkShell {
          buildInputs = with pkgs; [ which clang sbt pkgs.sn-bindgen ];
          inherit BINDGEN_PATH PDAPI_PATH;
        };
        packages.default = pkgs.callPackage ./derivation.nix { inherit (inputs) gitignore-source; inherit BINDGEN_PATH PDAPI_PATH; };
      }
    );
}
