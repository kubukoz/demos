{
  inputs.nixpkgs.url = "github:NixOS/nixpkgs";
  outputs = { nixpkgs, ... }:
    let
      system = "aarch64-darwin";
      pkgs = import nixpkgs { inherit system; };
    in {
      devShells.${system}.default = pkgs.mkShell {
        packages = [ pkgs.emscripten ];
        shellHook = ''
          cp -r ${pkgs.emscripten}/share/emscripten/cache ~/.emscripten_cache
          chmod -R u+rwX ~/.emscripten_cache
          export EM_CACHE=~/.emscripten_cache
        '';
      };
    };
}
