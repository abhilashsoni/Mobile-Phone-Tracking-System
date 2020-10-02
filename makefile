all:
	javac checker.java
	javac Myset.java
	javac RoutingMapTree.java
	java checker

clean:
	rm checker.class Myset.class RoutingMapTree.class
