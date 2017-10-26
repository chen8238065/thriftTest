#!/bin/bash
#########################################################################
# File Name: templates.thrift-gen.sh
# Author: chapa
# mail: cpp8238065@163.com
# Created Time: 2017年10月23日 星期一 11时24分43秒
#########################################################################
set -e
mecho () {
    echo -e "\033[32m [CmdInfo]: $* \033[0m"
}
throw () {
    echo -e  "\033[31m [ERROR]: $* \033[0m" >&2
    exit 1
}
getFiles(){
	find ./src/main/resources -name "*.templates.thrift"
}

THRIFT=`which thrift`
test ! -f $THRIFT && throw "templates.thrift-compiler not exist, please intatll (sudo apt-get install templates.thrift-compiler)"
test ! -x $THRIFT && throw "templates.thrift can not run,please grant execute permission for current user(`uname -n`)"
mecho "Begin Gen!"
find ./src/main/resources -name "*.templates.thrift"|while read line;do
	mecho "Gen $line"
	$THRIFT -r --gen java -out src/main/java -I src/main/resources/thrift $line
done
mecho "SUCCESS!"
