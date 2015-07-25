package com.tecacet.poc.clp;

import org.chocosolver.solver.Solver;
import org.chocosolver.solver.constraints.IntConstraintFactory;
import org.chocosolver.solver.search.strategy.IntStrategyFactory;
import org.chocosolver.solver.trace.Chatterbox;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.solver.variables.VariableFactory;

public class ChocoQueens {

    public static void main(String[] args) {
        int numberOfQueens = 8;
        Solver solver = new Solver();
        IntVar[] queens = new IntVar[numberOfQueens];
        for (int i = 0; i < queens.length; i++) {
            queens[i] = VariableFactory.enumerated("Q_" + i, 1, numberOfQueens, solver);

        }

        solver.post(IntConstraintFactory.alldifferent(queens, "BC"));
        for (int i = 0; i < queens.length; i++) {
            for (int j = i + 1; j < queens.length; j++) {
                int k = j - i;
                solver.post(IntConstraintFactory.arithm(queens[i], "!=", queens[j], "+", -k));
                solver.post(IntConstraintFactory.arithm(queens[i], "!=", queens[j], "+", k));
            }
        }

        solver.set(IntStrategyFactory.minDom_LB(queens));
        long solutionCount = solver.findAllSolutions();
        
        Chatterbox.printStatistics(solver);
        Chatterbox.printSolutions(solver);
    }

}
