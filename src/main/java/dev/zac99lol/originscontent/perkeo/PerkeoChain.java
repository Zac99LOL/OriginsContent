// dev/zac99lol/originscontent/perkeo/PerkeoChain.java
package dev.zac99lol.originscontent.perkeo;

import dev.zac99lol.originscontent.perkeo.steps.DormantCatalystStep;

import static dev.zac99lol.originscontent.OriginsContent.devLog;

public class PerkeoChain {
    public static void initialize() {
        devLog("Initialising perkeo chain...");
        DormantCatalystStep.initialize();
        // More steps will be added here as we implement them
    }
}