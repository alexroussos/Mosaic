package mosaic;


import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Generate n random colors.
 * 
 * @author alexroussos@gmail.com
 *
 */
public class RandomColorPaletteGenerator implements ColorPaletteGenerator {
	
	private static final int MAX_BYTE = 255; // Byte.MAX_VALUE is only 127
	private final List<Color> COLORS;

	public RandomColorPaletteGenerator(int maxColors) {
		COLORS = initializeColorPalette(maxColors);
	}
	
	/**
	 * Returning colors as a subset of a fixed palette ensures that multiple images will use the 
	 * same colors even for different palette sizes.
	 */
	@Override
	public List<Color> generateColorPalette(int numColors) {
		return COLORS.subList(0, numColors);
	}
	
	private List<Color> initializeColorPalette(int maxColors) {
        Random r = new Random();
        List<Color> colors = new ArrayList<Color>();
        for (int i = 0; i < maxColors; i++) {
            colors.add(new Color(r.nextInt(MAX_BYTE), r.nextInt(MAX_BYTE), r.nextInt(MAX_BYTE)));
        }
        return colors;
	}

}
