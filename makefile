run: all
	java -cp build -Xint Main src/input.txt 

all:
	javac -d build src/*.java 

clean:
	rm -rf build

doc:
	javadoc -d doc src/*.java

zip:
	zip -r EmirhanAltunel_200104004035_HW7.zip src doc makefile report.pdf

.PHONY: all clean run doc zip