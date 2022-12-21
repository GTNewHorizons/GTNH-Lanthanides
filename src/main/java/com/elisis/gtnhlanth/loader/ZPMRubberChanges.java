package com.elisis.gtnhlanth.loader;

import static gregtech.api.enums.OrePrefixes.*;

import com.dreammaster.gthandler.CustomItemList;
import com.elisis.gtnhlanth.common.register.WerkstoffMaterialPool;
import com.github.bartimaeusnek.bartworks.API.LoaderReference;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_Log;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Recipe.GT_Recipe_AssemblyLine;
import gregtech.api.util.GT_Recipe.GT_Recipe_Map;
import gregtech.api.util.GT_Shaped_Recipe;
import gregtech.api.util.GT_Utility;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import org.apache.commons.lang3.reflect.FieldUtils;

public class ZPMRubberChanges implements Runnable {

    @SuppressWarnings("unchecked")
    public void run() {

        List<IRecipe> bufferedRecipeList = null;

        try {
            bufferedRecipeList =
                    (List<IRecipe>) FieldUtils.getDeclaredField(GT_ModHandler.class, "sBufferRecipeList", true)
                            .get(null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        HashSet<ItemStack> zpmPlusComponents = new HashSet<>();
        OrePrefixes[] rubberGenerated = {plate};

        Arrays.stream(ItemList.values())
                .filter(item -> (item.toString().contains("ZPM")
                                || item.toString().contains("UV")
                                || item.toString().contains("UHV")
                                || item.toString().contains("UEV"))
                        && item.hasBeenSet())
                .forEach(item -> zpmPlusComponents.add(item.get(1)));

        if (LoaderReference.dreamcraft) {
            addDreamcraftItemListItems(zpmPlusComponents);
        }

        for (ItemStack component : zpmPlusComponents) {
            GT_Log.out.print(component.getDisplayName() + " ");
        }

        replaceAllRecipes(zpmPlusComponents, rubberGenerated, bufferedRecipeList);
    }

    private static void replaceAllRecipes(
            Collection<ItemStack> components, OrePrefixes[] rubberGenerated, List<IRecipe> bufferedRecipeList) {

        for (GT_Recipe_AssemblyLine sAssemblylineRecipe : GT_Recipe_AssemblyLine.sAssemblylineRecipes) {
            for (ItemStack stack : components) {
                rewriteAsslineRecipes(stack, rubberGenerated, sAssemblylineRecipe);
            }
        }

        for (GT_Recipe_Map map : GT_Recipe_Map.sMappings) {
            for (GT_Recipe recipe : map.mRecipeList) {
                for (ItemStack stack : components) {
                    rewriteMachineRecipes(stack, rubberGenerated, recipe);
                }
            }
        }

        for (ItemStack stack : components) {
            Predicate<IRecipe> recipeFilter = (recipe) -> recipe instanceof GT_Shaped_Recipe
                    && GT_Utility.areStacksEqual(((GT_Shaped_Recipe) recipe).getRecipeOutput(), stack, true);
            rewriteCraftingRecipes(bufferedRecipeList, rubberGenerated, recipeFilter);
        }
    }

    private static void addDreamcraftItemListItems(Collection<ItemStack> components) {
        final CustomItemList[] values = CustomItemList.values();
        for (CustomItemList entry : values) {
            final String entryName = entry.toString();
            if ((entryName.contains("ZPM")
                            || entryName.contains("UV")
                            || entryName.contains("UHV")
                            || entryName.contains("UEV"))
                    && entry.hasBeenSet()) {
                components.add(entry.get(1L));
            }
        }
    }

    private static void rewriteCraftingRecipes(
            List<IRecipe> bufferedRecipeList, OrePrefixes[] rubberGenerated, Predicate<IRecipe> recipeFilter) {
        for (OrePrefixes prefix : rubberGenerated) {
            Consumer<IRecipe> recipeAction = (recipe) -> {
                ZPMRubberChanges.replaceStack(
                        ((GT_Shaped_Recipe) recipe).getInput(),
                        GT_OreDictUnificator.get(prefix, Materials.Silicone, 1),
                        WerkstoffMaterialPool.PTMEGElastomer.get(prefix));
                ZPMRubberChanges.replaceStack(
                        ((GT_Shaped_Recipe) recipe).getInput(),
                        GT_OreDictUnificator.get(prefix, Materials.StyreneButadieneRubber, 1),
                        WerkstoffMaterialPool.PTMEGElastomer.get(prefix));
            };

            CraftingManager.getInstance().getRecipeList().stream()
                    .filter(recipeFilter)
                    .forEach(recipeAction);
            bufferedRecipeList.stream().filter(recipeFilter).forEach(recipeAction);
        }
    }

    private static void rewriteMachineRecipes(ItemStack stack, OrePrefixes[] rubberGenerated, GT_Recipe recipe) {
        if (ZPMRubberChanges.doStacksContain(recipe.mInputs, stack)
                || ZPMRubberChanges.doStacksContain(recipe.mOutputs, stack)) {
            for (OrePrefixes prefix : rubberGenerated) {
                ZPMRubberChanges.replaceStack(
                        recipe.mInputs,
                        GT_OreDictUnificator.get(prefix, Materials.Silicone, 1),
                        WerkstoffMaterialPool.PTMEGElastomer.get(prefix));
                ZPMRubberChanges.replaceStack(
                        recipe.mOutputs,
                        GT_OreDictUnificator.get(prefix, Materials.Silicone, 1),
                        WerkstoffMaterialPool.PTMEGElastomer.get(prefix));

                ZPMRubberChanges.replaceStack(
                        recipe.mInputs,
                        GT_OreDictUnificator.get(prefix, Materials.StyreneButadieneRubber, 1),
                        WerkstoffMaterialPool.PTMEGElastomer.get(prefix));
                ZPMRubberChanges.replaceStack(
                        recipe.mOutputs,
                        GT_OreDictUnificator.get(prefix, Materials.StyreneButadieneRubber, 1),
                        WerkstoffMaterialPool.PTMEGElastomer.get(prefix));
            }
            ZPMRubberChanges.replaceStack(
                    recipe.mFluidInputs,
                    Materials.Silicone.getMolten(1),
                    WerkstoffMaterialPool.PTMEGElastomer.getMolten(1).getFluid());
            ZPMRubberChanges.replaceStack(
                    recipe.mFluidOutputs,
                    Materials.Silicone.getMolten(1),
                    WerkstoffMaterialPool.PTMEGElastomer.getMolten(1).getFluid());

            ZPMRubberChanges.replaceStack(
                    recipe.mFluidInputs,
                    Materials.StyreneButadieneRubber.getMolten(1),
                    WerkstoffMaterialPool.PTMEGElastomer.getMolten(1).getFluid());
            ZPMRubberChanges.replaceStack(
                    recipe.mFluidOutputs,
                    Materials.StyreneButadieneRubber.getMolten(1),
                    WerkstoffMaterialPool.PTMEGElastomer.getMolten(1).getFluid());
        }
    }

    private static void rewriteAsslineRecipes(
            ItemStack stack, OrePrefixes[] rubberGenerated, GT_Recipe.GT_Recipe_AssemblyLine recipe) {
        if (ZPMRubberChanges.doStacksContain(recipe.mInputs, stack)
                || ZPMRubberChanges.doStacksContain(new ItemStack[] {recipe.mOutput}, stack)) {
            for (OrePrefixes prefix : rubberGenerated) {
                ZPMRubberChanges.replaceStack(
                        recipe.mInputs,
                        GT_OreDictUnificator.get(prefix, Materials.Silicone, 1),
                        WerkstoffMaterialPool.PTMEGElastomer.get(prefix));
                ZPMRubberChanges.replaceStack(
                        new ItemStack[] {recipe.mOutput},
                        GT_OreDictUnificator.get(prefix, Materials.Silicone, 1),
                        WerkstoffMaterialPool.PTMEGElastomer.get(prefix));

                ZPMRubberChanges.replaceStack(
                        recipe.mInputs,
                        GT_OreDictUnificator.get(prefix, Materials.StyreneButadieneRubber, 1),
                        WerkstoffMaterialPool.PTMEGElastomer.get(prefix));
                ZPMRubberChanges.replaceStack(
                        new Object[] {recipe.mOutput},
                        GT_OreDictUnificator.get(prefix, Materials.StyreneButadieneRubber, 1),
                        WerkstoffMaterialPool.PTMEGElastomer.get(prefix));
            }
            ZPMRubberChanges.replaceStack(
                    recipe.mFluidInputs,
                    Materials.Silicone.getMolten(1),
                    WerkstoffMaterialPool.PTMEGElastomer.getMolten(1).getFluid());

            ZPMRubberChanges.replaceStack(
                    recipe.mFluidInputs,
                    Materials.StyreneButadieneRubber.getMolten(1),
                    WerkstoffMaterialPool.PTMEGElastomer.getMolten(1).getFluid());
        }
    }

    private static boolean doStacksContain(ItemStack[] stacks, ItemStack target) {
        for (ItemStack stack : stacks) {
            if (GT_Utility.areStacksEqual(stack, target, true)) {
                return true;
            }
        }
        return false;
    }

    private static void replaceStack(Object[] stacks, ItemStack target, ItemStack replacement) {
        for (int i = 0; i < stacks.length; i++) {
            if (stacks[i] instanceof ArrayList<?>) {
                final ArrayList<ItemStack> variants = (ArrayList<ItemStack>) stacks[i];
                if (!variants.isEmpty()) {
                    if (GT_Utility.areStacksEqual(target, variants.get(0), true)) {
                        int amount = variants.get(0).stackSize;
                        stacks[i] = new ArrayList<>(Arrays.asList(GT_Utility.copyAmount(amount, replacement)));
                    }
                }
            } else if (GT_Utility.isStackValid(stacks[i])
                    && GT_Utility.areStacksEqual(target, (ItemStack) stacks[i], true)) {
                int amount = ((ItemStack) stacks[i]).stackSize;
                stacks[i] = GT_Utility.copyAmount(amount, replacement);
            }
        }
    }

    private static void replaceStack(FluidStack[] stacks, FluidStack target, Fluid replacement) {
        for (int i = 0; i < stacks.length; i++) {
            if (GT_Utility.areFluidsEqual(target, stacks[i])) {
                int amount = stacks[i].amount;
                stacks[i] = new FluidStack(replacement, amount);
            }
        }
    }
}
