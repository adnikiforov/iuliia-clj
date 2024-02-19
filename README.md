# iuliia-clj

Small library to properly transliterate cyrillic to latin. 

Use https://github.com/nalgeon/iuliia for transliteration schemas.

# WIP WIP WIP
Not ready to use yet

# TODO

- debug and fix translation logic (some schemas does not pass sample tests yet)
- add docs
- add full tests
- release

## Usage

Add to project:

`deps.edn`

```clojure
{:deps
 {io.github.adnikiforov/iuliia-clj {:git/tag "v0.0.1"}}}
```

`Leiningen`

Doesn't support yet, but you can use https://github.com/tobyhede/lein-git-deps to pull deps from repo

Require and use

```clojure
(ns com.ns
  (:require [iuliia-clj.core :as iuliia]))

(def schema (iuliia/load_schema "ala_lc"))
(println
  (iuliia/translate "Юлия, съешь ещё этих мягких французских булок из Йошкар-Олы, да выпей алтайского чаю" schema))

#> I͡ulii͡a, sʺeshʹ eshchё ėtikh mi͡agkikh frant͡suzskikh bulok iz Ĭoshkar-Oly, da vypeĭ altaĭskogo chai͡u
```

Or take a look at all available schemas

```clojure
(all_schemas)

#> (mvd_310 bs_2979 gost_779_alt icao_doc_9303 mvd_310_fr mvd_782 iso_9_1968_alt mosmetro gost_7034 gost_16876_alt gost_52290 ungegn_1987 telegram gost_16876 gost_779 wikipedia bgn_pcgn iso_9_1968 yandex_money ala_lc yandex_maps gost_52535 bgn_pcgn_alt iso_9_1954 bs_2979_alt scientific ala_lc_alt)
```

## License

MIT License

Copyright (c) 2024 Andrey Nikiforov

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
