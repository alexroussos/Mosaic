package palettegenerator;


import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * Return the first n colors from a manually-defined list. This is useful if you want to define the colors for an image
 * to achieve an effect (eg limiting the color range).
 * 
 * @author alexroussos@gmail.com
 *
 */
public class ManualColorPaletteGenerator implements ColorPaletteGenerator {

	private final static List<Color> COLORS = new ArrayList<Color>();

	public ManualColorPaletteGenerator() {
		COLORS.add(new Color(0x55, 0x56, 0x14));
        COLORS.add(new Color(0xba, 0x8c, 0x12));
        COLORS.add(new Color(0xde, 0x5e, 0x37));
        COLORS.add(new Color(0xd2, 0x56, 0x78));
        COLORS.add(new Color(0xb8, 0x39, 0x44));
        COLORS.add(new Color(0x6f, 0x55, 0x60));
        COLORS.add(new Color(0x7d, 0xc1, 0x98));
        COLORS.add(new Color(0xb9, 0xb4, 0x34));
        COLORS.add(new Color(0xef, 0xd2, 0x46));
        COLORS.add(new Color(0xe0, 0x8d, 0x47));
        COLORS.add(new Color(0xed, 0x6b, 0x69));
        COLORS.add(new Color(0xcd, 0x53, 0x44));
        COLORS.add(new Color(0xa8, 0x7d, 0x5b));
        COLORS.add(new Color(0x0b, 0x2b, 0x00));
        COLORS.add(new Color(0x98, 0xad, 0x2e));
        COLORS.add(new Color(0xd8, 0xd0, 0x23));
        COLORS.add(new Color(0xdf, 0xa1, 0x4e));
        COLORS.add(new Color(0xdb, 0x43, 0x1c));
	}

    public List<Color> generateColorPalette(int numColors) {
        return COLORS.subList(0, numColors);
    }
}
