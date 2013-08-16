package mosaic;


import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * Return n colors evenly distributed across the RGB spectrum.
 * 
 * NOTE: For palette sizes that are not powers of 3, the palette will be missing some colors with higher
 * RGB values -- ie the brighter colors.
 * 
 * @author alexroussos@gmail.com
 *
 */
public class DistributedColorPaletteGenerator implements ColorPaletteGenerator {
	
	private static final int MIN_COLORS = 8;
	private static final int MAX_BYTE = 255; // Byte.MAX_VALUE is only 127
	
	/**
	 * NOTE: see comments within method for caveat.
	 */
	@Override
	public List<Color> generateColorPalette(int numColors) {
		if (numColors < MIN_COLORS) {
			throw new IllegalArgumentException("Number of colors must be at least " + MIN_COLORS);
		}
		int numChannels = 3;
        List<Color> colors = new ArrayList<Color>();
        int stepsPerChannel = (int) Math.pow(numColors, (double) 1 / numChannels); // how many r values there will be (or g, or b values...)
        int stepSize = MAX_BYTE / (stepsPerChannel - 1); // increment the rgb value by this amount each iteration
        
        int r = 0, g = 0, b = 0;
        
        /* 
         * CAVEAT:
         * This only works out perfectly when numColors is a power of 3 -- otherwise there is no way to use three
         * nested loops to generate evenly-distributed colors. For 11 colors using only stepsPerChannel, we would 
         * have only 2*2*2, or 8, colors. Here we work around this by using more B steps to ensure that we generate 
         * enough colors -- eg for 11 colors, we will generate 2*2*3, or 12, colors, of which we take the first 11. 
         * Note that this leaves out some of the brighter colors.
         */
        int numMissingColors = numColors - (int) Math.pow(stepsPerChannel, numChannels);
        int additionalBSteps = (int) Math.ceil(numMissingColors / Math.pow(stepsPerChannel, numChannels - 1));
        int numBSteps = stepsPerChannel + additionalBSteps ;
        int bStepSize = MAX_BYTE / (numBSteps - 1);
        
        for (int rSteps = 0; rSteps < stepsPerChannel; rSteps++) {
            for (int gSteps = 0; gSteps < stepsPerChannel; gSteps++) {
                for (int bSteps = 0; bSteps < numBSteps; bSteps++) {
                    colors.add(new Color(r, g, b));
                    b += bStepSize;
                    if (colors.size() == numColors) {
                    	return colors;
                    }
                }
                g += stepSize;
                b = 0;
            }
            r += stepSize;
            g = 0;
        }
        return colors;
	}
}
