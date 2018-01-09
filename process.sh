#!/bin/bash
# Compile java source
# mvn compile

# $1 : path, $2 : folder, $3 : short filename, $4 : extension, $5 : methods
# java target/classes/fr/rouen/univ/App.java $1 $2 $3 $4 $5

# Setup environment
mkdir results/ results/tree-tagger results/genia-tagger

# Copy files on folder files.
cp -R src/resources/files/ ./
cd files/$2

# Loop on each file present on folder files/$2
for file in *.txt
do
  cat "$file" | ../libs/tree-tagger/cmd/tree-tagger-english > ../results/tree-tagger/$1/"$file"
  cat "$file" | ../../libs/genia-tagger/geniatagger > ../results/genia-tagger/$1/"$file"
  # cd ../libs/genia
  # ./geniatagger < ../../files/"$file" > ../../files/outgenia/"$file"
  # cd ../../files
done

# Path Corpus/Folder File Extension Methods
