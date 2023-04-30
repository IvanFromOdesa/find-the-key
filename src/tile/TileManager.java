package tile;

import lombok.Getter;
import main.GamePanel;
import main.PositionKeeper;
import main.UtilityTool;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

import static main.GamePanel.*;

public class TileManager extends PositionKeeper {

    GamePanel gp;

    @Getter
    Tile[] tile;

    @Getter
    int[][] mapTileNum;
    private final boolean drawPath = true;

    public TileManager(GamePanel gp) {
        this.gp = gp;
        tile = new Tile[44]; // 44 types of tiles

        mapTileNum = new int[MAX_WORLD_COLUMN][MAX_WORLD_ROW];
        getTileImage();
        loadMap("/maps/map.txt");
    }

    private void getTileImage() {
        // LOCATION: PARK

        // GRASS
        setup(0, "park", "grasses/grass", false);
        setup(1, "park", "grasses/grass_2", false);
        setup(2, "park", "grasses/grass_3", false);
        setup(3, "park", "grasses/grass_4", false);
        setup(31, "park", "grasses/grass_5", false);
        setup(32, "park", "grasses/grass_6", false);
        setup(33, "park", "grasses/grass_7", false);
        setup(34, "park", "grasses/grass_8", false);
        setup(35, "park", "grasses/grass_9", false);
        setup(36, "park", "grasses/grass_10", false);
        setup(37, "park", "grasses/grass_11", false);
        setup(38, "park", "grasses/grass_12", false);
        setup(39, "park", "grasses/grass_13", false);
        setup(40, "park", "grasses/grass_14", false);

        // COBBLESTONE PATH
        setup(41, "park", "cob_pathes/cob_path_1", false);
        setup(42, "park", "cob_pathes/cob_path_2", false);
        setup(43, "park", "cob_pathes/cob_path_3", false);

        // WATER
        // FILL
        setGIF(4, "park", "water/water_fill", true);
        // BOTTOM LEFT
        setGIF(5, "park", "water/water_angle_bottom_left", true);
        // BOTTOM RIGHT
        setGIF(6, "park", "water/water_angle_bottom_right", true);
        // TOP LEFT
        setGIF(7, "park", "water/water_angle_top_left", true);
        // TOP RIGHT
        setGIF(8, "park", "water/water_angle_top_right", true);
        // SIDE BOTTOM
        setGIF(9, "park", "water/water_side_bottom", true);
        // SIDE LEFT
        setGIF(10, "park", "water/water_side_left", true);
        // SIDE RIGHT
        setGIF(11, "park", "water/water_side_right", true);
        // SIDE TOP
        setGIF(12, "park", "water/water_side_top", true);

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

        // NENUPHAR
        setup(26, "park", "water/nenuphar", true);
        // NENUPHAR V2 WITHOUT LITTLE NENUPHAR
        setup(27, "park", "water/nenuphar_v2", true);
        // LILIA V1
        setup(28, "park", "water/lilia_v1", true);
        // LILIA V2
        setup(29, "park", "water/lilia_v2", true);
        // LILIA V3
        setup(30, "park", "water/lilia_v3", true);

    }

    public void setGIF(int index, String location, String imagePath, boolean collision) {
        tile[index] = new Tile();
        tile[index].setIcon(new ImageIcon(Objects.requireNonNull(
                getClass().getResource("/" + location + "/tiles/" + imagePath + ".gif"))).getImage());
        tile[index].setIcon(tile[index].getIcon().getScaledInstance(TILE_SIZE, TILE_SIZE, Image.SCALE_DEFAULT));
        tile[index].setCollision(collision);
        tile[index].setAnimated(true);
    }

    public void setup(int index, String location, String imagePath, boolean collision) {

        try {
            tile[index] = new Tile();
            tile[index].setImage(ImageIO.read(Objects.requireNonNull(
                    getClass().getResourceAsStream("/" + location + "/tiles/" + imagePath + ".png"))));
            tile[index].setImage(UtilityTool.scaleImage(tile[index].getImage(), TILE_SIZE, TILE_SIZE));
            tile[index].setCollision(collision);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // LOAD THE TILES FROM txt FILE
    private void loadMap(String filePath) {
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

    @Override
    public void draw(Graphics2D g2) {

       int worldCol = 0;
       int worldRow = 0;

       while(worldCol < MAX_WORLD_COLUMN && worldRow < MAX_WORLD_ROW) {

           int tileNum = mapTileNum[worldCol][worldRow];

           // POSITION ON THE MAP
           worldX = worldCol * TILE_SIZE;
           worldY = worldRow * TILE_SIZE;

           // WHERE ON THE SCREEN THE TILE IS DRAWN
           screenX = worldX - gp.player.worldX + gp.player.screenX;
           screenY = worldY - gp.player.worldY + gp.player.screenY;

           // STOP MOVING THE CAMERA AT THE EDGE
           UtilityTool.adjustCamera(gp, this, worldX, worldY);

           if (worldX + TILE_SIZE > gp.player.worldX - gp.player.screenX &&
                worldX - TILE_SIZE < gp.player.worldX  + gp.player.screenX &&
                worldY + TILE_SIZE > gp.player.worldY - gp.player.screenY &&
                worldY - TILE_SIZE < gp.player.worldY + gp.player.screenY &&
                   !tile[tileNum].isAnimated()) {
               g2.drawImage(tile[tileNum].getImage(), screenX, screenY, null);
           }
           else if ((gp.player.screenX > gp.player.worldX || gp.player.screenY > gp.player.worldY ||
                   SCREEN_WIDTH - gp.player.screenX > WORLD_WIDTH - gp.player.worldX ||
                   SCREEN_HEIGHT - gp.player.screenY > WORLD_HEIGHT - gp.player.worldY) &&
                   !tile[tileNum].isAnimated()) {
               g2.drawImage(tile[tileNum].getImage(), screenX, screenY, null);
           }

           // DRAW WHOLE ANIMATED TILES
           if(tile[tileNum].isAnimated()) {
               g2.drawImage(tile[tileNum].getIcon(), screenX, screenY, null);
           }
           worldCol++;

           if(worldCol == MAX_WORLD_COLUMN) {
               worldCol = 0;
               worldRow++;
           }
       }

       if (drawPath) {
           g2.setColor(new Color(255, 0, 0, 70));

           for (int i = 0; i < gp.pf.getPathList().size(); i ++) {
               int worldX = gp.pf.getPathList().get(i).getCol() * TILE_SIZE;
               int worldY = gp.pf.getPathList().get(i).getRow() * TILE_SIZE;
               int screenX = worldX - gp.player.worldX + gp.player.screenX;
               int screenY = worldY - gp.player.worldY + gp.player.screenY;

               g2.fillRect(screenX, screenY, TILE_SIZE, TILE_SIZE);
           }
       }
    }
}
