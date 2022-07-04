{
  inputs.nixpkgs.url = "github:nixos/nixpkgs";
  inputs.flake-utils.url = "github:numtide/flake-utils";

  outputs = { nixpkgs, flake-utils, self }: flake-utils.lib.eachDefaultSystem (system: {
    packages.default =
      let
        pkgs = import nixpkgs { inherit system; };

        deps = pkgs.stdenv.mkDerivation {
          name = "demo-deps";
          buildInputs = [ pkgs.scala-cli ];

          src = ./.;

          outputHashMode = "recursive";
          outputHashAlgo = "sha256";

          # this build is able to access the internet because it's a fixed output derivation - the hash of the output is known.
          # or at least it should - it seems like it's not, because fetching dependencies doesn't seem to be reproducible.
          outputHash = "sha256-PCIkMjmZFO0ExPUnBGLt1szcacqi/9yU6jqCWZAC0P4=";

          COURSIER_CACHE = ".nix/cache";
          COURSIER_ARCHIVE_CACHE = ".nix/arc";
          COURSIER_JVM_CACHE = ".nix/jvm";
          buildPhase = ''
            ls -alG
            # this should only fetch all the dependencies, but doing a `package` just in case
            scala-cli package . --server=false --home="$(pwd)/.nix" -v -v -v -f
          '';
          installPhase = ''
            mkdir -p $out
            cp -R .nix $out
          '';
        };
      in
      # this shouldn't need to use the internet
        # since everything is cached in `deps`
      pkgs.stdenv.mkDerivation {
        name = "demo";
        buildInputs = [ pkgs.scala-cli ];
        nativeBuildInputs = [ pkgs.makeWrapper ];

        src = ./.;

        COURSIER_CACHE = ".nix/cache";
        COURSIER_ARCHIVE_CACHE = ".nix/arc";
        COURSIER_JVM_CACHE = ".nix/jvm";
        buildPhase = ''
          cp -R ${deps}/.nix .nix
          ls -alG
          # workaround: reusing the `.nix` directory from `deps` seems to cause permision issues. So I'm using .nix2 here
          scala-cli package . --server=false --home="$(pwd)/.nix2" -v -v -v -f
        '';
        installPhase = ''
          mkdir -p $out/bin
          cp hello $out/bin

          wrapProgram $out/bin/hello \
            --set JAVA_HOME ${pkgs.jre.home}
        '';
      };
  });
}
