package com.tecacet.poc.clp;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solution;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.variables.IntVar;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class GroupFinder {

    public List<Solution> findGroups(int order) {
        Model model = new Model("group");
        IntVar[][] grid = buildGrid(model, order);
        applyConstraints(model, grid);

        // solve it
        Solver solver = model.getSolver();

        List<Solution> solutions = new ArrayList<>();
        while (solver.solve()) {
            Solution solution = new Solution(model).record();
            solutions.add(solution);
        }
        return solutions;
    }


    private IntVar[][] buildGrid(Model model, int size) {
        IntVar[][] grid = new IntVar[size][size];
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (row == 0) {
                    grid[row][col] = model.intVar(name(row, col), col);
                } else if (col == 0) {
                    grid[row][col] = model.intVar(name(row, col), row);
                } else {
                    grid[row][col] = model.intVar(name(row, col), 0, size-1);
                }
            }
        }
        return grid;
    }

    private String name(int row, int column) {
        return String.format("[%s.%s]", row, column);
    }

    private static void applyConstraints(Model model, IntVar[][] grid) {
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

    private static void printSolution(Solution solution, IntVar[][] grid) {
        int size = grid.length;

        for (int row = 0; row != size; row++) {
            for (int column = 0; column != size; column++) {
                IntVar variable = grid[row][column];
                System.out.print(variable.getValue());
            }
            System.out.println();
        }
    }
}
