package tile;

import lombok.Getter;
import main.GamePanel;
import main.ScreenPositionKeeper;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

import static main.GamePanel.*;

public class TileManager extends ScreenPositionKeeper {

    GamePanel gp;

    @Getter
    Tile[] tile;

    @Getter
    int[][] mapTileNum;

    UtilityTool uTool = new UtilityTool();

    public TileManager(GamePanel gp) {
        this.gp = gp;
        tile = new Tile[26]; // 25 types of tiles

        mapTileNum = new int[MAX_WORLD_COLUMN][MAX_WORLD_ROW];
        getTileImage();
        loadMap("/maps/map.txt");
    }

    public void getTileImage() {
        // LOCATION: PARK

        // GRASS
        setup(0, "park", "grasses/grass", false);
        setup(1, "park", "grasses/grass_2", false);
        setup(2, "park", "grasses/grass_3", false);
        setup(3, "park", "grasses/grass_4", false);

        // WATER
        // FILL
        setup(4, "park", "water/water_fill", true);
        // BOTTOM LEFT
        setup(5, "park", "water/water_angle_bottom_left", true);
        // BOTTOM RIGHT
        setup(6, "park", "water/water_angle_bottom_right", true);
        // TOP LEFT
        setup(7, "park", "water/water_angle_top_left", true);
        // TOP RIGHT
        setup(8, "park", "water/water_angle_top_right", true);
        // SIDE BOTTOM
        setup(9, "park", "water/water_side_bottom", true);
        // SIDE LEFT
        setup(10, "park", "water/water_side_left", true);
        // SIDE RIGHT
        setup(11, "park", "water/water_side_right", true);
        // SIDE TOP
        setup(12, "park", "water/water_side_top", true);

        // GROUND
        //  FILL
        setup(13, "park", "grounds/ground_fill", false);
        // BOTTOM LEFT
        setup(14, "park", "grounds/ground_angle_bottom_left", false);
        // BOTTOM RIGHT
        setup(15, "park", "grounds/ground_angle_bottom_right", false);
        // TOP LEFT
        setup(16, "park", "grounds/ground_angle_top_left", false);
        // TOP RIGHT
        setup(17, "park", "grounds/ground_angle_top_right", false);
        // SIDE BOTTOM
        setup(18, "park", "grounds/ground_side_bottom", false);
        // SIDE LEFT
        setup(19, "park", "grounds/ground_side_left", false);
        // SIDE RIGHT
        setup(20, "park", "grounds/ground_side_right", false);
        // SIDE TOP
        setup(21, "park", "grounds/ground_side_top", false);
        // PART BOTTOM LEFT
        setup(22, "park", "grounds/ground_part_bottom_left", false);
        // PART BOTTOM RIGHT
        setup(23, "park", "grounds/ground_part_bottom_right", false);
        // PART TOP LEFT
        setup(24, "park", "grounds/ground_part_top_left", false);
        // PART TOP RIGHT
        setup(25, "park", "grounds/ground_part_top_right", false);
    }

    public void setup(int index, String location, String imagePath, boolean collision) {

        try {
            tile[index] = new Tile();
            tile[index].setImage(ImageIO.read(Objects.requireNonNull(
                    getClass().getResourceAsStream("/" + location + "/tiles/" + imagePath + ".png"))));
            tile[index].setImage(uTool.scaleImage(tile[index].image, TILE_SIZE, TILE_SIZE));
            tile[index].setCollision(collision);

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
           screenX = worldX - gp.player.worldX + gp.player.screenX;
           screenY = worldY - gp.player.worldY + gp.player.screenY;

           // STOP MOVING THE CAMERA AT THE EDGE
           uTool.adjustCamera(gp, this, worldX, worldY);

           if (worldX + TILE_SIZE > gp.player.worldX - gp.player.screenX &&
                worldX - TILE_SIZE < gp.player.worldX  + gp.player.screenX &&
                worldY + TILE_SIZE > gp.player.worldY - gp.player.screenY &&
                worldY - TILE_SIZE < gp.player.worldY + gp.player.screenY) {
               g2.drawImage(tile[tileNum].getImage(), screenX, screenY, null);
           }
           else if (gp.player.screenX > gp.player.worldX || gp.player.screenY > gp.player.worldY ||
                   SCREEN_WIDTH - gp.player.screenX > WORLD_WIDTH - gp.player.worldX ||
                   SCREEN_HEIGHT - gp.player.screenY > WORLD_HEIGHT - gp.player.worldY) {
               g2.drawImage(tile[tileNum].getImage(), screenX, screenY, null);
           }
           worldCol++;

           if(worldCol == MAX_WORLD_COLUMN) {
               worldCol = 0;
               worldRow++;
           }
       }
    }
}
