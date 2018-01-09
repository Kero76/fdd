package fr.rouen.univ.mining;

import fr.rouen.univ.mining.algorithms.frequentpatterns.apriori_close.AlgoAprioriClose;
import fr.rouen.univ.mining.patterns.itemset_array_integers_with_count.Itemsets;

import java.io.IOException;

public class MineMain {
    public static void main(String[] args) {
        final String path = "src/resources/files/";
        final String filename = "29054232_mesh.txt";

        try {
            Itemsets is = new AlgoAprioriClose().runAlgorithm(0.5, path + filename, path + "out.txt" );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
