package com.tecacet.poc.clp;

import org.junit.Test;

import static org.junit.Assert.*;

public class JacopQueensTest {

    @Test
    public void findAnySolution() {
        JacopQueens queens = new JacopQueens();
        queens.findAnySolution(8);
    }

}