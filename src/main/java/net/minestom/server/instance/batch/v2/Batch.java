package net.minestom.server.instance.batch.v2;

import net.minestom.server.MinecraftServer;
import net.minestom.server.data.Data;
import net.minestom.server.instance.BlockModifier;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.block.CustomBlock;
import net.minestom.server.utils.thread.MinestomThread;
import net.minestom.server.utils.validate.Check;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.ExecutorService;

/**
 * A Batch is a tool used to cache a list of block changes, and apply the changes whenever you want.
 * <p>
 * Batches offer a performance benefit because clients are not notified of any change until all of
 * the blocks have been placed.
 * <p>
 * All batches may be rotated using {link}, however rotate operations do not mutate the batch, so the
 * result should be cached if used multiple times.
 * <p>
 * If reversal is a desired behavior, batches may be applied in "reversal mode" using {link}. This
 * operation will return a new batch with the blocks set to whatever they were before the batch was
 * applied.
 *
 * @see ChunkBatch
 * @see AbsoluteBlockBatch
 * @see RelativeBlockBatch
 */
public interface Batch<Callback> extends BlockModifier {
    ExecutorService BLOCK_BATCH_POOL = new MinestomThread(MinecraftServer.THREAD_COUNT_BLOCK_BATCH, MinecraftServer.THREAD_NAME_BLOCK_BATCH);

    @Override
    default void setBlockStateId(int x, int y, int z, short blockStateId, @Nullable Data data) {
        setSeparateBlocks(x, y, z, blockStateId, (short) 0, data);
    }

    @Override
    default void setCustomBlock(int x, int y, int z, short customBlockId, @Nullable Data data) {
        final CustomBlock customBlock = BLOCK_MANAGER.getCustomBlock(customBlockId);
        Check.notNull(customBlock, "The custom block with the id " + customBlockId + " does not exist!");
        setSeparateBlocks((byte) x, y, (byte) z, customBlock.getDefaultBlockStateId(), customBlockId, data);
    }

    @Override
    void setSeparateBlocks(int x, int y, int z, short blockStateId, short customBlockId, @Nullable Data data);

    /**
     * Removes all block data from this batch.
     */
    void clear();

    // Batch rotate(? );

    /**
     * Called to apply the batch to the given instance.
     * <p>
     * The implementation for all current batches executes the block updates in a dedicated pool,
     * and runs the callback on the next instance update after block placement is complete. This
     * means that the callback can be called up to 50ms after the blocks have been placed, however,
     * it will be called in a determinable thread. If immediate execution of the callback is needed,
     * see the unsafeApply method in each implementation.
     * <p>
     * See the specific batch classes for alternative application methods.
     *
     * @param instance The instance in which the batch should be applied
     * @param callback The callback to be executed when the batch is applied
     */
    void apply(@NotNull InstanceContainer instance, @Nullable Callback callback);

//    @NotNull
//    Batch<Callback> reversableApply(@NotNull InstanceContainer instance, @Nullable Callback callback);
}
