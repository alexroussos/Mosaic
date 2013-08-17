package mosaic.palettegenerator;


import java.awt.Color;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

/**
 * Generate n random colors.
 * 
 * @author alexroussos@gmail.com
 *
 */
public class RandomColorPaletteGenerator implements ColorPaletteGenerator {
	
	private static final int MAX_BYTE = 255; // Byte.MAX_VALUE is only 127
	private final Map<String, Color> COLORS;

	public RandomColorPaletteGenerator(int maxColors) {
		COLORS = initializeColorPalette(maxColors);
	}
	
	/**
	 * Returning colors as a subset of a fixed palette ensures that multiple images will use the 
	 * same colors even for different palette sizes.
	 */
	@Override
	public Map<String, Color> generateColorPalette(int numColors) {
		return PaletteUtils.cloneFirstEntries(COLORS, numColors);
	}
	
	private Map<String, Color> initializeColorPalette(int maxColors) {
        Random rand = new Random();
        Map<String, Color> colors = new LinkedHashMap<String, Color>();
        for (int i = 0; i < maxColors; i++) {
        	int r = rand.nextInt(MAX_BYTE);
        	int g = rand.nextInt(MAX_BYTE);
        	int b = rand.nextInt(MAX_BYTE);
        	
        	String name = Integer.toHexString(r) + Integer.toHexString(g) + Integer.toHexString(b) ;
            colors.put(name, new Color(r, g, b));
        }
        return colors;
	}
}
