#!/bin/bash
#

rm -r ../Explorer/organizations/*

cp -r "/home/dan/Docs/fabric-samples/test-network/organizations/peerOrganizations/" "../Explorer/organizations/";
cp -r "/home/dan/Docs/fabric-samples/test-network/organizations/ordererOrganizations/" "../Explorer/organizations/";

ls ../Explorer/organizations/peerOrganizations/org1.example.com/users/Admin@org1.example.com/msp/keystore/ > ../Explorer/connection-profile/newAdminKey.txt


