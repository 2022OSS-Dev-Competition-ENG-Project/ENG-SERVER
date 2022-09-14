#!/bin/bash

arg1=$1
arg2=$2

CURRENT_PID=$(ps | grep $2 | grep .jar | awk '{print $1}')
echo "$CURRENT_PID"

if [ "$arg1" == "build" ] ; then

    if [ "$arg2" == "manager" ] ; then
        echo "[+] manager-service build start"
        cd manager-service
        ./gradlew build
        echo "[+] Build Success"
    elif [ "$arg2" == "user" ] ; then
        echo "[+] user-service build start"
        cd user-service
        ./gradlew build
        echo "[+] Build Success"
    else
        echo "[!] ------ Command ------  "
    echo "[!] ./build.sh build [manager] or [user] "
    echo "[!] ./build.sh start [manager] or [user] "
    fi

elif [ "$arg1" == "start" ] ; then

    if [ "$arg2" == "manager" ] ; then
        if [ -z CURRENT_PID ] ; then
            echo "[+] manager-service starting"
            nohup java -jar manager-service/build/libs/manager-service-0.0.1-SNAPSHOT.jar&
            echo "[+] 성공적으로 manager-Service를 실행시켰습니다."
        else
            echo "[!] 현재 manager-service가 실행중입니다."
            echo "[!] 종료후 다시 시작하겠습니다."
            kill -9 $CURRENT_PID
            echo "[+] 성공적으로 종료 시켰습니다."
            echo "[+] manager-service starting"
            nohup java -jar manager-service/build/libs/manager-service-0.0.1-SNAPSHOT.jar&
            echo "[+] 성공적으로 manager-Service를 실행시켰습니다."
        fi

    elif [ "$arg2" == "user" ] ; then

        if [ -z CURRENT_PID ] ; then
            echo "[+] user-service starting"
            nohup java -jar user-service/build/libs/user-service-0.0.1-SNAPSHOT.jar&
            echo "[+] 성공적으로 user-Service를 실행시켰습니다."
        else
            echo "[!] 현재 user-service가 실행중입니다."
            echo "[!] 종료후 다시 시작하겠습니다."
            kill -9 $CURRENT_PID
            echo "[+] 성공적으로 종료 시켰습니다."
            echo "[+] user-service starting"
            nohup java -jar user-service/build/libs/user-service-0.0.1-SNAPSHOT.jar&
            echo "[+] 성공적으로 user-Service를 실행시켰습니다."
        fi

    else
    echo "[!] ------ Command ------  "
    echo "[!] ./build.sh build [manager] or [user] "
    echo "[!] ./build.sh start [manager] or [user] "
    fi

else
    echo "[!] ------ Command ------  "
    echo "[!] ./build.sh build [manager] or [user] "
    echo "[!] ./build.sh start [manager] or [user] "
fi