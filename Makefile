
all: test1 test2 test3

build1: src/AList.java src/Heap.java
	mkdir -p build # ensure the build directory exists
	javac -cp .:build -d build src/AList.java src/Heap.java 

build2: src/AList.java src/HashTable.java
	mkdir -p build # ensure the build directory exists
	javac -cp .:build -d build src/AList.java src/HashTable.java 

build3: build2
	javac -cp .:build -d build src/Heap.java 

buildtest1: src/Phase1Test.java build1
	javac -d build -cp .:build:./lib/junit-4.12.jar src/AListTest.java src/Phase1Test.java

buildtest2: src/Phase2Test.java build2
	javac -d build -cp .:build:./lib/junit-4.12.jar src/Phase2Test.java

buildtest3: src/Phase3Test.java build3
	javac -d build -cp .:build:./lib/junit-4.12.jar src/Phase3Test.java

test1: buildtest1
	java -cp .:build:lib/junit-4.12.jar:lib/hamcrest-core-1.3.jar org.junit.runner.JUnitCore heap.Phase1Test

test2: buildtest2
	java -cp .:build:lib/junit-4.12.jar:lib/hamcrest-core-1.3.jar org.junit.runner.JUnitCore heap.Phase2Test

test3: buildtest3
	java -cp .:build:lib/junit-4.12.jar:lib/hamcrest-core-1.3.jar org.junit.runner.JUnitCore heap.Phase3Test


clean:
	rm -r build/*
