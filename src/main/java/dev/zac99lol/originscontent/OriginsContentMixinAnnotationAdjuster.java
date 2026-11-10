package dev.zac99lol.originscontent;

import com.bawnorton.mixinsquared.adjuster.tools.AdjustableAnnotationNode;
import com.bawnorton.mixinsquared.adjuster.tools.AdjustableInjectNode;
import com.bawnorton.mixinsquared.api.MixinAnnotationAdjuster;
import org.jetbrains.annotations.Nullable;
import org.objectweb.asm.tree.MethodNode;
import org.spongepowered.asm.mixin.injection.Inject;

import java.util.List;

public class OriginsContentMixinAnnotationAdjuster implements MixinAnnotationAdjuster {
    @Override
    public @Nullable AdjustableAnnotationNode adjust(List<String> targetClassNames, String mixinClassName, MethodNode handlerNode, AdjustableAnnotationNode annotationNode) {
        if (!mixinClassName.equals("doctor4t.defile.mixin.inkling.InklingDivePlayerEntityMixin")) return annotationNode;
        if (!annotationNode.is(Inject.class)) return annotationNode;
        AdjustableInjectNode injectNode = annotationNode.as(AdjustableInjectNode.class);

        if (injectNode.getMethod().get(0).equals("isInvulnerableTo")) return null;

        return annotationNode;
    }
}
