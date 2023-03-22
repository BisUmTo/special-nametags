package it.bisumto.specialnametags.mixin;

import net.minecraft.entity.mob.VindicatorEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(VindicatorEntity.class)
public interface VindicatorEntityAccessor {
    @Accessor
    void setJohnny(boolean johnny);
}
