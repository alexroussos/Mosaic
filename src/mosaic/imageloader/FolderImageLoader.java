package mosaic.imageloader;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

public class FolderImageLoader implements ImageLoader {
	private static final String[] IMAGE_EXTENSIONS = {".jpg", ".jpeg", ".gif", ".bmp", ".png"};
	File root;
	
	public FolderImageLoader(String folderLocation){
		root = new File(folderLocation);
	}

	@Override
	public List<String> getImageLocations() {
		File[] file = root.listFiles();
		List<String> images = new ArrayList<String>();
		for (int i = 0; i < file.length; i++) {
			if(isImage(file[i])){
				try {
					images.add(file[i].toURI().toURL().toString());
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return images;
	}

	private boolean isImage(File file) {
		if(file.isDirectory()){
			return false;
		}
		
		String name = file.getName().toLowerCase();
		for (int i = 0; i < IMAGE_EXTENSIONS.length; i++) {
			if(name.endsWith(IMAGE_EXTENSIONS[i])){
				return true;
			}
		}
		
		return false;
	}

}
