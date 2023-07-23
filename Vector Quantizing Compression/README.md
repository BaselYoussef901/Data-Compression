First Check the pdf that shows how Vector Quantizing works

This Program is to Reduce image size into grey level colors..
First we need our inputs which are:
image:
![_Original_Image](https://github.com/Besbol100/Data-Compression/assets/113455518/d5d6450a-71df-43f7-9252-978113514142)
[Original Size: 258 KB]
Vector Information which is in "_InputDataToCompress.txt" in _InputsDaata File [Width - Height - CodeBlock] of the vectors

Image Information:

![Image Information 1](https://github.com/Besbol100/Data-Compression/assets/113455518/810ba022-f363-4202-9a8f-70053a772b75)

>>> What the Code Doin! <<<
all details in "_TextFilesOut" which is
1- ImageData.txt is for converting the Image into numbered Pixeled in Grey Level
2- ReconstructedData.txt is for after Optimizing the final result to reduce its size without making it blur
3- ScaleData.txt is for making sure that the new image is a 2D-numeric pixels
4- VectorData.txt is showing each pixel in image with the Optimal Vector


 Finally Compressed Image after applying Vector Quantizer:
 
 ![CompressedImage](https://github.com/Besbol100/Data-Compression/assets/113455518/45f835f5-6398-4d00-95f2-d4f47b1831a5)
[Compressed Size: 31.9 KB]


__another Example to show Image Scaling is Important:
Original Image:

>>>![CheckOn](https://github.com/Besbol100/Data-Compression/assets/113455518/6b33080c-90a0-4eeb-86f7-7a34faa12889)
[Original Size: 2.52 KB]
Image Information:

![Image Information 2](https://github.com/Besbol100/Data-Compression/assets/113455518/efc8c39f-ad8a-4083-a12d-963081fc0c63)

Compressed Image:

![CompressedImage](https://github.com/Besbol100/Data-Compression/assets/113455518/83adf990-5e8a-4fa1-b5c0-e2c508637438)
[Compressed Size: 1.92 KB]
