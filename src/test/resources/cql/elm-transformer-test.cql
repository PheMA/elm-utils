define "statement one":
    true

define "statement two":
    true

define "statement three":
    "statement one" or "statement two"


define "aa":
    "statement three" and ("bb" or "cc")

define "dd":
    1 = 3

define "bb":
    false

define "cc":
    "dd" and (true or false)