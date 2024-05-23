run: all
	java -cp build -Xint Main src/input.txt 

all:
	javac -d build src/*.java 

clean:
	rm -rf build

doc:
	javadoc -d doc src/*.java

.PHONY: all clean run doc