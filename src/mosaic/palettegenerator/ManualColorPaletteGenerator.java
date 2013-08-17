package mosaic.palettegenerator;


import java.awt.Color;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Return the first n colors from a manually-defined list. This is useful if you want to define the colors for an image
 * to achieve an effect (eg limiting the color range).
 * 
 * @author alexroussos@gmail.com
 *
 */
public class ManualColorPaletteGenerator implements ColorPaletteGenerator {

	private final static Map<String, Color> COLORS = new LinkedHashMap<String, Color>();

	public ManualColorPaletteGenerator() {
		COLORS.put("555614", new Color(0x55, 0x56, 0x14));
        COLORS.put("ba8c12", new Color(0xba, 0x8c, 0x12));
        COLORS.put("de5e37", new Color(0xde, 0x5e, 0x37));
        COLORS.put("d25678", new Color(0xd2, 0x56, 0x78));
        COLORS.put("b83944", new Color(0xb8, 0x39, 0x44));
        COLORS.put("6f5560", new Color(0x6f, 0x55, 0x60));
        COLORS.put("7dc198", new Color(0x7d, 0xc1, 0x98));
        COLORS.put("b9b434", new Color(0xb9, 0xb4, 0x34));
        COLORS.put("efd246", new Color(0xef, 0xd2, 0x46));
        COLORS.put("e08d47", new Color(0xe0, 0x8d, 0x47));
        COLORS.put("ed6b69", new Color(0xed, 0x6b, 0x69));
        COLORS.put("cd5344", new Color(0xcd, 0x53, 0x44));
        COLORS.put("a87d5b", new Color(0xa8, 0x7d, 0x5b));
        COLORS.put("0b2b00", new Color(0x0b, 0x2b, 0x00));
        COLORS.put("98ad2e", new Color(0x98, 0xad, 0x2e));
        COLORS.put("d8d023", new Color(0xd8, 0xd0, 0x23));
        COLORS.put("dfa14e", new Color(0xdf, 0xa1, 0x4e));
        COLORS.put("db431c", new Color(0xdb, 0x43, 0x1c));
	}

    public Map<String, Color> generateColorPalette(int numColors) {
        return PaletteUtils.cloneFirstEntries(COLORS, numColors);
    }
}
