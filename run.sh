#!/bin/bash

# Set the JavaFX library path
JAVAFX_LIB="/home/ahmedtm/Documents/java_work/Project/src/lib"

# Compile the Java files
javac --module-path $JAVAFX_LIB --add-modules javafx.controls,javafx.fxml \
      -d /home/ahmedtm/Documents/java_work/Project/out \
      /home/ahmedtm/Documents/java_work/Project/src/main/*.java \
      /home/ahmedtm/Documents/java_work/Project/src/fx/*.java \
      /home/ahmedtm/Documents/java_work/Project/src/utils/*.java

# Run the application
java --module-path $JAVAFX_LIB --add-modules javafx.controls,javafx.fxml \
     -cp /home/ahmedtm/Documents/java_work/Project/out \
     main.loadmanagementsystem
// Commit 2
// Commit 16
// Commit 30
// Commit 44
