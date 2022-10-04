#!/bin/bash

arg1=$1
arg2=$2


rm -rf $( ls | grep test | awk '{print $1}')

./$(ls | grep service | awk '{print $1}')/gradlew build