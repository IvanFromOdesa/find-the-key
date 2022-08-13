package main;

import object.OBJ_Tree_1;

import static main.GamePanel.TILE_SIZE;

public class ObjectSetter {

    GamePanel gp;

    public ObjectSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {

        // TREES
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
    }
}
