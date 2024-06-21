package com.subterra.recipe;
/*
import com.mojang.serialization.MapCodec;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.recipe.*;
import net.minecraft.recipe.book.CookingRecipeCategory;
import net.minecraft.recipe.input.RecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

public class AlloyCookingRecipe implements Recipe<RecipeInput> {
    protected final Identifier id;
    protected final Ingredient ingredient1;
    protected final Ingredient ingredient2;
    protected final ItemStack result;
    private final RecipeType<?> type;
    private final RecipeSerializer<?> serializer;
    protected final String group;
    protected final float experience;

    public AlloyCookingRecipe(Identifier id, RecipeType<?> type, RecipeSerializer<?> serializer, String group, Ingredient ingredient1, Ingredient ingredient2, ItemStack result, float experience) {
        this.id = id;
        this.type = type;
        this.serializer = serializer;
        this.group = group;
        this.ingredient1 = ingredient1;
        this.ingredient2 = ingredient2;
        this.result = result;
        this.experience = experience;
    }

    @Override
    public RecipeType<?> getType() {
        return this.type;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.ALLOY_RECIPE_SERIALIZER;
    }

    @Override
    public String getGroup() {
        return this.group;
    }

    @Override
    public ItemStack getResult(RegistryWrapper.WrapperLookup registriesLookup) {
        return this.result;
    }

    @Override
    public DefaultedList<Ingredient> getIngredients() {
        DefaultedList<Ingredient> defaultedList = DefaultedList.of();
        defaultedList.add(this.ingredient1);
        defaultedList.add(this.ingredient2);
        return defaultedList;
    }
    public Ingredient getIngredient1(){
        return ingredient1;
    }
    public Ingredient getIngredient2(){
        return ingredient2;
    }

    @Override
    public boolean matches(RecipeInput input, World world) {
        return false;
    }

    @Override
    public ItemStack craft(RecipeInput input, RegistryWrapper.WrapperLookup lookup) {
        return this.result.copy();
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    public static class Type implements RecipeType<AlloyCookingRecipe>{
        public static final Type INSTANCE = new Type();
    }

    public static interface RecipeFactory<T extends AlloyCookingRecipe> {
        public T create(String var1, Ingredient var2, Ingredient var3, ItemStack var4, float var5);
    }
}*/