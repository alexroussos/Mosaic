package mosaic;


import java.awt.Color;
import java.util.List;

/**
 * ColorPaletteGenerators return a list of colors.
 * @author alexroussos@gmail.com
 *
 */
public interface ColorPaletteGenerator {
	public List<Color> generateColorPalette(int numColors);
}
