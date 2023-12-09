{ mkSbtDerivation, gitignore-source, which, clang }:

let pname = "demo"; in

mkSbtDerivation {
  inherit pname;
  version = "0.1.0";
  depsSha256 = "sha256-WI2YN2iov2SzDuDSZqY9UUIZG741v1+6iLt8NLC6jIU=";

  buildInputs = [ which clang ];

  depsWarmupCommand = ''
    sbt appNative/compile
  '';

  src = gitignore-source.lib.gitignoreSource ./.;

  buildPhase = ''
    sbt nativeLink
  '';

  installPhase = ''
    mkdir -p $out/bin
    cp app/.native/target/scala-3.3.1/demo-out $out/bin/$pname
  '';
}
