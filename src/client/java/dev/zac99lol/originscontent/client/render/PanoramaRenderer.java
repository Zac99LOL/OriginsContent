package dev.zac99lol.originscontent.client.render;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.zac99lol.originscontent.OriginsContent;
import net.ludocrypt.specialmodels.api.SpecialModelRenderer;
import net.ludocrypt.specialmodels.impl.render.MutableQuad;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.chunk.ChunkRendererRegion;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec2f;
import org.joml.Matrix4f;

public class PanoramaRenderer extends SpecialModelRenderer {
    @Override
    public void setup(MatrixStack matrices, Matrix4f viewMatrix, Matrix4f positionMatrix, float tickDelta, ShaderProgram shader, BlockPos chunkOrigin) {
        RenderSystem.polygonOffset(-1, -1);

        for (int i = 0; i < 6; ++i) {
            RenderSystem.setShaderTexture(i, OriginsContent.id("textures/block/panorama/" + i + ".png"));
        }

        MinecraftClient client = MinecraftClient.getInstance();
        Camera camera = client.gameRenderer.getCamera();
        Matrix4f matrix = new MatrixStack().peek().getPositionMatrix();
        matrix.rotate(RotationAxis.POSITIVE_X.rotationDegrees(camera.getPitch()));
        matrix.rotate(RotationAxis.POSITIVE_Y.rotationDegrees(camera.getYaw() + 180.0F));
        if (shader.getUniform("RotMat") != null) {
            //noinspection DataFlowIssue
            shader.getUniform("RotMat").set(matrix);
        }
    }

    @Override
    public MutableQuad modifyQuad(ChunkRendererRegion chunkRenderRegion, BlockPos pos, BlockState state, BakedModel model, BakedQuad quadIn, long modelSeed, MutableQuad quad) {
        quad.getV1().setUv(new Vec2f(0.0F, 0.0F));
        quad.getV2().setUv(new Vec2f(0.0F, 1.0F));
        quad.getV3().setUv(new Vec2f(1.0F, 1.0F));
        quad.getV4().setUv(new Vec2f(1.0F, 0.0F));
        return quad;
    }
}
