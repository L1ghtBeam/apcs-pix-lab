import java.awt.*;
import java.awt.font.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.text.*;
import java.util.*;
import java.util.List; // resolves problem with java.awt.List and java.util.List
import java.lang.Math;

/**
 * A class that represents a picture.  This class inherits from 
 * SimplePicture and allows the student to add functionality to
 * the Picture class.  
 * 
 * @author Barbara Ericson ericson@cc.gatech.edu
 */
public class Picture extends SimplePicture 
{
    ///////////////////// constructors //////////////////////////////////

    /**
     * Constructor that takes no arguments 
     */
    public Picture ()
    {
        /* not needed but use it to show students the implicit call to super()
         * child constructors always call a parent constructor 
         */
        super();  
    }

    /**
     * Constructor that takes a file name and creates the picture 
     * @param fileName the name of the file to create the picture from
     */
    public Picture(String fileName)
    {
        // let the parent class handle this fileName
        super(fileName);
    }

    /**
     * Constructor that takes the width and height
     * @param height the height of the desired picture
     * @param width the width of the desired picture
     */
    public Picture(int height, int width)
    {
        // let the parent class handle this width and height
        super(width,height);
    }

    /**
     * Constructor that takes a picture and creates a 
     * copy of that picture
     * @param copyPicture the picture to copy
     */
    public Picture(Picture copyPicture)
    {
        // let the parent class do the copy
        super(copyPicture);
    }

    /**
     * Constructor that takes a buffered image
     * @param image the buffered image to use
     */
    public Picture(BufferedImage image)
    {
        super(image);
    }

    ////////////////////// methods ///////////////////////////////////////

    /**
     * Method to return a string with information about this picture.
     * @return a string with information about the picture such as fileName,
     * height and width.
     */
    public String toString()
    {
        String output = "Picture, filename " + getFileName() + 
            " height " + getHeight() 
            + " width " + getWidth();
        return output;

    }

    /** Method to set the blue to 0 */
    public void zeroBlue()
    {
        Pixel[][] pixels = this.getPixels2D();
        for (Pixel[] rowArray : pixels)
        {
            for (Pixel pixelObj : rowArray)
            {
                pixelObj.setBlue(0);
            }
        }
    }

    /** Method that mirrors the picture around a 
     * vertical mirror in the center of the picture
     * from left to right */
    public void mirrorVertical()
    {
        Pixel[][] pixels = this.getPixels2D();
        Pixel leftPixel = null;
        Pixel rightPixel = null;
        int width = pixels[0].length;
        for (int row = 0; row < pixels.length; row++)
        {
            for (int col = 0; col < width / 2; col++)
            {
                leftPixel = pixels[row][col];
                rightPixel = pixels[row][width - 1 - col];
                rightPixel.setColor(leftPixel.getColor());
            }
        } 
    }

    /** Mirror just part of a picture of a temple */
    public void mirrorTemple()
    {
        int mirrorPoint = 276;
        Pixel leftPixel = null;
        Pixel rightPixel = null;
        int count = 0;
        Pixel[][] pixels = this.getPixels2D();

        // loop through the rows
        for (int row = 27; row < 97; row++)
        {
            // loop from 13 to just before the mirror point
            for (int col = 13; col < mirrorPoint; col++)
            {
                count++;
                leftPixel = pixels[row][col];      
                rightPixel = pixels[row]                       
                [mirrorPoint - col + mirrorPoint];
                rightPixel.setColor(leftPixel.getColor());
            }
        }
        System.out.println(count);
    }

    /** copy from the passed fromPic to the
     * specified startRow and startCol in the
     * current picture
     * @param fromPic the picture to copy from
     * @param startRow the start row to copy to
     * @param startCol the start col to copy to
     */
    public void copy(Picture fromPic, 
    int startRow, int startCol)
    {
        Pixel fromPixel = null;
        Pixel toPixel = null;
        Pixel[][] toPixels = this.getPixels2D();
        Pixel[][] fromPixels = fromPic.getPixels2D();
        for (int fromRow = 0, toRow = startRow; 
        fromRow < fromPixels.length &&
        toRow < toPixels.length; 
        fromRow++, toRow++)
        {
            for (int fromCol = 0, toCol = startCol; 
            fromCol < fromPixels[0].length &&
            toCol < toPixels[0].length;  
            fromCol++, toCol++)
            {
                fromPixel = fromPixels[fromRow][fromCol];
                toPixel = toPixels[toRow][toCol];
                toPixel.setColor(fromPixel.getColor());
            }
        }   
    }
    
