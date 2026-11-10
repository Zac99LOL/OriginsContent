package dev.zac99lol.originscontent.mixin.client.neverend_changes;

import dev.zac99lol.originscontent.OriginsContent;
import net.fabricmc.loader.api.FabricLoader;
import net.irisshaders.iris.Iris;
import net.irisshaders.iris.gl.shader.StandardMacros;
import net.irisshaders.iris.shaderpack.ShaderPack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Stream;

@Mixin(Iris.class)
public abstract class IrisMixin {
    @Shadow
    public static void clearShaderPackOptionQueue() {
        throw new UnsupportedOperationException("Implemented via mixin");
    }

    @Shadow
    private static boolean resetShaderPackOptions;

    @Shadow
    private static ShaderPack currentPack;

    @Shadow
    private static FileSystem zipFileSystem;

    @Shadow
    private static String currentPackName;

    @Inject(method = "loadExternalShaderpack", at = @At("HEAD"), cancellable = true)
    private static void originscontent$loadBuiltinShaders(String name, CallbackInfoReturnable<Boolean> cir) throws IOException {
        if (name.equals("neverend")) {
            cir.setReturnValue(true);
            Path shaderPackPath = originscontent$loadBuiltinZipShaderpack(name).orElseThrow();

            clearShaderPackOptionQueue();
            resetShaderPackOptions = false;

            currentPack = new ShaderPack(shaderPackPath, new HashMap<>(), StandardMacros.createStandardEnvironmentDefines());
            currentPackName = "neverend";
        }
    }

    @Unique
    private static Optional<Path> originscontent$loadBuiltinZipShaderpack(String shaderpackName) throws IOException {
        FileSystem zipSystem = FileSystems.newFileSystem(FabricLoader.getInstance().getModContainer(OriginsContent.MOD_ID).orElseThrow().findPath("shaders/" + shaderpackName + ".zip").orElseThrow(), net.irisshaders.iris.Iris.class.getClassLoader());
        zipFileSystem = zipSystem;
        Path root = (Path)zipSystem.getRootDirectories().iterator().next();
        Path potentialShaderDir = zipSystem.getPath("shaders");
        if (Files.exists(potentialShaderDir)) {
            return Optional.of(potentialShaderDir);
        } else {
            try (Stream<Path> stream = Files.walk(root)) {
                return stream.filter(Files::isDirectory).filter((path) -> path.endsWith("shaders")).findFirst();
            }
        }
    }
}
