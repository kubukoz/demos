{
  inputs.nixpkgs.url = "github:nixos/nixpkgs";

  outputs = { nixpkgs, self }: {
    packages.aarch64-darwin.default = let pkgs = import nixpkgs { system = "aarch64-darwin"; }; in
      pkgs.stdenv.mkDerivation {
        name = "demo";
        buildInputs = [ pkgs.scala-cli ];

        src = ./.;

        COURSIER_CACHE = ".nix/cache";
        COURSIER_ARCHIVE_CACHE = ".nix/arc";
        COURSIER_JVM_CACHE = ".nix/jvm";
        buildPhase = ''
          ls -alG
          mkdir -p .nix/cache
          scala-cli package . --server=false --home="$(pwd)/.nix" -v -v -v
          mkdir -p $out/bin
        '';
        installPhase = ''
          cp app $out/bin/app
        '';
      };
  };
}
