#!/bin/bash

blade gw clean bootJar \
 && mkdir -p build/dependency \
 && (cd build/dependency; jar -xf ../libs/*.jar) \
 && docker build -t it.smc.aperitech/imperative-aggregator .
