package com.ultreon.mcmods.windowborder.mixin;

import com.ultreon.mcmods.windowborder.WindowBorder;
import net.minecraft.client.MainWindow;
import net.minecraft.client.shader.Framebuffer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Framebuffer.class)
public class FramebufferMixin{
//    @Inject(method = "getWidth()I", at = @At("RETURN"), cancellable = true)
//    public void getWidth(CallbackInfoReturnable<Integer> cir) {
//        int retVal = cir.getReturnValueI();
//        retVal -= WindowBorder.BORDER_WIDTH_LEFT + WindowBorder.BORDER_WIDTH_RIGHT;
//        cir.setReturnValue(retVal);
//    }
//
//    @Inject(method = "getHeight()I", at = @At("RETURN"), cancellable = true)
//    public void getHeight(CallbackInfoReturnable<Integer> cir) {
//        int retVal = cir.getReturnValueI();
//        retVal -= WindowBorder.BORDER_WIDTH_TOP + WindowBorder.BORDER_WIDTH_BOTTOM;
//        cir.setReturnValue(retVal);
//    }
}
