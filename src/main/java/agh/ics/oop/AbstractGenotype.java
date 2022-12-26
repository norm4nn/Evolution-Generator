package agh.ics.oop;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public abstract class AbstractGenotype {
    protected int currentGen;
    protected int[] genotype;
    protected Parametrs parametrs;


    public int useGen() {
        int result = this.genotype[this.currentGen];
        this.currentGen += 1;
        this.currentGen %= this.genotype.length;
        return result;
    }

    public int[][] divideGenotype(float dividePointF) {
        int dividePoint = (int) (this.genotype.length *  dividePointF);

        int[] genLeftSide = new int[dividePoint];
        System.arraycopy(this.genotype, 0, genLeftSide, 0, genLeftSide.length);

        int[] genRightSide = new int[this.genotype.length - dividePoint];
        System.arraycopy(this.genotype, dividePoint, genRightSide, 0, genRightSide.length);

        int[][] result = new int[2][];
        result[0] = genLeftSide;
        result[1] = genRightSide;
        return result;
    }

    protected abstract void mutate();

    public int getCurrentGen() {
        return this.currentGen;
    }

    public int[] getGenotypeArray() {
        return this.genotype;
    }
}
