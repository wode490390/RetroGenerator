package cn.wode490390.nukkit.retrogen;

import cn.nukkit.level.ChunkManager;
import cn.nukkit.level.biome.EnumBiome;
import cn.nukkit.level.format.generic.BaseFullChunk;
import cn.nukkit.level.generator.Generator;
import cn.nukkit.math.NukkitRandom;
import cn.nukkit.math.Vector3;
import cn.wode490390.nukkit.retrogen.noise.PerlinSimplexNoise;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class RetroGenerator extends Generator {

    protected ChunkManager level;
    protected NukkitRandom nukkitRandom;
    protected long localSeed1;
    protected long localSeed2;
    protected RetroGeneratorSettings settings;

    protected PerlinSimplexNoise noise;

    public RetroGenerator() {
        this(null);
    }

    public RetroGenerator(Map<String, Object> options) {
        this.settings = RetroGeneratorPlugin.getInstance().getSettings();
    }

    @Override
    public int getId() {
        return TYPE_INFINITE;
    }

    @Override
    public String getName() {
        return "normal";
    }

    @Override
    public ChunkManager getChunkManager() {
        return this.level;
    }

    @Override
    public Map<String, Object> getSettings() {
        return Collections.emptyMap();
    }

    @Override
    public void init(ChunkManager level, NukkitRandom random) {
        this.level = level;
        this.nukkitRandom = random;
        this.nukkitRandom.setSeed(this.level.getSeed());
        this.localSeed1 = ThreadLocalRandom.current().nextLong();
        this.localSeed2 = ThreadLocalRandom.current().nextLong();

        this.noise = new PerlinSimplexNoise(this.nukkitRandom, 6);
    }

    @Override
    public void generateChunk(int chunkX, int chunkZ) {
        this.nukkitRandom.setSeed(chunkX * this.localSeed1 ^ chunkZ * this.localSeed2 ^ this.level.getSeed());
        BaseFullChunk chunk = this.level.getChunk(chunkX, chunkZ);

        for (int x = 0; x < 16; ++x) {
            float xF = x / 16f;
            for (int z = 0; z < 16; ++z) {
                for (int y = 0; y < 64 + (this.noise.getValue(chunkX + xF, chunkZ + z / 16f) + 1) * 20; ++y) {
                    if (x == 0 || z == 0 || y % 16 == 0) {
                        chunk.setBlock(x, y, z, this.settings.getLineId(), this.settings.getLineMeta());
                    } else {
                        chunk.setBlock(x, y, z, this.settings.getBackgroundId(), this.settings.getBackgroundMeta());
                    }
                }
                chunk.setBiome(x, z, EnumBiome.JUNGLE.biome);
            }
        }
    }

    @Override
    public void populateChunk(int chunkX, int chunkZ) {

    }

    @Override
    public Vector3 getSpawn() {
        return new Vector3(0.5, 256, 0.5);
    }
}
