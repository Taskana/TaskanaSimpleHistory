#!/bin/bash
set -e #fail fast

#H Usage:
#H build.sh -h | build.sh --help
#H
#H prints this help and exits  
#H 
#H build.sh <module> 
#H
#H   if a release version exists (extracted from TRAVIS_TAG) 
#H   the maven versions of all modules will be changed to the given release version.
#H
#H module:
#H   directory of a maven project
#H version:
#H
#H   new version for the <module>.
#H   format: ^v[0-9]+\.[0-9]+\.[0-9]+$
#H   if the format is wrong it will be ignored.
#H   
#H requirements:
#H
#H   - <module>'s pom file contains the profiles 'release' and 'snapshot'
# Arguments:
#   $1: exit code
function helpAndExit {
  cat "$0" | grep "^#H" | cut -c4-
  exit "$1"
}

# changing version in pom and all its children
# Arguments:
#   $1: directory of pom
#   $2: new version
function change_version {
  mvn versions:set -f "$1" -DnewVersion="$2"   -DartifactId=*  -DgroupId=* versions:commit
}

function main {
  [[ "$2" =~ ^v[0-9]+\.[0-9]+\.[0-9]+$ ]] && change_version "$1" "${2##v}"
  mvn clean install -q -f "$1" -Dmaven.javadoc.skip=true
}

main "$@"
