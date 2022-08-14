package main;

import entity.Entity;

import static main.GamePanel.TILE_SIZE;

public class CollisionChecker {

    GamePanel gp;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    public void checkTile(Entity entity) {
        int entityLeftWorldX = entity.worldX + entity.getSolidArea().x;
        int entityRightWorldX = entity.worldX + entity.getSolidArea().x + entity.getSolidArea().width;
        int entityTopWorldY = entity.worldY + entity.getSolidArea().y;
        int entityBottomWorldY = entity.worldY + entity.getSolidArea().y + entity.getSolidArea().height;

        int entityLeftCol = entityLeftWorldX / TILE_SIZE;
        int entityRightCol = entityRightWorldX / TILE_SIZE;
        int entityTopRow = entityTopWorldY / TILE_SIZE;
        int entityBottomRow = entityBottomWorldY / TILE_SIZE;

        int tileNum1, tileNum2;

        switch (entity.getDirection()) {
            case "stand":
                break;
            case "up":
                entityTopRow = (entityTopWorldY - entity.getSpeed()) / TILE_SIZE;
                tileNum1 = gp.tileM.getMapTileNum()[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.getMapTileNum()[entityRightCol][entityTopRow];
                if(gp.tileM.getTile()[tileNum1].collision || gp.tileM.getTile()[tileNum2].collision) {
                    entity.setCollisionOn(true);
                }
                break;
            case "down":
                entityBottomRow = (entityBottomWorldY + entity.getSpeed()) / TILE_SIZE;
                tileNum1 = gp.tileM.getMapTileNum()[entityLeftCol][entityBottomRow];
                tileNum2 = gp.tileM.getMapTileNum()[entityRightCol][entityBottomRow];
                if(gp.tileM.getTile()[tileNum1].collision || gp.tileM.getTile()[tileNum2].collision) {
                    entity.setCollisionOn(true);
                }
                break;
            case "right":
                entityRightCol = (entityRightWorldX + entity.getSpeed()) / TILE_SIZE;
                tileNum1 = gp.tileM.getMapTileNum()[entityRightCol][entityTopRow];
                tileNum2 = gp.tileM.getMapTileNum()[entityRightCol][entityBottomRow];
                if(gp.tileM.getTile()[tileNum1].collision || gp.tileM.getTile()[tileNum2].collision) {
                    entity.setCollisionOn(true);
                }
                break;
            case "left":
                entityLeftCol = (entityLeftWorldX - entity.getSpeed()) / TILE_SIZE;
                tileNum1 = gp.tileM.getMapTileNum()[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.getMapTileNum()[entityLeftCol][entityBottomRow];
                if(gp.tileM.getTile()[tileNum1].collision || gp.tileM.getTile()[tileNum2].collision) {
                    entity.setCollisionOn(true);
                }
                break;
        }
    }
}
