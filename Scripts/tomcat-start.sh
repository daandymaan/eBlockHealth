#!/bin/bash

#Copy gateways
cp -a /home/dan/Docs/BlockchainPrescribing/Orgs/Org1/gateway/. ../Application/app/src/main/resources/
cp -a /home/dan/Docs/BlockchainPrescribing/Orgs/Org2/gateway/. ../Application/app/src/main/resources/

#Copy webapp
cp -r /home/dan/Docs/BlockchainPrescribing/webapp/. ../Application/app/src/main/webapp/

# $CATALINA_HOME/bin/startup.sh
