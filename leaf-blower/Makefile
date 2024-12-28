all: leaf-blower leaf-blower-scala demo wasm

leaf-blower:
	mkdir -p lib
	clang -shared -o lib/libleaf-blower.dylib -Iinclude src/leafblower.c

leaf-blower-scala:
	clang -shared -o lib/libleaf-blower-scala.dylib -Iinclude src/leafblower-scala.c

demo:
	mkdir -p bin
	rm bin/demo || exit 0
	clang -o bin/demo -Iinclude -Llib -lleaf-blower -lleaf-blower-scala src/demo.c && ./bin/demo

wasm:
	emcc src/leafblower.c -Iinclude -o lib/libleaf-blower.js \
		-s EXPORT_NAME=LeafBlower \
		-s MODULARIZE=1 \
		-s SHARED_MEMORY=1 \
		-s INITIAL_MEMORY=8mb \
		-s MAXIMUM_MEMORY=8mb \
		-s ALLOW_MEMORY_GROWTH=0 \
		-s IMPORTED_MEMORY=1

	emcc src/leafblower-scala.c -Iinclude -o lib/libleaf-blower-scala.js \
		-s EXPORT_NAME=LeafBlowerScala \
		-s MODULARIZE=1 \
		-s SHARED_MEMORY=1 \
		-s INITIAL_MEMORY=8mb \
		-s MAXIMUM_MEMORY=8mb \
		-s ALLOW_MEMORY_GROWTH=0 \
		-s IMPORTED_MEMORY=1


clean:
	rm -f lib/*.dylib
	rm -f lib/*.wasm
	rm -f bin/demo
