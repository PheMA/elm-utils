using FHIR

valueset "Anticoagulant": '2.16.840.1.113883.17.4077.2.1012'

define "simplest":
    true

define "simpleBoolean":
    false or true

define "callingStatement":
    false or "simpleBoolean"

define SimpleRetrieve:
    [MedicationRequest: "Anticoagulant"]

define SimpleDateInterval:
    @2018-01-01 in Interval[@2019-01-01 - 2 years, @2019-01-01 + 2 years]

define SimpleAg:
    Count([MedicationRequest: "Anticoagulant"]) >=2


define "one":
    "two" + 2

define "two":
    "three" + 3

define "three":
    4

define "four":
    ("one" < 3)
        or
    "five"

define "five":
    false or "six"

define "six":
    true and "seven"

define "seven":
    true