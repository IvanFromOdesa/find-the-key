package main;

import entity.enemy.ENM_Green_Slime;
import entity.npc.NPC_Elvie;
import entity.npc.NPC_Monk;
import entity.npc.NPC_Natalie;
import entity.npc.NPC_Viki;
import object.building.BLD_Family_House;
import object.building.wooden_fence.*;
import object.obstacle.bush.OBJ_Bush_1;
import object.obstacle.bush.OBJ_Bush_2;
import object.obstacle.bush.OBJ_Bush_3;
import object.obstacle.rock.OBJ_Rock_1;
import object.obstacle.tree.*;

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

        // FAMILY HOUSE
        setEntitiesManually(gp.objects, new BLD_Family_House(gp), 26, 130, 495);

        setEntitiesManually(gp.objects, new BLD_Wooden_Fence_1(gp), 27, 60, 800);
        setEntitiesManually(gp.objects, new BLD_Wooden_Fence_2(gp), 30, 32, 743);
        setEntitiesManually(gp.objects, new BLD_Wooden_Fence_4(gp), 31, 32, 692);
        setEntitiesManually(gp.objects, new BLD_Wooden_Fence_4(gp), 33, 32, 638);
        setEntitiesManually(gp.objects, new BLD_Wooden_Fence_4(gp), 35, 32, 584);
        setEntitiesManually(gp.objects, new BLD_Wooden_Fence_4(gp), 37, 32, 530);
        setEntitiesManually(gp.objects, new BLD_Wooden_Fence_7(gp), 39, 32, 476);
        setEntitiesManually(gp.objects, new BLD_Wooden_Fence_5(gp), 41, 60, 476);

        setEntitiesManually(gp.objects, new BLD_Wooden_Fence_1(gp), 28, 250, 800);
        setEntitiesManually(gp.objects, new BLD_Wooden_Fence_3(gp), 29, 350, 743);
        setEntitiesManually(gp.objects, new BLD_Wooden_Fence_4(gp), 32, 350, 692);
        setEntitiesManually(gp.objects, new BLD_Wooden_Fence_4(gp), 34, 350, 638);
        setEntitiesManually(gp.objects, new BLD_Wooden_Fence_4(gp), 36, 350, 584);
        setEntitiesManually(gp.objects, new BLD_Wooden_Fence_4(gp), 38, 350, 530);
        setEntitiesManually(gp.objects, new BLD_Wooden_Fence_4(gp), 40, 350, 476);
        setEntitiesManually(gp.objects, new BLD_Wooden_Fence_5(gp), 42, 250, 476);
        setEntitiesManually(gp.objects, new BLD_Wooden_Fence_6(gp), 43, 167, 473);

        // CHERRY TREE
        setEntitiesManually(gp.objects, new OBJ_Tree_Cherry(gp), 44, 65, 415);
    }

    /**
     * Sets entities statically at the start of the game
     */
    public void setEntities() {
        // MONK
        setEntitiesByTile(gp.entities, new NPC_Monk(gp), 0, 12, 12);
        // NATALIE
        setEntitiesByTile(gp.entities, new NPC_Natalie(gp), 1, 13, 12);
        // VIKI
        setEntitiesByTile(gp.entities, new NPC_Viki(gp), 2, 29, 16);
        // ELVIE
        setEntitiesByTile(gp.entities, new NPC_Elvie(gp), 3, 14, 13);

        // TODO: make a spawner class
        setEntitiesByTile(gp.entities, new ENM_Green_Slime(gp), 4, 10, 12);
        //setEntitiesByTile(gp.entities, new ENM_Green_Slime(gp), 5, 26, 17);
        setEntitiesByTile(gp.entities, new ENM_Green_Slime(gp), 6, 32, 10);
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
