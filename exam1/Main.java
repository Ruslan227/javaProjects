import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static class SudokuSolver {

        private int[][] table;

        SudokuSolver() {
            table = new int[9][9];
        }

        public void Solve() {
            if (Rsolve(table))
                printTable(table);
            else
                System.out.println("No solution");
        }

        private boolean Rsolve(int[][] table) {
            ArrayList<Integer> listOfPossibleValues;
            ArrayList<Integer> minOfPossibleValues;
            int minRow;
            int minClm;
            do {
                minOfPossibleValues = new ArrayList<>();
                minRow = 0;
                minClm = 0;
                for (int row = 0; row < 9; row++) {
                    for (int clm = 0; clm < 9; clm++) {
                        if (table[row][clm] != 0)
                            continue;
                        listOfPossibleValues = TryToFindValues(table, row, clm);
                        if (listOfPossibleValues.size() == 0)
                            return false;
                        if (listOfPossibleValues.size() == 1)
                            table[row][clm] = listOfPossibleValues.get(0);
                        if (minOfPossibleValues.isEmpty() || minOfPossibleValues.size() > listOfPossibleValues.size()) {
                            minOfPossibleValues = listOfPossibleValues;
                            minRow = row;
                            minClm = clm;
                        }
                    }
                }
                if (minOfPossibleValues.isEmpty())
                    return true;
            } while (minOfPossibleValues.size() <= 1);

            for (int e : minOfPossibleValues) {
                int[][] new_table = new int[9][9];
                for (int i = 0; i < 9; i++)
                    System.arraycopy(table[i], 0, new_table[i], 0, 9);
                new_table[minRow][minClm] = e;
                if (Rsolve(new_table)) {
                    for (int i = 0; i < 9; i++)
                        System.arraycopy(new_table[i], 0, table[i], 0, 9);
                    return true;
                }
            }
            return false;
        }


        private void findDifference(ArrayList<Integer> listOfValues, ArrayList<Integer> temp) {
            for (int e : temp)
                if (listOfValues.contains(e))
                    listOfValues.remove((Integer) e);
        }

        private ArrayList<Integer> TryToFindValues(int[][] table_, int i, int j) {
            ArrayList<Integer> listOfValues = new ArrayList<>();
            for (int e = 0; e < 9; e++)
                listOfValues.add(e + 1);
            findDifference(listOfValues, getRow(table_, j));
            findDifference(listOfValues, getCol(table_, i));
            findDifference(listOfValues, getBlock(table_, i, j));
            return listOfValues;
        }

        private ArrayList<Integer> getBlock(int[][] table_, int i, int j) {
            ArrayList<Integer> temp = new ArrayList<>();
            int blockRow = 3 * (i / 3);
            int blockClm = 3 * (j / 3);
            for (int row = blockRow; row < blockRow + 3; row++)
                for (int clm = blockClm; clm < blockClm + 3; clm++)
                    temp.add(table_[row][clm]);
            return temp;
        }

        private ArrayList<Integer> getCol(int[][] table_, int i) {
            ArrayList<Integer> temp = new ArrayList<>();
            for (int clm = 0; clm < 9; clm++)
                temp.add(table_[i][clm]);
            return temp;
        }

        private ArrayList<Integer> getRow(int[][] table_, int j) {
            ArrayList<Integer> temp = new ArrayList<>();
            for (int row = 0; row < 9; row++)
                temp.add(table_[row][j]);
            return temp;
        }

        public void readTable() {
            Scanner scanner = new Scanner(System.in);
            for (int i = 0; i < 9; i++)
                for (int j = 0; j < 9; j++)
                    if (scanner.hasNextInt())
                        this.table[i][j] = scanner.nextInt();
        }

        public void printTable(int[][] table) {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++)
                    System.out.print(table[i][j]);
                System.out.println();
            }
            System.out.println();
        }

    }

    public static void main(String[] args) {
        SudokuSolver ss = new SudokuSolver();
        ss.readTable();
        ss.Solve();
    }
}


/*
6 0 0 1 5 7 0 0 0
3 0 0 2 0 4 0 9 0
0 1 0 0 0 6 0 4 0
2 6 0 0 1 0 8 0 3
5 0 0 0 0 0 9 2 4
0 0 3 9 0 0 0 0 5
1 3 0 6 0 2 0 0 0
9 4 6 8 3 1 7 0 0
7 0 0 0 4 9 0 1 0
 */