package me.justahuman.easy_item_list.hooks;

import me.justahuman.easy_item_list.EasyItemList;
import me.justahuman.easy_item_list.api.Hook;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.IRuntimeRegistration;
import mezz.jei.api.runtime.IIngredientManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

@JeiPlugin
public class JeiHook extends Hook implements IModPlugin {
    private IIngredientManager manager;

    @Override
    public void registerRuntime(IRuntimeRegistration registration) {
        this.manager = registration.getIngredientManager();
        load();
    }

    @Override
    public boolean alreadyAdded(ItemStack itemStack) {
        // Simple check - if the item and its components match any existing stack
        return ITEM_STACKS.stream().anyMatch(stack -> 
            stack.getItem() == itemStack.getItem() && 
            stack.getComponents().equals(itemStack.getComponents())
        );
    }

    @Override
    public void addItemStacks() {
        this.manager.addIngredientsAtRuntime(VanillaTypes.ITEM_STACK, ITEM_STACKS);
    }

    @Override
    public @NotNull Identifier getPluginUid() {
        return Identifier.of(EasyItemList.MOD_ID, "jei_hook");
    }
}
