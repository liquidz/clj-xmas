# GraalVM {{{
PLATFORM := $(shell uname -s | tr '[:upper:]' '[:lower:]')
GRAAL_ROOT ?= /tmp/.graalvm
GRAAL_VERSION ?= 21.3.0
GRAAL_HOME ?= $(GRAAL_ROOT)/graalvm-ce-java11-$(GRAAL_VERSION)
GRAAL_ARCHIVE := graalvm-ce-java11-$(PLATFORM)-amd64-$(GRAAL_VERSION).tar.gz

ifeq ($(PLATFORM),darwin)
	GRAAL_HOME := $(GRAAL_HOME)/Contents/Home
	GRAAL_EXTRA_OPTION :=
else
	GRAAL_EXTRA_OPTION := "--static"
endif

$(GRAAL_ROOT)/fetch/$(GRAAL_ARCHIVE):
	@mkdir -p $(GRAAL_ROOT)/fetch
	curl --location --output $@ https://github.com/graalvm/graalvm-ce-builds/releases/download/vm-$(GRAAL_VERSION)/$(GRAAL_ARCHIVE)

$(GRAAL_HOME): $(GRAAL_ROOT)/fetch/$(GRAAL_ARCHIVE)
	tar -xz -C $(GRAAL_ROOT) -f $<

$(GRAAL_HOME)/bin/native-image: $(GRAAL_HOME)
	$(GRAAL_HOME)/bin/gu install native-image

.PHONY: graalvm
graalvm: $(GRAAL_HOME)/bin/native-image
# }}}

# Clojure CLI Tool {{{
PWD := $(shell pwd)
TOOL_ARTIFACT := com.github.liquidz/clj-xmas
TOOL_NAME := merry

.PHONY: tool-install
tool-install:
	clojure -Ttools install ${TOOL_ARTIFACT} '{:local/root "${PWD}"}' :as ${TOOL_NAME}

.PHONY: tool-remove
tool-remove:
	clojure -Ttools remove :tool ${TOOL_NAME}
# }}}

.PHONY: lint
lint:
	cljstyle check
	clj-kondo --lint src:test

.PHONY: uberjar
uberjar: clean
	clojure -T:build uberjar

.PHONY: native-image
native-image: graalvm uberjar
	$(GRAAL_HOME)/bin/native-image \
		-jar target/clj-xmas.jar \
		-H:Name=clj-xmas \
		-H:+ReportExceptionStackTraces \
		-J-Dclojure.spec.skip-macros=true \
		-J-Dclojure.compiler.direct-linking=true \
		--initialize-at-build-time  \
		--report-unsupported-elements-at-runtime \
		-H:Log=registerResource: \
		--verbose \
		--no-fallback \
		--no-server \
		$(GRAAL_EXTRA_OPTION) \
		"-J-Xmx3g"

.PHONY: install
install:
	\cp -pi target/clj-xmas /usr/local/bin

.PHONY: uninstall
uninstall:
	\rm -f /usr/local/bin/clj-xmas

.PHONY: repl
repl:
	iced repl -A:dev

.PHONY: run
run:
	clojure -M -m xmas.core

.PHONY: test
test:
	clojure -R:dev -A:test

.PHONY: outdated
outdated:
	clojure -M:outdated --upgrade

.PHONY: clean
clean:
	\rm -rf .cpcache target
