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

#Export appropriate enviromental variables 
export PATH=/home/dan/Docs/fabric-samples/bin:$PATH
export FABRIC_CFG_PATH=/home/dan/Docs/fabric-samples/config/
export PATH=$PATH:$FABRIC_CFG_PATH 
export COMPOSE_PROJECT_NAME=net
export IMAGE_TAG=latest
export SYS_CHANNEL=system-channel

#Stops tomcat server
$CATALINA_HOME/bin/shutdown.sh

#Go to network directory
cd /home/dan/Docs/fabric-samples/test-network

#Remove docker 
docker kill logspout || true 

#Stop network if already running
./network.sh down 

#Delete wallets/identities folder 
if [ -d "/home/dan/Docs/BlockchainPrescribing/wallets" ]; then 
    rm -r /home/dan/Docs/BlockchainPrescribing/wallets
    #docker rm $(docker ps -a -q)
fi 

#Delete wallets/identities folder 
if [ -d "/opt/tomcat/apache-tomcat-8.5.63/webapps/wallets" ]; then 
    rm -r /opt/tomcat/apache-tomcat-8.5.63/webapps/wallets
    #docker rm $(docker ps -a -q)
fi 

