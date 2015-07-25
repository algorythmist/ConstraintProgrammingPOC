package com.tecacet.poc.clp;

import org.jacop.constraints.Alldiff;
import org.jacop.constraints.XneqY;
import org.jacop.constraints.XplusCeqZ;
import org.jacop.core.IntVar;
import org.jacop.core.Store;
import org.jacop.search.DepthFirstSearch;
import org.jacop.search.IndomainMiddle;
import org.jacop.search.Search;
import org.jacop.search.SelectChoicePoint;
import org.jacop.search.SimpleSelect;
import org.jacop.search.SmallestDomain;

public class JacopQueens {

    public static void main(String[] args) {
        int numberOfQueens = 8;
        Store store = new Store();
        IntVar[] queens = new IntVar[numberOfQueens];

        // Each queen variable has a domain from 1 to numberQ
        // Value of queen variable represents the row
        for (int i = 0; i < numberOfQueens; i++) {
            queens[i] = new IntVar(store, "Q" + (i + 1), 1, numberOfQueens);
        }
        store.impose(new Alldiff(queens));
            
        for (int i = 0; i < queens.length; i++) {
            for (int j = i + 1; j < queens.length; j++) {

                // Temporary variable denotes the chessboard
                // field in j-th column which is checked by
                // i-th column queen
                // If temporarty variable has value outside
                // range 1..numberQ then i-th column queen
                // does not check any field in j-th column

                // Checking diagonals like this \
                // Note that C constant is positive
                IntVar temporary = new IntVar(store, -2 * numberOfQueens, 2 * numberOfQueens);
                store.impose(new XplusCeqZ(queens[j], j - i, temporary));
                store.impose(new XneqY(queens[i], temporary));

                // Checking diagonals like this /
                // Note that C constant is negative
                temporary = new IntVar(store, -2 * numberOfQueens, 2 * numberOfQueens);
                store.impose(new XplusCeqZ(queens[j], -(j - i), temporary));
                store.impose(new XneqY(queens[i], temporary));
            }
        }
        
        Search<IntVar> search = new DepthFirstSearch<IntVar>();
        search.getSolutionListener().searchAll(true);
        search.getSolutionListener().recordSolutions(true);
        SelectChoicePoint<IntVar> select = new SimpleSelect<IntVar>(queens,
                new SmallestDomain<IntVar>(),
                new IndomainMiddle<IntVar>());
        
        boolean result = search.labeling(store, select);
        search.printAllSolutions();
        
    }
}
