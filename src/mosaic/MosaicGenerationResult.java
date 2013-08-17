package mosaic;

import java.awt.image.BufferedImage;
import java.util.Map;

public class MosaicGenerationResult {
	private final BufferedImage mosaic;
	private final Map<String, Integer> counts;
	
	public MosaicGenerationResult(BufferedImage image, Map<String, Integer> tileCounts){
		mosaic = image;
		counts = tileCounts;
	}

	public BufferedImage getMosaic() {
		return mosaic;
	}

	public Map<String, Integer> getCounts() {
		return counts;
	}
}
