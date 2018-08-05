package de.outinetworks.InfoMod.mods;

import de.outinetworks.InfoMod.config.InfoModConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumHandSide;
import org.lwjgl.opengl.GL11;

public class DurabilityViewer
{
	private Minecraft MC;
	
	public DurabilityViewer(Minecraft mc)
	{
		MC = mc;
        if(InfoModConfig.showHotBarDurability) DrawHotBarOverlay(MC.player);
        if(InfoModConfig.showArmorDurability) DrawArmor(MC.player.getArmorInventoryList());
	}

	private void DrawHotBarOverlay(EntityPlayer player)
    {
        ScaledResolution scaled = new ScaledResolution(MC);
        int width = scaled.getScaledWidth();
        int height = scaled.getScaledHeight();

        GL11.glPushMatrix();
        GlStateManager.disableDepth();
        GL11.glScalef(0.5F, 0.5F, 0.5F);

        for (int SlotID = 0; SlotID <= 8; SlotID ++)
        {
            DrawHotBarItemOverlay(player, width, height, player.inventory.getStackInSlot(SlotID), SlotID * 20);
        }
        DrawHotBarItemOverlay(player, width, height, player.getHeldItemOffhand(), player.getPrimaryHand() == EnumHandSide.RIGHT ? (-1 * 20 - 9) : (9 * 20 + 9));
        GL11.glScalef(1F, 1F, 1F);
        GlStateManager.enableDepth();
        GL11.glPopMatrix();
    }

    private void DrawHotBarItemOverlay(EntityPlayer player, int width, int height, ItemStack HotBarItem, int xOffset)
    {
        // check if Tool
        if (HotBarItem.getMaxDamage() > 0)
        {
            int damage = HotBarItem.getMaxDamage() - HotBarItem.getItemDamage();
            int color;

            if (damage > HotBarItem.getMaxDamage() / 4)
                color = 0x00ff00;
            else
                color = 0xff0000;

            MC.fontRenderer.drawString(Integer.toString(damage), (((width / 2) - 88) + xOffset) * 2, (height - 18) * 2, color);

            if (HotBarItem.getItem().equals(Items.BOW))
            {
                int ArrowCount = GetInventoryArrowCount(player);

                if(HasInfinity(HotBarItem) && ArrowCount > 0)
                    MC.fontRenderer.drawString(Character.toString('\u221e'), (((width / 2) - 78) + xOffset) * 2, (height - 10) * 2, 0xdddddd);
                else
                    MC.fontRenderer.drawString(Integer.toString(ArrowCount), (((width / 2) - 83) + xOffset) * 2, (height - 10) * 2, 0xdddddd);
            }

        }
    }

	private void DrawArmor(Iterable<ItemStack> armorInventory)
	{
		int i = 0;
		for(ItemStack stack : armorInventory)
		{
			if(stack.getCount() != 0)
				DrawArmorItem(stack, 0, 64-((i + 1) * 16));
			i++;
		}
	}
	
	private void DrawArmorItem(ItemStack ArmorItem, int x, int y)
	{
        RenderItem itemRender = MC.getRenderItem();
		GL11.glPushMatrix();
        GlStateManager.disableDepth();
        GL11.glScalef(1F, 1F, 1F);
        
        if (ArmorItem != null)
        {
        	itemRender.renderItemIntoGUI(ArmorItem, x, y);
        	int damage = ArmorItem.getMaxDamage() - ArmorItem.getItemDamage();
			int color;
			
			if (damage > ArmorItem.getMaxDamage() / 4)
				color = 0x00ff00;
			else
				color = 0xff0000;

			MC.fontRenderer.drawString(Integer.toString(damage), x + 17, y + 5 , color);
    	}
        GlStateManager.enableDepth();
        GL11.glPopMatrix();
	}
	
	private int GetInventoryArrowCount(EntityPlayer player)
	{
		int count = 0;
		
		for (int Slot = 0; Slot < player.inventory.getSizeInventory(); Slot++)
		{
			ItemStack Stack = player.inventory.getStackInSlot(Slot);
			if (Stack.getItem().equals(Items.ARROW))
				count += Stack.getCount();
		}
		return count;
	}
	
	private boolean HasInfinity(ItemStack item)
	{
		if(item.isItemEnchanted())
		{
			NBTTagList enchantmentList = item.getEnchantmentTagList();
			for(int i = 0; i < enchantmentList.tagCount(); i++)
			{
				if (enchantmentList.getCompoundTagAt(i).getShort("id") == 51)
					return true;
	        }
			return false;
		}
		else
			return false;
	}
}
