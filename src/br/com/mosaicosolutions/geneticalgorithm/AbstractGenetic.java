package br.com.mosaicosolutions.geneticalgorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Genetic Algorithm Java Classes
 * <p> Copyright 1996-2012 by Mark Watson. All rights reserved. <p/>
 * <p> Adaptation of the original class by Bruno Xavier. </p>
 * @author BrunoXavier
 * @version 1.0
 */
public abstract class AbstractGenetic {
    protected int numberOfGenesPerChromosome;
    protected int numberOfChromosomes;

    private List<Chromosome> chromosomes;
    private float crossoverFraction;
    private float mutationFraction;
    private int[] rouletteWheel;
    private int rouletteWheelSize;

    public AbstractGenetic(int numberOfGenesPerChromosome, int numberOfChromosomes) {
        this(numberOfGenesPerChromosome, numberOfChromosomes, 0.8f, 0.01f);
    }

    public AbstractGenetic(int numberOfGenesPerChromosome, int numberOfChromosomes, float crossoverFraction, float mutationFraction) {
        this.numberOfGenesPerChromosome = numberOfGenesPerChromosome;
        this.numberOfChromosomes = numberOfChromosomes;
        this.crossoverFraction = crossoverFraction;
        this.mutationFraction = mutationFraction;

        initializeChromosome();
        sort();
        initializeRouletteWheel();

    }

    private void initializeChromosome() {
        this.chromosomes = new ArrayList<>(numberOfChromosomes);

        for (int i = 0; i < numberOfChromosomes; i++) {
            /*Chromosome chromosome = new Chromosome(numberOfGenesPerChromosome);

            chromosome.fill(() -> Math.random() < 0.5);//new Random(2).nextFloat() < 0.5);
            chromosomes.add(chromosome);*/
            
            chromosomes.add(new Chromosome(this.numberOfGenesPerChromosome));
            for (int j = 0; j < this.numberOfGenesPerChromosome; j++) {
                chromosomes.get(i).setBit(j, Math.random() < 0.5);
            }
        }
        
        
    }

    public void sort() {
        this.chromosomes.sort((o1, o2) -> (int) (1000 * (o1.getFitness() - o2.getFitness())));
    }

    private void initializeRouletteWheel() {
        // define the roulette wheel:
        this.rouletteWheelSize = 0;

        for (int i = 0; i < numberOfGenesPerChromosome; i++) {
            rouletteWheelSize += i + 1;
        }

        System.out.println("count of slots in roulette wheel=" + rouletteWheelSize);

        rouletteWheel = new int[rouletteWheelSize];

        int numTrials = numberOfGenesPerChromosome;
        int index = 0;

        for (int i = 0; i < numberOfChromosomes; i++) {

            for (int j = 0; j < numTrials; j++)
                rouletteWheel[index++] = i;

            numTrials--;
        }
    }

    public boolean getGene(int chromosome, int gene) {
        return chromosomes.get(chromosome).getBit(gene);
    }

    public void setGene(int chromosome, int gene, boolean value) {
        chromosomes.get(chromosome).setBit(gene, value);
    }

    public Chromosome getChromosome(int index) {
        return chromosomes.get(index);
    }

    public void evolve() {
        calculateFitness();
        sort();
        //System.out.println(Arrays.toString(this.chromosomes.toArray()));
        doCrossovers();
        doMutations();
        doRemoveDuplicates();
    }

    public abstract void calculateFitness();

    public void doCrossovers() {
        int num = (int) (numberOfChromosomes * crossoverFraction);
        for (int i = num - 1; i >= 0; i--) {
            // 8/11/2008: don't overwrite the "best" chromosome from current generation:
            int c1 = 1 + (int) ((rouletteWheelSize - 1) * Math.random() * 0.9999f);
            int c2 = 1 + (int) ((rouletteWheelSize - 1) * Math.random() * 0.9999f);
            c1 = rouletteWheel[c1];
            c2 = rouletteWheel[c2];
            if (c1 != c2) {
                int locus = 1 + (int) ((numberOfGenesPerChromosome - 2) * Math.random());

                for (int g = 0; g < numberOfGenesPerChromosome; g++)
                    if (g < locus)
                        setGene(i, g, getGene(c1, g));
                    else
                        setGene(i, g, getGene(c2, g));

            }
        }
    }

    public void doMutations() {
        int num = (int) (numberOfChromosomes * mutationFraction);
        for (int i = 0; i < num; i++) {
            // 8/11/2008: don't overwrite the "best" chromosome from current generation:
            int c = 1 + (int) ((numberOfChromosomes - 1) * Math.random() * 0.99);
            int g = (int) (numberOfGenesPerChromosome * Math.random() * 0.99);
            setGene(c, g, !getGene(c, g));
        }
    }

    public void doRemoveDuplicates() {

        for (int i = numberOfChromosomes - 1; i > 3; i--)
            for (int j = 0; j < i; j++)
                if (chromosomes.get(i).equals(chromosomes.get(j))) {
                    int g = (int) (numberOfGenesPerChromosome * Math.random() * 0.99);
                    setGene(i, g, !getGene(i, g));
                    break;
                }
    }

    public Iterable<Chromosome> getChromosomes() {
        return chromosomes;
    }
}