    /** copy part of the picture from the passed fromPic to the
     * specified startRow and startCol in the
     * current picture
     * @param fromPic the picture to copy from
     * @param fromPicStartRow the start row to copy from
     * @param fromPicEndRow the end row to copy from
     * @param fromPicStartCol the start column to copy from
     * @param fromPicEndCol the end column to copy from
     * @param startRow the start row to copy to
     * @param startCol the start col to copy to
     */
    public void copy(Picture fromPic, int fromPicStartRow, int fromPicStartCol, int fromPicEndRow, int fromPicEndCol,
    int startRow, int startCol)
    {
        Pixel fromPixel = null;
        Pixel toPixel = null;
        Pixel[][] toPixels = this.getPixels2D();
        Pixel[][] fromPixels = fromPic.getPixels2D();
        for (int fromRow = fromPicStartRow, toRow = startRow; 
        fromRow < fromPixels.length &&
        fromRow < fromPicEndRow && 
        toRow < toPixels.length;
        fromRow++, toRow++)
        {
            for (int fromCol = fromPicStartCol, toCol = startCol; 
            fromCol < fromPixels[0].length &&
            fromCol < fromPicEndCol &&
            toCol < toPixels[0].length;  
            fromCol++, toCol++)
            {
                fromPixel = fromPixels[fromRow][fromCol];
                toPixel = toPixels[toRow][toCol];
                toPixel.setColor(fromPixel.getColor());
            }
        }   
    }

    /** Method to create a collage of several pictures */
    public void createCollage()
    {
        Picture flower1 = new Picture("flower1.jpg");
        Picture flower2 = new Picture("flower2.jpg");
        this.copy(flower1,0,0);
        this.copy(flower2,100,0);
        this.copy(flower1,200,0);
        Picture flowerNoBlue = new Picture(flower2);
        flowerNoBlue.zeroBlue();
        this.copy(flowerNoBlue,300,0);
        this.copy(flower1,400,0);
        this.copy(flower2,500,0);
        this.mirrorVertical();
        this.write("collage.jpg");
    }
    
    public void myCollage()
    {
        Picture flower1 = new Picture("flower1.jpg");
        this.copy(flower1,0,0,100,50,0,0);
        this.copy(flower1,0,50,100,100,100,50);
        flower1.mirrorVertical();
        this.copy(flower1,200,0);
        this.write("collage.jpg");
    }

    /** Method to show large changes in color 
     * @param edgeDist the distance for finding edges
     */
    public void edgeDetection(int edgeDist)
    {
        Pixel leftPixel = null;
        Pixel rightPixel = null;
        Pixel downPixel = null;
        Pixel[][] pixels = this.getPixels2D();
        Color rightColor = null;
        Color downColor = null;
        for (int row = 0; row < pixels.length-1; row++)
        {
            for (int col = 0; 
            col < pixels[0].length-1; col++)
            {
                leftPixel = pixels[row][col];
                rightPixel = pixels[row][col+1];
                downPixel = pixels[row+1][col];
                rightColor = rightPixel.getColor();
                downColor = downPixel.getColor();
                if (leftPixel.colorDistance(rightColor) > edgeDist || leftPixel.colorDistance(downColor) > edgeDist)
                    leftPixel.setColor(Color.BLACK);
                else
                    leftPixel.setColor(Color.WHITE);
            }
        }
    }
    
    /**
     * Method to show large changes in brightness
     * @param edgeDist the distance for finding edges
     */
    public void edgeDetection2(double edgeDist){
        Pixel[][] pixels = getPixels2D();
        for (int r = 0; r < pixels.length-1; r++){
            for (int c = 0; c < pixels[r].length-1; c++){
                Pixel pix = pixels[r][c];
                double brightness = pix.getAverage();
                if (Math.abs(brightness - pixels[r][c+1].getAverage()) > edgeDist){
                    pix.setColor(Color.BLACK);
                }
                else if (Math.abs(brightness - pixels[r+1][c].getAverage()) > edgeDist){
                    pix.setColor(Color.BLACK);
                }
                else {
                    pix.setColor(Color.WHITE);
                }
            }
        }
    }

