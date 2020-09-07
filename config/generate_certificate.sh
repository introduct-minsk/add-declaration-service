#!/bin/sh
keytool -genkeypair -keyalg RSA -keysize 3072 -alias root-ca -dname "C=EE,ST=Tallin,L=Tallin,O=devops,OU=devops,CN=dev" -ext BC:c=ca:true -ext KU=keyCertSign -validity 3650 -keystore ./ca.jks -storepass secret -keypass secret 
keytool -exportcert -keystore ./ca.jks -storepass secret -alias root-ca -rfc -file ./ca.pem
keytool -genkeypair -keyalg RSA -keysize 3072 -alias localhost -dname "C=EE,ST=Tallin,L=Tallin,O=devops,OU=devops,CN=dev" -ext BC:c=ca:false -ext EKU:c=serverAuth -ext "SAN:c=DNS:localhost,IP:3.125.114.22" -validity 800 -keystore ./certificate.jks -storepass secret -keypass secret
keytool -certreq -keystore ./certificate.jks -storepass secret -alias localhost -keypass secret -file ./certificate.csr
keytool -gencert -keystore ./ca.jks -storepass secret -infile ./certificate.csr -alias root-ca -keypass secret -ext BC:c=ca:false -ext EKU:c=serverAuth -ext "SAN:c=DNS:localhost,IP:3.125.114.22" -validity 800 -rfc -outfile ./certificate.pem
keytool -importcert -noprompt -keystore ./certificate.jks -storepass secret -alias root-ca -keypass secret -file ./ca.pem 
keytool -importcert -noprompt -keystore ./certificate.jks -storepass secret -alias localhost -keypass secret -file ./certificate.pem
