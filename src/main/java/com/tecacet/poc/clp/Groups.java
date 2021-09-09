package com.tecacet.poc.clp;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solution;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.variables.IntVar;

import java.util.List;
import java.util.stream.Stream;

public class Groups {

    public static void main(String[] args) {
        int size = 4;
        Model model = new Model("group");
        IntVar[][] grid = buildGrid(model, size);
        applyConnectionConstraints(model, grid);

        // solve it
        Solver solver = model.getSolver();
        while (solver.solve()) {
            System.out.println(new Solution(model).record());
        }
    }

    private static IntVar[][] buildGrid(Model model, int size) {

        IntVar[][] grid = new IntVar[size][size];

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (row == 0) {
                    grid[row][col] = model.intVar(col);
                } else if (col == 0) {
                    grid[row][col] = model.intVar(row);
                } else {
                    grid[row][col] = model.intVar(String.format("[%s.%s]", row, col), 0, size-1);
                }
            }
        }

        return grid;
    }

    private static void applyConnectionConstraints(Model model, IntVar[][] grid) {
        int size = grid.length;
        // all the rows are different
        for (int i = 0; i != size; i++) {
            model.allDifferent(getCellsInRow(grid, i)).post();
            model.allDifferent(getCellsInColumn(grid, i)).post();
        }
    }

    private static IntVar[] getCellsInRow(IntVar[][] grid, int row) {
        return grid[row];
    }

    private static IntVar[] getCellsInColumn(IntVar[][] grid, int column) {
        return Stream.of(grid).map(row -> row[column]).toArray(IntVar[]::new);
    }

    private static void printGrid(IntVar[][] grid) {
        int size = grid.length;
        // add each row to the table
        for (int row = 0; row != size; row++) {
            for (int column = 0; column != size; column++) {
                IntVar variable = grid[row][column];

                // this is the number value for the cell, if we're showing the solution,
                // and this is an original value, we want to wrap it in stars
                System.out.print(variable.getValue());
            }
            System.out.println();
        }
    }
}
