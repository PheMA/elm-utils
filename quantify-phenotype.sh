#!/usr/bin/env zsh

set -x

ls -la ../phema-workbench-repository/bundles | egrep -p -o "\d+\.[a-z0-9-]+[^M]" | while read line ; do  mvn -q exec:java -Dexec.args="../phema-workbench-repository/bundles/phema-phenotype.${line}bundle.json" > ../phema-workbench-repository/analysis/metrics/${line}metrics.json ; done

