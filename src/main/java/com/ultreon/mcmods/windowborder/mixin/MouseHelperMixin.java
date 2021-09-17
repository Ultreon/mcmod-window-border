package com.ultreon.mcmods.windowborder.mixin;

import com.ultreon.mcmods.windowborder.WindowBorder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHelper;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.screen.Screen;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@SuppressWarnings("ALL")
@Mixin(MouseHelper.class)
public abstract class MouseHelperMixin {
    @Shadow private boolean ignoreFirstMove;

    @Shadow private double mouseY;

    @Shadow private double mouseX;

    @Shadow @Final private Minecraft minecraft;

    @Shadow private double eventTime;

    @Shadow private int activeButton;

    @Shadow public abstract boolean isMouseGrabbed();

    @Shadow private double xVelocity;

    @Shadow private double yVelocity;

    @Shadow public abstract void updatePlayerLook();

    /**
     * @author Qboi123
     */
    @Overwrite
    private void cursorPosCallback(long handle, double xpos, double ypos) {
        if (handle == Minecraft.getInstance().getMainWindow().getHandle()) {
            if (this.ignoreFirstMove) {
                this.mouseX = (xpos - (WindowBorder.BORDER_WIDTH_LEFT * minecraft.getMainWindow().getGuiScaleFactor()));
                this.mouseY = (ypos - (WindowBorder.BORDER_WIDTH_TOP * minecraft.getMainWindow().getGuiScaleFactor()));
                this.ignoreFirstMove = false;
            }

            IGuiEventListener iguieventlistener = this.minecraft.currentScreen;
            if (iguieventlistener != null && this.minecraft.loadingGui == null) {
                double d0 = (xpos * (double)this.minecraft.getMainWindow().getScaledWidth() - WindowBorder.BORDER_WIDTH_LEFT) / (double)this.minecraft.getMainWindow().getWidth();
                double d1 = (ypos * (double)this.minecraft.getMainWindow().getScaledHeight() - WindowBorder.BORDER_WIDTH_TOP) / (double)this.minecraft.getMainWindow().getHeight();
                Screen.wrapScreenError(() -> {
                    iguieventlistener.mouseMoved(d0, d1);
                }, "mouseMoved event handler", iguieventlistener.getClass().getCanonicalName());
                if (this.activeButton != -1 && this.eventTime > 0.0D) {
                    double d2 = ((xpos - this.mouseX) * (double)this.minecraft.getMainWindow().getScaledWidth() - WindowBorder.BORDER_WIDTH_LEFT) / (double)this.minecraft.getMainWindow().getWidth();
                    double d3 = ((ypos - this.mouseY) * (double)this.minecraft.getMainWindow().getScaledHeight() - WindowBorder.BORDER_WIDTH_TOP) / (double)this.minecraft.getMainWindow().getHeight();
                    Screen.wrapScreenError(() -> {
                        if (net.minecraftforge.client.ForgeHooksClient.onGuiMouseDragPre(this.minecraft.currentScreen, d0, d1, this.activeButton, d2, d3)) return;
                        if (iguieventlistener.mouseDragged(d0, d1, this.activeButton, d2, d3)) return;
                        net.minecraftforge.client.ForgeHooksClient.onGuiMouseDragPost(this.minecraft.currentScreen, d0, d1, this.activeButton, d2, d3);
                    }, "mouseDragged event handler", iguieventlistener.getClass().getCanonicalName());
                }
            }

            this.minecraft.getProfiler().startSection("mouse");
            if (this.isMouseGrabbed() && this.minecraft.isGameFocused()) {
                this.xVelocity += (xpos - WindowBorder.BORDER_WIDTH_LEFT) - this.mouseX;
                this.yVelocity += (ypos - WindowBorder.BORDER_WIDTH_TOP) - this.mouseY;
            }

            this.updatePlayerLook();
            this.mouseX = xpos - (WindowBorder.BORDER_WIDTH_LEFT * minecraft.getMainWindow().getGuiScaleFactor());
            this.mouseY = ypos - (WindowBorder.BORDER_WIDTH_TOP * minecraft.getMainWindow().getGuiScaleFactor());
            this.minecraft.getProfiler().endSection();
        }

//        System.out.println("X:(" + mouseX + ") ... Y:(" + mouseY + ")");
    }
}
