#!/bin/bash

# Check existence of folder libs.
if [ ! -d "./libs" ]
then
    mkdir ./libs ./libs/tree-tagger
fi

# Move on folder tree-tagger, download install and remove useless files after process.
cd libs/tree-tagger
wget http://www.cis.uni-muenchen.de/~schmid/tools/TreeTagger/data/tree-tagger-ARM64-3.2.tar.gz
wget http://www.cis.uni-muenchen.de/~schmid/tools/TreeTagger/data/tagger-scripts.tar.gz
wget http://www.cis.uni-muenchen.de/~schmid/tools/TreeTagger/data/install-tagger.sh
wget http://www.cis.uni-muenchen.de/~schmid/tools/TreeTagger/data/english-par-linux-3.2-utf8.bin.gz
sh install-tagger.sh
rm tree-tagger-ARM64-3.2.tar.gz
rm tagger-scripts.tar.gz
rm install-tagger.sh
rm english-par-linux-3.2-utf8.bin.gz

# Same works for genia-tagger.
cd ../
wget http://www.nactem.ac.uk/tsujii/GENIA/tagger/geniatagger-3.0.2.tar.gz
tar zxvf geniatagger-3.0.2.tar.gz
rm geniatagger-3.0.2.tar.gz
mv geniatagger-3.0.2 genia-tagger && cd genia-tagger
make

# Same works for yatea.
cd ../
wget http://search.cpan.org/CPAN/authors/id/T/TH/THHAMON/Lingua-YaTeA-0.622.tar.gz
tar zxvf Lingua-YaTeA-0.622.tar.gz
rm Lingua-YaTeA-0.622.tar.gz
mv Lingua-YaTeA-0.622 yatea && cd yatea
perl Makefile.PL
make
make test
make install
