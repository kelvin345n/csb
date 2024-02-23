import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int openSitesCount;
    private boolean[][] grid;
    public WeightedQuickUnionUF wQUset;
    private WeightedQuickUnionUF wQUset2;
    private int topIndexQU;
    private int botIndexQU;

    /** creates a boolean grid, size: N by N. And creates a weighted quick union (WQU)
     * of the amount of sites in the grid. For example, in a 5 by 5 grid,
     * 25 items will be in the WQU. And each site in the grid are labeled in
     * numerical order. Row 0 Col 0 -> index 0 in the WQU. Row 0 Col 4 -> index 4
     * Row 1 Col 0 -> index 5 in WQU, so Row 4 Col 4 -> index 24*/
    public Percolation(int N) {
        openSitesCount = 0;
        grid = new boolean[N][N];
        wQUset = new WeightedQuickUnionUF(N*N + 2);
        wQUset2 = new WeightedQuickUnionUF(N*N + 1);
        topIndexQU = N*N;
        botIndexQU = topIndexQU + 1;
    }

    /** Opens a specific site by turning it to true, if that site
     * has not been opened yet and increments the number of open
     * sites by 1 */
    public void open(int row, int col) {
        checkIndexOutOfBounds(row, col);
        if (!isOpen(row, col)){
            grid[row][col] = true;
            openSitesCount++;
            connectIfOpen(row-1, col, convertRowColumn(row, col));
            connectIfOpen(row+1, col, convertRowColumn(row, col));
            connectIfOpen(row, col-1, convertRowColumn(row, col));
            connectIfOpen(row, col+1, convertRowColumn(row, col));
            if (row == 0){
                wQUset.union(topIndexQU, convertRowColumn(row, col));
                wQUset2.union(topIndexQU, convertRowColumn(row, col));

            } else if (row == grid.length-1){
                wQUset.union(botIndexQU, convertRowColumn(row, col));
            }
        }
    }

    private void connectIfOpen(int row, int col, int index){
        if (isValidPosition(row, col)){
            if (isOpen(row, col)){
                wQUset.union(convertRowColumn(row, col), index);
                wQUset2.union(convertRowColumn(row, col), index);
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

    /** Returns true if that site is being percolated from the top
     * else returns false */
    public boolean isFull(int row, int col) {
        checkIndexOutOfBounds(row, col);
        return wQUset2.connected(convertRowColumn(row, col), topIndexQU);
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
        return wQUset.connected(topIndexQU, botIndexQU);
    }
}

