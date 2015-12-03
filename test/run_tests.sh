#!/bin/bash
script_dir=$(dirname "$(readlink -f "$0")")
export KB_DEPLOYMENT_CONFIG=$script_dir/../deploy.cfg
export KB_AUTH_TOKEN=`cat /kb/module/work/token`
export JAVA_HOME=/kb/runtime/java
/kb/runtime/ant/bin/ant test -Djars.dir=/kb/deployment/lib/jars
