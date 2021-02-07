#!/bin/bash

initEnv(){
    ORG=$1
    source config-env.sh $ORG
}

queryCC(){
    peer lifecycle chaincode queryinstalled >&log.txt
    cat log.txt
    PACKAGE_ID=$(sed -n "/${CC_NAME}_${CC_VERSION}/{s/^Package ID: //; s/, Label:.*$//; p;}" log.txt)
}

infoln "Initialzing envirnoment for peer 1"
initEnv 2

infoln "Packaging code ./Smart-Contract/contract"
peer lifecycle chaincode package cp.tar.gz --lang java --path ./Smart-Contract/contract --label cp_0 

infoln "Installing chaincode peer 1"
peer lifecycle chaincode install cp.tar.gz

infoln "Querying chaincode on peer 1"
queryCC

infoln "Appoving chaincode on peer 1"
peer lifecycle chaincode approveformyorg --orderer localhost:7050 --ordererTLSHostnameOverride orderer.example.com --channelID mychannel --name prescriptioncontract -v 0 --package-id $PACKAGE_ID --sequence 1 --tls --cafile $ORDERER_CA

infoln "Initialzing envirnoment for peer 2"
initEnv 1

infoln "Installing chaincode peer 2"
peer lifecycle chaincode install cp.tar.gz

infoln "Querying chaincode on peer 2"
queryCC

infoln "Appoving chaincode on peer 2"
peer lifecycle chaincode approveformyorg --orderer localhost:7050 --ordererTLSHostnameOverride orderer.example.com --channelID mychannel --name prescriptioncontract -v 0 --package-id $PACKAGE_ID --sequence 1 --tls --cafile $ORDERER_CA

infoln "Commiting chaincode"
peer lifecycle chaincode commit -o localhost:7050 --ordererTLSHostnameOverride orderer.example.com --peerAddresses localhost:7051 --tlsRootCertFiles ${PEER0_ORG1_CA} --peerAddresses localhost:9051 --tlsRootCertFiles ${PEER0_ORG2_CA} --channelID mychannel --name prescriptioncontract -v 0 --sequence 1 --tls --cafile $ORDERER_CA --waitForEvent


