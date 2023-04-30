package main;

import entity.Entity;
import object.SuperObject;
import object.weapon.projectile.Projectile;

import java.awt.Rectangle;

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
                tileNum1 = 0;
                tileNum2 = 0;

                try {
                    tileNum1 = gp.tileM.getMapTileNum()[entityLeftCol][entityTopRow];
                    tileNum2 = gp.tileM.getMapTileNum()[entityRightCol][entityTopRow];
                } catch (ArrayIndexOutOfBoundsException e) {
                    entity.setCollisionOn(true);
                }

                if(gp.tileM.getTile()[tileNum1].collision || gp.tileM.getTile()[tileNum2].collision) {
                    entity.setCollisionOn(true);
                }
                break;
            case "down":
                entityBottomRow = (entityBottomWorldY + entity.getSpeed()) / TILE_SIZE;
                tileNum1 = 0;
                tileNum2 = 0;

                try {
                    tileNum1 = gp.tileM.getMapTileNum()[entityLeftCol][entityBottomRow];
                    tileNum2 = gp.tileM.getMapTileNum()[entityRightCol][entityBottomRow];
                } catch (ArrayIndexOutOfBoundsException e) {
                    entity.setCollisionOn(true);
                }

                if(gp.tileM.getTile()[tileNum1].collision || gp.tileM.getTile()[tileNum2].collision) {
                    entity.setCollisionOn(true);
                }
                break;
            case "right":
                entityRightCol = (entityRightWorldX + entity.getSpeed()) / TILE_SIZE;
                tileNum1 = 0;
                tileNum2 = 0;

                try {
                    tileNum1 = gp.tileM.getMapTileNum()[entityRightCol][entityTopRow];
                    tileNum2 = gp.tileM.getMapTileNum()[entityRightCol][entityBottomRow];
                } catch (ArrayIndexOutOfBoundsException e) {
                    entity.setCollisionOn(true);
                }

                if(gp.tileM.getTile()[tileNum1].collision || gp.tileM.getTile()[tileNum2].collision) {
                    entity.setCollisionOn(true);
                }
                break;
            case "left":
                entityLeftCol = (entityLeftWorldX - entity.getSpeed()) / TILE_SIZE;
                tileNum1 = 0;
                tileNum2 = 0;

                try {
                    tileNum1 = gp.tileM.getMapTileNum()[entityLeftCol][entityTopRow];
                    tileNum2 = gp.tileM.getMapTileNum()[entityLeftCol][entityBottomRow];
                } catch (ArrayIndexOutOfBoundsException e) {
                    entity.setCollisionOn(true);
                }

                if(gp.tileM.getTile()[tileNum1].collision || gp.tileM.getTile()[tileNum2].collision) {
                    entity.setCollisionOn(true);
                }
                break;
        }
    }

    public int checkObject(Entity entity, boolean isPlayer) {

        int index = -1;

        for(int i = 0; i < gp.objects.length; i++) {
            if(gp.objects[i] != null) {

                // Get entity's solid area position
                entity.getSolidArea().x = entity.worldX + entity.getSolidArea().x;
                entity.getSolidArea().y = entity.worldY + entity.getSolidArea().y;

                // Get the object's solid area position
                Rectangle objSolidArea = gp.objects[i].solidArea;
                objSolidArea.x = gp.objects[i].worldX + objSolidArea.x;
                objSolidArea.y = gp.objects[i].worldY + objSolidArea.y;

                switch (entity.getDirection()) {
                    case "stand":
                        break;
                    case "up":
                        entity.getSolidArea().y -= entity.getSpeed();
                        if(entity.getSolidArea().intersects(objSolidArea)) {
                            if(gp.objects[i].isCollision()) entity.setCollisionOn(true);
                            if(isPlayer) index = i;
                        }
                        break;
                    case "down":
                        entity.getSolidArea().y += entity.getSpeed();
                        if(entity.getSolidArea().intersects(objSolidArea)) {
                            if(gp.objects[i].isCollision()) entity.setCollisionOn(true);
                            if(isPlayer) index = i;
                        }
                        break;
                    case "right":
                        entity.getSolidArea().x += entity.getSpeed();
                        if(entity.getSolidArea().intersects(objSolidArea)) {
                            if(gp.objects[i].isCollision()) entity.setCollisionOn(true);
                            if(isPlayer) index = i;
                        }
                        break;
                    case "left":
                        entity.getSolidArea().x -= entity.getSpeed();
                        if(entity.getSolidArea().intersects(objSolidArea)) {
                            if(gp.objects[i].isCollision()) entity.setCollisionOn(true);
                            if(isPlayer) index = i;
                        }
                        break;
                }

                entity.getSolidArea().x = entity.getSolidAreaDefaultX();
                entity.getSolidArea().y = entity.getSolidAreaDefaultY();
                objSolidArea.x = gp.objects[i].solidAreaDefaultX;
                objSolidArea.y = gp.objects[i].solidAreaDefaultY;
            }
        }

        return index;
    }

    // NPC OR ENEMY
    public void checkEntity(Entity entity, Entity[] target) {

        for (Entity targetEntity : target) {
            if (entity != targetEntity) {
                if (targetEntity != null) {
                    checkCollisionConditions(entity, targetEntity);
                }
            }
        }

    }

    private void checkCollisionOnEntities(Entity entity, Entity targetEntity) {
        if (entity.getSolidArea().intersects(targetEntity.getSolidArea())) {
            targetEntity.setCollisionOnEntity(true, entity);
        }
    }

    private void checkCollisionConditions(Entity entity, Entity targetEntity) {
        entity.getSolidArea().x = entity.worldX + entity.getSolidArea().x;
        entity.getSolidArea().y = entity.worldY + entity.getSolidArea().y;

        targetEntity.getSolidArea().x = targetEntity.worldX + targetEntity.getSolidArea().x;
        targetEntity.getSolidArea().y = targetEntity.worldY + targetEntity.getSolidArea().y;

        switch (entity.getDirection()) {
            case "stand":
                checkCollisionOnEntities(entity, targetEntity);
                break;
            case "up":
                entity.getSolidArea().y -= entity.getSpeed();
                checkCollisionOnEntities(entity, targetEntity);
                break;
            case "down":
                entity.getSolidArea().y += entity.getSpeed();
                checkCollisionOnEntities(entity, targetEntity);
                break;
            case "right":
                entity.getSolidArea().x += entity.getSpeed();
                checkCollisionOnEntities(entity, targetEntity);
                break;
            case "left":
                entity.getSolidArea().x -= entity.getSpeed();
                checkCollisionOnEntities(entity, targetEntity);
                break;
        }

        entity.getSolidArea().x = entity.getSolidAreaDefaultX();
        entity.getSolidArea().y = entity.getSolidAreaDefaultY();
        targetEntity.getSolidArea().x = targetEntity.getSolidAreaDefaultX();
        targetEntity.getSolidArea().y = targetEntity.getSolidAreaDefaultY();
    }

    public void checkPlayer(Entity entity) {
        checkCollisionConditions(entity,  gp.player);
    }

    public void checkProjectiles(Entity entity) {
        for(Projectile projectile : gp.projectiles) {
            if(projectile != null) {

                if(projectile.getUser() == entity) continue;

                entity.getSolidArea().x = entity.worldX + entity.getSolidArea().x;
                entity.getSolidArea().y = entity.worldY + entity.getSolidArea().y;
                projectile.solidArea.x = (int) (projectile.worldX + projectile.solidArea.x);
                projectile.solidArea.y = (int) (projectile.worldY + projectile.solidArea.y);

                if(projectile.solidArea.intersects(entity.getSolidArea()) && projectile.getLife() != 0) {
                    entity.setCurrentLife(entity.getCurrentLife() -
                            (projectile.getDamage() + projectile.getGun().getDamage()));
                    entity.setDamaged(true);
                    projectile.setLife(0);
                }

                entity.getSolidArea().x = entity.getSolidAreaDefaultX();
                entity.getSolidArea().y = entity.getSolidAreaDefaultY();
                projectile.solidArea.x = projectile.solidAreaDefaultX;
                projectile.solidArea.y = projectile.solidAreaDefaultY;
            }
        }
    }

    public void checkProjectileObjects(Projectile projectile) {
        for (SuperObject object : gp.objects) {
            if (object != null) {

                object.solidArea.x = object.worldX + object.solidArea.x;
                object.solidArea.y = object.worldY + object.solidArea.y;
                projectile.solidArea.x = (int) (projectile.worldX + projectile.solidArea.x);
                projectile.solidArea.y = (int) (projectile.worldY + projectile.solidArea.y);

                if (object.solidArea.intersects(projectile.solidArea)) projectile.setLife(0);

                object.solidArea.x = object.solidAreaDefaultX;
                object.solidArea.y = object.solidAreaDefaultY;
                projectile.solidArea.x = projectile.solidAreaDefaultX;
                projectile.solidArea.y = projectile.solidAreaDefaultY;
            }
        }
    }
}
