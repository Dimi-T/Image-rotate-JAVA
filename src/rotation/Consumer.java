package rotation;

public class Consumer extends Thread {
  private Buffer buffer;
  private int[] controller;
  private int[][] matrix;

  public Consumer(Buffer buffer, int[] controller, int h, int w) {
    this.matrix = new int[h][w];
    this.buffer = buffer;
    this.controller = new int[4];
    this.controller = controller;
  }

  public void run() {

    int w = matrix[0].length;
    int[][] trd_quarters  = new int[controller[0]][w];
    int[][] fth_quarter = new int[controller[3] - controller[2] - 1][w];

    for (int i = 0; i < 3; ++i) {
      trd_quarters = buffer.get(controller[i] - controller[0], controller[i], w);
        for (int k = 0; k < w; ++k)
          matrix[j + controller[i] - controller[0]][k] = trd_quarters[j][k];
      System.out.println("Consumer a primit un sfert din informatie.\n");
      try {
        sleep(1000);
      } catch (InterruptedException e) {}
    }

    fth_quarter = buffer.get(controller[2] + 1, controller[3], w);
      for (int j = 0; j < w; ++j)
        matrix[i + controller[2]][j] = fth_quarter[i][j];
    System.out.println("Consumer a primit ultimul sfert din informatie.\n");
    try {
      sleep(1000);
    } catch (InterruptedException e) {}
  }

  public int[][] get_matrix()                                                   
  {
    return this.matrix;
  }
}
