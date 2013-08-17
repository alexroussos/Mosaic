package mosaic;

public class Dimensions {
	private int width;
	private int height;
	
	public Dimensions(int width, int height){
		this.width = width;
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	public Dimensions scaledToFitWithinThis(int sourceWidth, int sourceHeight){
		//if a dimension is -1, then any value should be accepted.
		
		//first scale to fit width
		if(width >= 0){
			double scaleFactor = (double)sourceWidth/(double)width;
			sourceWidth = (int) (sourceWidth/scaleFactor);
			sourceHeight = (int) (sourceHeight/scaleFactor);
		}
		
		//if does not fit within height, scale down to fit within height.
		if(height >= 0 && height < sourceHeight){
			double scaleFactor = (double)sourceHeight/(double)height;
			sourceWidth = (int) (sourceWidth/scaleFactor);
			sourceHeight = (int) (sourceHeight/scaleFactor);
		}
		
		return new Dimensions(sourceWidth, sourceHeight);
	}
	
	public String toString(){
		return width + "x" + height;
	}
}
