#!/bin/bash
#
function _exit(){
    printf "Exiting:%s\n" "$1"
    exit -1
}

#Returns the value of the last pipe which exited
set -o pipefail
#-e = Exits if command has non-zero status
#-v = Displays each command run by bash script
set -ev

#Current directory
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

#Path are already exported in script in profile.d folder
export PATH=/home/dan/Docs/fabric-samples/bin:$PATH
export FABRIC_CFG_PATH=/home/dan/Docs/fabric-samples/config/
export PATH=$PATH:$FABRIC_CFG_PATH 

#Go to network directory
cd /home/dan/Docs/fabric-samples/test-network

#Stop network if already running
./network.sh down 
#Creates a network with ca and each have a couchdb
./network.sh up createChannel -ca -s couchdb 

cp "/home/dan/Docs/fabric-samples/test-network/organizations/peerOrganizations/org1.example.com/connection-org1.yaml" "${DIR}/Orgs/Org1/gateway/"
cp "/home/dan/Docs/fabric-samples/test-network/organizations/peerOrganizations/org2.example.com/connection-org2.yaml" "${DIR}/Orgs/Org2/gateway/"
