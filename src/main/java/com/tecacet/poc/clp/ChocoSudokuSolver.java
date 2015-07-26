package com.tecacet.poc.clp;

import org.chocosolver.solver.Solver;
import org.chocosolver.solver.constraints.IntConstraintFactory;
import org.chocosolver.solver.search.solution.Solution;
import org.chocosolver.solver.search.strategy.IntStrategyFactory;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.solver.variables.VariableFactory;
import org.chocosolver.util.tools.ArrayUtils;

public class ChocoSudokuSolver {

    private static final int size = 9;

    private static int[][] problem = { 
            { 0, 1, 0, 4, 2, 0, 0, 0, 5 }, 
            { 0, 0, 2, 0, 7, 1, 0, 3, 9 }, 
            { 0, 0, 0, 0, 0, 0, 0, 4, 0 },
            { 2, 0, 7, 1, 0, 0, 0, 0, 6 }, 
            { 0, 0, 0, 0, 4, 0, 0, 0, 0 }, 
            { 6, 0, 0, 0, 0, 7, 4, 0, 3 }, 
            { 0, 7, 0, 0, 0, 0, 0, 0, 0 },
            { 1, 2, 0, 7, 3, 0, 5, 0, 0 }, 
            { 3, 0, 0, 0, 8, 2, 0, 7, 0 } 
    };

    public static void main(String[] args) {
        IntVar[][] rows = new IntVar[size][size];
        IntVar[][] cols = new IntVar[size][size];

        Solver solver = new Solver("Sudoku");
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (problem[i][j] > 0) {
                    rows[i][j] = VariableFactory.fixed(problem[i][j], solver);
                } else {
                    rows[i][j] = VariableFactory.enumerated("c_" + i + "_" + j, 1, size, solver);
                }
                cols[j][i] = rows[i][j]; //simple references to existing variables
            }
        }
        IntVar[][] squares = new IntVar[size][size];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {
                    squares[j + k * 3][i] = rows[k * 3][i + j * 3];
                    squares[j + k * 3][i + 3] = rows[1 + k * 3][i + j * 3];
                    squares[j + k * 3][i + 6] = rows[2 + k * 3][i + j * 3];
                }
            }
        }

        for (int i = 0; i < size; i++) {
            solver.post(IntConstraintFactory.alldifferent(rows[i], "AC"));
            solver.post(IntConstraintFactory.alldifferent(cols[i], "AC"));
            solver.post(IntConstraintFactory.alldifferent(squares[i], "AC"));
        }

        solver.set(IntStrategyFactory.minDom_LB(ArrayUtils.append(rows)));
        solver.findSolution();
        Solution solution = solver.getSolutionRecorder().getLastSolution();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int n = solution.getIntVal(rows[i][j]);
                System.out.print(n+" ");
            }
            System.out.println("");
        }
    }
}
