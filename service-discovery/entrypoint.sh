#!/bin/sh

./wait && java $JAVA_OPTS -cp app:app/lib/* it.smc.aperitech.servicediscovery.ServiceDiscoveryApplication
