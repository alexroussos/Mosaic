Mosaic
======

Mosaic generator with k-means color palette

Generates a series of mosaics with various resolutions to simulate tile sizes as well as various color palettes. The mosaics are then stitched together into one composite mosaic. See the [sample images][] for examples.

By default, the colors used in the image will be generated by the ImageBasedColorPaletteGenerator, which uses k-means clustering to select an optimal palette. This makes use of the Java Machine Learning (javaml) library, which is included with this project. Other palette generators are also included, eg the ManualColorPaletteGenerator, which allows the user to specify the colors to be used. 

BUILDING:<br>
The included jarfile may work for you, but if you wish to build the project, use Ant: `ant build`, which will create the `mosaic.jar`

RUNNING:<br>
To use from the command line:<br>
`java -jar mosaic.jar <imgUrl> <numColors> <widths>`<br>
For example, to generate an array of mosaics using 4, 8 and 12 colors that are 50, 100, and 200 pixels wide:<br>
`java -jar mosaic.jar https://mysite.com/myimage.png 4,8,12 50,100,200`<br>
You images will be saved in the ```./generated_mosaics``` directory, relative to wherever you executed the jar.<br>

USE AS LIBRARY:<br>
The `MosaicGenerator` class exposes methods in order to make integration into other applications possible. These methods are documented in the MosaicGenerator class and allow you generate single mosaics and composite mosaics (sets of images showing the permutations of the specified color palettes and resolutions).
  
[sample images]: https://github.com/alexroussos/Mosaic/blob/master/sample-images
