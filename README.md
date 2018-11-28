# clj-xmas

![xmas tree](xmas.png)

## Usage

```clj
clj -Sdeps '{:deps {liquidz/clj-xmas {:git/url "https://github.com/liquidz/clj-xmas" :sha "9652260fbce5046755ad0a67da34bf79ec6e1b5d"}}}' -m xmas.core
```

You can specify tree size
```clj
clj -Sdeps '...' -m xmas.core 20
```

## native image

 * graalvm is required.
 * `$GRAALVM_HOME` must be defined.
 * `make` to build native image.
