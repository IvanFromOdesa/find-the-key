package tile;

import lombok.Getter;
import lombok.Setter;

import java.awt.image.BufferedImage;

@Getter
@Setter
public class Tile  {

    public BufferedImage image;
    public boolean collision = false;

}
