package main;

import entity.NPC_Monk;
import entity.NPC_Natalie;
import object.*;

import static main.GamePanel.TILE_SIZE;

/**
 *  Places objects and entities on the map
 */
public class EntityPlacer {

    GamePanel gp;

    public EntityPlacer(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {

        // TREE 1
        setEntities(gp.objects, new OBJ_Tree_1(), 0, 9, 17);
        setEntities(gp.objects, new OBJ_Tree_1(), 1, 10, 19);
        setEntities(gp.objects, new OBJ_Tree_1(), 2, 23, 7);

        // TREE 2
        setEntities(gp.objects, new OBJ_Tree_2(), 3, 17, 4);
        setEntities(gp.objects, new OBJ_Tree_2(), 4, 29, 17);

        // BUSH 1
        setEntities(gp.objects, new OBJ_Bush_1(), 5, 25, 15);
        //setEntities(gp.objects, new OBJ_Bush_1(), 6, 15, 21);

        // BUSH 2
        setEntities(gp.objects, new OBJ_Bush_2(), 7, 22, 9);
        setEntities(gp.objects, new OBJ_Bush_2(), 8, 24, 10);

        // BUSH 3
        setEntities(gp.objects, new OBJ_Bush_3(), 9, 6, 20);

        // ROCK 1
        setEntities(gp.objects, new OBJ_Rock_1(), 10, 14, 23);
    }

    public void setNpc() {
        // MONK
        setEntities(gp.npc, new NPC_Monk(gp), 0, 12, 12);
        // NATALIE
        setEntities(gp.npc, new NPC_Natalie(gp), 1, 11, 11);
    }

    private <T extends PositionKeeper> void setEntities(T[] array, T ent, int index, int worldX, int worldY) {
        array[index] = ent;
        array[index].worldX = TILE_SIZE * worldX;
        array[index].worldY = TILE_SIZE * worldY;
    }
}
