package br.com.mosaicosolutions.geneticalgorithm;

/**
 * Genetic Algorithm Java Classes
 * <p> Copyright 1996-2012 by Mark Watson. All rights reserved. <p/>
 * <p> Adaptation of the original class by Bruno Xavier. </p>
 * @author BrunoXavier
 * @version 1.0
 */
public class Genetic extends AbstractGenetic {

    public Genetic(int numberOfGenesPerChromosome, int numberOfChromosomes) {
        super(numberOfGenesPerChromosome, numberOfChromosomes);
    }

    public Genetic(int numberOfGenesPerChromosome, int numberOfChromosomes, float crossoverFraction, float mutationFraction) {
        super(numberOfGenesPerChromosome, numberOfChromosomes, crossoverFraction, mutationFraction);
    }

    @Override
    public void calculateFitness() {
        for (int i=0; i < numberOfChromosomes; i++) {
            float x = geneToFloat(i);
            getChromosome(i).setFitness(fitness(x));
        }
    }

    public float geneToFloat(int chromosomeIndex) {
        int base = 1;
        float x = 0;
        for (int i = 0; i < numberOfGenesPerChromosome; i++)  {
            if (getGene(chromosomeIndex, i))
                x += base;

            base *= 2;
        }
        x /= 102.4f;
        //System.out.println("float=" + x);
        return x;
    }

    private float fitness(float x) {
        return (float)(Math.sin(x) * Math.cos(0.4f*x) * Math.sin(2.0f*x));
    }

    public void print() {
        float sum = 0.0f;
        for (int i = 0; i < numberOfChromosomes; i++) {

            Chromosome chromosome = getChromosome(i);

            float x = geneToFloat(i);

            sum += chromosome.getFitness();

            if (true) { // (i < (numChromosomes / 2)) {  // show best half of chromosomes
                System.out.print("Fitness for chromosome ");
                System.out.print(i);
                System.out.print(" is ");
                System.out.println(chromosome.getFitness() + ", occurs at x=" + x);
            }
        }
        sum /= (float)numberOfChromosomes;
        System.out.println("Average fitness=" + sum + " and best fitness for this generation:" + getChromosome(0).getFitness());
    }
}
