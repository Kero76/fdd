#!/bin/bash

# Download all libs 
wget http://www.cis.uni-muenchen.de/~schmid/tools/TreeTagger/data/tree-tagger-ARM64-3.2.tar.gz
wget http://www.nactem.ac.uk/tsujii/GENIA/tagger/geniatagger-3.0.2.tar.gz
wget http://search.cpan.org/CPAN/authors/id/T/TH/THHAMON/Lingua-YaTeA-0.622.tar.gz

# Check existence of folder libs.
if [ ! -d "./libs" ] 
then
    mkdir ./libs
fi

# Move libs on folder libs.
mv tree-tagger-ARM64-3.2.tar.gz ./libs
mv geniatagger-3.0.2.tar.gz ./libs
mv Lingua-YaTeA-0.622.tar.gz ./libs

# Uncompress .tar.gz files.
cd libs/
mkdir tree-tagger && tar zxvf tree-tagger-ARM64-3.2.tar.gz -C tree-tagger
mkdir genia-tagger && tar zxvf geniatagger-3.0.2.tar.gz -C genia-tagger
mkdir yatea && tar zxvf Lingua-YaTeA-0.622.tar.gz -C yatea

# Remove .tar.gz
rm tree-tagger-ARM64-3.2.tar.gz
rm geniatagger-3.0.2.tar.gz
rm Lingua-YaTeA-0.622.tar.gz
