Mosaic
======

Mosaic generator with k-means color palette

Generates a series of mosaics with various resolutions (tile sizes) and color palettes. The mosaics are then stitched together into one composite mosaic.

By default, the colors used in the image will be generated by the ImageBasedColorPaletteGenerator, which uses k-means clustering to select an optimal palette. You will need to include the Java Machine Learning (javaml) JAR that is included with this project. Other palette generators exist, eg the ManualColorPaletteGenerator, which allows the user to specify the colors to be used. 

To use from the command line:
```MosaicGenerator <imgUrl> <numColors> <widths>```

For example, to generate an array of mosaics using 4, 8 and 12 colors that are 50, 100, and 200 pixels wide:
```MosaicGenerator https://mysite.com/myimage.png 4,8,12 50,100,200```
  