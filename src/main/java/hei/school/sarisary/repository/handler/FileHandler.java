package hei.school.sarisary.repository.handler;

import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import org.springframework.stereotype.Component;

@Component
public class FileHandler {
  public BufferedImage changeColor(BufferedImage bufferImg) {
    ColorSpace setColor = ColorSpace.getInstance(ColorSpace.CS_GRAY);
    ColorConvertOp conColor = new ColorConvertOp(setColor, null);
    BufferedImage newBufferedImg = conColor.filter(bufferImg, null);
    return newBufferedImg;
  }
}
