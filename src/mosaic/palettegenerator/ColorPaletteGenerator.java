package mosaic.palettegenerator;


import java.awt.Color;
import java.util.Map;

/**
 * ColorPaletteGenerators return a list of colors.
 * @author alexroussos@gmail.com
 *
 */
public interface ColorPaletteGenerator {
	public Map<String, Color> generateColorPalette(int numColors);
}
