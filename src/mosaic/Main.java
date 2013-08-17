package mosaic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mosaic.imageloader.FolderImageLoader;
import mosaic.imageloader.ImageLoader;
import mosaic.imageloader.ManualImageLoader;

/**
 * OVERVIEW:
 * Generates a series of mosaics with various resolutions (tile sizes) and color palettes. The mosaics are then
 * stitched together into one composite mosaic. See the samples directory for examples.
 * 
 * By default, the colors used in the image will be generated by the ImageBasedColorPaletteGenerator, which uses
 * k-means clustering to select an optimal palette. You will need to include the Java Machine Learning (javaml) JAR
 * that is included with this project. Other palette generators exist, such as the ManualColorPaletteGenerator, 
 * which allows the user to specify the colors to be used. 
 * 
 * BUILDING:
 * To build, execute the command 'ant build', which will create the file 'mosaic.jar'.
 * 
 * RUNNING:
 * To use from the command line:
 * 	java -jar mosaic.jar <imgUrl> <numColors> <widths>
 * 	e.g.: java -jar mosaic.jar https://mysite.com/myimage.png 4,8,12  50x30,100x60,200x120
 * 
 * ENHANCEMENTS:
 * TODO bash script so don't have to type 'java -jar mosaic.jar'. it should also build for you if jar not present.
 * TODO create a config object containing all options to pass to MosaicGenerator
 * TODO expose a composite generator that returns the variations as a two-dimensional array to allow further manipulation
 * TODO Support named arguments on CLI so user can choose which args to specify
 * TODO Allow user to specify the ColorPaletteGenerator from the CLI
 * TODO Print a strip on the composite showing the colors in the palette
 * 
 * SOURCE:
 * https://github.com/alexroussos/Mosaic
 * 
 * @author alexroussos@gmail.com
 *
 */
public class Main {

    private static final ImageLoader DEFAULT_IMAGE_LOADER = new FolderImageLoader("/Users/ryoullar/Desktop/Dropbox/CP in spring/");
    private static final String DEFAULT_OUTPUT_DIRECTORY = "generated_mosaics";
    private static final List<Integer> DEFAULT_COLOR_COUNTS = new ArrayList<Integer>(Arrays.asList(new Integer[] {115}));
    private static final List<Dimensions> DEFAULT_DIMENSIONS = new ArrayList<Dimensions>(Arrays.asList(new Dimensions[] { new Dimensions(-1, 200)}));
    
    /**
     * Can be with no args for default image and settings.
     */
    public static void main(String[] args) throws IOException {
    	List<String> imageLocations = DEFAULT_IMAGE_LOADER.getImageLocations();
    	for (String inputFilename : imageLocations) {
            String outputDirectory = DEFAULT_OUTPUT_DIRECTORY;
            List<Dimensions> dims = DEFAULT_DIMENSIONS;
            List<Integer> colorCounts = DEFAULT_COLOR_COUNTS;
            
            // Override defaults with CLI args if provided
            if (args.length > 0) {
            	try {
    	            inputFilename = args[0];
    	            colorCounts = getArgAsList(args[1]);
    	            dims = getArgAsDimList(args[2]);
            	} catch (Exception e) {
                    System.out.println("Usage is: MosaicGenerator <imgUrl> <numColors> <widths>");
                    System.out.println("e.g.: MosaicGenerator https://mysite.com/myimage.png 4,8,12 50x30,100x60,200x120");
            	}
            }
            
            MosaicGenerator mosaicGenerator = new MosaicGenerator();
            mosaicGenerator.run(inputFilename, outputDirectory, colorCounts, dims);
		}
    	
    	System.out.println("done");
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
    
    /**
     * Parses a compound argument of comma-separated integers into a list of integers.
     * @param args Argument in the form "10,20,30"
     * @return
     */
    private static List<Dimensions> getArgAsDimList(String args) {
    	List<Dimensions> list = new ArrayList<Dimensions>();
    	for (String arg : args.split(",")) {
    		String[] size = arg.split("x");
    		list.add(new Dimensions(Integer.parseInt(size[0]), Integer.parseInt(size[1])));
    	}
    	return list;
    }
}
