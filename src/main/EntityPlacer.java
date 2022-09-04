package main;

import entity.NPC_Elvie;
import entity.NPC_Monk;
import entity.NPC_Natalie;
import entity.NPC_Viki;
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
        setEntitiesManually(gp.objects, new OBJ_Tree_1(gp), 25, 1368, 520);

        // TREE 2
        setEntitiesByTile(gp.objects, new OBJ_Tree_2(gp), 3, 17, 4);
        setEntitiesByTile(gp.objects, new OBJ_Tree_2(gp), 4, 29, 17);

        // TREE 3
        //setEntitiesByTile(gp.objects, new OBJ_Tree_3(gp), 16, 2, 21);
        setEntitiesByTile(gp.objects, new OBJ_Tree_3(gp), 17, 33, 20);
        setEntitiesByTile(gp.objects, new OBJ_Tree_3(gp), 21, 30, 7);
        setEntitiesByTile(gp.objects, new OBJ_Tree_3(gp), 22, 32, 7);
        setEntitiesByTile(gp.objects, new OBJ_Tree_3(gp), 23, 34, 7);
        setEntitiesByTile(gp.objects, new OBJ_Tree_3(gp), 24, 36, 7);

        // TREE 4
        /*setEntitiesByTile(gp.objects, new OBJ_Tree_4(gp), 18, 1, 6);
        setEntitiesByTile(gp.objects, new OBJ_Tree_4(gp), 19, 2, 10);
        setEntitiesByTile(gp.objects, new OBJ_Tree_4(gp), 20, 39, 4);*/

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
        setEntitiesByTile(gp.npc, new NPC_Natalie(gp), 1, 13, 12);
        // VIKI
        setEntitiesByTile(gp.npc, new NPC_Viki(gp), 2, 29, 16);
        // ELVIE
        setEntitiesByTile(gp.npc, new NPC_Elvie(gp), 3, 14, 13);
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
