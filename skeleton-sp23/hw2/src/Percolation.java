import edu.princeton.cs.algs4.WeightedQuickUnionUF;
// TODO: Add any other necessary imports.

public class Percolation {
    public int openSitesCount;
    public boolean[][] grid;
    public WeightedQuickUnionUF wQUset;

    /** creates a boolean grid, size: N by N. And creates a weighted quick union (WQU)
     * of the amount of sites in the grid. For example, in a 5 by 5 grid,
     * 25 items will be in the WQU. And each site in the grid are labeled in
     * numerical order. Row 0 Col 0 -> index 0 in the WQU. Row 0 Col 4 -> index 4
     * Row 1 Col 0 -> index 5 in WQU, so Row 4 Col 4 -> index 24*/
    public Percolation(int N) {
        openSitesCount = 0;
        grid = new boolean[N][N];
        wQUset = new WeightedQuickUnionUF(N*N);
    }

    /** Opens a specific site by turning it to true, if that site
     * has not been opened yet and increments the number of open
     * sites by 1 */
    public void open(int row, int col) {
        checkIndexOutOfBounds(row, col);
        if (!isOpen(row, col)){
            grid[row][col] = true;
            openSitesCount++;
        }
        if (isValidPosition(row-1, col)){
            if (isOpen(row-1, col)){
                wQUset.union(convertRowColumn(row-1, col), convertRowColumn(row, col));
            }
        }
        if (isValidPosition(row+1, col)){
            if (isOpen(row+1, col)){
                wQUset.union(convertRowColumn(row+1, col), convertRowColumn(row, col));
            }
        }
        if (isValidPosition(row, col-1)){
            if (isOpen(row, col-1)){
                wQUset.union(convertRowColumn(row, col-1), convertRowColumn(row, col));
            }
        }
        if (isValidPosition(row, col+1)){
            if (isOpen(row, col+1)){
                wQUset.union(convertRowColumn(row, col+1), convertRowColumn(row, col));
            }
        }
    }

    private int convertRowColumn(int row, int col){
        return ((row * grid.length) + col);
    }

    private boolean isValidPosition(int row, int col){
        if (row < 0 || row >= grid.length || col < 0 || col >= grid.length){
            return false;
        }
        return true;
    }

    /** if a site is open, then isOpen returns true. If not
     * it returns false */
    public boolean isOpen(int row, int col) {
        checkIndexOutOfBounds(row, col);
        return grid[row][col];
    }

    public boolean isFull(int row, int col) {
        checkIndexOutOfBounds(row, col);
        if (!isOpen(row, col)){
            return false;
        } else {
            for (int i = 0; i < grid.length; i++){
                if (wQUset.connected(convertRowColumn(row, col), i)){
                    return true;
                }
            }
            return false;
        }
    }

    /** checks if the inputs for row and column are valid for
     * the size of the grid */
    private void checkIndexOutOfBounds(int row, int col){
        if (row < 0 || row >= grid.length || col < 0 || col >= grid.length) {
            throw new java.lang.IndexOutOfBoundsException();
        }
    }

    /** Returns the number of open sites in the grid */
    public int numberOfOpenSites() {
        return openSitesCount;
    }

    /** Returns true if there is a path that connects the top of
     * the grid (Row 0) to the bottom (Row N-1) aka the grid
     * "percolates" */
    public boolean percolates() {
        for (int i = 0; i < grid.length; i++){
            for (int j = 0; j < grid.length; j++){
                if (wQUset.connected(i, convertRowColumn(grid.length-1, j))){
                    return true;
                }
            }
        }
        return false;
    }
}
