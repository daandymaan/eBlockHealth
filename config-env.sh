#!/bin/bash
#
# SPDX-License-Identifier: Apache-2.0
shopt -s extglob

function _exit(){
    printf "Exiting:%s\n" "$1"
    exit -1
}

re='^[1-2]+$'
if ! [[ $1 =~ $re ]] ; then
   echo "error: Not a number" >&2; exit 2
fi

: ${CHANNEL_NAME:="mychannel"}
: ${DELAY:="3"}
: ${MAX_RETRY:="5"}
: ${VERBOSE:="false"}


DIR=${PWD}

#CD into test network directory 
cd /home/dan/Docs/fabric-samples/test-network/
env | sort > /tmp/env.orig

if [ $1 == 1 ]
then 
    #Admin 2 
    OVERRIDE_ORG="1"
else 
    #Admin 1
    OVERRIDE_ORG="2"
fi 
. ./scripts/envVar.sh

#Setting peer parameters to use when deploying code
parsePeerConnectionParameters 1 2
export PEER_PARMS="${PEER_CONN_PARMS##*( )}"


#Setting enviromental variables
export PATH=/home/dan/Docs/fabric-samples/bin:$PATH
export FABRIC_CFG_PATH=/home/dan/Docs/fabric-samples/config/
export PATH=$PATH:$FABRIC_CFG_PATH 


env | sort | comm -1 -3 /tmp/env.orig - | sed -E 's/(.*)=(.*)/export \1="\2"/'
rm /tmp/env.orig

#sudo update-alternatives --config java

cd ${DIR}
