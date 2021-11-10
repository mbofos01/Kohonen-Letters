# !/bin/bash

dos2unix parameters.txt
javac dataModels/*.java
javac graph/*.java
javac main/*.java
javac utility/*.java
javac *.java
java Kohonen


rm *.class
