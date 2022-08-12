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
    int[][] mapTileNum;

    public TileManager(GamePanel gp) throws IOException {
        this.gp = gp;
        tile = new Tile[11]; // 11 types of tiles

        mapTileNum = new int[MAX_SCREEN_COLUMN][MAX_SCREEN_ROW];
        getTileImage();
        loadMap("/maps/map.txt");
    }

    public void getTileImage() throws IOException {
        tile[0] = new Tile();
        tile[0].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/grass.png")));

        tile[1] = new Tile();
        tile[1].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/ground.png")));

        tile[2] = new Tile();
        tile[2].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/water/water_fill.png")));

        tile[3] = new Tile();
        tile[3].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/water/water_angle_top_left.png")));

        tile[4] = new Tile();
        tile[4].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/water/water_angle_top_right.png")));

        tile[5] = new Tile();
        tile[5].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/water/water_angle_bottom_left.png")));

        tile[6] = new Tile();
        tile[6].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/water/water_angle_bottom_right.png")));

        tile[7] = new Tile();
        tile[7].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/water/water_side_top.png")));

        tile[8] = new Tile();
        tile[8].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/water/water_side_left.png")));

        tile[9] = new Tile();
        tile[9].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/water/water_side_right.png")));

        tile[10] = new Tile();
        tile[10].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/water/water_side_bottom.png")));

    }

    // LOAD THE TILES FROM txt FILE
    public void loadMap(String filePath) {
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            assert is != null;

            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while (col < MAX_SCREEN_COLUMN && row < MAX_SCREEN_ROW) {
                String line = br.readLine();

                while(col < MAX_SCREEN_COLUMN) {
                    String[] numbers = line.split(" ");

                    int num = Integer.parseInt(numbers[col]);

                    mapTileNum[col][row] = num;
                    col++;
                }

                if(col == MAX_SCREEN_COLUMN) {
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

       while(col < MAX_SCREEN_COLUMN && row < MAX_SCREEN_ROW) {

           int tileNum = mapTileNum[col][row];

           g2.drawImage(tile[tileNum].image, x, y, TILE_SIZE, TILE_SIZE, null);
           col++;
           x += TILE_SIZE;

           if(col == MAX_SCREEN_COLUMN) {
               col = 0;
               x = 0;
               row++;
               y += TILE_SIZE;
           }
       }
    }
}
