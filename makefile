run: all
	java -cp build -Xint Main src/input.txt 

all:
	javac -d build src/*.java 

clean:
	rm -rf build

.PHONY: all clean run