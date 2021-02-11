package com.viaversion.viaversion.api.minecraft.entities;

import com.viaversion.viaversion.api.Via;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Entity1_10Types {
   public static Entity1_10Types.EntityType getTypeFromId(int typeID, boolean isObject) {
      Optional type;
      if (isObject) {
         type = com.viaversion.viaversion.api.minecraft.entities.Entity1_10Types.ObjectType.getPCEntity(typeID);
      } else {
         type = Entity1_10Types.EntityType.findById(typeID);
      }

      if (!type.isPresent()) {
         Via.getPlatform().getLogger().severe("Could not find 1.10 type id " + typeID + " isObject=" + isObject);
         return Entity1_10Types.EntityType.ENTITY;
      } else {
         return (Entity1_10Types.EntityType)type.get();
      }
   }

   public
   enum EntityType implements com.viaversion.viaversion.api.minecraft.entities.EntityType {
      ENTITY(-1),
      DROPPED_ITEM(1, ENTITY),
      EXPERIENCE_ORB(2, ENTITY),
      LEASH_HITCH(8, ENTITY),
      PAINTING(9, ENTITY),
      ARROW(10, ENTITY),
      SNOWBALL(11, ENTITY),
      FIREBALL(12, ENTITY),
      SMALL_FIREBALL(13, ENTITY),
      ENDER_PEARL(14, ENTITY),
      ENDER_SIGNAL(15, ENTITY),
      THROWN_EXP_BOTTLE(17, ENTITY),
      ITEM_FRAME(18, ENTITY),
      WITHER_SKULL(19, ENTITY),
      PRIMED_TNT(20, ENTITY),
      FALLING_BLOCK(21, ENTITY),
      FIREWORK(22, ENTITY),
      TIPPED_ARROW(23, ARROW),
      SPECTRAL_ARROW(24, ARROW),
      SHULKER_BULLET(25, ENTITY),
      DRAGON_FIREBALL(26, FIREBALL),
      ENTITY_LIVING(-1, ENTITY),
      ENTITY_INSENTIENT(-1, ENTITY_LIVING),
      ENTITY_AGEABLE(-1, ENTITY_INSENTIENT),
      ENTITY_TAMEABLE_ANIMAL(-1, ENTITY_AGEABLE),
      ENTITY_HUMAN(-1, ENTITY_LIVING),
      ARMOR_STAND(30, ENTITY_LIVING),
      MINECART_ABSTRACT(-1, ENTITY),
      MINECART_COMMAND(40, MINECART_ABSTRACT),
      BOAT(41, ENTITY),
      MINECART_RIDEABLE(42, MINECART_ABSTRACT),
      MINECART_CHEST(43, MINECART_ABSTRACT),
      MINECART_FURNACE(44, MINECART_ABSTRACT),
      MINECART_TNT(45, MINECART_ABSTRACT),
      MINECART_HOPPER(46, MINECART_ABSTRACT),
      MINECART_MOB_SPAWNER(47, MINECART_ABSTRACT),
      CREEPER(50, ENTITY_INSENTIENT),
      SKELETON(51, ENTITY_INSENTIENT),
      SPIDER(52, ENTITY_INSENTIENT),
      GIANT(53, ENTITY_INSENTIENT),
      ZOMBIE(54, ENTITY_INSENTIENT),
      SLIME(55, ENTITY_INSENTIENT),
      GHAST(56, ENTITY_INSENTIENT),
      PIG_ZOMBIE(57, ZOMBIE),
      ENDERMAN(58, ENTITY_INSENTIENT),
      CAVE_SPIDER(59, SPIDER),
      SILVERFISH(60, ENTITY_INSENTIENT),
      BLAZE(61, ENTITY_INSENTIENT),
      MAGMA_CUBE(62, SLIME),
      ENDER_DRAGON(63, ENTITY_INSENTIENT),
      WITHER(64, ENTITY_INSENTIENT),
      BAT(65, ENTITY_INSENTIENT),
      WITCH(66, ENTITY_INSENTIENT),
      ENDERMITE(67, ENTITY_INSENTIENT),
      GUARDIAN(68, ENTITY_INSENTIENT),
      IRON_GOLEM(99, ENTITY_INSENTIENT),
      SHULKER(69, IRON_GOLEM),
      PIG(90, ENTITY_AGEABLE),
      SHEEP(91, ENTITY_AGEABLE),
      COW(92, ENTITY_AGEABLE),
      CHICKEN(93, ENTITY_AGEABLE),
      SQUID(94, ENTITY_INSENTIENT),
      WOLF(95, ENTITY_TAMEABLE_ANIMAL),
      MUSHROOM_COW(96, COW),
      SNOWMAN(97, IRON_GOLEM),
      OCELOT(98, ENTITY_TAMEABLE_ANIMAL),
      HORSE(100, ENTITY_AGEABLE),
      RABBIT(101, ENTITY_AGEABLE),
      POLAR_BEAR(102, ENTITY_AGEABLE),
      VILLAGER(120, ENTITY_AGEABLE),
      ENDER_CRYSTAL(200, ENTITY),
      SPLASH_POTION(-1, ENTITY),
      LINGERING_POTION(-1, SPLASH_POTION),
      AREA_EFFECT_CLOUD(-1, ENTITY),
      EGG(-1, ENTITY),
      FISHING_HOOK(-1, ENTITY),
      LIGHTNING(-1, ENTITY),
      WEATHER(-1, ENTITY),
      PLAYER(-1, ENTITY_HUMAN),
      COMPLEX_PART(-1, ENTITY);

      private static final Map TYPES = new HashMap();
      // $FF: renamed from: id int
      private final int field_66;
      private final Entity1_10Types.EntityType parent;

      EntityType(int id) {
         this.field_66 = id;
         this.parent = null;
      }

      EntityType(int id, Entity1_10Types.EntityType parent) {
         this.field_66 = id;
         this.parent = parent;
      }

      public static Optional findById(int id) {
         return id == -1 ? Optional.empty() : Optional.ofNullable((Entity1_10Types.EntityType)TYPES.get(id));
      }

      public int getId() {
         return this.field_66;
      }

      public Entity1_10Types.EntityType getParent() {
         return this.parent;
      }

      static {
         Entity1_10Types.EntityType[] var0 = values();
         int var1 = var0.length;

         for(int var2 = 0; var2 < var1; ++var2) {
            Entity1_10Types.EntityType type = var0[var2];
            TYPES.put(type.field_66, type);
         }

      }
   }
}
