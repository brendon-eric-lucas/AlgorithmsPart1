import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    // instance variables

    // n x n grid of booleans representing open/closed sites
    private boolean[][] grid;
    // union find data structure used to determine percolation
    private final WeightedQuickUnionUF uf;
    // size (i.e., value of 'n') of grid
    private final int size;
    // keep track of number of open sites
    private int openSites = 0;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Value of n must be > 0");
        }
        size = n;
        // row, col indices are 0 ... size - 1
        grid = new boolean[size][size];
        // 0 ... n^2 - 1 are ids of grid sites; top vs has id n^2; bottom vs has id n^2 + 1
        uf = new WeightedQuickUnionUF((int) Math.pow(size, 2) + 2);
    }

    // get union find id for site (row, col)
    private int getId(int row, int col) {
        return (size * row) + (col - size) - 1;
    }

    // return 'true' if row and/or col value is out of bounds
    private boolean invalidInput(int row, int col) {
        return ((row < 1 || row > size) || (col < 1 || col > size));

    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (invalidInput(row, col)) {
            throw new IllegalArgumentException(
                    "Valid values for row, col are between 1 and " + size);
        }
        if (!grid[row - 1][col - 1]) {

            // open site
            grid[row - 1][col - 1] = true;
            openSites++;

            // get site ids
            int site = getId(row, col);
            int north = getId(row - 1, col);
            int east = getId(row, col - 1);
            int south = getId(row + 1, col);
            int west = getId(row, col + 1);

            if (row == 1) {
                // sites in the top row should be in the cc containing the top vs
                uf.union(site, (int) Math.pow(size, 2));
            }
            else if (row == size) {
                // sites in the bottom row should be in the cc containing the bottom vs
                uf.union(site, (int) Math.pow(size, 2) + 1);
            }
            // west site - (r, c + 1)
            if (col + 1 <= size) {
                if (isOpen(row, col + 1)) {
                    uf.union(site, west);
                }
            }
            // east site - (r, c - 1)
            if (col - 1 >= 1) {
                if (isOpen(row, col - 1)) {
                    uf.union(east, site);
                }
            }
            // south site - (r + 1, c)
            if (row + 1 <= size) {
                if (isOpen(row + 1, col)) {
                    uf.union(south, site);
                }
            }
            // north site - (r - 1, c)
            if (row - 1 >= 1) {
                if (isOpen(row - 1, col)) {
                    uf.union(site, north);
                }
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (invalidInput(row, col)) {
            throw new IllegalArgumentException(
                    "Valid values for row, col are between 1 and " + size);
        }
        return grid[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (invalidInput(row, col)) {
            throw new IllegalArgumentException(
                    "Valid values for row, col are between 1 and " + size);
        }
        else {
            int site = getId(row, col);
            int set = uf.find(site);
            return set == uf.find((int) Math.pow(size, 2));
        }
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        if (size == 1 && numberOfOpenSites() == 1) {
            return true;
        }
        int vst = (int) Math.pow(size, 2);
        int vsb = (int) Math.pow(size, 2) + 1;
        return uf.find(vst) == uf.find(vsb);
    }

    // test client (optional)
    public static void main(String[] args) {
        // tests can be placed here, if desired
    }

}
