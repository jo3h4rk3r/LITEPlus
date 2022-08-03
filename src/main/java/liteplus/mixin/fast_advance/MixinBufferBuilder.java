package liteplus.mixin.fast_advance;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.FixedColorVertexConsumer;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormatElement;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(BufferBuilder.class)
public abstract class MixinBufferBuilder extends FixedColorVertexConsumer  {
    @Shadow
    private VertexFormat format;

    @Shadow
    private VertexFormatElement currentElement;

    @Shadow
    private int elementOffset;

    @Shadow
    private int currentElementId;

    /**
     * @author JellySquid
     * @reason Remove modulo operations and recursion
     */
    @Overwrite
    public void nextElement() {
        ImmutableList<VertexFormatElement> elements = this.format.getElements();

        do {
            this.elementOffset += this.currentElement.getByteLength();

            // Wrap around the element pointer without using modulo
            if (++this.currentElementId >= elements.size()) {
                this.currentElementId -= elements.size();
            }

            this.currentElement = elements.get(this.currentElementId);
        } while (this.currentElement.getType() == VertexFormatElement.Type.PADDING);

        if (this.colorFixed && this.currentElement.getType() == VertexFormatElement.Type.COLOR) {
            this.color(this.fixedRed, this.fixedGreen, this.fixedBlue, this.fixedAlpha);
        }
    }
}