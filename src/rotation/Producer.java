package rotation;

import java.awt.image.BufferedImage;

public class Producer extends Thread {
  private Buffer buffer;
  private int[] controller;
  private BufferedImage img;

  public Producer(Buffer buffer, int[] controller, BufferedImage img) {
    this.buffer = buffer;
    this.controller = new int[4];
    this.controller = controller;
    this.img = img;
  }

  public void run() {

    int w = img.getWidth();
    int[][] trd_quarters  = new int[controller[0]][w];
    int[][] fth_quarter = new int[controller[3] - controller[2] - 1][w];

    for(int i = 0; i < 3; ++i) {
      for (int j = 0; j < controller[0]; ++j)
        for (int k = 0; k < w; ++k) {
          trd_quarters[j][k] = img.getRGB(k, j + controller[i] - controller[0]);
        }
      buffer.put(trd_quarters);
      System.out.println("Producer a incarcat un sfert din informatie.");
      try {
        sleep(1000);
      } catch (InterruptedException e) {}
    }

    for (int i = 0; i < controller[3] - controller[2] - 1; ++i)
      for (int j = 0; j < w; ++j)
        fth_quarter[i][j] = img.getRGB(j, i + controller[2]);
    System.out.println("Producer a incarcat ultimul sfert din informatie.");    
    try {
      sleep(1000);
    } catch (InterruptedException e) {}
  }
}
