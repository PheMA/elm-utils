using QUICK

valueset "Diabetes VS": '1.2.3.4.5'
valueset "Inpatient VS": '6.7.8.9.10'

valueset "Echo VS": '2.16.840.1.999999.1'
valueset "HF Dx VS": '2.16.840.1.113883.3.526.3.376'
valueset "Inpatient Encounter VS": '2.16.840.1.113762.1.4.1182.286'
valueset "Outpatient Encounter VS": '2.16.840.1.113883.3.464.1003.101.11.1277'

context Patient

define "statement one":
    true

define "statement two":
    true

define "statement three":
    "statement one" or "statement two"


define "Phenotype Case":
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

define "single ref"
    true and "bb"

define "Simplest Case":
    [Encounter: "Inpatient VS"] E
       with [Condition: "Diabetes VS"] C
          such that E.id = C.encounter

define "Adult":
    AgeInYears() >= 18

define "Has Echo":
    exists(["Procedure": "Echo VS"])

define "Has HF Dx":
    exists(["Condition": "HF Dx VS"])

define "Inpatient Encounters With HF Dx":
    [Encounter: "Inpatient Encounter VS"] E
        with [Condition: "HF Dx VS"] C
            such that E.id = C.encounter

define "Outpatient Encounters With HF Dx":
    [Encounter: "Outpatient Encounter VS"] E
        with [Condition: "HF Dx VS"] C
            such that E.id = C.encounter

define "Encounter Criteria":
    exists("Inpatient Encounters With HF Dx") or
    Count("Outpatient Encounters With HF Dx") >= 2

define "HF Case":
    "Adult" and "Has Echo" and "Encounter Criteria"
