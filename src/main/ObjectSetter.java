package main;

import object.*;

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

        gp.objects[1].worldX = 10 * TILE_SIZE;
        gp.objects[1].worldY = 19 * TILE_SIZE;

        gp.objects[2] = new OBJ_Tree_1();

        gp.objects[2].worldX = 23 * TILE_SIZE;
        gp.objects[2].worldY = 7 * TILE_SIZE;

        // TREE 2
        gp.objects[3] = new OBJ_Tree_2();

        gp.objects[3].worldX = 17 * TILE_SIZE;
        gp.objects[3].worldY = 4 * TILE_SIZE;

        gp.objects[4] = new OBJ_Tree_2();

        gp.objects[4].worldX = 29 * TILE_SIZE;
        gp.objects[4].worldY = 17 * TILE_SIZE;

        // BUSH 1
        gp.objects[5] = new OBJ_Bush_1();

        gp.objects[5].worldX = 25 * TILE_SIZE;
        gp.objects[5].worldY = 15 * TILE_SIZE;

        /*gp.objects[6] = new OBJ_Bush_1();

        gp.objects[6].worldX = 15 * TILE_SIZE;
        gp.objects[6].worldY = 21 * TILE_SIZE;*/

        // BUSH 2
        gp.objects[7] = new OBJ_Bush_2();

        gp.objects[7].worldX = 22 * TILE_SIZE;
        gp.objects[7].worldY = 9 * TILE_SIZE;

        gp.objects[8] = new OBJ_Bush_2();

        gp.objects[8].worldX = 24 * TILE_SIZE;
        gp.objects[8].worldY = 10 * TILE_SIZE;

        // BUSH 3
        gp.objects[9] = new OBJ_Bush_3();

        gp.objects[9].worldX = 6 * TILE_SIZE;
        gp.objects[9].worldY = 20 * TILE_SIZE;

//        gp.objects[10] = new OBJ_Bush_3();
//
//        gp.objects[10].worldX = 15 * TILE_SIZE;
//        gp.objects[10].worldY = 10 * TILE_SIZE;
    }
}
