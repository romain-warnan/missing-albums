#!/usr/bin/sh
mvn package
mv target/missing-albums-1.0-jar-with-dependencies.jar missing-albums.jar
