package de.outinetworks.InfoMod.mods;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.NonNullList;

public class DurabilityViewer
{
	
	public static RenderItem itemRender;
	int width;
	int height;
	Minecraft MC;
	
	public DurabilityViewer(Minecraft mc)
	{
		MC = mc;
		itemRender = MC.getRenderItem();
		ScaledResolution scaled = new ScaledResolution(MC);
		width = scaled.getScaledWidth();
		height = scaled.getScaledHeight();
		
		EntityPlayer player = MC.player;
		GL11.glPushMatrix();
        GlStateManager.disableDepth();
        GL11.glScalef(0.5F, 0.5F, 0.5F);
        
		for (int SlotID = 0; SlotID <= 8; SlotID ++)
		{	
			ItemStack HotbarItem = player.inventory.getStackInSlot(SlotID);
			// check if Tool
			if (HotbarItem != null)
			{
				if (HotbarItem.getMaxDamage() > 0)
				{
					int damage = HotbarItem.getMaxDamage() - HotbarItem.getItemDamage();
					int color;
					
					if (damage > HotbarItem.getMaxDamage() / 4)
						color = 0x00ff00;
					else
						color = 0xff0000;
					
					MC.fontRendererObj.drawString(Integer.toString(damage), (((width / 2) - 88) + (SlotID * 20)) * 2, (height - 18) * 2, color);
					
					if (HotbarItem.getItem().equals(Items.BOW))
					{
						int ArrowCount = GetIventoryArrowCount(player);
						
						if(HasInfinity(HotbarItem) && ArrowCount > 0)
							MC.fontRendererObj.drawString(Character.toString('\u221e'), (((width / 2) - 78) + (SlotID * 20)) * 2, (height - 10) * 2, 0xdddddd);
						else
							MC.fontRendererObj.drawString(Integer.toString(ArrowCount), (((width / 2) - 83) + (SlotID * 20)) * 2, (height - 10) * 2, 0xdddddd);
					}
						
				}
			}
		}
		GL11.glScalef(1F, 1F, 1F);
        GlStateManager.enableDepth();
        GL11.glPopMatrix();
        
        DrawArmor(player.getArmorInventoryList());
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

			MC.fontRendererObj.drawString(Integer.toString(damage), x + 17, y + 5 , color);
    	}
        GlStateManager.enableDepth();
        GL11.glPopMatrix();
	}
	
	private int GetIventoryArrowCount(EntityPlayer player)
	{
		int count = 0;
		
		for (int Slot = 0; Slot < player.inventory.getSizeInventory(); Slot++)
		{
			ItemStack Stack = player.inventory.getStackInSlot(Slot);
			if (Stack != null && Stack.getItem().equals(Items.ARROW))
				count += Stack.getCount();
		}
		return count;
	}
	
	private boolean HasInfinity(ItemStack item)
	{
		if(item.isItemEnchanted())
		{
			NBTTagList enchlist = item.getEnchantmentTagList();
			for(int i = 0; i < enchlist.tagCount(); i++)
	        {
				if (enchlist.getCompoundTagAt(i).getShort("id") == Enchantment.getEnchantmentID(Enchantment.getEnchantmentByLocation("infinity")))
					return true;
	        }
			return false;
		}
		else
			return false;
	}
}
