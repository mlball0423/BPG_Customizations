
package net.mcreator.bpgcustomizations.item;

import net.minecraftforge.registries.ObjectHolder;

import net.minecraft.item.Rarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.block.BlockState;

import net.mcreator.bpgcustomizations.itemgroup.BPGCustomizationsTabItemGroup;
import net.mcreator.bpgcustomizations.BpgCustomizationsModElements;

@BpgCustomizationsModElements.ModElement.Tag
public class InfinitiumCatalystItem extends BpgCustomizationsModElements.ModElement {
	@ObjectHolder("bpg_customizations:infinitium_catalyst")
	public static final Item block = null;
	public InfinitiumCatalystItem(BpgCustomizationsModElements instance) {
		super(instance, 68);
	}

	@Override
	public void initElements() {
		elements.items.add(() -> new ItemCustom());
	}
	public static class ItemCustom extends Item {
		public ItemCustom() {
			super(new Item.Properties().group(BPGCustomizationsTabItemGroup.tab).maxStackSize(16).isImmuneToFire().rarity(Rarity.COMMON));
			setRegistryName("infinitium_catalyst");
		}

		@Override
		public int getItemEnchantability() {
			return 0;
		}

		@Override
		public int getUseDuration(ItemStack itemstack) {
			return 0;
		}

		@Override
		public float getDestroySpeed(ItemStack par1ItemStack, BlockState par2Block) {
			return 1F;
		}
	}
}
