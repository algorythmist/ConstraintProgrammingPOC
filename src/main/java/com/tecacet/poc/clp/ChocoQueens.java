package com.tecacet.poc.clp;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solution;
import org.chocosolver.solver.variables.IntVar;

public class ChocoQueens {

    public class QueensSolution {

        Solution solution;
        IntVar[] queens;

        public QueensSolution(Solution solution, IntVar[] queens) {
            this.solution = solution;
            this.queens = queens;
        }
    }

    public QueensSolution findAnySolution(int numberOfQueens) {
        Model model = new Model(numberOfQueens + " model");
        IntVar[] queens = new IntVar[numberOfQueens];
        //set range of each variable
        for (int i = 0; i < queens.length; i++) {
            queens[i] = model.intVar("Q_" + i, 1, numberOfQueens);
        }
        applyConstraints(model, queens);
        return new QueensSolution(model.getSolver().findSolution(), queens);
    }

    private void applyConstraints(Model model, IntVar[] queens) {
        //exclude queens on the same vertical
        model.allDifferent(queens).post();
        //exclude queens on the diagonals
        for (int i = 0; i < queens.length; i++) {
            for (int j = i + 1; j < queens.length; j++) {
                int k = j - i;
                model.arithm(queens[i], "!=", queens[j]).post();
                model.arithm(queens[i], "!=", queens[j], "+", -k).post();
                model.arithm(queens[i], "!=", queens[j], "+", k).post();
            }
        }

    }

    public static void plotQueens(Solution solution, IntVar[] queens) {
        for (int i = 0; i < 8; i++) {
            int q = solution.getIntVal(queens[i]);
            for (int j = 0; j < 8; j++) {
                if (q == j) {
                    System.out.print("|Q");
                } else {
                    System.out.print("| ");
                }
            }
            System.out.println("|");
        }
    }

}
