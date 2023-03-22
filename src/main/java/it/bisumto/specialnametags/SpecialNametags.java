package it.bisumto.specialnametags;

import it.bisumto.specialnametags.mixin.ArmorStandEntityAccessor;
import it.bisumto.specialnametags.mixin.VindicatorEntityAccessor;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.EventFactory;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.mob.HoglinEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PiglinEntity;
import net.minecraft.entity.mob.VindicatorEntity;
import net.minecraft.entity.passive.RabbitEntity;
import net.minecraft.text.Text;
import net.minecraft.util.TypeFilter;
import net.minecraft.util.math.Vec3d;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

public class SpecialNametags implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("specialnametags");

    public static void specialNametags(String name, LivingEntity entity) {
        switch (name.toLowerCase()) {
            case "freeze", "noai", "congela", "congelato" -> {
                entity.setInvulnerable(true);
                if (entity instanceof MobEntity mob) {
                    mob.setAiDisabled(true);
                }
            }
            case "unfreeze", "ai", "scongela", "scongelato" -> {
                entity.setInvulnerable(false);
                if (entity instanceof MobEntity mob) {
                    mob.setAiDisabled(false);
                }
            }
            case "silent", "silenzia", "muta", "mutato" -> entity.setSilent(true);
            case "unsilent", "smuta", "smutato" -> entity.setSilent(false);
            case "baby", "cucciolo", "bambino" -> {
                if (entity instanceof MobEntity mob) {
                    mob.setBaby(true);
                    mob.addCommandTag("gb.is_baby");
                }
            }
            case "unbaby", "adult", "adulto" -> {
                if (entity instanceof MobEntity mob) {
                    mob.setBaby(false);
                    mob.removeScoreboardTag("gb.is_baby");
                }
            }
            case "gravity", "gravità" -> entity.setNoGravity(false);
            case "ungravity", "floating", "fluttuante", "nogravity" -> entity.setNoGravity(true);
            case "invulnerable", "invulnerabile" -> entity.setInvulnerable(true);
            case "vulnerable", "vulnerabile" -> entity.setInvulnerable(false);
            case "noname", "unname", "nonome" -> entity.setCustomName(Text.empty());
            case "killer rabbit" -> {
                if (entity instanceof RabbitEntity rabbit) {
                    rabbit.setVariant(RabbitEntity.RabbitType.EVIL);
                }
            }
            case "nozombification", "nozombificazione" -> {
                if (entity instanceof PiglinEntity piglin) {
                    piglin.setImmuneToZombification(true);
                }
                if (entity instanceof HoglinEntity hoglin) {
                    hoglin.setImmuneToZombification(true);
                }
            }
            case "zombification", "zombificazione" -> {
                if (entity instanceof PiglinEntity piglin) {
                    piglin.setImmuneToZombification(false);
                }
                if (entity instanceof HoglinEntity hoglin) {
                    hoglin.setImmuneToZombification(false);
                }
            }
            case "johnny" -> {
                if (entity instanceof VindicatorEntity vindicator) {
                    ((VindicatorEntityAccessor) vindicator).setJohnny(true);
                }
            }
            case "unjohnny" -> {
                if (entity instanceof VindicatorEntity vindicator) {
                    ((VindicatorEntityAccessor) vindicator).setJohnny(false);
                }
            }
            case "noclip" -> entity.noClip = true;
            case "clip" -> entity.noClip = false;
            case "sit", "seduto" -> {
                if (!entity.hasVehicle()) {
                    entity.addCommandTag("gb.sitted");
                    ArmorStandEntity armorStand = new ArmorStandEntity(entity.world, entity.getX(), entity.getY() - 0.1, entity.getZ());
                    armorStand.addCommandTag("gb.sitted.armor_stand");
                    ((ArmorStandEntityAccessor) armorStand).invokeSetMarker(true);
                    armorStand.setNoGravity(true);
                    armorStand.setInvisible(true);
                    armorStand.setBodyYaw(entity.getBodyYaw());
                    entity.startRiding(armorStand);
                    // TODO: remove armorStand when entity is removed
                }
            }
            case "unsleep", "unsit", "alzato" -> {
                Set<String> tags = entity.getCommandTags();
                tags.remove("gb.is_sleeping");
                tags.remove("sb.sitted");
                entity.dismountVehicle();
                entity.setNoGravity(false);
                entity.move(MovementType.SELF, new Vec3d(0, 0.5, 0));
            }
            case "sleep", "sdraiato" -> {
                entity.setNoGravity(true);
                entity.addCommandTag("gb.is_sleeping");
            }
        }
    }

    private static final TypeFilter<Entity, MobEntity> ANY = new TypeFilter<>() {
        @Override
        public MobEntity downcast(Entity entity) {
            if (entity instanceof MobEntity living)
                return living;
            return null;
        }

        @Override
        public Class<? extends Entity> getBaseClass() {
            return MobEntity.class;
        }
    };

    @Override
    public void onInitialize() {
        LOGGER.info("Mod loaded");
        EventFactory.createArrayBacked(ServerTickEvents.EndWorldTick.class, listeners -> world -> {

            Predicate<MobEntity> sleepingPredicate = living -> living.getCommandTags().contains("gb.is_sleeping");
            List<? extends MobEntity> sleeping = world.getEntitiesByType(ANY, sleepingPredicate);
            sleeping.forEach(living -> living.sleep(living.getBlockPos()));

            // SLOW LOOPER
            if (world.getServer().getTicks() % 12000 != 0) return;

            Predicate<MobEntity> babyPredicate = living -> living.getCommandTags().contains("gb.is_baby");
            List<? extends MobEntity> babies = world.getEntitiesByType(ANY, babyPredicate);
            babies.forEach(living -> living.setBaby(true));

            Predicate<ArmorStandEntity> sitPredicate = armorStand -> armorStand.getCommandTags().contains("gb.sitted.armor_stand");
            List<? extends ArmorStandEntity> armorStands = world.getEntitiesByType(EntityType.ARMOR_STAND, sitPredicate);
            armorStands.forEach(armorStand -> {
                if (!armorStand.hasPassengers()) armorStand.remove(Entity.RemovalReason.KILLED);
            });

        });
    }
}
