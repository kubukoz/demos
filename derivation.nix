{ mkSbtDerivation, gitignore-source, which, clang, sqlite, sn-bindgen, BINDGEN_PATH, SQLITE_PATH }:

let pname = "demo"; in

mkSbtDerivation {
  inherit pname;
  version = "0.1.0";
  depsSha256 = "sha256-OaLOr6MbxSkiVr2cBy/BdqZFICEeuSXocQ9WYKCGntw=";

  buildInputs = [ which clang ];
  nativeBuildInputs = [ sqlite.dev ];

  depsWarmupCommand = ''
    sbt appNative/compile
  '';

  overrideDepsAttrs = final: prev: {
    buildInputs = [ which clang ];
    inherit BINDGEN_PATH SQLITE_PATH;
  };

  inherit BINDGEN_PATH SQLITE_PATH;

  src = gitignore-source.lib.gitignoreSource ./.;

  buildPhase = ''
    sbt nativeLink
  '';

  installPhase = ''
    mkdir -p $out/bin
    cp app/.native/target/scala-3.3.1/demo-out $out/bin/$pname
  '';
}
