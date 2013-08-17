package mosaic.imageloader;

import java.util.ArrayList;
import java.util.List;

public class ManualImageLoader implements ImageLoader{

	@Override
	public List<String> getImageLocations() {
		List<String> images = new ArrayList<String>();
		images.add("https://dl.dropboxusercontent.com/u/65411942/IMG_1646.JPG");
		images.add("https://dl.dropboxusercontent.com/u/65411942/IMG_1913.JPG");
		images.add("https://dl.dropboxusercontent.com/u/65411942/IMG_1914.JPG");
		return images;
	}

}
