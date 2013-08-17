package mosaic.palettegenerator;

import java.awt.Color;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class PaletteUtils {
	static Map<String, Color> cloneFirstEntries(Map<String, Color> source, int quantity){
		LinkedHashMap<String, Color> colors = new LinkedHashMap<String, Color>();
		int count = 0;
		Set<Map.Entry<String, Color>> entries = source.entrySet();
		for (Map.Entry<String, Color> entry : entries) {
			colors.put(entry.getKey(), entry.getValue());
			count++;
			if(count == quantity){
				return colors;
			}
		}
		
		return colors;
	}
}
