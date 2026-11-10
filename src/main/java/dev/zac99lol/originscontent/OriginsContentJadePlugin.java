package dev.zac99lol.originscontent;

import dev.zac99lol.neverend.index.ModBlocks;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@WailaPlugin
public class OriginsContentJadePlugin implements IWailaPlugin {
    @Override
    public void registerClient(IWailaClientRegistration registration) {
        registration.hideTarget(ModBlocks.LIMINAL_PORTAL);
        registration.hideTarget(ModBlocks.LIMINAL_END_PORTAL_FRAME);
    }
}
