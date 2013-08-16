package mosaic;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;

import palettegenerator.ColorPaletteGenerator;
import palettegenerator.ImageBasedColorPaletteGenerator;

/**
 * Generates a series of mosaics with various resolutions (tile sizes) and color palettes. The mosaics are then
 * stitched together into one composite mosaic.
 * 
 * By default, the colors used in the image will be generated by the ImageBasedColorPaletteGenerator, which uses
 * k-means clustering to select an optimal palette. You will need to include the Java Machine Learning (javaml) JAR
 * that is included with this project. Other palette generators exist, such as the ManualColorPaletteGenerator, 
 * which allows the user to specify the colors to be used. 
 * 
 * To use from the command line:
 * 	MosaicGenerator <imgUrl> <numColors> <widths>
 * 	e.g.: MosaicGenerator https://mysite.com/myimage.png 4,8,12  50,100,200
 * 
 * Future enhancements:
 * TODO Support named arguments on CLI so user can choose which args to specify
 * TODO Allow user to specify the ColorPaletteGenerator from the CLI
 * TODO Print a strip on the composite showing the colors in the palette
 * TODO Better output filename - maybe include input filename eg mosaic-IMG_1913.png
 * 
 * @author alexroussos@gmail.com
 *
 */
public class MosaicGenerator {

    private static final String DEFAULT_IMAGE = "https://dl.dropboxusercontent.com/u/65411942/IMG_1913.JPG";
    private static final String DEFAULT_OUTPUT_DIRECTORY = System.getProperty("user.home");
    private static final List<Integer> DEFAULT_COLOR_COUNTS = new ArrayList<Integer>(Arrays.asList(new Integer[] { 6, 12, 24 }));
    private static final List<Integer> DEFAULT_WIDTHS = new ArrayList<Integer>(Arrays.asList(new Integer[] { 50, 100, 200 }));
    
    /**
     * Can be with no args for default image and settings.
     */
    public static void main(String[] args) throws IOException {
        String inputFilename = DEFAULT_IMAGE;
        String outputDirectory = DEFAULT_OUTPUT_DIRECTORY;
        List<Integer> widths = DEFAULT_WIDTHS;
        List<Integer> colorCounts = DEFAULT_COLOR_COUNTS;
        
        // Override defaults with CLI args if provided
        if (args.length > 0) {
        	try {
	            inputFilename = args[0];
	            colorCounts = getArgAsList(args[1]);
	            widths = getArgAsList(args[2]);
        	} catch (Exception e) {
                System.out.println("Usage is: MosaicGenerator <imgUrl> <numColors> <widths>");
                System.out.println("e.g.: MosaicGenerator https://mysite.com/myimage.png 4,8,12 50,100,200");
        	}
        }
        
        MosaicGenerator mosaicGenerator = new MosaicGenerator();
        mosaicGenerator.run(inputFilename, outputDirectory, colorCounts, widths);
    }

    /**
     * Generate a composite of mosaics based on different color palettes and resolutions.
     * 
     * NOTE: Depending on where images are hosted, they may be unreadable by ImageIO. Images hosted on Dropbox seem to work fine.
     * 
     * @param inputFilename URL of image to be transformed into mosaic.
     * @param outputDirectory Directory where composite image is to be saved. Defaults to user's home dir.
     * @param colorCounts Number of colors to use in each mosaic (corresponds to rows in composite image) eg {8, 12, 24}
     * @param widths The widths of each mosaic in pixels (corresponds to columns in composite image), eg {50, 100, 200}
     * @throws MalformedURLException
     * @throws IOException
     */
    private void run(String inputFilename, String outputDirectory, List<Integer> colorCounts, List<Integer> widths) throws MalformedURLException, IOException {
        BufferedImage rawImage = ImageIO.read(new URL(inputFilename));
        BufferedImage compositeImage = generateCompositeMosaic(rawImage, colorCounts, widths, new ImageBasedColorPaletteGenerator(rawImage));
        String outputFilename = "mosaic-" + System.currentTimeMillis() + ".png";
        ImageIO.write(compositeImage, "png", new File(outputDirectory, outputFilename));
    }
    
