/* Homework 3, class PPMImageStacker 
 * Vasilis Stergiou, 2454
 * Antonis Leventakis, 2459
 */

package ce325.hw3;

import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

public class PPMImageStacker  {
    private List<PPMImage> myStacker;     // list to store images of given directory
    private RGBImage myImg;               // stacked image. 
    
    
    // method that reads all files of a directory and adds them to a list. 
    
    public PPMImageStacker(File dir)throws UnsupportedFileFormatException,FileNotFoundException{
          
        if(!dir.exists()){
            throw new FileNotFoundException("[ERROR] Directory " + dir.getAbsolutePath()+ " does not exist!");
        }
        else if(!dir.isDirectory()){
            throw new UnsupportedFileFormatException("[ERROR] "+ dir.getAbsolutePath() + " is not a directory!");
        }

        myStacker = new LinkedList<>();

        File[] filesInDir = dir.listFiles();

        for(File file : filesInDir){
            myStacker.add(new PPMImage(file));
        }
    }
    
    
    // stacks the given images of the list and creates stacked image .
    
    public void stack(){
        
        int height = myStacker.get(0).getHeight();
        int width = myStacker.get(0).getWidth();
        int colorDepth = myStacker.get(0).getColorDepth();
        Integer red=0,green=0,blue=0;
        
        myImg = new RGBImage(width,height,colorDepth);
        int N = myStacker.size();
        
        for(int row=0; row< height;row++){
            for(int col=0; col<width;col++){
                for(int k =0; k<myStacker.size();k++) {    // combines all (row,col) pixels of the images. 
                    
                    red+= myStacker.get(k).getPixel(row,col).getRed();
                    green+=myStacker.get(k).getPixel(row,col).getGreen();
                    blue+= myStacker.get(k).getPixel(row,col).getBlue();
                }
                // calculates the values of red,green,blue of stacked image. 
                red =red/N;
                green = green/N;
                blue = blue/N;
                myImg.createPixel(red.shortValue(),green.shortValue(),blue.shortValue(),row,col); //creates final RGBpixel at (row,col). 

                red =0;
                green =0;
                blue = 0;
            }
        }
    }
    
    // returns stacked image. 
    
    public PPMImage getStackedImage(){
        PPMImage stackedImg = new PPMImage(myImg);
        return stackedImg;
    }
    
}
