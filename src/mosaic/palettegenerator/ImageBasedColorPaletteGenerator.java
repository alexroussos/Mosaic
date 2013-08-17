package mosaic.palettegenerator;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import net.sf.javaml.clustering.KMeans;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.DefaultDataset;
import net.sf.javaml.core.Instance;
import net.sf.javaml.core.SparseInstance;
import net.sf.javaml.tools.DatasetTools;

/**
 * Generate a color palette of n colors based on the colors found in the image. This uses the Java Machine Learning
 * library (javaml) to perform k-means clustering (generating n clusters of similar colors), and then chooses the colors 
 * from the centroid of each cluster.
 * 
 * @author alexroussos@gmail.com
 *
 */
public class ImageBasedColorPaletteGenerator implements ColorPaletteGenerator {
	
	private static final int KMEANS_ITERATIONS = 3;
	private static final int NUM_PIXELS_TO_SAMPLE = 1000;
	private final BufferedImage image;
	
	public ImageBasedColorPaletteGenerator(BufferedImage image) {
		this.image = image;
	}
	
	/**
	 * Takes a random sampling of pixels from the image, clusters them based on color (as many clusters as desired colors)
	 * and then generates a palate based on those clusters.
	 */
	@Override
	public Map<String, Color> generateColorPalette(int numColors) {
		Map<String, Color> colors = new LinkedHashMap<String, Color>();
        Random r = new Random();
        int height = image.getHeight();
        int width = image.getWidth();
    	KMeans kmeans = new KMeans(numColors, KMEANS_ITERATIONS);
    	Dataset data = new DefaultDataset();
    	
    	// Take a random sampling of pixels from the image to use in clustering
    	for (int i = 0; i < NUM_PIXELS_TO_SAMPLE; i++) {
    		int rgb = image.getRGB(r.nextInt(width), r.nextInt(height));
    		Color c = new Color(rgb);
    		double[] vector = new double[] {c.getRed(), c.getGreen(), c.getBlue()};
        	Instance instance = new SparseInstance(vector);
        	data.add(instance);
    	}
    	
    	// Peform the clustering
    	Dataset[] clusters = kmeans.cluster(data);
    	
    	// Add a color to the palette for each cluster
    	for (Dataset cluster : clusters) {
    		Color c = getColorFromCluster(cluster);
    		colors.put(Integer.toHexString(c.getRed()) + Integer.toHexString(c.getBlue()) + Integer.toHexString(c.getGreen()), c);
    	}
    	return colors;
	}
    
	/*
	 * Return the Color representing by this cluster's centroid. The centroid (average) is represented as a
	 * collection of doubles representing the R, G and B value.
	 */
    private static Color getColorFromCluster(Dataset cluster) {
    	Instance centroid = DatasetTools.average(cluster);
    	Collection<Double> rgb = centroid.values();
    	Iterator<Double> iter = rgb.iterator();
    	Color c = new Color(iter.next().intValue(), iter.next().intValue(), iter.next().intValue());
    	return c;
	}
}
