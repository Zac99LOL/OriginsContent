package dev.zac99lol.originscontent.config;

import dev.zac99lol.originscontent.OriginsContent;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name = OriginsContent.MOD_ID)
public class ModConfig implements ConfigData {
    public boolean wikiCommandEnabled = false;
    public boolean mapCommandEnabled = false;

    public String reallyAssMethodOfMakingACommentBecauseClothConfigDoesntProperlySupportIt = "This should be a valid url, e.g. https://google.com/ would pass. Also, if wikiCommandEnabled is false, this does not matter (it wont crash dw)";
    public String wikiAddress = "CHANGEME";
    public String reallyAssMethodOfMakingACommentBecauseClothConfigDoesntProperlySupportItAGAIN = "This should be a valid url, e.g. https://google.com/ would pass. Also, if mapCommandEnabled is false, this does not matter (it wont crash dw)";
    public String mapAddress = "CHANGEMETOO";

    @Override
    public void validatePostLoad() throws ValidationException {
        if (!wikiAddress.equals("CHANGEME") && !wikiAddress.matches("https?://[\\w\\-.]+(:\\d+)?(/.*)?") && wikiCommandEnabled) {
            throw new RuntimeException("[OriginsContent] Invalid config: wikiAddress must be a valid URL, got: \"" + wikiAddress + "\"");
        }
        if (!mapAddress.equals("CHANGEMETOO") && !mapAddress.matches("https?://[\\w\\-.]+(:\\d+)?(/.*)?") && mapCommandEnabled) {
            throw new RuntimeException("[OriginsContent] Invalid config: mapAddress must be a valid URL, got: \"" + mapAddress + "\"");
        }
    }
}
