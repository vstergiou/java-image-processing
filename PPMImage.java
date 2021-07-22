/* Homework 3, class PPMImage 
 * Vasilis Stergiou, 2454
 * Antonis Leventakis, 2459
 */

package ce325.hw3;

import java.io.*;
import java.io.File;


public class PPMImage extends RGBImage {
   
    public PPMImage(RGBImage img) {

        super(img);
    }
    

    public PPMImage(YUVImage img){
        super(img);
    }
    
    /* method that reads a ppm image file and stores its 
     * data at the array of pixels of this object .
     */
     
    public PPMImage(File file) throws UnsupportedFileFormatException, FileNotFoundException {

        FileReader fReader = null;

        try{

            if(!file.exists()) {
                throw new FileNotFoundException();
            }
            fReader = new FileReader(file);
            
            BufferedReader in = new BufferedReader(fReader);
            String inputLine;
            StringBuffer ppmStr = new StringBuffer();

            try{
                while ((inputLine = in.readLine()) != null) {     // stores data to a string 
                        ppmStr.append(inputLine);
                        ppmStr.append(" ");
                }

            }
            catch(IOException ex) {

                System.out.println("IOException occured while reading from file "
                + file.getName());
            }
            String myPPM = ppmStr.toString();
            String[] pixels = myPPM.split("\\s+");

            String magicNumber = pixels[0];
            
            if(!magicNumber.equals("P3")) {             // magic number should be "P3" to support PPM format . 
                    throw new UnsupportedFileFormatException("Unsupported file format!");
            }
            
            //reads and stores width,height and colorDepth of ppm Image. 
            int width = Integer.parseInt(pixels[1]);    
            int height = Integer.parseInt(pixels[2]);
            int colorDepth = Integer.parseInt(pixels[3]);
            
            super.setColorDepth(colorDepth);
            initArray(height,width);         // intiates this.array of pixels 
            
            int j;
            int rgb =1,row =0, col=0;
            
            // intiates values of red,green,blue. 
            Short red =  Short.valueOf("0");
            Short green=  Short.valueOf("0"); 
            Short blue=  Short.valueOf("0");
            
            // reads 3 values of data at a time
            // and store them at red,gree,blue successively. 
            for(int i =4; i<= pixels.length-3; i=j ) {

                for( j=i; j< (i+3);j++) {
                    
                    switch (rgb){
                        case 1 : 
                             red = Short.valueOf(pixels[j]);
                             rgb=2;
                             break;
                        case 2:
                            green = Short.valueOf(pixels[j]);
                            rgb=3;
                            break;
                            
                        case 3:
                            blue = Short.valueOf(pixels[j]);
                            rgb=1;
                    } 
                }
                super.createPixel(red, green, blue, row, col);     // creates a pixels with red,green,blue values that have read. 
                col++;
                if(col==width) {
                        row++;
                        col=0;
                }
            }
        }
        finally {
        
            if( fReader != null) {   // close reader 
                try {
                  fReader.close();
                } 
                catch(IOException ex) {
                  System.out.println("IOException occured while closing file "
                +file.getName());
                }
            }
        }
    }
    
    
    
    // returns a string of PPM format. 

    @Override
    public String toString() {
        return getPPM();

    }
    
    
    // creates a PPM image file of this object.

    public void toFile(File file) {
        BufferedWriter writer = null;
        
        try {
            writer = new BufferedWriter(new OutputStreamWriter(
                  new FileOutputStream(file,false), "utf-8"));
            writer.write(getPPM());
        } 
        catch (IOException ex) {
            System.out.println("IOException occured while writing.");
        } 
        finally {
           try {
               if(writer!=null){
                    writer.close();
               }    
           } 
           catch (IOException ex) {
                System.out.println("IOException occured while writing.");
            }
        }

    }
    
    
    //creates the ppm string of this object.
    
    private  String getPPM() {
        StringBuffer ppmStr = new StringBuffer("P3\n");
        ppmStr.append(getArray()[0].length); 
        ppmStr.append(" ");
        ppmStr.append(getArray().length);
        ppmStr.append(" ");
        ppmStr.append(getColorDepth());
        ppmStr.append("\n");
      
        for(int i=0; i < getArray().length ; i++) {
            for(int j = 0; j<getArray()[0].length;j++) {

                ppmStr.append(getPixel(i,j).getRed());
                ppmStr.append(" ");
                ppmStr.append(getPixel(i,j).getGreen());
                ppmStr.append(" ");
                ppmStr.append(getPixel(i,j).getBlue());
                ppmStr.append("\n");
            }
        }
        return ppmStr.toString();
    }
}



