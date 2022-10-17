package rotation;

public class RotationO extends RotationN {
  public int[][] rotate_180(int[][] matrix, int... dim) {

        int h, w;

        try {
          h = dim[0];
          w = dim[1];
        } catch (Exception e) {
          h = matrix.length;
          w = matrix[0].length;
        }

        int[][] rotated_matrix = new int[h][w];

        for (int i=0; i<h; i++)
          for (int j=0;j<w; j++)
		   	   rotated_matrix [i][j] = matrix[h-i-1][w-1-j];                        
        return rotated_matrix;
  }
}
