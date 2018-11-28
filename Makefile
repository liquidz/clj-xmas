.PHONY: native-image install uninstall repl run test ancient clean

native-image:
	mkdir -p target && clojure -A:native-image

install:
	\cp -pi target/xmas /usr/local/bin

uninstall:
	\rm -f /usr/local/bin/xmas

repl:
	iced repl -A:dev

run:
	clj -m xmas.core

test:
	clojure -R:dev -A:test

ancient:
	clojure -A:ancient

clean:
	\rm -rf target
