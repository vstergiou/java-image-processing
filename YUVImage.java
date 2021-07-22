
package ce325.hw3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class YUVImage {
    
    private YUVPixel [][] array;   // to store YUVPixels 
    
    public YUVImage(int width,int height){
    
        array = new YUVPixel[height][width];
        
        Short y = Short.valueOf("18");
        Short u = Short.valueOf("128");
        Short v = Short.valueOf("128");
        
        for(int i=0; i< height ; i++){
            for(int j=0; j< width; j++){
                array[i][j] = new YUVPixel(y,u,v);
            }
        }
    }
    
    public YUVImage(YUVImage copyImg){
        this(copyImg.getWidth(),copyImg.getHeight());
        for(int i=0; i < array.length ; i++) {
            for(int j = 0; j<array[0].length;j++) {
                this.array[i][j] = new YUVPixel(copyImg.getArray()[i][j]);
            }
        }
    }
    
    public YUVImage(RGBImage Img){
        array = new YUVPixel[Img.getHeight()][Img.getWidth()];
        
        for(int i=0; i < array.length ; i++) {
            for(int j = 0; j<array[0].length;j++) {
                array[i][j] = new YUVPixel(Img.getArray()[i][j]);
            }
        }
    }
    
    /* method that reads a ppm image file and stores its 
     * data at the array of pixels of this object .
     */
    
    public YUVImage(java.io.File file)throws UnsupportedFileFormatException, FileNotFoundException {
        
        FileReader fReader = null;

        try{

            if(!file.exists()) {
                throw new FileNotFoundException();
            }
            fReader = new FileReader(file);
            BufferedReader in = new BufferedReader(fReader);
            String inputLine;
            StringBuffer yuvStr = new StringBuffer();

            try{
                while ((inputLine = in.readLine()) != null) {  // stores data to a string 
                    yuvStr.append(inputLine);
                    yuvStr.append(" ");
                }

            }
            catch(IOException ex) {

                System.out.println("IOException occured while reading from file "
                + file.getName());
            }

            String myYUV = yuvStr.toString();
            String[] pixels = myYUV.split("\\s+");

            String magicNumber = pixels[0];
            if(!magicNumber.equals("YUV3")) {          // magic number should be "YUV3" to support YUV format .
                throw new UnsupportedFileFormatException("Unsupported file format!");
            }
            
            //reads and stores width,height of YUV Image.
            int width = Integer.parseInt(pixels[1]);
            int height = Integer.parseInt(pixels[2]);
            
            initArray(height,width);   // intiates this.array of pixels 

            int j;
            int yuv =1,row =0, col=0;
            
            // intiates values of Y,U,V. 
            Short Y =  Short.valueOf("0");
            Short U=  Short.valueOf("0"); 
            Short V=  Short.valueOf("0");
            
            // reads 3 values of data at a time
            // and store them at Y, U, V successively. 

            for(int i =3; i<= pixels.length-3; i=j ) {

                for(j=i; j< (i+3);j++) {
                    switch (yuv){
                        case 1 : 
                             Y = Short.valueOf(pixels[j]);
                             yuv=2;
                             break;
                        case 2:
                            U = Short.valueOf(pixels[j]);
                            yuv=3;
                            break;
                            
                        case 3:
                            V = Short.valueOf(pixels[j]);
                            yuv=1;
                    } 
                }
                createPixel(Y, U, V, row, col);  // creates a pixels with red,green,blue values that have read. 
                col++;
                if(col==width) {
                        row++;
                        col=0;
                }
            }

         }
        finally {
            if( fReader != null) {
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
    
    
    
    // method that equalizes the histogram of this image
    
    public void equalize(){
        Histogram myHistogram = new Histogram(this);
        myHistogram.equalize();
        this.setArray(myHistogram.getEqualizedImage());
    
    }
    
    // returns a string of format YUV
        
    public String toString() {
        return getMyYUV();
    }
    
    
    // creates a YUV image file 

    public void toFile(File file) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(
                  new FileOutputStream(file,false), "utf-8"));
            writer.write(getMyYUV());
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

    //creates a string with YUV format . 
    
    private  String getMyYUV() {
        StringBuffer yuvStr = new StringBuffer("YUV3\n");
        yuvStr.append(this.array[0].length); 
        yuvStr.append(" ");
        yuvStr.append(this.array.length);
        yuvStr.append("\n");
      
        for(int i=0; i < this.array.length ; i++) {
            for(int j = 0; j<this.array[0].length;j++) {

                yuvStr.append(getPixel(i,j).getY());
                yuvStr.append(" ");
                yuvStr.append(getPixel(i,j).getU());
                yuvStr.append(" ");
                yuvStr.append(getPixel(i,j).getV());
                yuvStr.append("\n");
            }
        }
        return yuvStr.toString();
    }
    
    
    // returns width of array. 
    
    public int getWidth(){
        return array[0].length;
    }
    
    // returns height of array 
    
    public int getHeight(){
        return array.length;
    }
    
    // returns array of YUV pixels 
    
    public YUVPixel[][] getArray(){
        return array;
    } 
    
    // initiates array. 
    
    private void initArray(int height,int width) {
            this.array = new YUVPixel[height][width];
    }
    
    // creates pixel at (row,col).
    
    private void createPixel(short Y,short U,short V,int row,int col){
        this.array[row][col] = new YUVPixel(Y,U,V);
    }
    
    
    // returns pixel at (row,col).
    
    public YUVPixel getPixel(int row, int col){
        return array[row][col];
    }
    
    
    // sets array.
    
    public void setArray(YUVImage img) {
    	for(int i=0; i < this.array.length ; i++) {
            for(int j = 0; j<this.array[0].length;j++) {
            	this.array[i][j] = new YUVPixel(img.getPixel(i, j));
            }
        }
    }
}
