package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

import static main.GamePanel.*;

public class TileManager {

    GamePanel gp;
    Tile[] tile;
    int mapTileNum[][];

    public TileManager(GamePanel gp) {
        this.gp = gp;
        tile = new Tile[10]; // 10 types of tiles

        mapTileNum = new int[getMAX_SCREEN_COLUMN()][getMAX_SCREEN_ROW()];
        getTileImage();
        loadMap("/maps/map.txt");
    }

    public void getTileImage() {
        try {
            tile[0] = new Tile();
            tile[0].setImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/grass.png"))));

            tile[1] = new Tile();
            tile[1].setImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/ground.png"))));

            tile[2] = new Tile();
            tile[2].setImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/water.png"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // LOAD THE TILES FROM txt FILE
    public void loadMap(String filePath) {
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            assert is != null;

            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while (col < getMAX_SCREEN_COLUMN() && row < getMAX_SCREEN_ROW()) {
                String line = br.readLine();

                while(col < getMAX_SCREEN_COLUMN()) {
                    String[] numbers = line.split(" ");

                    int num = Integer.parseInt(numbers[col]);

                    mapTileNum[col][row] = num;
                    col++;
                }

                if(col == getMAX_SCREEN_COLUMN()) {
                    col = 0;
                    row++;
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {

       int col = 0;
       int row = 0;
       int x = 0;
       int y = 0;

       while(col < getMAX_SCREEN_COLUMN() && row < getMAX_SCREEN_ROW()) {

           int tileNum = mapTileNum[col][row];

           g2.drawImage(tile[tileNum].getImage(), x, y, TILE_SIZE, TILE_SIZE, null);
           col++;
           x += TILE_SIZE;

           if(col == getMAX_SCREEN_COLUMN()) {
               col = 0;
               x = 0;
               row++;
               y += TILE_SIZE;
           }
       }
    }
}
