package liteplus.mixin;

import liteplus.litepluscore;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.chunk.ChunkBuilder;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChunkBuilder.class)
public class MixinChunkBuilder {
    private int counter33 = 0;

    @Shadow @Final
    WorldRenderer worldRenderer;


    @Inject(method = "scheduleRunTasks", at = @At("HEAD"), cancellable = true)
    private void getChunkBuilder(CallbackInfo ci) {
        if (!litepluscore.isChunksLoading) {

            counter33++;
            if (counter33 == 12) {

               // System.out.println("testttt");

                ci.cancel();
                counter33 = 0;
            }


        }

    }




    @ModifyVariable(method = "<init>", index = 9, at = @At(value = "INVOKE", target = "Lcom/google/common/collect/Lists;newArrayListWithExpectedSize(I)Ljava/util/ArrayList;", remap = false))
    private int modifyThreadPoolSize(int prev) {


        return 1;
    }



}