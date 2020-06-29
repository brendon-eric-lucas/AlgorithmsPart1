# AlgorithmsPart1
Solutions to Princeton's Algorithms Part 1 on Coursera

Percolation - This solution suffers from the problem of 'backwash', which is an artefact of the use of virtual nodes to model connections between sites in the top
and bottom rows. Use of virtual nodes results in an algorithm which erroneously classifies certain sites as "full" (i.e., as connected via a path of open sites to
sites in the top row). Because the test condition for detecting whether a system percolates does not make reference to whether a site is full, this defect impacts
only the visualization of the algorithm (and only at the last step). Thus, the algorithm is technically correct, even though the accompanying visualization is 
slightly defective. 
