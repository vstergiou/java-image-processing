/* Homework 3, class YUVPixel.
 * Vasilis Stergiou, 2454
 * Antonis Leventakis, 2459
 */


package ce325.hw3;

public class YUVPixel {
    
    private int yuv;

    public YUVPixel(short Y, short U, short V) {
        yuv =0;
        yuv =  (Y <<16) | (U <<8) | V;
    }
    
    public YUVPixel(YUVPixel pixel) {
        this(pixel.getY(),pixel.getU(),pixel.getV());
    }
    

    public YUVPixel(RGBPixel pixel) {
        yuv = 0;
        
        short R = pixel.getRed();
        short G=pixel.getGreen();
        short B = pixel.getBlue();
        
        int Y = ( (  66 * R + 129 * G +  25 * B + 128) >> 8) +  16;
        int U = ( ( -38 * R -  74 * G + 112 * B + 128) >> 8) + 128;
        int  V = ( ( 112 * R -  94 * G -  18 * B + 128) >> 8) + 128;

        this.yuv =  (Y <<16) | (U <<8) | V;
    }

    public short getY(){
        Integer Y = (yuv>>16) & 0xff;
        return Y.shortValue();
    }
    
    public short getU(){
        Integer U= (yuv>>8) & 0xff;
        return U.shortValue();
    }
    
    public short getV() {
        Integer V = yuv & 0xff;
        return V.shortValue();
    }
    
    public void setY(short Y){
        int U = getU();
        int V= getY();
        yuv = 0;
        yuv =  ( Y <<16) | (U<<8) | V;
    }
    
    public void setU(short U){
        int Y = getY();
        int V= getY();
        yuv = 0;
        yuv =  ( Y <<16) | (U<<8) | V;
    }
    
    public void setV(short V) {
        int Y = getY();
        int U= getU();
        yuv = 0;
        yuv =  ( Y <<16) | (U<<8) | V;
    }
    
}
