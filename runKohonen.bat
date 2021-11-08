javac dataModels/*.java
javac graph/*.java
javac main/*.java
javac utility/*.java
javac *.java

java Kohonen

echo "Running Kohonen"


del *.class
cd dataModels
del *.class
cd ../
cd graph
del *.class
cd ../
cd main
del *.class
cd ../
cd utility
del *.class
cd ../