package br.com.mosaicosolutions.geneticalgorithm;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Genetic Algorithm Java Classes
 * <p> Copyright 1996-2012 by Mark Watson. All rights reserved. <p/>
 * <p> Adaptation of the original class by Bruno Xavier. </p>
 * @author BrunoXavier
 * @version 1.0
 */
public class Program {

    public static void main(String[] args) {
        new Thread(() -> {

                
                int genesPerChromosome = 10;//Integer.valueOf(properties.getProperty("genes.per.chromosome"));
                int numberOfChromosomes = 20;
                float crossoverFraction = 0.6f;
                float mutationFraction = 0.2f;
                
                System.out.println("genes.per.chromosome : " + genesPerChromosome);
                System.out.println("number.of.chromosomes: " + numberOfChromosomes);
                System.out.println("crossover.fraction   : " + crossoverFraction);
                System.out.println("mutation.fraction    :" + mutationFraction);
                System.out.println();
                
                Genetic geneticExperiment = new Genetic(
                        genesPerChromosome,
                        numberOfChromosomes,
                        crossoverFraction,
                        mutationFraction
                );

                int geneIndex = 0; //  debug only

                for (Chromosome chromosome  : geneticExperiment.getChromosomes()) {
                    System.out.println(chromosome + " : " + geneticExperiment.geneToFloat(geneIndex++));
                }

                final int NUMBER_CYCLES = 500;

                for (int i=0; i<NUMBER_CYCLES; i++) {

                    geneticExperiment.evolve();

                    if (( i % (NUMBER_CYCLES / 5) ) == 0 || i == (NUMBER_CYCLES - 1) ) {
                        System.out.println("Generation " + i);
                        geneticExperiment.calculateFitness(); // suggested by Rick Hall
                        geneticExperiment.sort();        // suggested by Rick Hall
                        geneticExperiment.print();
                    }
                }

        }).start();
    }

    private static Properties loadPropertiesFromFile(String fileName) {
        try {

            Properties properties = new Properties();
            properties.load(new FileInputStream(fileName));
            return properties;

        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getMessageErro() {
        return "The file name containing the properties was not reported \n" +
                "A file with the following properties is required: \n" +
                "genes.per.chromosome=int\nnumber.of.chromosomes=int\ncrossover.fraction=float\nmutation.fraction=float";
    }
}
