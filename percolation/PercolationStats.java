import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;


public class PercolationStats {
    // 95% CI Value
    private static final double CONFIDENCE_95 = 1.96;

    // perform independent trials on an n-by-n grid
    private final int t;
    private final double[] open;


    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("Usage: n >= 0, trials >= 0");
        }
        t = trials;
        open = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation p = new Percolation(n);
            while (!p.percolates()) {
                int r;
                int c;
                do {
                    r = StdRandom.uniform(1, n + 1);
                    c = StdRandom.uniform(1, n + 1);
                } while (p.isOpen(r, c));
                p.open(r, c);
                // StdOut.println(p.numberOfOpenSites());
                // StdOut.println(p.percolates());
            }
            // StdOut.println("OUTSIDE WHILE LOOP");
            open[i] = p.numberOfOpenSites() / Math.pow(n, 2);
            // StdOut.println(i);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(open);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(open);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - ((CONFIDENCE_95 * stddev()) / Math.sqrt(t));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + ((CONFIDENCE_95 * stddev()) / Math.sqrt(t));
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        PercolationStats pS = new PercolationStats(n, trials);
        StdOut.println("mean                    = " + pS.mean());
        StdOut.println("stddev                  = " + pS.stddev());
        StdOut.println(
                "95% confidence interval = " + "[" + pS.confidenceLo() + ", " + pS.confidenceHi()
                        + "]");
    }
}
