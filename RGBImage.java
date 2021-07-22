/* Homework 3, Class RGBImage
 * Vasilis Stergiou, 2454
 * Antonis Leventakis, 2459
 */

package ce325.hw3;

public class RGBImage implements Image {
	
    private RGBPixel [][]array;                 // to store RGBPixels 
    private int colorDepth;
    public static int MAX_COLORDEPTH =255;
    

    public RGBImage(){
        this.array =null;
        this.colorDepth = 255;
    }
    
    
    public RGBImage(int width, int height, int colorDepth) {
        array = new RGBPixel[height][width];
        this.colorDepth = colorDepth;
    }
    
    
    // copies an RGBImage and creates a new object. 
    
    public RGBImage(RGBImage copyImg) {
        this(copyImg.getWidth(),copyImg.getHeight(),copyImg.getColorDepth());
        
        for(int i=0; i < array.length ; i++) {
            for(int j = 0; j<array[0].length;j++) {
                this.array[i][j] = new RGBPixel(copyImg.getArray()[i][j]);
            }
        }
        this.colorDepth = copyImg.getColorDepth();

    }
    
    
    // creates an RGBImage, given an YUVImage. 
    
    public RGBImage(YUVImage Img){
        array = new RGBPixel[Img.getHeight()][Img.getWidth()];
        
        for(int i=0; i < array.length ; i++) {
            for(int j = 0; j<array[0].length;j++) {
                array[i][j] = new RGBPixel(Img.getPixel(i,j));
            }
        }
        this.colorDepth = 255;
    }
    
    
    public void setColorDepth(int colorDepth){
        this.colorDepth = colorDepth;
    }
    

    public int getColorDepth() {
        return colorDepth;
    }
    
    
    public int getWidth(){
        return array[0].length;
    }
    
    
     public int getHeight(){
        return array.length;
    }
    
    
    // sets the pixel at (row,col).
    
     public void setPixel(int row, int col, RGBPixel pixel){
        this.array[row][col] = pixel;
    }
    
    
    //returns the pixel at (row,col).
    
    public RGBPixel getPixel(int row, int col){
        return array[row][col];
    }
    
    
    // turns image to grayscale. 
    
    @Override
    public  void grayscale() {

        for (RGBPixel[] array1 : array) {
            for (int j = 0; j<array[0].length; j++) {
                int r = array1[j].getRed();
                int g = array1[j].getGreen();
                int b = array1[j].getBlue();
                Double gr = r * 0.3 + g *0.59 + b*0.11;
                short gray = gr.shortValue();
                array1[j].setRed(gray);
                array1[j].setGreen(gray);
                array1[j].setBlue(gray);
            }
        }
    }

    // increases size of image to double. 
    
    @Override
    public void doublesize() {
        int width = array[0].length;
        int height = array.length;

        RGBPixel [][]temp = new RGBPixel[2*height][2*width];
        
        for(int row=0; row < height ; row++) {
            for(int col=0; col<width;col++){

                temp[2*row][2*col] = array[row][col];
                temp[2*row+1][2*col] = array[row][col];
                temp[2*row][2*col+1] = array[row][col];
                temp[2*row+1][2*col+1] = array[row][col];

            }
        }

        array = new RGBPixel[2*height][2*width];
        array = temp;
    }
    
    
    // decreases size of image to half. 

    @Override
    public void halfsize() {

        int width = array[0].length/2;
        int height = array.length/2;
        
        if(width == 0 || height == 0){  //case image is too small for halfsize. 
            System.out.println("This image is too small.");
            return ;
        }
        
        short red, green, blue;
        RGBPixel [][]temp = new RGBPixel[height][width];

        for(int row=0; row < height ; row++) {
            for(int col=0; col< width; col++) {
                Integer r = (array[2*row][2*col].getRed() + array[2*row+1][2*col].getRed()+
                                array[2*row][2*col+1].getRed() + array[2*row+1][2*col+1].getRed())/4;
                Integer g = (array[2*row][2*col].getGreen() + array[2*row+1][2*col].getGreen()+
                                array[2*row][2*col+1].getGreen() + array[2*row+1][2*col+1].getGreen())/4;
                Integer b = (array[2*row][2*col].getBlue() + array[2*row+1][2*col].getBlue()+
                                array[2*row][2*col+1].getBlue() + array[2*row+1][2*col+1].getBlue())/4;
                red = r.shortValue();
                green = g.shortValue();
                blue = b.shortValue();
                temp[row][col] = new RGBPixel(red,green,blue);
            }
        }

        array = new RGBPixel[height/2][width/2];
        array = temp;
    }
    
    
    // rotates image 90 degrees clockwise 
    
    @Override
    public void rotateClockwise() {

        int width = array[0].length;
        int height = array.length; 

        RGBPixel [][]rotatedArray = new RGBPixel[width][height];

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                rotatedArray[col][height-1-row] = array[row][col];
            }
         }

        array = new RGBPixel[width][height];
        array = rotatedArray;
    }
    
    
    // creates a new RGBPixel at (row,col) position of the array
    
    public void createPixel(short red,short green, short blue, int row , int col) {
            this.array[row][col] = new RGBPixel(red,green,blue);
    }
    
    
    // initiates array to an (height * width) array.
    
    public void initArray(int height,int width) {
            this.array = new RGBPixel[height][width];
    }
    
    
    // sets array of pixels to given array.
    
    public  void setArray(RGBPixel [][]array){
        this.array = new RGBPixel[array.length][array[0].length];
        
        for(int i=0; i < array.length ; i++) {
            for(int j = 0; j<array[0].length;j++){
                this.array[i][j] = new RGBPixel(array[i][j]);
            }
        }
    }
    
    
    //method that returns the array of pixels.
    
    public RGBPixel[][] getArray(){
        return array;
    }
    
}
