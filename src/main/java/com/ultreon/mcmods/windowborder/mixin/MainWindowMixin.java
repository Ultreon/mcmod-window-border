package com.ultreon.mcmods.windowborder.mixin;

import com.ultreon.mcmods.windowborder.WindowBorder;
import net.minecraft.client.MainWindow;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MainWindow.class)
public abstract class MainWindowMixin implements AutoCloseable{
    @Shadow public abstract double getGuiScaleFactor();

    @Shadow public abstract int getWidth();

    @Shadow public abstract int getHeight();

    @Shadow private int framebufferWidth;

    @Shadow private int framebufferHeight;

    @Override
    public void close() throws Exception {

    }

    @Inject(method = "getWidth()I", at = @At("RETURN"), cancellable = true)
    public void getWidth(CallbackInfoReturnable<Integer> cir) {
        int retVal = cir.getReturnValueI();
        retVal -= (WindowBorder.BORDER_WIDTH_LEFT + WindowBorder.BORDER_WIDTH_RIGHT) * getGuiScaleFactor();
        cir.setReturnValue(retVal);
    }

    @Inject(method = "getHeight()I", at = @At("RETURN"), cancellable = true)
    public void getHeight(CallbackInfoReturnable<Integer> cir) {
        int retVal = cir.getReturnValueI();
        retVal -= (WindowBorder.BORDER_WIDTH_TOP + WindowBorder.BORDER_WIDTH_BOTTOM) * getGuiScaleFactor();
        cir.setReturnValue(retVal);
    }

    @Inject(method = "getScaledWidth", at = @At("RETURN"), cancellable = true)
    public void getScaledWidth(CallbackInfoReturnable<Integer> cir) {
        int retVal = cir.getReturnValueI();
        retVal -= (WindowBorder.BORDER_WIDTH_LEFT + WindowBorder.BORDER_WIDTH_RIGHT);
        cir.setReturnValue(retVal);
    }

    @Inject(method = "getScaledHeight", at = @At("RETURN"), cancellable = true)
    public void getScaledHeight(CallbackInfoReturnable<Integer> cir) {
        int retVal = cir.getReturnValueI();
        retVal -= (WindowBorder.BORDER_WIDTH_TOP + WindowBorder.BORDER_WIDTH_BOTTOM);
        cir.setReturnValue(retVal);
    }

    /**
     * @author Qboi123
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Overwrite
    public int calcGuiScale(int guiScaleIn, boolean forceUnicode) {
        int w = this.framebufferWidth + (int)(WindowBorder.BORDER_WIDTH_LEFT * guiScaleIn);
        int h = this.framebufferHeight + (int)(WindowBorder.BORDER_WIDTH_LEFT * guiScaleIn);

        int i;
        for(i = 1; i != guiScaleIn && i < w && i < h && w / (i + 1) >= 320 && h / (i + 1) >= 240; ++i) {

        }

        if (forceUnicode && i % 2 != 0) {
            ++i;
        }

        return i;
    }

//    @Inject(method = "getFramebufferWidth()I", at = @At("RETURN"), cancellable = true)
//    public void getFramebufferWidth(CallbackInfoReturnable<Integer> cir) {
//        int retVal = cir.getReturnValueI();
//        retVal -= WindowBorder.BORDER_WIDTH_LEFT + WindowBorder.BORDER_WIDTH_RIGHT;
//        cir.setReturnValue(retVal);
//    }
//
//    @Inject(method = "getFramebufferHeight()I", at = @At("RETURN"), cancellable = true)
//    public void getFramebufferHeight(CallbackInfoReturnable<Integer> cir) {
//        int retVal = cir.getReturnValueI();
//        retVal -= WindowBorder.BORDER_WIDTH_TOP + WindowBorder.BORDER_WIDTH_BOTTOM;
//        cir.setReturnValue(retVal);
//    }
}
