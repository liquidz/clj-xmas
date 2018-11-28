# clj-xmas

![xmas tree](xmas.png)

## Usage

```clj
clj -Sdeps '{:deps {liquidz/clj-xmas {:git/url "https://github.com/liquidz/clj-xmas" :sha "12f9248d793e6ac55d765c9fc9cae18a68ee7407"}}}' -m xmas.core
```

You can specify tree size
```clj
clj -Sdeps '...' -m xmas.core 20
```

## native image

 * graalvm is required.
 * `$GRAALVM_HOME` must be defined.
 * `make` to build native image.
