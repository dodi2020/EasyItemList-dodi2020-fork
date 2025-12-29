package me.justahuman.easy_item_list.api;

import me.justahuman.easy_item_list.EasyItemList;
import net.minecraft.client.MinecraftClient;
import net.minecraft.component.ComponentMap;
import net.minecraft.component.ComponentType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.registry.BuiltinRegistries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class Hook {
    public static final Set<ComponentType<?>> COMPONENTS_TO_CHECK = Set.of(
            DataComponentTypes.ITEM_NAME, 
            DataComponentTypes.CUSTOM_NAME, 
            DataComponentTypes.LORE, 
            DataComponentTypes.FOOD, 
            DataComponentTypes.CUSTOM_MODEL_DATA
    );
    public static final RegistryWrapper.WrapperLookup LOOKUP = BuiltinRegistries.createWrapperLookup();
    protected static final List<ItemStack> ITEM_STACKS = new ArrayList<>();
    protected static final Map<ItemStack, String> NAMESPACES = new HashMap<>();

    public abstract boolean alreadyAdded(ItemStack itemStack);
    public abstract void addItemStacks();

    public void load() {
        ITEM_STACKS.clear();

        final World world = MinecraftClient.getInstance().world;
        if (world == null || world.getRecipeManager() == null) {
            return;
        }

        //  The Recipe API has changed significantly in 1.21.11
        // For now, we'll skip recipe loading to ensure compatibility
        // TODO: Update to use the new Recipe API when documentation is available
        
        EasyItemList.LOGGER.info("Recipe loading is currently disabled in 1.21.11");
        
        // Sort and add any manually added items
        if (!ITEM_STACKS.isEmpty()) {
            ITEM_STACKS.sort(Comparator.comparing(stack -> stack.getName().getString()));
            ITEM_STACKS.sort(Comparator.comparing(NAMESPACES::get));
            addItemStacks();
        }
    }

    public void handleIngredient(String namespace, Ingredient ingredient) {
        try {
            List<ItemStack> stacks = ingredient.getMatchingItems()
                    .map(entry -> entry.value().getDefaultStack())
                    .collect(Collectors.toList());
            for (ItemStack itemStack : stacks) {
                handleItem(itemStack.copy(), namespace);
            }
        } catch (Exception e) {
            // Ingredient might not support getMatchingItems(), skip it
        }
    }

    public void handleItem(ItemStack itemStack, String namespace) {
        if (itemStack == null || !isCustom(itemStack) || alreadyAdded(itemStack)) {
            return;
        }

        ITEM_STACKS.add(itemStack);
        NAMESPACES.put(itemStack, namespace);
    }

    public boolean isCustom(ItemStack itemStack) {
        final ComponentMap components = itemStack.getComponents();
        
        // Check for custom NBT data
        if (components.contains(DataComponentTypes.CUSTOM_DATA)) {
            final NbtComponent customData = components.get(DataComponentTypes.CUSTOM_DATA);
            final NbtCompound nbt = customData.copyNbt();
            
            // Remove VVIProtocol keys (internal data)
            for (String key : new HashSet<>(nbt.getKeys())) {
                if (key.contains("VVIProtocol")) {
                    nbt.remove(key);
                }
            }

            // Remove vanilla technical data that doesn't make items custom
            nbt.remove("Damage");
            nbt.remove("Enchantments");
            nbt.remove("Patterns");
            nbt.remove("Trim");
            nbt.remove("StoredEnchantments");
            nbt.remove("EntityTag");
            nbt.remove("Fireworks");
            nbt.remove("pages");
            nbt.remove("author");
            nbt.remove("generation");
            nbt.remove("title");
            nbt.remove("display"); // Remove display as it's handled by components now

            if (!nbt.isEmpty()) {
                return true;
            }
        }

        // Check for custom component changes
        for (ComponentType<?> componentType : COMPONENTS_TO_CHECK) {
            if (itemStack.getComponentChanges().get(componentType) != null) {
                return true;
            }
        }
        
        return false;
    }
}
