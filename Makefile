.PHONY: native-image docker-build install uninstall repl run test ancient clean

target/xmas:
	mkdir -p target && clojure -A:native-image

native-image: target/xmas

docker-build: target/xmas
	docker build -t uochan/xmas .

install:
	\cp -pi target/xmas /usr/local/bin

uninstall:
	\rm -f /usr/local/bin/xmas

repl:
	iced repl -A:dev

run:
	clojure -m xmas.core

test:
	clojure -R:dev -A:test

ancient:
	clojure -A:ancient

clean:
	\rm -rf target
