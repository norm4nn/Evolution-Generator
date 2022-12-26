package agh.ics.oop;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Genotype extends AbstractGenotype {

    public Genotype( Parametrs parametrs) {//if its created at begin of simulation
        this.parametrs = parametrs;
        this.genotype = new int[parametrs.getGenotypeLength()];
        this.currentGen = (int) (Math.random() * (parametrs.getGenotypeLength()));
        for(int i=0;i<parametrs.getGenotypeLength();++i) {
            int randomNum = (int) (Math.random() * 8);
            this.genotype[i] = randomNum;
        }

    }

    public Genotype(int[] genLeftSide,int[] genRightSide, Parametrs parametrs) {//if its created from two other genotypes
        this.parametrs = parametrs;
        this.genotype = new int[genLeftSide.length + genRightSide.length];
        this.currentGen = (int) (Math.random() * (genLeftSide.length + genRightSide.length));
        System.arraycopy(genLeftSide, 0, this.genotype, 0, genLeftSide.length);
        System.arraycopy(genRightSide, 0, this.genotype, genLeftSide.length, genRightSide.length);
    }

    @Override
    protected void mutate() {
        int len = this.genotype.length;
        int range = this.parametrs.getMaxNumberOfMutations() - this.parametrs.getMinNumberOfMutations() + 1;
        int numberOfMutation = (int) (Math.random() * range) + this.parametrs.getMinNumberOfMutations();
        LinkedList<Integer> positions = new LinkedList<>();
        LinkedList<Integer> selectedToMutate = new LinkedList<>();
        for(int i=0; i < len;++i)
            positions.add(i);

        for(int i=0;i < numberOfMutation;++i) {//selecting which gens (on which position) will mutate
            int rand = (int) (Math.random() * positions.size());
            selectedToMutate.add(positions.get(rand));
            positions.remove(rand);
        }

        for(Integer selected: selectedToMutate) {//every selected gen mutates
            this.genotype[selected] += Math.pow(-1, (int) (Math.random() * 2));
            this.genotype[selected] %= 8;
            if (this.genotype[selected] < 0)    this.genotype[selected] = 7;
        }
    }

}
