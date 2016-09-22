#!/bin/bash
#echo "add group 1 with a master(localhost:6380), Notice: do not use localhost when in produciton"
../bin/codis-config -c config.ini -L ./log/cconfig.log server add 1 host:port master
../bin/codis-config -c config.ini -L ./log/cconfig.log server add 1 host:port slave

#echo "add group 2 with a master(localhost:6382), Notice: do not use localhost when in produciton"
../bin/codis-config -c config.ini -L ./log/cconfig.log server add 2 host:port master
../bin/codis-config -c config.ini -L ./log/cconfig.log server add 2 host:port slave

