#!/bin/bash
$CATALINA_HOME/bin/shutdown.sh
dir=$(pwd)

#Copy gateways
cp -a /home/dan/Docs/BlockchainPrescribing/Orgs/Org1/gateway/. ../Application/app/src/main/resources/
cp -a /home/dan/Docs/BlockchainPrescribing/Orgs/Org2/gateway/. ../Application/app/src/main/resources/

#Copy webapp
cp -r /home/dan/Docs/BlockchainPrescribing/webapp/. ../Application/app/src/main/webapp/

#Remove previous version
cd $CATALINA_HOME/webapps 
rm -r app 
rm app.war
cd $dir

#Create war file 
cd ../Application
gradle assemble 
cd app/build/libs/

#Copy new war to directory
cp app.war $CATALINA_HOME/webapps/

$CATALINA_HOME/bin/startup.sh
