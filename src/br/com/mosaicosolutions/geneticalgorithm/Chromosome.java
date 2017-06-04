package br.com.mosaicosolutions.geneticalgorithm;

import java.util.BitSet;
import java.util.function.Supplier;

/**
 * Genetic Algorithm Java Classes
 * <p> Copyright 1996-2012 by Mark Watson. All rights reserved. <p/>
 * <p> Adaptation of the original class by Bruno Xavier. </p>
 * @author BrunoXavier
 * @version 1.0
 */
public final class Chromosome {

    private BitSet chromosome;
    private float fitness;

    public Chromosome(int numberOfGenes) {
        this(numberOfGenes, -999);
    }

    public Chromosome(int numberOfGenes, float fitness) {
        this.chromosome = new BitSet(numberOfGenes);
        this.fitness = fitness;
    }

    public boolean getBit(int index) {
        return chromosome.get(index);
    }

    public void setBit(int index, boolean value) {
        chromosome.set(index, value);
    }

    public float getFitness() {
        return fitness;
    }

    public void setFitness(float value) {
        fitness = value;
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof BitSet && this.chromosome.equals(other);
    }

    @Override
    public String toString() {
        return "[Chromosome: fitness: " + fitness + ", bit set: " + chromosome + "]";
    }

    public void fill(Supplier<Boolean> forEachBit) {

        for (int i = 0; i < chromosome.length(); i++)
            chromosome.set(i, forEachBit.get());
    }
}
