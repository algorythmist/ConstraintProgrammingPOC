package com.tecacet.poc.clp;

import org.chocosolver.solver.Solver;
import org.chocosolver.solver.constraints.Constraint;
import org.chocosolver.solver.constraints.IntConstraintFactory;
import org.chocosolver.solver.search.strategy.IntStrategyFactory;
import org.chocosolver.solver.trace.Chatterbox;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.solver.variables.VariableFactory;

public class ChocoMagicSeries {

    public static void main(String[] args) {
        int length = 7;
        Solver solver = new Solver();
        IntVar[] vars = new IntVar[length];
        for (int i = 0; i < vars.length; i++) {
            vars[i] = VariableFactory.enumerated("S_" + i, 0, length-1, solver);
        }
        for (int i = 0; i < vars.length; i++) {
            // Number of variables equal to i = vars[i]
//            Constraint c = IntConstraintFactory.count(i, vars, VariableFactory.eq(vars[i]));
            Constraint c = IntConstraintFactory.count(i, vars, vars[i]);
            solver.post(c);
        }
        solver.set(IntStrategyFactory.lexico_UB(vars));
        //solver.set(IntStrategyFactory.minDom_LB(vars));
        long solutionCount = solver.findAllSolutions();
        
        Chatterbox.printStatistics(solver);
        Chatterbox.printSolutions(solver);
    }

}
