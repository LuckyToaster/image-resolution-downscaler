import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;
import javax.imageio.ImageIO;

public class ImageDownScaler {
	public static void main(String[] args) throws IOException {

		// user input
		Scanner in = new Scanner(System.in);
		System.out.print("Enter image URL: ");
		String url = in.nextLine();
		System.out.print("Enter the new filename: ");
		String fileName = in.nextLine();
		System.out.print("By how much do you want to downscale? [1-100]: ");
		double factor = in.nextInt();
		in.close();
			
		// read the image
		File file = new File(url);
		BufferedImage image = ImageIO.read(file);
			
		// new width & height
		int nw = (int) (image.getWidth() * (factor / 100));
		int nh = (int) (image.getHeight() * (factor / 100));
		int pixelStep = (int) (100 / factor);
		

		// pixel matrix with desired dimensions
		int[][] pixels = new int[nh][nw];

		// fill the pixel matrix with color values from the image
		for (int row=0; row < pixels.length; row++) {
			for (int col=0; col < pixels[row].length; col++) {
				pixels[row][col] = image.getRGB((col * pixelStep), (row * pixelStep));
			}
		}

		// instantiate the output image
		BufferedImage output = new BufferedImage(nw, nh, BufferedImage.TYPE_INT_RGB);

		// add pixels to the output image from the pixel matrix
		for (int row=0; row < pixels.length; row++) {
			for (int col=0; col < pixels[0].length; col++) {
				int value = pixels[row][col];
	               output.setRGB(col, row, value);
			}
		}
			
		// Get directory
		String directory = Paths.get(url).getParent().toString();
		// Determine OS and write the output image
		File outputImage = null;
		if (System.getProperty("os.name").equals("Windows 10")) {
			outputImage = new File(directory + "\\" + fileName);
			ImageIO.write(output, "png", outputImage);
		} else if(System.getProperty("os.name").equals("Linux")) {
			outputImage = new File(directory + "/" + fileName);
			ImageIO.write(output, "png", outputImage);
		} else {
			System.out.println("Cannot determine OS, are you on Windows 10 or Linux?");
		}
		
	}
}


