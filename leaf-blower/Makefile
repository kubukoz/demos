leaf-blower:
	clang -shared -o lib/libleaf-blower.dylib -Iinclude src/leafblower.c

leaf-blower-scala:
	clang -shared -o lib/libleaf-blower-scala.dylib -Iinclude src/leafblower-scala.c

demo:
	mkdir -p bin && (rm bin/demo || exit 0) && clang -o bin/demo -Iinclude -Llib -lleaf-blower -lleaf-blower-scala src/demo.c && ./bin/demo

all: leaf-blower leaf-blower-scala demo

clean:
	rm -f lib/libleaf-blower.dylib
	rm -f lib/libleaf-blower-scala.dylib
	rm -f bin/demo
