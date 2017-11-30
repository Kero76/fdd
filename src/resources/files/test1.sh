#!/bin/sh

mkdir "out"
mkdir "outgenia"
for fichier in *.txt
do

cat "$fichier" | ../libs/tree-tagger-linux-3.2.1/cmd/tree-tagger-english > out/"$fichier"
cd ../libs/genia
./geniatagger < ../../files/"$fichier" > ../../files/outgenia/"$fichier"
cd ../../files
done
