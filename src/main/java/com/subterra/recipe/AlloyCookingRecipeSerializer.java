package com.subterra.recipe;
/*
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;

public class AlloyCookingRecipeSerializer
        implements RecipeSerializer<AlloyCookingRecipe> {
    public static final Codec<AlloyCookingRecipe> CODEC = RecordCodecBuilder.create(in -> in.group(
            Ingredient.DISALLOW_EMPTY_CODEC.fieldOf("ingredient1").forGetter(AlloyCookingRecipe::getIngredient1),
            Ingredient.DISALLOW_EMPTY_CODEC.fieldOf("ingredient2").forGetter(AlloyCookingRecipe::getIngredient2),
            ItemStack.VALIDATED_UNCOUNTED_CODEC.fieldOf("output").forGetter(AlloyCookingRecipe::getResult)
    );

    @Override
    public MapCodec<AlloyCookingRecipe> codec() {
        return CODEC;
    }

    @Override
    public PacketCodec<RegistryByteBuf, AlloyCookingRecipe> packetCodec() {
        return null;
    }
}*/
