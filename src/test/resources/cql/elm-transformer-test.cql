using QUICK

valueset "Diabetes VS": '1.2.3.4.5'

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

define "Diabetes Expression":
  [Condition: "Diabetes VS"]

define "Exists from expression":
  exists("Diabetes Expression")

define "Exists from referenced expression":
  "Exists from expression"