package cn.wode490390.nukkit.retrogen;

import cn.nukkit.block.Block;
import cn.nukkit.level.GlobalBlockPalette;
import cn.nukkit.level.generator.Generator;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.DyeColor;
import cn.wode490390.nukkit.retrogen.util.MetricsLite;

import java.util.NoSuchElementException;

public class RetroGeneratorPlugin extends PluginBase {

    private static RetroGeneratorPlugin INSTANCE;

    private RetroGeneratorSettings settings;

    @Override
    public void onLoad() {
        INSTANCE = this;
    }

    @Override
    public void onEnable() {
        try {
            new MetricsLite(this, 6997);
        } catch (Throwable ignore) {

        }

        this.saveDefaultConfig();
        Config config = this.getConfig();

        String node = "line.material";
        int lineId = Block.CONCRETE;
        try {
            lineId = config.getInt(node, lineId);
        } catch (Exception e) {
            this.logConfigException(node, e);
        }
        int lineMeta = DyeColor.LIME.getWoolData();
        node = "line.meta";
        try {
            lineMeta = config.getInt(node, lineMeta);
        } catch (Exception e) {
            this.logConfigException(node, e);
        }
        node = "background.material";
        int backgroundId = Block.CONCRETE;
        try {
            backgroundId = config.getInt(node, backgroundId);
        } catch (Exception e) {
            this.logConfigException(node, e);
        }
        int backgroundMeta = DyeColor.BLACK.getWoolData();
        node = "background.meta";
        try {
            backgroundMeta = config.getInt(node, backgroundMeta);
        } catch (Exception e) {
            this.logConfigException(node, e);
        }

        try {
            GlobalBlockPalette.getOrCreateRuntimeId(lineId, 0);
            try {
                GlobalBlockPalette.getOrCreateRuntimeId(lineId, lineMeta);
            } catch (NoSuchElementException e) {
                lineMeta = 0;
                this.getLogger().warning("Invalid block meta. Use the default value.");
            }
        } catch (NoSuchElementException e) {
            lineId = Block.CONCRETE;
            lineMeta = DyeColor.LIME.getWoolData();
            this.getLogger().warning("Invalid block ID. Use the default value.");
        }
        try {
            GlobalBlockPalette.getOrCreateRuntimeId(backgroundId, 0);
            try {
                GlobalBlockPalette.getOrCreateRuntimeId(backgroundId, backgroundMeta);
            } catch (NoSuchElementException e) {
                backgroundMeta = 0;
                this.getLogger().warning("Invalid block meta. Use the default value.");
            }
        } catch (NoSuchElementException e) {
            backgroundId = Block.CONCRETE;
            backgroundMeta = DyeColor.BLACK.getWoolData();
            this.getLogger().warning("Invalid block ID. Use the default value.");
        }

        this.settings = new RetroGeneratorSettings(lineId, lineMeta, backgroundId, backgroundMeta);

        Generator.addGenerator(RetroGenerator.class, "default", Generator.TYPE_INFINITE);
        Generator.addGenerator(RetroGenerator.class, "normal", Generator.TYPE_INFINITE);
    }

    public RetroGeneratorSettings getSettings() {
        return this.settings;
    }

    private void logConfigException(String node, Throwable t) {
        this.getLogger().alert("An error occurred while reading the configuration '" + node + "'. Use the default value.", t);
    }

    public static RetroGeneratorPlugin getInstance() {
        return INSTANCE;
    }
}
