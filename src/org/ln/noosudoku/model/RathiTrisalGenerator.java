package org.ln.noosudoku.model;

/**
 * Generator implementation based on the Rathi/Trisal algorithm for producing Sudoku puzzles.
 * Following is the improved logic for the problem.
 * 1. Fill all the diagonal 3x3 matrices.
 * 2. Fill recursively rest of the non-diagonal matrices.
 * For every cell to be filled, we try all numbers until
 * we find a safe number to be placed.
 * 3. Once matrix is fully filled, remove K elements
 *  randomly to complete game.
 *
 * Article by Aarti_Rathi and Ankur Trisal
 *
 * This code is modified by Susobhan Akhuli
 */
import java.util.concurrent.ThreadLocalRandom;

public class RathiTrisalGenerator implements SudokuGenerator{

    private int[][] puzzle;
    private int grid; // number of columns/rows.
    private int squareRoot; // square root of grid

    /**
     * Creates a generator using the default 9x9 grid with no removals specified.
     */
    public RathiTrisalGenerator(){
	this(9, 0);
    }

    /**
     * Creates a generator for the specified grid size and missing cell count.
     *
     * @param n overall grid dimension
     * @param k number of cells to remove (currently unused)
     */
    public RathiTrisalGenerator(int n, int k){
        this.grid = n;
        //this.missing = k;

        // Compute square root of grid
        Double squareDouble = Math.sqrt(grid);
        squareRoot = squareDouble.intValue();
        puzzle = new int[grid][grid];
        fillValues();
    }

    /**
     * Initializes the puzzle matrix with a valid Sudoku solution.
     */
    public void fillValues() {
        // Fill the diagonal of squareRoot x squareRoot matrices
        fillDiagonal();

        // Fill remaining blocks
        fillRemaining(0, squareRoot);
     }

    // Fill the diagonal squareRoot number of squareRoot x squareRoot matrices
    void fillDiagonal(){
        for (int i = 0; i<grid; i=i+squareRoot) {
			// for diagonal box, start coordinates->i==j
            fillBox(i, i);
		}
    }

    // Returns false if given 3 x 3 block contains num.
    boolean unUsedInBox(int rowStart, int colStart, int num){
        for (int i = 0; i<squareRoot; i++) {
			for (int j = 0; j<squareRoot; j++) {
				if (puzzle[rowStart+i][colStart+j]==num) {
					return false;
				}
			}
		}

        return true;
    }

    // Fill a 3 x 3 matrix.
    void fillBox(int row,int col){
        int num;
        for (int i=0; i<squareRoot; i++){
            for (int j=0; j<squareRoot; j++){
                do{
                    num = randomGenerator(grid);
                }
                while (!unUsedInBox(row, col, num));
                puzzle[row+i][col+j] = num;
            }
        }
    }

    // Random generator
    int randomGenerator(int num){
        return ThreadLocalRandom.current().nextInt(1, num + 1);
    }

    // Check if safe to put in cell
    boolean CheckIfSafe(int i, int j, int num){
        return (unUsedInRow(i, num) &&
                unUsedInCol(j, num) &&
                unUsedInBox(i-i%squareRoot, j-j%squareRoot, num));
    }

    // check in the row for existence
    boolean unUsedInRow(int i,int num){
        for (int j = 0; j<grid; j++) {
			if (puzzle[i][j] == num) {
				return false;
			}
		}
        return true;
    }

    // check in the col for existence
    boolean unUsedInCol(int j,int num){
        for (int i = 0; i<grid; i++) {
			if (puzzle[i][j] == num) {
				return false;
			}
		}
        return true;
    }

    // A recursive function to fill remaining
    // matrix
    boolean fillRemaining(int i, int j) {
        //  System.out.println(i+" "+j);
                // Wrap to the next row once the column index reaches the grid width
        if (j>=grid && i<grid-1){
            i = i + 1;
            j = 0;
        }
        if (i>=grid && j>=grid) {
			return true;
		}

        if (i < squareRoot){
            if (j < squareRoot) {
				j = squareRoot;
			}
        }
        else if (i < grid-squareRoot){
            if (j==i/squareRoot*squareRoot) {
                                // Skip over columns already covered by filled subgrids
                                j =  j + squareRoot;
                        }
        }
        else{
            if (j == grid-squareRoot){
                // Transition into the final block of rows once subgrid columns are filled
                i = i + 1;
                j = 0;
                if (i>=grid) {
                                        return true;
                                }
            }
        }

        for (int num = 1; num<=grid; num++){
            if (CheckIfSafe(i, j, num)){
                puzzle[i][j] = num;
                if (fillRemaining(i, j+1)) {
					return true;
				}

                puzzle[i][j] = 0;
            }
        }
        return false;
    }

	/**
	 * Returns the generated puzzle grid for consumption by the UI.
	 */
	@Override
	public int[][] getPuzzle() {
		return puzzle;
	}

    /**
     * Outputs the generated puzzle to standard out for debugging.
     */
    public void printSudoku(){
        for (int i = 0; i<grid; i++){
            for (int j = 0; j<grid; j++) {
				System.out.print(puzzle[i][j] + " ");
			}
            System.out.println();
        }
        System.out.println();
    }


	// Driver code - for debug
    /**
     * Convenience entry point for manual verification of generated boards.
     *
     * @param args standard command line arguments (unused)
     */
    public static void main(String[] args){
       RathiTrisalGenerator sudoku = new RathiTrisalGenerator();
       sudoku.printSudoku();
    }


}