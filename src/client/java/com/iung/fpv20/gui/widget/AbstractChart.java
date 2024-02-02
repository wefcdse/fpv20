package com.iung.fpv20.gui.widget;

import com.iung.fpv20.utils.FastMath;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Drawable;

public abstract class AbstractChart implements Drawable {
    private int x;
    private int y;
    int width;
    int height;

    public AbstractChart(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public abstract void render(DrawContext context, int mouseX, int mouseY, float delta);
//        context.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
//        context.drawHorizontalLine(RenderLayer.LINES, 0, 100, 10, 0xffffff);
//        context.drawTexture(Textures.COLOURED, this.x, y, 0, 0, width, height, width, height);
//        context.fill(x, y, x + width, y + height, 0xffffffff);
//
//
//        context.fill(x + width / 2, y + height / 2, x + width / 2 + 1, y + height / 2 + 1, 0xff_ff_00_00);
//
//        clear(context, 0xff_ff_ff_ff);
//
//        drawCurve(context, (x) -> x * x, 0xff_00_00_ff);
//
//        border(context, 0xff_00_ff_00);

    //        MatrixStack matrixStack = context.getMatrices();
//        Tessellator tessellator = Tessellator.getInstance();
//        BufferBuilder buffer = tessellator.getBuffer();
//        buffer.begin(VertexFormat.DrawMode.DEBUG_LINES, VertexFormats.POSITION_COLOR);
////        Fpv20.LOGGER.info("here010");
//
//        matrixStack.push();
//        matrixStack.translate(this.x, this.y + this.height, 0);
//        matrixStack.scale(this.width, -this.height, 1);
//        Matrix4f matrix = matrixStack.peek().getPositionMatrix();
//
////        Fpv20.LOGGER.info("here00");
//
//
//        buffer.vertex(matrix, 0, 0, 0).color(1f, 1f, 1f, 1f).next();
//        buffer.vertex(matrix, 0, 1, 0).color(1f, 1f, 1f, 1f).next();
//
//        buffer.vertex(matrix, 0, 0, 1).color(1f, 1f, 1f, 1f).next();
//        buffer.vertex(matrix, 1, 0, 1).color(1f, 1f, 1f, 1f).next();
//
//        buffer.vertex(matrix, 1, 0, 0).color(1f, 1f, 1f, 1f).next();
//        buffer.vertex(matrix, 1, 1, 0).color(1f, 1f, 1f, 1f).next();
//
//        buffer.vertex(matrix, 0, 1, 0).color(1f, 1f, 1f, 1f).next();
//        buffer.vertex(matrix, 1, 1, 0).color(1f, 1f, 1f, 1f).next();
//
////        GL11.glDisable( GL11.GL_TEXTURE_2D);
////        buffer.end();
////        RenderSystem.depthMask(false);
////        Fpv20.LOGGER.info("here111");
////        RenderSystem.disableDepthTest();
////        GL13.glLineWidth(1.5f);
//
////        RenderSystem.
////        RenderSystem.depthMask(false);
////        Fpv20.LOGGER.info("here111");
//
////        GL11.glLineWidth(2.0F);
//
////        Fpv20.LOGGER.info("here");
//        tessellator.draw();
//
////        GL13.glLineWidth(1.0f);
////        RenderSystem.depthMask(true);
////        GL11.glEnable( GL11.GL_TEXTURE_2D);
//
//
////        GL11.glLineWidth(1.0F);
////        RenderSystem.depthMask(true);
////        ButtonWidget::render
//        matrixStack.pop();
//    }

    void drawCurve(DrawContext context, Curve c, int color) {
        for (int x = 0; x < width; x++) {
            float xf = (float) x / (float) width;
            int y = (int) (c.cacl(xf) * height);
            if (y < 0) {
                y = 0;
            }
            if (y > height) {
                y = height;
            }
            drawDot(context, x, this.height - y - 1, color);
        }
    }

    void drawDot(DrawContext context, int x, int y, int color) {
        drawDotGlobal(context, x + this.x, y + this.y, color);
    }

    void drawDot(DrawContext context, float x, float y, int color) {

        int ix = (int) (x * height);
        ix = FastMath.clamp(ix, 0, width);


        int iy = (int) (y * height);
        iy = FastMath.clamp(iy, 0, height);


        drawDot(context, ix, this.height - iy - 1, color);
        drawDot(context, ix - 1, this.height - iy - 1 - 1, color);
        drawDot(context, ix + 1, this.height - iy - 1 - 1, color);
        drawDot(context, ix - 1, this.height - iy - 1 + 1, color);
        drawDot(context, ix + 1, this.height - iy - 1 + 1, color);

//        drawDotGlobal(context, x + this.x, iy + this.y, color);
    }

    void fillLocal(DrawContext context, int x, int y, int width, int height, int color) {
        context.fill(x + this.x, y + this.y, x + this.x + width, y + this.y + height, color);
    }

    static void drawDotGlobal(DrawContext context, int x, int y, int color) {
        context.fill(x, y, x + 1, y + 1, color);
    }

    void clear(DrawContext context, int color) {
        context.fill(x, y, x + width, y + height, color);
    }

    void border(DrawContext context, int color) {
        context.drawBorder(x - 1, y - 1, width + 2, height + 2, color);
    }


    @FunctionalInterface
    public interface Curve {
        float cacl(float in);
    }

    @FunctionalInterface
    public interface ValueGetter {
        float get();
    }

}

