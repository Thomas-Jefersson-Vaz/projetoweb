#!/bin/bash
mvn clean package && cp target/ROOT.war ~/tomcat/webapps/ROOT.war
clear
echo "Deploy Concluido!"