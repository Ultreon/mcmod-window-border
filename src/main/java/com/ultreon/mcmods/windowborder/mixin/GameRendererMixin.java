package com.ultreon.mcmods.windowborder.mixin;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.ultreon.mcmods.windowborder.WindowBorder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.IResourceManager;
import net.minecraft.resources.IResourceManagerReloadListener;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererMixin implements IResourceManagerReloadListener, AutoCloseable {
    @Shadow @Final private Minecraft mc;

    @Override
    public void close() throws Exception {

    }

    @Override
    public void onResourceManagerReload(IResourceManager resourceManager) {

    }

    public void windowBorder_borderRow(MatrixStack matrixStack, int y, int widthP, int height, int vOffset) {
        Screen.blit(matrixStack, 0, y, 8, height, 1, vOffset, 8, height, 256, 256);

        for (int i = 8; i <= widthP; i += 220) {
            int w = Math.min(widthP - i, 220);
            Screen.blit(matrixStack, i, y, w, height, 9, vOffset, w, height, 256, 256);
        }

        Screen.blit(matrixStack, widthP, y, 8, height, 229, vOffset, 8, height, 256, 256);
    }

    @Inject(method = "updateCameraAndRender(FJZ)V", at = @At(value = "NEW", target = "com/mojang/blaze3d/matrix/MatrixStack", ordinal = 1))
    public void updateCameraAndRender(float partialTicks, long nanoTime, boolean renderWorldIn, CallbackInfo ci) {
//        Minecraft client = Minecraft.getInstance()
//        boolean fullscreen;
//
//        //Set value if it isn't set already.
//        if(System.getProperty("org.lwjgl.opengl.Window.undecorated") == null){
//            System.setProperty("org.lwjgl.opengl.Window.undecorated", "false");
//        }
//
//        //If we're in actual fullscreen right now, then we need to fix that.
//        if(Display.isFullscreen()) {
//            fullscreen = true;
//        }
//
//        String expectedState = goFullScreen ? "true":"false";
//        // If all state is valid, there is nothing to do and we just exit.
//        if(fullscreen == goFullScreen
//                && !Display.isFullscreen()//Display in fullscreen mode: Change required
//                && System.getProperty("org.lwjgl.opengl.Window.undecorated") == expectedState // Window not in expected state
//        )
//            return;
//
//        //Save our current display parameters
//        Rectangle currentCoordinates = new Rectangle(Display.getX(), Display.getY(), Display.getWidth(), Display.getHeight());
//        if(goFullScreen && !Display.isFullscreen())
//            _savedWindowedBounds = currentCoordinates;
//
//        //Changing this property and causing a Display update will cause LWJGL to add/remove decorations (borderless).
//        System.setProperty("org.lwjgl.opengl.Window.undecorated",expectedState);
//
//        //Get the fullscreen dimensions for the appropriate screen.
//        Rectangle screenBounds = getAppropriateScreenBounds(currentCoordinates, desiredMonitor);
//
//        //This is the new bounds we have to apply.
//        Rectangle newBounds = goFullScreen ? screenBounds : _savedWindowedBounds;
//        if(newBounds == null)
//            newBounds = screenBounds;
//
//        if(goFullScreen == false && ClientProxy.fullscreen == false) {
//            newBounds = currentCoordinates;
//            _savedWindowedBounds = currentCoordinates;
//        }
//
//        try {
//            fullscreen = goFullScreen;
//            client.fullscreen = fullscreen;
//            if( client.gameSettings.fullScreen != fullscreen) {
//                client.gameSettings.fullScreen = fullscreen;
//                client.gameSettings.saveOptions();
//            }
//            Display.setFullscreen(false);
//            Display.setResizable(!goFullScreen);
//            Display.setDisplayMode(new DisplayMode((int) newBounds.getWidth(), (int) newBounds.getHeight()));
//            Display.setLocation(newBounds.x, newBounds.y);
//
//            client.resize((int) newBounds.getWidth(), (int) newBounds.getHeight());
//            Display.setVSyncEnabled(client.gameSettings.enableVsync);
//            client.updateDisplay();
//
//        } catch (LWJGLException e) {
//            e.printStackTrace();
//        }

        MatrixStack matrixStack = new MatrixStack();

//        matrixStack.translate(0, 1, 0);

//        RenderSystem.translated(WindowBorder.BORDER_WIDTH_LEFT, WindowBorder.BORDER_WIDTH_TOP, 10000);
        mc.textureManager.bindTexture(WindowBorder.rl("textures/gui/window.png"));

        int width = mc.getMainWindow().getScaledWidth();
        int widthP = mc.getMainWindow().getScaledWidth() + WindowBorder.BORDER_WIDTH_LEFT;
        int widthPP = mc.getMainWindow().getScaledWidth() + WindowBorder.BORDER_WIDTH_LEFT + WindowBorder.BORDER_WIDTH_RIGHT;

        int height = mc.getMainWindow().getScaledHeight();
        int heightP = mc.getMainWindow().getScaledHeight() + WindowBorder.BORDER_WIDTH_TOP;
        int heightPP = mc.getMainWindow().getScaledHeight() + WindowBorder.BORDER_WIDTH_TOP + WindowBorder.BORDER_WIDTH_BOTTOM;

        windowBorder_borderRow(matrixStack, 0, widthP, 28, 1);

        for (int i = 28; i <= heightP; i += 18) {
            int h = Math.min(heightP - i, 18);
            windowBorder_borderRow(matrixStack, i, widthP, h, 29);
        }
        windowBorder_borderRow(matrixStack, heightP, widthP, 8, 47);

//        Screen.blit(matrixStack, 0, 0, 8, 28, 1, 1, 8, 28, 256, 256);
//
//        for (int i = 8; i <= widthP; i += 220) {
//            int w = Math.min(widthP - i, 220);
//            Screen.blit(matrixStack, i, 0, w, 28, 9, 1, w, 28, 256, 256);
//        }
//
//        Screen.blit(matrixStack, widthP, 0, 8, 28, 229, 1, 8, 28, 256, 256);

//        // Bottom part
//        Screen.blit(matrixStack, 0, heightP, 8, 8, 1, 47, 8, 8, 256, 256);
//
//        for (int i = 8; i <= widthP; i += 220) {
//            int w = Math.min(widthP - i, 220);
//            Screen.blit(matrixStack, i, heightP, w, 8, 9, 47, w, 8, 256, 256);
//        }
//
//        Screen.blit(matrixStack, widthP, heightP, 8, 8, 229, 47, 8, 8, 256, 256);

        RenderSystem.translated(WindowBorder.BORDER_WIDTH_LEFT, WindowBorder.BORDER_WIDTH_TOP, 0);
//        matrixStack.translate(0, 2, 0);
        RenderSystem.enableScissor(
                (int) (WindowBorder.BORDER_WIDTH_LEFT * mc.getMainWindow().getGuiScaleFactor()),
                (int) (WindowBorder.BORDER_WIDTH_BOTTOM * mc.getMainWindow().getGuiScaleFactor()),
                (int) (mc.getMainWindow().getWidth()/* - WindowBorder.BORDER_WIDTH_LEFT * mc.getMainWindow().getGuiScaleFactor()*/),
                (int) (mc.getMainWindow().getHeight()/* - WindowBorder.BORDER_WIDTH_TOP * mc.getMainWindow().getGuiScaleFactor()*/)
        );
        RenderSystem.pushMatrix();
    }

    @Inject(method = "updateCameraAndRender(FJZ)V", at = @At(value = "INVOKE", target = "Lnet/minecraftforge/client/ForgeHooksClient;drawScreen(Lnet/minecraft/client/gui/screen/Screen;Lcom/mojang/blaze3d/matrix/MatrixStack;IIF)V", ordinal = 0))
    public void updateCameraAndRender2(float partialTicks, long nanoTime, boolean renderWorldIn, CallbackInfo ci) {

    }

    @Inject(method = "updateCameraAndRender(FJZ)V", at = @At(value = "RETURN"))
    public void updateCameraAndRender1(float partialTicks, long nanoTime, boolean renderWorldIn, CallbackInfo ci) {
        RenderSystem.popMatrix();
        RenderSystem.disableScissor();
    }
}
