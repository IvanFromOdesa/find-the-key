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
        setEntitiesByTile(gp.objects, new OBJ_Tree_1(gp), 0, 9, 17);
        setEntitiesByTile(gp.objects, new OBJ_Tree_1(gp), 1, 10, 19);
        setEntitiesByTile(gp.objects, new OBJ_Tree_1(gp), 2, 23, 7);
        setEntitiesByTile(gp.objects, new OBJ_Tree_1(gp), 6, 2, 1);
        setEntitiesManually(gp.objects, new OBJ_Tree_1(gp), 12, 150, 30);
        setEntitiesManually(gp.objects, new OBJ_Tree_1(gp), 15, 1230, 25);

        // TREE 2
        setEntitiesByTile(gp.objects, new OBJ_Tree_2(gp), 3, 17, 4);
        setEntitiesByTile(gp.objects, new OBJ_Tree_2(gp), 4, 29, 17);

        // BUSH 1
        setEntitiesByTile(gp.objects, new OBJ_Bush_1(gp), 5, 25, 15);
        setEntitiesManually(gp.objects, new OBJ_Bush_1(gp), 13, 78, 165);

        // BUSH 2
        setEntitiesByTile(gp.objects, new OBJ_Bush_2(gp), 7, 22, 9);
        setEntitiesByTile(gp.objects, new OBJ_Bush_2(gp), 8, 24, 10);

        // BUSH 3
        setEntitiesByTile(gp.objects, new OBJ_Bush_3(gp), 9, 6, 20);

        // ROCK 1
        setEntitiesByTile(gp.objects, new OBJ_Rock_1(gp), 10, 14, 23);
        setEntitiesManually(gp.objects, new OBJ_Rock_1(gp), 11, 20, 20);

        // TRUNK 1
        setEntitiesByTile(gp.objects, new OBJ_Trunk_1(gp), 14, 24, 5);
    }

    public void setNpc() {
        // MONK
        setEntitiesByTile(gp.npc, new NPC_Monk(gp), 0, 12, 12);
        // NATALIE
        setEntitiesByTile(gp.npc, new NPC_Natalie(gp), 1, 11, 11);
    }

    private <T extends PositionKeeper> void setEntitiesByTile(T[] array, T ent, int index, int worldX, int worldY) {
        array[index] = ent;
        array[index].worldX = TILE_SIZE * worldX;
        array[index].worldY = TILE_SIZE * worldY;
    }

    private <T extends PositionKeeper> void setEntitiesManually(T[] array, T ent, int index, int worldX, int worldY) {
        array[index] = ent;
        array[index].worldX = worldX;
        array[index].worldY = worldY;
    }
}
