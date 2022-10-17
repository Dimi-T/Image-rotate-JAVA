package rotation;

public class Buffer extends Container {
  private int[][] pixel;
  private boolean available = false;

  public Buffer(int... size) {
    pixel = new int[size[0]][size[1]];
  }


  public synchronized int[][] get(int h1, int h2, int w) {
    while (!available) {
      try {
        wait();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    available = false;
    notifyAll();

    int[][] cur_pixel = new int[h2 - h1][w];
    for (int i = 0; i < h2 - h1; ++i)
      for(int j = 0; j < w; ++j)
    	  cur_pixel[i][j] = pixel[i][j];
    return cur_pixel;
  }

  public synchronized void put(int pixel[][]) {
    while (available) {
      try {
        wait();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    this.pixel = new int[pixel.length][pixel[0].length];
    this.pixel = pixel;
    available = true;                                                           
    notifyAll();
  }

}
