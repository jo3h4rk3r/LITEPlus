package liteplus.mixin;


import liteplus.litepluscore;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(LeavesBlock.class)
@Environment(EnvType.CLIENT)
public class MixinLeavesBlock extends Block {

    public MixinLeavesBlock(Settings settings) {
        super(settings);
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isSideInvisible(BlockState state, BlockState neighborState, Direction offset) {
        if (litepluscore.SmartTrees) {
            return neighborState.getBlock() instanceof LeavesBlock;
        }
        else return false;
    }


}