    /**
     * Create a large image containing a mosaic for each permutation of number of colors and resolution.
     * The smaller mosaics will be scaled up so that all are the same width as the largest.
     */
    private BufferedImage generateCompositeMosaic(BufferedImage sourceImage, List<Integer> colorCounts, List<Integer> widths, ColorPaletteGenerator paletteGenerator) {
        int labelOffsetY = 10;
        int cellWidth = Collections.max(widths);
        int cellHeight = cellWidth * sourceImage.getHeight() / sourceImage.getWidth();
        int containerWidth = cellWidth * widths.size();
        int containerHeight = cellHeight * colorCounts.size();
        BufferedImage outImage = new BufferedImage(containerWidth, containerHeight, sourceImage.getType());
        Graphics graphics = outImage.getGraphics();

        int destX1 = 0, destY1 = 0, destX2 = cellWidth, destY2 = cellHeight, srcX1 = 0, srcY1 = 0, srcX2 = 0, srcY2 = 0;

        for (Integer colorCount : colorCounts) {
            destX1 = 0;
            List<Color> colors = paletteGenerator.generateColorPalette(colorCount);
            for (Integer width : widths) {
                BufferedImage tileImage = colorImage(scaleImage(sourceImage, width), colors);
                srcX2 = tileImage.getWidth();
                srcY2 = tileImage.getHeight();
                graphics.drawImage(tileImage, destX1, destY1, destX2, destY2, srcX1, srcY1, srcX2, srcY2, null);
                graphics.drawString(srcX2 + "x" + srcY2 + "-" + colorCount + "color", destX1, destY1 + labelOffsetY);
                destX1 = destX2;
                destX2 += cellWidth;
            }
            destY1 = destY2;
            destY2 += cellHeight;
            destX2 = cellWidth;
        }

        return outImage;
    }

    /**
     * Scale an image down to the specified width, maintaining the aspect ratio.
     */
    private BufferedImage scaleImage(BufferedImage image, int targetWidth) {
        double scaleFactor = ((double) targetWidth) / image.getWidth();
        AffineTransform scaleTrans = AffineTransform.getScaleInstance(scaleFactor, scaleFactor);
        AffineTransformOp scaleOp = new AffineTransformOp(scaleTrans, AffineTransformOp.TYPE_BILINEAR);
        int scaledWidth = (int) (image.getWidth() * scaleFactor);
        int scaledHeight = (int) (image.getHeight() * scaleFactor);
        BufferedImage scaledImage = scaleOp.filter(image, new BufferedImage(scaledWidth, scaledHeight, image.getType()));
        return scaledImage;
    }

    /**
     * Generate an image containing only the colors in the specified palette.
     */
    private BufferedImage colorImage(BufferedImage image, List<Color> availableColors) {
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                Color c = new Color(image.getRGB(x, y));
                image.setRGB(x, y, getNearestColor(c, availableColors).getRGB());
            }
        }
        return image;
    }

    /**
     * Find the closest color in the palette to the specified target color.
     */
    private Color getNearestColor(Color targetColor, List<Color> availableColors) {
        Color closestColor = null;
        int minDistance = Integer.MAX_VALUE;
        for (Color color : availableColors) {
            int distance = getDistance(targetColor, color);
            if (distance < minDistance) {
                closestColor = color;
                minDistance = distance;
            }
        }
        return closestColor;
    }

    /**
     * Find the Euclidean distance between two colors.
     */
    private int getDistance(Color c1, Color c2) {
        int distance = (int) Math.sqrt((double) Math.pow(c2.getRed() - c1.getRed(), 2) + Math.pow(c2.getGreen() - c1.getGreen(), 2) + Math.pow(c2.getBlue() - c1.getBlue(), 2));
        return distance;
    }
    
    /**
     * Parses a compound argument of comma-separated integers into a list of integers.
     * @param args Argument in the form "10,20,30"
     * @return
     */
    private static List<Integer> getArgAsList(String args) {
    	List<Integer> list = new ArrayList<Integer>();
    	for (String arg : args.split(",")) {
    		list.add(Integer.parseInt(arg));
    	}
    	return list;
    }
}