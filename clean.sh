#!/usr/bin/env bash

set -e

COMPONENT=${@:-all}

RED='\033[0;31m'
NC='\033[0m' # No Color

if echo ${COMPONENT} | grep -q 'all\|infra\|plugin\|gradle'; then
echo
echo -e "${RED}+-------------------------------------------+${NC}"
echo -e "${RED}| cleaning 'gradle-plugins'                 |${NC}"
echo -e "${RED}+-------------------------------------------+${NC}"
./gradle-plugins/gradlew -p gradle-plugins clean
fi

if echo ${COMPONENT} | grep -q 'all\|infra\|starter\|messag'; then
echo
echo -e "${RED}+-------------------------------------------+${NC}"
echo -e "${RED}| cleaning 'messaging-boot-starter'         |${NC}"
echo -e "${RED}+-------------------------------------------+${NC}"
./messaging-boot-starter/gradlew -p messaging-boot-starter clean
fi

if echo ${COMPONENT} | grep -q 'all\|infra\|config'; then
echo
echo -e "${RED}+-------------------------------------------+${NC}"
echo -e "${RED}| cleaning 'config-server'                  |${NC}"
echo -e "${RED}+-------------------------------------------+${NC}"
./config-server/gradlew -p config-server clean
fi

if echo ${COMPONENT} | grep -q 'all\|service\|order'; then
echo
echo -e "${RED}+-------------------------------------------+${NC}"
echo -e "${RED}| cleaning 'order'                          |${NC}"
echo -e "${RED}+-------------------------------------------+${NC}"
./order/gradlew -p order clean
fi

if echo ${COMPONENT} | grep -q 'all\|service\|bakery'; then
echo
echo -e "${RED}+-------------------------------------------+${NC}"
echo -e "${RED}| cleaning 'bakery'                         |${NC}"
echo -e "${RED}+-------------------------------------------+${NC}"
./bakery/gradlew -p bakery clean
fi

if echo ${COMPONENT} | grep -q 'all\|ui\|delivery'; then
echo
echo -e "${RED}+-------------------------------------------+${NC}"
echo -e "${RED}| cleaning 'delivery'                       |${NC}"
echo -e "${RED}+-------------------------------------------+${NC}"
./delivery/gradlew -p delivery clean
fi

if echo ${COMPONENT} | grep -q 'all\|ui\|web'; then
echo
echo -e "${RED}+-------------------------------------------+${NC}"
echo -e "${RED}| cleaning 'order-ui'                       |${NC}"
echo -e "${RED}+-------------------------------------------+${NC}"
./order-ui/gradlew -p order-ui clean
fi
