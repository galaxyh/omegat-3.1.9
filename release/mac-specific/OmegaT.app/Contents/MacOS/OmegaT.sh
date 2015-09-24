#!/bin/bash

cd "$(dirname "$0")/Java"

MACOS="-Xdock:name=OmegaT -Xdock:icon=../../Resources/OmegaT.icns"

JAVA=../jre/bin/java

# Un-comment the following line to use the system-supplied Java 1.6.
#JAVA=$(/usr/libexec/java_home -v 1.6)/bin/java

#LANGUAGE="-Duser.language=en"
#COUNTRY="-Duser.country=GB"
# Settings to access the Internet behind a proxy
#PROXY_HOST="-Dhttp.proxyHost=192.168.1.1"
#PROXY_PORT="-Dhttp.proxyPort=3128"
# Google Translate v2 API key
#GOOGLE_API_KEY="-Dgoogle.api.key=0123456789A0123456789B0123456789C0123456789D"
# Microsoft Translator credentials
#MS_CLIENT_ID="-Dmicrosoft.api.client_id=xxxxx"
#MS_CLIENT_SECRET="-Dmicrosoft.api.client_secret=xxxxx"
# MyMemory email
#MY_MEMORY_EMAIL="-Dmymemory.api.email=xxxxx@xxxxx.xx"
# TaaS user key
#TAAS_USER_KEY="-Dtaas.user.key=xxxxx"
# Yandex Translate API Key
#YANDEX_API_KEY="-Dyandex.api.key=xxxxx"
# TrueTranslate configurations
#TT_API_KEY="-Dtruetranslate.api.key=0123456789A0123456789B0123456789C0123456789D"
#TT_ENCH="-Dtruetranslate.api.ench=xxx"
#TT_CHEN="-Dtruetranslate.api.chen=xxx"

${JAVA} -Xmx1024m ${MACOS} ${LANGUAGE} ${COUNTRY} ${PROXY_HOST} ${PROXY_PORT} ${TT_API_KEY} ${TT_ENCH} ${TT_CHEN} ${GOOGLE_API_KEY} ${MS_CLIENT_ID} ${MS_CLIENT_SECRET} ${MY_MEMORY_EMAIL} ${TAAS_USER_KEY} ${YANDEX_API_KEY} -jar OmegaT.jar
