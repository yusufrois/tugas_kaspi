/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package handler;

//import ij.plugin.PGM_Reader;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

/**
 *
 * @author agus
 */
public class ImageResizer {
    private Image sourceImage;
    private int sourceImageWidth,  sourceImageHeight;
    
    public ImageResizer() {
    }
    public ImageResizer(Image sourceImage) {
        this.sourceImage = sourceImage;
        sourceImageWidth = sourceImage.getWidth(null);
        sourceImageHeight = sourceImage.getHeight(null);
    }

    
    public BufferedImage resizeTo(int desiredWidth, int desiredHeight, Color bgColor) {
        BufferedImage targetImage = new BufferedImage(desiredWidth, desiredHeight, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = targetImage.getGraphics();
        graphics.setColor(bgColor);
        graphics.fillRect(0, 0, desiredWidth, desiredHeight);
        
        float ratio = 0;
        
        float targetImageRatio = (float) desiredWidth / (float) desiredHeight;
        float sourceImageRatio = (float) sourceImageWidth / (float) sourceImageHeight;
        int x = 0, y = 0;
        int newWidth = 0, newHeight = 0;
        
        if (targetImageRatio < sourceImageRatio) {
            ratio = (float) desiredWidth / (float) sourceImageWidth;
        } else {
            ratio = (float) desiredHeight / (float) sourceImageHeight;
        }
        
        newWidth = (int) (sourceImageWidth * ratio);
        newHeight = (int) (sourceImageHeight * ratio);
        x = (desiredWidth - newWidth) / 2;
        y = (desiredHeight - newHeight) / 2;
        
        Image tempImage = sourceImage.getScaledInstance(newWidth, newHeight, 0);
        graphics.drawImage(tempImage, x, y, null);
        
        return targetImage;
    }

}
