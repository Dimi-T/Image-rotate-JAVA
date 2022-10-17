package test;

import java.util.Arrays;
import java.util.Scanner;

import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import rotation.Buffer;
import rotation.Consumer;
import rotation.Producer;
import rotation.RotationT;


public class Main {
  private final static Integer[] arr = new Integer[]{90, 180, 270};
  private final static RotationT rot = new RotationT();
  private final static String[] etape = new String[]{
	      "Reading of runtime data was done in  ",
	      "Reading of input file was done in  ",
	      "The image was rotated in  ",
	      "Writing of ouput file was done in "
	    };

  public static void main(String[] args) throws IOException {

    long startTime = System.currentTimeMillis();
    long endTime;
    long[] timeElapsed = new long[etape.length];

    Scanner sc = new Scanner(System.in);

    System.out.println("Selected how much you want the image to be rotated with (degrees): ");
    System.out.println("\t-  90;");
    System.out.println("\t- 180;");
    System.out.println("\t- 270.\n");

    int degrees = sc.nextInt();

    String path_in = "src/input/" + args[0];
    String path_out = "src/output/" + args[1];

    BufferedImage img_in = ImageIO.read(new File(path_in));
    int h = img_in.getHeight();
    int w = img_in.getWidth();

    int[] controller = new int[4];
    for (int i = 0; i < 3; ++i)
      controller[i] = h * (i + 1) / 4;
    controller[3] = h;

    while (!Arrays.asList(arr).contains(degrees)) {
        System.out.println("\nIncorrect input degrees (" + degrees +
        "). Please try again.");
        System.out.println("\nTo exit the program, enter 0.");
        degrees = sc.nextInt();
        if (degrees == 0){
        	System.out.println("\nExiting program.");
        	System.exit(0);
        }
      }

    int amount = degrees / 90;

    endTime = System.currentTimeMillis();
    timeElapsed[0] = endTime - startTime;

    startTime = System.currentTimeMillis();

    Buffer buffer = new Buffer(controller[3], w);
    Producer p = new Producer(buffer, controller, img_in);
    Consumer c = new Consumer(buffer, controller, h, w);
    p.start();
    c.start();
    try {
        p.join();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    try {
        c.join();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

    endTime = System.currentTimeMillis();
    timeElapsed[1] = endTime - startTime;

    startTime = System.currentTimeMillis();
    int[][] matrix_img = null;
    matrix_img = c.get_matrix();

    if (amount % 2 == 1) {
      h = h + w;
      w = h - w;
      h = h - w;
    }

    BufferedImage img_out = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
    int[][] final_matrix = null;

    switch (amount) {
      case 1:
    	final_matrix = rot.rotate_90(matrix_img);
        break;
      case 2:
    	final_matrix = rot.rotate_180(matrix_img);
        break;
      case 3:
    	final_matrix = rot.rotate_270(matrix_img);
        break;
      default:
    	final_matrix = matrix_img;
        break;
    }

    for (int i = 0; i < h; ++i)
      for (int j = 0; j < w; ++j)
        img_out.setRGB(j, i, final_matrix[i][j]);

    System.out.println();

    endTime = System.currentTimeMillis();
    timeElapsed[2] = endTime - startTime;

    startTime = System.currentTimeMillis();

    try {
      ImageIO.write(img_out, "bmp", new File(path_out));
    } catch (IOException e) {
      System.out.println("Error: " + e);
    }

    endTime = System.currentTimeMillis();
    timeElapsed[3] = endTime - startTime;

    for (int i = 0; i < 4; ++i)
      System.out.println(etape[i] + (float)timeElapsed[i]/1000 + " secunde.");

    sc.close();
    sc = null;
    timeElapsed = null;
    for (int i = 0; i < matrix_img.length; ++i)
      matrix_img[i] = null;
    matrix_img = null;
    controller = null;
    for (int i = 0; i < final_matrix.length; ++i)
      final_matrix[i] = null;
    final_matrix = null;
    img_in = null;
    img_out = null;

  }
}
