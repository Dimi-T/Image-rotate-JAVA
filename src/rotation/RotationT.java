package rotation;

public class RotationT extends RotationO {
	public int[][] rotate_270(int[][] matrix, int... dim) {

	    int h, w;

	    try {
	      h = dim[0];
	      w = dim[1];
	    } catch (Exception e) {
	      h = matrix.length;
	      w = matrix[0].length;
	    }

	    int[][] rotated_matrix = new int[w][h];

	    for (int i=0; i<h; i++)
		   for (int j=0;j<w; j++)
	    	   rotated_matrix [j][i] = matrix[h-i-1][j];														

	    return rotated_matrix;

	  }
}
