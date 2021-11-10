# !/bin/bash

pip3 install matplotlib > /den/null 2>&1
dos2unix parameters.txt > /dev/null 2>&1

javac *.java
java Kohonen

rm *.class
