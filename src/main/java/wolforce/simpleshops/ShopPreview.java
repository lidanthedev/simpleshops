package wolforce.simpleshops;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.ContainerScreen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod.EventBusSubscriber(modid = "mymod", bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ShopPreview {

    private static final Logger log = LoggerFactory.getLogger(ShopPreview.class);

    public void openPreviewScreen(Player player, ItemStack itemStack, Component message) {
        Inventory inv = player.getInventory();
        ChestMenu chestMenu = ChestMenu.oneRow(456, inv);
        chestMenu.setItem(4, 1234, itemStack);
        ContainerScreen screen = new ContainerScreen(chestMenu, player.getInventory(), message);
        Minecraft.getInstance().setScreen(screen);
    }

    @SubscribeEvent
    public void onPlayerInteractRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        Level level = event.getLevel();
        if (!level.isClientSide) {
            return;
        }
        BlockPos pos = event.getPos();
        BlockEntity _te = level.getBlockEntity(pos);
        Player player = event.getEntity();
        if (!player.isShiftKeyDown()) {
            return;
        }
        if (!(_te instanceof SimpleShopTileEntity shop)) {
            return;
        }
        openPreviewScreen(player, shop.getOutputStack(), Component.literal("Preview"));
    }
}
