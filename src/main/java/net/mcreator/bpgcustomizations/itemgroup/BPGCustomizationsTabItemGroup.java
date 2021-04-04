
package net.mcreator.bpgcustomizations.itemgroup;

import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemGroup;

import net.mcreator.bpgcustomizations.block.InfinitiumBlockBlock;
import net.mcreator.bpgcustomizations.BpgCustomizationsModElements;

@BpgCustomizationsModElements.ModElement.Tag
public class BPGCustomizationsTabItemGroup extends BpgCustomizationsModElements.ModElement {
	public BPGCustomizationsTabItemGroup(BpgCustomizationsModElements instance) {
		super(instance, 9);
	}

	@Override
	public void initElements() {
		tab = new ItemGroup("tabbpg_customizations_tab") {
			@OnlyIn(Dist.CLIENT)
			@Override
			public ItemStack createIcon() {
				return new ItemStack(InfinitiumBlockBlock.block, (int) (1));
			}

			@OnlyIn(Dist.CLIENT)
			public boolean hasSearchBar() {
				return false;
			}
		};
	}
	public static ItemGroup tab;
}
