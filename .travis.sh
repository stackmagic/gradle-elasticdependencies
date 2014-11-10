#!/bin/bash

set -e

# same steps for jdk6 and jdk7
gradle assemble
gradle check

if [ -n "$(java -version 2>&1 | grep 'java version' | cut -d'"' -f2 | egrep '^1.7')" ]; then
	echo ">>> we're on jdk7, also running smoketest"
	gradle smoketest
fi