    public void keepOnlyBlue(){
        Pixel[] pixels = getPixels();
        for (Pixel pix : pixels){
            pix.setRed(0);
            pix.setGreen(0);
        }
    }

    public void negate(){
        Pixel[] pixels = getPixels();
        for (Pixel pix : pixels){
            pix.setRed(255 - pix.getRed());
            pix.setGreen(255 - pix.getGreen());
            pix.setBlue(255 - pix.getBlue());
        }
    }

    public void grayscale(){
        Pixel[] pixels = getPixels();
        for (Pixel pix : pixels){
            int avg = (pix.getRed() + pix.getGreen() + pix.getBlue()) / 3;
            pix.setRed(avg);
            pix.setGreen(avg);
            pix.setBlue(avg);
        }
    }

    /**
     * Reduces blue and green levels by 50% and 60% and increases red levels by 200%
     */
    public void fixUnderwater(){
        Pixel[] pixels = getPixels();
        for (Pixel pix : pixels){
            pix.setRed((int)(pix.getRed() * 2.0));
            pix.setGreen((int)(pix.getGreen() * 0.4));
            pix.setBlue((int)(pix.getBlue() * 0.5));
        }
    }

    public void mirrorVerticalRightToLeft()
    {
        Pixel[][] pixels = this.getPixels2D();
        Pixel leftPixel = null;
        Pixel rightPixel = null;
        int width = pixels[0].length;
        for (int row = pixels.length - 1; row >= 0; row--)
        {
            for (int col = 0; col < width / 2; col++)
            {
                leftPixel = pixels[row][col];
                rightPixel = pixels[row][width - 1 - col];
                rightPixel.setColor(leftPixel.getColor());
            }
        } 
    }

    public void mirrorHorizontal(){
        Pixel[][] pixels = getPixels2D();
        for (int row = pixels.length / 2; row < pixels.length; row++){
            for (int col = 0; col < pixels[row].length; col++){
                pixels[row][col].setColor(pixels[pixels.length - row - 1][col].getColor());
            }
        }
    }

    public void mirrorHorizontalBotToTop(){
        Pixel[][] pixels = getPixels2D();
        for (int row = pixels.length - 1; row >= pixels.length / 2; row--){
            for (int col = 0; col < pixels[row].length; col++){
                pixels[row][col].setColor(pixels[pixels.length - row - 1][col].getColor());
            }
        }
    }

    public void mirrorDiagonal(){
        Pixel[][] pixels = getPixels2D();
        int size = Math.min(pixels.length, pixels[0].length);
        for (int c = 0; c < size; c++){
            for (int r = 0; r < c; r++){
                pixels[r][c].setColor(pixels[c][r].getColor());
            }
        }
    }
    
    public void mirrorArms(){
        int[] arm1 = {159, 105, 190, 169}; // (r, c, r2, c2)
        int[] arm2 = {172, 239, 195, 293};
        int mirrorRow = 191;
        
        Pixel[][] pixels = getPixels2D();
        for (int r = 0; r < pixels.length; r++){
            for (int c = 0; c < pixels[r].length; c++){
                if ((r >= arm1[0] && r <= arm1[2] && c >= arm1[1] && c <= arm1[3]) || (r >= arm2[0] && r <= arm2[2] && c >= arm2[1] && c <= arm2[3])){
                    pixels[2 * mirrorRow - r][c].setColor(pixels[r][c].getColor());
                }
            }
        }
    }
    
    public void mirrorGull(){
        int[] box = {234, 238, 319, 343}; // (r, c, r2, c2)
        int mirrorColumn = 343;
        
        Pixel[][] pixels = getPixels2D();
        for (int r = box[0]; r <= box[2]; r++){
            for (int c = box[1]; c < box[3]; c++){
                pixels[r][2 * mirrorColumn - c].setColor(pixels[r][c].getColor());
            }
        }
    }

    /* Main method for testing - each class in Java can have a main 
     * method 
     */
    public static void main(String[] args) 
    {
        Picture beach = new Picture("beach.jpg");
        beach.explore();
        beach.zeroBlue();
        beach.explore();
    }

} // this } is the end of class Picture, put all new methods before this
