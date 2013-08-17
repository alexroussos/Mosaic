package mosaic.palettegenerator;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * Generates a color palette from a list given in a properties file.
 * 
 * @author ryoullar
 *
 */
public class PropertiesFilePaletteGenerator implements ColorPaletteGenerator {
	private final static Map<String, Color> COLORS = new LinkedHashMap<String, Color>();
	
	public PropertiesFilePaletteGenerator(String propertiesFile){
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(new File(propertiesFile)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Set<String> names = prop.stringPropertyNames();
		for (String key : names) {
			String value = prop.getProperty(key);
			int r = parseInt(value.substring(0, 2));
			int g = parseInt(value.substring(2, 4));
			int b = parseInt(value.substring(4, 6));
			COLORS.put(key, new Color(r, g, b));
		}
	}

	@Override
	public Map<String, Color> generateColorPalette(int numColors) {
		return PaletteUtils.cloneFirstEntries(COLORS, numColors);
	}

	/**
	 * Parses an int out of a String representing a hexadecimal value.
	 * @param substring
	 * @return
	 */
	private int parseInt(String number) {
		int parsed = 0;
		for (int i = 0; i < number.length(); i++) {
			char letter = number.charAt(i);
			int digit = convertHexCharToIntValue(letter);
			parsed = parsed*16 + digit;
		}
		return parsed;
	}

	/**
	 * Converts a hexadecimal char into the int it represents.
	 * @param letter
	 * @return
	 */
	private static int convertHexCharToIntValue(char letter) {
		if(letter >= 'a' && letter <= 'f'){
			return letter - 'a' + 10;
		}
		
		if(letter >= 'A' && letter <= 'F'){
			return letter - 'A' + 10;
		}
		
		return letter - '0';
	}
}
