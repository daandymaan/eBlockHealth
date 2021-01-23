
: ${CHANNEL_NAME:="mychannel"}
: ${DELAY:="3"}
: ${MAX_RETRY:="5"}
: ${VERBOSE:="false"}


#CD into test network directory 
cd /home/dan/Docs/fabric-samples/test-network/
OVERRIDE_ORG="2"
. ./scripts/envVar.sh

#Setting peer parameters to use when deploying code
parsePeerConnectionParameters 1 2
export PEER_PARMS="${PEER_CONN_PARMS##*( )}"


#Setting enviromental variables
export PATH=/home/dan/Docs/fabric-samples/bin:$PATH
export FABRIC_CFG_PATH=/home/dan/Docs/fabric-samples/config/
export PATH=$PATH:$FABRIC_CFG_PATH 
sudo update-alternatives --config java
