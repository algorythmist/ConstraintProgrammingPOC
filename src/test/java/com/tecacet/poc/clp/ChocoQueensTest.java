package com.tecacet.poc.clp;


import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ChocoQueensTest {

    @Test
    public void findAnySolution() {
        ChocoQueens queens = new ChocoQueens();
        ChocoQueens.QueensSolution solution = queens.findAnySolution(8);
        assertEquals("Solution: Q_0=7, Q_1=4, Q_2=2, Q_3=5, Q_4=8, Q_5=1, Q_6=3, Q_7=6, ",
                solution.solution.toString());
        ChocoQueens.plotQueens(solution.solution, solution.queens);
    }
}