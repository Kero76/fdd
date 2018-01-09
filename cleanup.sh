#!/bin/bash
# Clean maven repository
mvn clean

# Remove folder libs.
# if [ -d "./libs" ]
# then
#     rm -rf libs/
# fi

if [ -d "./files" ]
then
  rm -rf files/
fi

if [ -d "./results" ]
then
  rm -rf results/
fi
