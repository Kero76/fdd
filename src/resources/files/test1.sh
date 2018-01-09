#!/bin/sh

mkdir "out"
mkdir "outgenia"
mkdir "outyatea"
mkdir "outyateagenia"
for fichier in *.txt
do

cat "$fichier" | ../libs/tree-tagger-linux-3.2.1/cmd/tree-tagger-english > out/"$fichier"
cd ../libs/genia
./geniatagger < ../../files/"$fichier" > ../../files/outgenia/"$fichier"
cd ../../files

cd ./outyatea
./../../libs/Lingua-YaTeA-0.622/bin/yatea ./../out/"$fichier" 
cd ../outyateagenia
./../../libs/Lingua-YaTeA-0.622/bin/yatea ./../outgenia/"$fichier"
cd ..
done
