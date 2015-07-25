package com.tecacet.poc.clp;


import org.jacop.constraints.Constraint;
import org.jacop.constraints.Count;
import org.jacop.core.IntVar;
import org.jacop.core.Store;
import org.jacop.search.DepthFirstSearch;
import org.jacop.search.IndomainMiddle;
import org.jacop.search.Search;
import org.jacop.search.SelectChoicePoint;
import org.jacop.search.SimpleSelect;
import org.jacop.search.SmallestDomain;

public class JacopMagicSeries {

    public static void main(String[] args) {
        
        int length = 7;
        Store store = new Store();
        IntVar[] vars = new IntVar[length];
        for (int i = 0; i < vars.length; i++) {
            vars[i] = new IntVar(store, "S" + i, 0, length);
         
        }
        for (int i = 0; i < vars.length; i++) {
           Constraint c = new Count(vars, vars[i], i);
           store.impose(c);
            
        }
        Search<IntVar> search = new DepthFirstSearch<IntVar>();
        search.getSolutionListener().searchAll(true);
        search.getSolutionListener().recordSolutions(true);
        SelectChoicePoint<IntVar> select = new SimpleSelect<IntVar>(vars,
                new SmallestDomain<IntVar>(),
                new IndomainMiddle<IntVar>());
        
        boolean result = search.labeling(store, select);
        search.printAllSolutions();
    }
}
