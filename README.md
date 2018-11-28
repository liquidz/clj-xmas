# clj-xmas

![xmas tree](xmas.png)

## Usage

```clj
clj -Sdeps '{:deps {liquidz/clj-xmas {:git/url "https://github.com/liquidz/clj-xmas" :sha "FIXME"}}}' -m xmas.core
```

You can specify tree size
```clj
clj -Sdeps '...' -m xmas.core 20
```

## native image

 * graalvm is required.
 * `$GRAALVM_HOME` must be defined.
 * `make` to build native image.
