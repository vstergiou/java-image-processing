/* Homework 3, class Histogram
 * Vasilis Stergiou, 2454
 * Antonis Leventakis, 2459
 */

package ce325.hw3;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.DoubleSummaryStatistics;


public class Histogram {
    
    private YUVImage myImg;                 // to store a copy of YUVimages that reads. 
    private double[] histogram;            // to store histogram. 
    private Integer[] luminocity;         // to store final value of luminocity
    private int numOfPixels;             // to store number of pixels      
    private final int maxColor =235;
    
    
    // creates histogram array for the YUVImage myImg
    
    public Histogram(YUVImage img){
    
        myImg = new YUVImage(img);   // copies YUVImage 
        histogram = new double[maxColor+1];
        this.numOfPixels = img.getHeight() * img.getWidth();
        
        for(int i=0; i < img.getHeight() ; i++) {
            for(int j = 0; j<img.getWidth();j++) {
                histogram[img.getPixel(i,j).getY()]++;
            }
        }
    }
    
    
    // equalizes histogram of the image  
    
    public void equalize(){
        
        double[] pmf = new double[maxColor+1];
        
        for(int i=0; i< pmf.length;i++){
            pmf[i]= histogram[i]/numOfPixels;
       
        }
        
        double [] cdf = new double[maxColor+1];
        Double [] temp= new Double[maxColor+1];
        
        cdf[0]=pmf[0];
        for(int i=1; i< pmf.length;i++){
            cdf[i]=cdf[i-1] + pmf[i];
        }
        
        luminocity = new Integer[maxColor+1];
        for(int i=1; i< luminocity.length;i++){
            temp[i] =cdf[i]*maxColor;
            this.luminocity[i] = temp[i].intValue();
        }
        
        int pos;
        short u =128;
       
        for(int i=0; i < myImg.getHeight() ; i++) {
            for(int j = 0; j<myImg.getWidth();j++) {
               pos = myImg.getPixel(i,j).getY();
               myImg.getPixel(i,j).setY(this.getEqualizedLuminocity(pos));
               myImg.getPixel(i,j).setU(u);
               myImg.getPixel(i,j).setV(u);
            }
        }
    }

    
    // returns equalized value of luminocity for given luminocity. 
    
    public short  getEqualizedLuminocity(int luminocity){
        return this.luminocity[luminocity].shortValue();
    }
    
    // returns equalized image
    
    public YUVImage getEqualizedImage(){
    	return this.myImg;
    }
    
    
    // returns string to print histogram. 
    @Override
    public String toString(){
        return printHistogram(histogram).toString();
    }
    
    
    // prints histogram to a file. 
    
    public void toFile(File file) {
        BufferedWriter writer = null;

        try {
            writer = new BufferedWriter(new OutputStreamWriter(
                  new FileOutputStream(file,false), "utf-8"));
            writer.write(this.toString());
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
    
    // used to print histogram. 
    
    private StringBuffer printHistogram(double[] array) {
        StringBuffer label = new StringBuffer();
        DoubleSummaryStatistics stat = Arrays.stream(array).summaryStatistics();
        double max = stat.getMax();
        double factor = 80/max;
        
         for (int range = 0; range < array.length; range++) {
            label.append(range);
            label.append(" : ");
            label.append(convertToStars(array[range]*factor));
            label.append("\n");
        }
        return label;
    }
    
    
    // prints "num" number of '*' . 

    private String convertToStars(double num) {
        StringBuilder builder = new StringBuilder();
        for (int j = 0; j < num ; j++) {
            builder.append('*');
        }
        return builder.toString();
    }
}
