package wolforce.simpleshops;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.entity.ItemRenderer;

public class ConfirmScreen extends Screen {
    private final ItemStack itemStack;
    private final Component message;
    private final Component confirmButtonText;
    private final Component cancelButtonText;
    private final Runnable onConfirm;
    private final Runnable onCancel;

    public ConfirmScreen(ItemStack itemStack, Component message, Component confirmButtonText, Component cancelButtonText, Runnable onConfirm, Runnable onCancel) {
        super(message);
        this.itemStack = itemStack;
        this.message = message;
        this.confirmButtonText = confirmButtonText;
        this.cancelButtonText = cancelButtonText;
        this.onConfirm = onConfirm;
        this.onCancel = onCancel;
    }

    @Override
    protected void init() {
        int buttonWidth = 100;
        int buttonHeight = 20;
        int centerX = this.width / 2;
        int centerY = this.height / 2;

        this.addRenderableWidget(new Button(centerX - buttonWidth - 10, centerY + 30, buttonWidth, buttonHeight, confirmButtonText, button -> {
            onConfirm.run();
            this.minecraft.setScreen(null);
        }));

        this.addRenderableWidget(new Button(centerX + 10, centerY + 30, buttonWidth, buttonHeight, cancelButtonText, button -> {
            onCancel.run();
            this.minecraft.setScreen(null);
        }));
    }

    @Override
    public void render(PoseStack pose ,int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(pose);
        drawCenteredString(pose, this.font, this.message.getString(), this.width / 2, this.height / 2 - 50, 0xFFFFFF);
        super.render(pose, mouseX, mouseY, partialTicks);

        RenderSystem.setShaderTexture(0, InventoryMenu.BLOCK_ATLAS);
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        itemRenderer.renderAndDecorateItem(this.itemStack, this.width / 2 - 8, this.height / 2 - 8);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}