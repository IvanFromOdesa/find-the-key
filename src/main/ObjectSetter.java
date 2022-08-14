package main;

import object.OBJ_Tree_1;
import object.OBJ_Tree_2;

import static main.GamePanel.TILE_SIZE;

public class ObjectSetter {

    GamePanel gp;

    public ObjectSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {

        // TREE 1
        gp.objects[0] = new OBJ_Tree_1();

        // COORDINATES
        gp.objects[0].worldX = 9 * TILE_SIZE;
        gp.objects[0].worldY = 17 * TILE_SIZE;

        gp.objects[1] = new OBJ_Tree_1();

        // COORDINATES
        gp.objects[1].worldX = 14 * TILE_SIZE;
        gp.objects[1].worldY = 13 * TILE_SIZE;

        gp.objects[2] = new OBJ_Tree_1();

        // COORDINATES
        gp.objects[2].worldX = 25 * TILE_SIZE;
        gp.objects[2].worldY = 7 * TILE_SIZE;

        // TREE 2
        gp.objects[3] = new OBJ_Tree_2();

        // COORDINATES
        gp.objects[3].worldX = 17 * TILE_SIZE;
        gp.objects[3].worldY = 4 * TILE_SIZE;

        gp.objects[4] = new OBJ_Tree_2();

        // COORDINATES
        gp.objects[4].worldX = 29 * TILE_SIZE;
        gp.objects[4].worldY = 17 * TILE_SIZE;
    }
}
