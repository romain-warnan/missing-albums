#!/usr/bin/sh
mvn package
mv target/album-release-notification-1.0-jar-with-dependencies.jar missing-albums.jar
