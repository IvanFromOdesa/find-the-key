package main;

import entity.Entity;
import object.weapon.projectile.Projectile;

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
                    // RESET THE POSITION IF OUT OF THE MAP
                    gp.player.setDefaultValues();
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
                    gp.player.setDefaultValues();
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
                    gp.player.setDefaultValues();
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
                    gp.player.setDefaultValues();
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
                gp.objects[i].solidArea.x = gp.objects[i].worldX + gp.objects[i].solidArea.x;
                gp.objects[i].solidArea.y = gp.objects[i].worldY + gp.objects[i].solidArea.y;

                switch (entity.getDirection()) {
                    case "stand":
                        break;
                    case "up":
                        entity.getSolidArea().y -= entity.getSpeed();
                        if(entity.getSolidArea().intersects(gp.objects[i].solidArea)) {
                            if(gp.objects[i].isCollision()) entity.setCollisionOn(true);
                            if(isPlayer) index = i;
                        }
                        break;
                    case "down":
                        entity.getSolidArea().y += entity.getSpeed();
                        if(entity.getSolidArea().intersects(gp.objects[i].solidArea)) {
                            if(gp.objects[i].isCollision()) entity.setCollisionOn(true);
                            if(isPlayer) index = i;
                        }
                        break;
                    case "right":
                        entity.getSolidArea().x += entity.getSpeed();
                        if(entity.getSolidArea().intersects(gp.objects[i].solidArea)) {
                            if(gp.objects[i].isCollision()) entity.setCollisionOn(true);
                            if(isPlayer) index = i;
                        }
                        break;
                    case "left":
                        entity.getSolidArea().x -= entity.getSpeed();
                        if(entity.getSolidArea().intersects(gp.objects[i].solidArea)) {
                            if(gp.objects[i].isCollision()) entity.setCollisionOn(true);
                            if(isPlayer) index = i;
                        }
                        break;
                }

                entity.getSolidArea().x = entity.getSolidAreaDefaultX();
                entity.getSolidArea().y = entity.getSolidAreaDefaultY();
                gp.objects[i].solidArea.x = gp.objects[i].solidAreaDefaultX;
                gp.objects[i].solidArea.y = gp.objects[i].solidAreaDefaultY;
            }
        }

        return index;
    }

    // NPC OR MONSTER
    public void checkEntity(Entity entity, Entity[] target) {

        for (Entity targetEntity : target) {
            if (entity != targetEntity) {
                if (targetEntity != null) {

                    entity.getSolidArea().x = entity.worldX + entity.getSolidArea().x;
                    entity.getSolidArea().y = entity.worldY + entity.getSolidArea().y;

                    targetEntity.getSolidArea().x = targetEntity.worldX + targetEntity.getSolidArea().x;
                    targetEntity.getSolidArea().y = targetEntity.worldY + targetEntity.getSolidArea().y;

                    switch (entity.getDirection()) {
                        case "stand":
                            if (entity.getSolidArea().intersects(targetEntity.getSolidArea())) {
                                entity.setCollisionOn(true);
                                targetEntity.setCollisionOnEntity(true);
                            }
                            break;
                        case "up":
                            entity.getSolidArea().y -= entity.getSpeed();
                            if (entity.getSolidArea().intersects(targetEntity.getSolidArea())) {
                                entity.setCollisionOn(true);
                                targetEntity.setCollisionOnEntity(true);
                            }
                            break;
                        case "down":
                            entity.getSolidArea().y += entity.getSpeed();
                            if (entity.getSolidArea().intersects(targetEntity.getSolidArea())) {
                                entity.setCollisionOn(true);
                                targetEntity.setCollisionOnEntity(true);
                            }
                            break;
                        case "right":
                            entity.getSolidArea().x += entity.getSpeed();
                            if (entity.getSolidArea().intersects(targetEntity.getSolidArea())) {
                                entity.setCollisionOn(true);
                                targetEntity.setCollisionOnEntity(true);
                            }
                            break;
                        case "left":
                            entity.getSolidArea().x -= entity.getSpeed();
                            if (entity.getSolidArea().intersects(targetEntity.getSolidArea())) {
                                entity.setCollisionOn(true);
                                targetEntity.setCollisionOnEntity(true);
                            }
                            break;
                    }

                    entity.getSolidArea().x = entity.getSolidAreaDefaultX();
                    entity.getSolidArea().y = entity.getSolidAreaDefaultY();
                    targetEntity.getSolidArea().x = targetEntity.getSolidAreaDefaultX();
                    targetEntity.getSolidArea().y = targetEntity.getSolidAreaDefaultY();
                }
            }
        }

    }

    public void checkPlayer(Entity entity) {
        entity.getSolidArea().x = entity.worldX + entity.getSolidArea().x;
        entity.getSolidArea().y = entity.worldY + entity.getSolidArea().y;

        gp.player.getSolidArea().x = gp.player.worldX + gp.player.getSolidArea().x;
        gp.player.getSolidArea().y = gp.player.worldY + gp.player.getSolidArea().y;

        switch (entity.getDirection()) {
            case "stand":
                if(entity.getSolidArea().intersects(gp.player.getSolidArea())) {
                    entity.setCollisionOn(true);
                    entity.setCollisionOnEntity(true);
                }
                break;
            case "up":
                entity.getSolidArea().y -= entity.getSpeed();
                if(entity.getSolidArea().intersects(gp.player.getSolidArea())) {
                    entity.setCollisionOn(true);
                    entity.setCollisionOnEntity(true);
                }
                break;
            case "down":
                entity.getSolidArea().y += entity.getSpeed();
                if(entity.getSolidArea().intersects(gp.player.getSolidArea())) {
                    entity.setCollisionOn(true);
                    entity.setCollisionOnEntity(true);
                }
                break;
            case "right":
                entity.getSolidArea().x += entity.getSpeed();
                if(entity.getSolidArea().intersects(gp.player.getSolidArea())) {
                    entity.setCollisionOn(true);
                    entity.setCollisionOnEntity(true);
                }
                break;
            case "left":
                entity.getSolidArea().x -= entity.getSpeed();
                if(entity.getSolidArea().intersects(gp.player.getSolidArea())) {
                    entity.setCollisionOn(true);
                    entity.setCollisionOnEntity(true);
                }
                break;
        }

        entity.getSolidArea().x = entity.getSolidAreaDefaultX();
        entity.getSolidArea().y = entity.getSolidAreaDefaultY();
        gp.player.getSolidArea().x = gp.player.getSolidAreaDefaultX();
        gp.player.getSolidArea().y = gp.player.getSolidAreaDefaultY();
    }

    public void checkPlayerProjectiles(Entity entity) {
        for(Projectile projectile : gp.projectiles) {
            if(projectile != null) {

                entity.getSolidArea().x = entity.screenX + entity.getSolidArea().x;
                entity.getSolidArea().y = entity.screenY + entity.getSolidArea().y;
                projectile.solidArea.x = (int) (projectile.worldX + projectile.solidArea.x);
                projectile.solidArea.y = (int) (projectile.worldY + projectile.solidArea.y);

                if(projectile.solidArea.intersects(entity.getSolidArea())) {
                    entity.setCurrentLife(entity.getCurrentLife() -
                            (projectile.getDamage() + projectile.getGun().getDamage()));
                    projectile.setAlive(false);
                }

                entity.getSolidArea().x = entity.getSolidAreaDefaultX();
                entity.getSolidArea().y = entity.getSolidAreaDefaultY();
                projectile.solidArea.x = projectile.solidAreaDefaultX;
                projectile.solidArea.y = projectile.solidAreaDefaultY;
            }
        }
    }
}
