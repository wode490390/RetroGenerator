package cn.wode490390.nukkit.retrogen;

public class RetroGeneratorSettings {

    private final int lineId;
    private final int lineMeta;
    private final int backgroundId;
    private final int backgroundMeta;

    public RetroGeneratorSettings(int lineId, int lineMeta, int backgroundId, int backgroundMeta) {
        this.lineId = lineId;
        this.lineMeta = lineMeta;
        this.backgroundId = backgroundId;
        this.backgroundMeta = backgroundMeta;
    }

    public int getLineId() {
        return this.lineId;
    }

    public int getLineMeta() {
        return this.lineMeta;
    }

    public int getBackgroundId() {
        return this.backgroundId;
    }

    public int getBackgroundMeta() {
        return this.backgroundMeta;
    }
}
