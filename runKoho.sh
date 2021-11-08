# !/bin/bash

javac dataModels/*.java
javac graph/*.java
javac main/*.java
javac utility/*.java
javac *.java
java Kohonen
#gnuplot graph1.sh
