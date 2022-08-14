package tile;

import lombok.Getter;
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

    @Getter
    Tile[] tile;

    @Getter
    int[][] mapTileNum;

    public TileManager(GamePanel gp) {
        this.gp = gp;
        tile = new Tile[26]; // 25 types of tiles

        mapTileNum = new int[MAX_WORLD_COLUMN][MAX_WORLD_ROW];
        getTileImage();
        loadMap("/maps/map.txt");
    }

    public void getTileImage() {
        try {
            // LOCATION: PARK

            // GRASS
            tile[0] = new Tile();
            tile[0].setImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/park/grasses/grass.png"))));

            tile[1] = new Tile();
            tile[1].setImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/park/grasses/grass_2.png"))));

            tile[2] = new Tile();
            tile[2].setImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/park/grasses/grass_3.png"))));

            tile[3] = new Tile();
            tile[3].setImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/park/grasses/grass_4.png"))));

            // WATER
            // FILL
            tile[4] = new Tile();
            tile[4].setImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/park/water/water_fill.png"))));
            tile[4].setCollision(true);

            // BOTTOM LEFT
            tile[5] = new Tile();
            tile[5].setImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/park/water/water_angle_bottom_left.png"))));
            tile[5].setCollision(true);

            // BOTTOM RIGHT
            tile[6] = new Tile();
            tile[6].setImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/park/water/water_angle_bottom_right.png"))));
            tile[6].setCollision(true);

            // TOP LEFT
            tile[7] = new Tile();
            tile[7].setImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/park/water/water_angle_top_left.png"))));
            tile[7].setCollision(true);

            // TOP RIGHT
            tile[8] = new Tile();
            tile[8].setImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/park/water/water_angle_top_right.png"))));
            tile[8].setCollision(true);

            // SIDE BOTTOM
            tile[9] = new Tile();
            tile[9].setImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/park/water/water_side_bottom.png"))));
            tile[9].setCollision(true);

            // SIDE LEFT
            tile[10] = new Tile();
            tile[10].setImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/park/water/water_side_left.png"))));
            tile[10].setCollision(true);

            // SIDE RIGHT
            tile[11] = new Tile();
            tile[11].setImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/park/water/water_side_right.png"))));
            tile[11].setCollision(true);

            // SIDE TOP
            tile[12] = new Tile();
            tile[12].setImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/park/water/water_side_top.png"))));
            tile[12].setCollision(true);

            // GROUND
            //  FILL
            tile[13] = new Tile();
            tile[13].setImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/park/grounds/ground_fill.png"))));

            // BOTTOM LEFT
            tile[14] = new Tile();
            tile[14].setImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/park/grounds/ground_angle_bottom_left.png"))));

            // BOTTOM RIGHT
            tile[15] = new Tile();
            tile[15].setImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/park/grounds/ground_angle_bottom_right.png"))));

            // TOP LEFT
            tile[16] = new Tile();
            tile[16].setImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/park/grounds/ground_angle_top_left.png"))));

            // TOP RIGHT
            tile[17] = new Tile();
            tile[17].setImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/park/grounds/ground_angle_top_right.png"))));

            // SIDE BOTTOM
            tile[18] = new Tile();
            tile[18].setImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/park/grounds/ground_side_bottom.png"))));

            // SIDE LEFT
            tile[19] = new Tile();
            tile[19].setImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/park/grounds/ground_side_left.png"))));

            // SIDE RIGHT
            tile[20] = new Tile();
            tile[20].setImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/park/grounds/ground_side_right.png"))));

            // SIDE TOP
            tile[21] = new Tile();
            tile[21].setImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/park/grounds/ground_side_top.png"))));

            // PART BOTTOM LEFT
            tile[22] = new Tile();
            tile[22].setImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/park/grounds/ground_part_bottom_left.png"))));

            // PART BOTTOM RIGHT
            tile[23] = new Tile();
            tile[23].setImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/park/grounds/ground_part_bottom_right.png"))));

            // PART TOP LEFT
            tile[24] = new Tile();
            tile[24].setImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/park/grounds/ground_part_top_left.png"))));

            // PART TOP RIGHT
            tile[25] = new Tile();
            tile[25].setImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/park/grounds/ground_part_top_right.png"))));

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

            while (col < MAX_WORLD_COLUMN && row < MAX_WORLD_ROW) {
                String line = br.readLine();

                while(col < MAX_WORLD_COLUMN) {
                    String[] numbers = line.split(" ");

                    int num = Integer.parseInt(numbers[col]);

                    mapTileNum[col][row] = num;
                    col++;
                }

                if(col == MAX_WORLD_COLUMN) {
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

       int worldCol = 0;
       int worldRow = 0;

       while(worldCol < MAX_WORLD_COLUMN && worldRow < MAX_WORLD_ROW) {

           int tileNum = mapTileNum[worldCol][worldRow];

           // POSITION ON THE MAP
           int worldX = worldCol * TILE_SIZE;
           int worldY = worldRow * TILE_SIZE;

           // WHERE ON THE SCREEN THE TILE IS DRAWN
           int screenX = worldX - gp.player.worldX + gp.player.screenX;
           int screenY = worldY - gp.player.worldY + gp.player.screenY;

           if (worldX + TILE_SIZE > gp.player.worldX - gp.player.screenX &&
                worldX - TILE_SIZE < gp.player.worldX  + gp.player.screenX &&
                worldY + TILE_SIZE > gp.player.worldY - gp.player.screenY &&
                worldY - TILE_SIZE < gp.player.worldY + gp.player.screenY) {
               g2.drawImage(tile[tileNum].getImage(), screenX, screenY, TILE_SIZE, TILE_SIZE, null);
           }
           worldCol++;

           if(worldCol == MAX_WORLD_COLUMN) {
               worldCol = 0;
               worldRow++;
           }
       }
    }
}
