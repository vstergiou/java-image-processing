/* Homework 3, Class RGBPixel
 * Vasilis Stergiou, 2454
 * Antonis Leventakis, 2459
 */

package ce325.hw3;


public class RGBPixel {
	
    private int rgb;

    public RGBPixel(short red, short green, short blue) {

        rgb = 0;
        rgb =  (red <<16) | (green<<8) | blue;
    }

    public RGBPixel(YUVPixel pixel) {
        rgb = 0;

        int C = pixel.getY() - 16;
        int D = pixel.getU() - 128;
        int E = pixel.getV() - 128;

        int R = clip(( 298 * C           + 409 * E + 128) >> 8);
        int G = clip(( 298 * C - 100 * D - 208 * E + 128) >> 8);
        int B = clip(( 298 * C + 516 * D           + 128) >> 8);

        rgb =  (R <<16) | (G<<8) | B;
    }

    public RGBPixel(RGBPixel pixel) {
        this(pixel.getRed(), pixel.getGreen(), pixel.getBlue());
    }



    public short getRed() {
        Integer r = (rgb>>16) & 0xff;
        return r.shortValue();
    }
    
    public short getGreen() {
        Integer g= (rgb>>8) & 0xff;
        return g.shortValue();
    }
    
    public short getBlue() {
        Integer b = rgb & 0xff;
        return b.shortValue();
    }
    
    public int getRGB(){
        return this.rgb;
    }
    

    public void setRed(short red) {
        int g = getGreen();
        int b = getBlue();
        rgb = 0;
        rgb =  (red <<16) | (g<<8) | b;
    }
    
    public void setGreen(short green) {
        int r = getRed();
        int b = getBlue();
        rgb = 0;
        rgb =  (r <<16) | (green<<8) | b;
    }
    
    public void setBlue(short blue) {

        int r = getRed();
        int g = getGreen();
        rgb = 0;
        rgb =  (r <<16) | (g<<8) | blue;
    }
    
    public void setRGB(int value){
        this.rgb=value;
    }
    
    final void setRGB(short red, short green, short blue){
        rgb = 0;
        rgb =  (red <<16) | (green<<8) | blue;
    }
    
    

    private int clip(int x) {
        if(x<0) 
            return 0;
        else if(x>255)
                return 255;
        else 
            return x;
    }

    @Override
    public String toString(){
        return "("+ getRed()+","+getGreen()+","+getBlue()+")";
    }

}
