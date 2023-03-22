package it.bisumto.specialnametags.mixin;

import net.minecraft.client.gui.screen.ingame.AnvilScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(AnvilScreen.class)
public class AnvilScreenMixin {
    @ModifyVariable(
            method = "onRenamed",
            at = @At("STORE"),
            ordinal = 1
    )
    String string(String value){
        return value
                .replaceAll("(?<=[^\\\\])\\$","§")
                .replaceAll("\\\\\\$","$");
    }
}
