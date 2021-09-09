package com.tecacet.poc.clp;

import org.chocosolver.solver.Solution;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class GroupFinderTest {

    @Test
    public void findGroups3() {
        GroupFinder groupFinder = new GroupFinder();
        List<Solution> solutions = groupFinder.findGroups(3);
        for (Solution solution : solutions) {
            System.out.println(solution);
        }
    }
}