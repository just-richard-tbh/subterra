package com.subterra.block.entity;

import com.google.common.collect.Maps;
import com.subterra.screen.AlloyFurnaceScreenHandler;
import net.minecraft.SharedConstants;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Util;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import oshi.util.tuples.Pair;

import java.util.*;

import static com.subterra.block.AlloyFurnaceBlock.LIT;

public class AlloyFurnaceBlockEntity extends LootableContainerBlockEntity
        implements NamedScreenHandlerFactory,
        ImplementedInventory,
        SidedInventory {
    protected static final int INPUT1_SLOT_INDEX = 0;
    protected static final int INPUT2_SLOT_INDEX = 1;
    protected static final int FUEL_SLOT_INDEX = 2;
    protected static final int OUTPUT_SLOT_INDEX = 3;
    public static final int BURN_TIME_PROPERTY_INDEX = 0;
    private static final int[] TOP_SLOTS = new int[]{0};
    private static final int[] BOTTOM_SLOTS = new int[]{3,2};
    private static final int[] NE_SLOTS = new int[]{1};
    private static final int[] SW_SLOTS = new int[]{2};
    public static final int FUEL_TIME_PROPERTY_INDEX = 1;
    public static final int COOK_TIME_PROPERTY_INDEX = 2;
    public static final int COOK_TIME_TOTAL_PROPERTY_INDEX = 3;
    public static final int PROPERTY_COUNT = 4;
    public static final int DEFAULT_COOK_TIME = 200;
    int currentFuel;
    int maxFuel;
    int currentProgress;
    int maxProgress;
    private DefaultedList<ItemStack> inventory;

    protected final PropertyDelegate propertyDelegate = new PropertyDelegate(){

        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> AlloyFurnaceBlockEntity.this.currentFuel;
                case 1 -> AlloyFurnaceBlockEntity.this.maxFuel;
                case 2 -> AlloyFurnaceBlockEntity.this.currentProgress;
                case 3 -> AlloyFurnaceBlockEntity.this.maxProgress;
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0: {
                    AlloyFurnaceBlockEntity.this.currentFuel = value;
                    break;
                }
                case 1: {
                    AlloyFurnaceBlockEntity.this.maxFuel = value;
                    break;
                }
                case 2: {
                    AlloyFurnaceBlockEntity.this.currentProgress = value;
                    break;
                }
                case 3: {
                    AlloyFurnaceBlockEntity.this.maxProgress = value;
                    break;
                }
            }
        }

        @Override
        public int size() {
            return 4;
        }
    };

    public AlloyFurnaceBlockEntity(BlockPos pos, BlockState state){
        super(ModBlockEntities.ALLOY_FURNACE_BLOCK_ENTITY, pos, state);
        this.inventory = DefaultedList.ofSize(4, ItemStack.EMPTY);
    }

    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        if (!this.writeLootTable(nbt)) {
            Inventories.writeNbt(nbt, this.inventory, registryLookup);
        }

    }

    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        this.inventory = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
        if (!this.readLootTable(nbt)) {
            Inventories.readNbt(nbt, this.inventory, registryLookup);
        }

    }

    @Override
    protected Text getContainerName() {
        return Text.translatable(getCachedState().getBlock().getTranslationKey());
    }

    @Override
    protected DefaultedList<ItemStack> getHeldStacks() {
        return this.inventory;
    }

    @Override
    protected void setHeldStacks(DefaultedList<ItemStack> inventory) {
        this.inventory = inventory;
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return new AlloyFurnaceScreenHandler(syncId, playerInventory, this);
    }

    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        //We provide *this* to the screenHandler as our class Implements Inventory
        //Only the Server has the Inventory at the start, this will be synced to the client in the ScreenHandler
        return new AlloyFurnaceScreenHandler(syncId, playerInventory, this);
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    public int size() {
        return 4;
    }

    @Override
    public int[] getAvailableSlots(Direction side) {
        switch(side){
            case Direction.UP -> {
                return TOP_SLOTS;
            }
            case Direction.NORTH, Direction.EAST -> {
                return NE_SLOTS;
            }
            case Direction.SOUTH, Direction.WEST -> {
                return SW_SLOTS;
            }
            case Direction.DOWN -> {
                return BOTTOM_SLOTS;
            }
            default -> {
                return null;
            }
        }
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
        return dir != Direction.DOWN;
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        return dir == Direction.DOWN;
    }

    private boolean isBurning() {
        return this.currentFuel > 0;
    }

    public static volatile Map<Item, Integer> fuelTimeMap;
    public Map<Item, Integer> createFuelTimeMap() {
        Map<Item, Integer> map = fuelTimeMap;
        if (map!=null){
            return map;
        }
        LinkedHashMap<Item, Integer> map2 = Maps.newLinkedHashMap();
        AlloyFurnaceBlockEntity.addFuel(map2, Items.COAL, 800);
        AlloyFurnaceBlockEntity.addFuel(map2, Items.CHARCOAL, 800);
        AlloyFurnaceBlockEntity.addFuel(map2, Items.BLAZE_ROD, 400);
        return map2;
    }
    private static boolean isNonFlammableWood(Item item) {
        return item.getRegistryEntry().isIn(ItemTags.NON_FLAMMABLE_WOOD);
    }

    protected int getFuelTime(ItemStack fuel) {
        if (fuel.isEmpty()) {
            return 0;
        }
        Item item = fuel.getItem();
        return AbstractFurnaceBlockEntity.createFuelTimeMap().getOrDefault(item, 0);
    }

    private static void addFuel(Map<Item, Integer> maxFuels, ItemConvertible item, int maxFuel) {
        Item item2 = item.asItem();
        if (AlloyFurnaceBlockEntity.isNonFlammableWood(item2)) {
            if (SharedConstants.isDevelopment) {
                throw Util.throwOrPause(new IllegalStateException("A developer tried to explicitly make fire resistant item " + item2.getName(null).getString() + " a furnace fuel. That will not work!"));
            }
            return;
        }
        maxFuels.put(item2, maxFuel);
    }

    public static boolean canUseAsFuel(ItemStack stack) {
        return AbstractFurnaceBlockEntity.createFuelTimeMap().containsKey(stack.getItem());
    }

    protected Item getRecipe(ItemStack ingredient1, ItemStack ingredient2) {
        if (ingredient1.isEmpty() || ingredient2.isEmpty()) {
            return null;
        }
        Vector<Item> itemPair = new Vector<Item>(Arrays.asList(ingredient1.getItem(), ingredient2.getItem()));
        return AlloyFurnaceBlockEntity.createRecipes().getOrDefault(itemPair, null);
    }

    private boolean hasValidRecipe(){
        DefaultedList<ItemStack> inventory = this.inventory;
        Vector<Item> itemPair = new Vector<Item>(Arrays.asList(inventory.get(0).getItem().asItem(), inventory.get(1).getItem().asItem()));
        if(AlloyFurnaceBlockEntity.createRecipes().containsKey(itemPair)) {
            return true;
        }
        else {
            itemPair = new Vector<Item>(Arrays.asList(inventory.get(1).getItem().asItem(), inventory.get(0).getItem().asItem()));
        }
        return AlloyFurnaceBlockEntity.createRecipes().containsKey(itemPair);
    }

    private static void addRecipe(Map<Vector<Item>, Item> recipeA, Vector<Item> ingredients, Item result){
        recipeA.put(ingredients, result);
    }

    public static volatile Map<Vector<Item>, Item> recipeMap;
    public static Map<Vector<Item>, Item> createRecipes(){
        Map<Vector<Item>, Item> map = recipeMap;
        if (map!=null){
            return map;
        }
        LinkedHashMap<Vector<Item>, Item> recipeMapNew = Maps.newLinkedHashMap();
        AlloyFurnaceBlockEntity.addRecipe(recipeMapNew, new Vector<Item>(Arrays.asList(Items.IRON_INGOT, Items.COAL)) , Items.GOLD_INGOT);
        return recipeMapNew;
    }

    public static void tick(World world, BlockPos blockPos, BlockState state, AlloyFurnaceBlockEntity blockEntity) {
        if(blockEntity.hasValidRecipe()){
            blockEntity.inventory.set(2, Items.BOW.getDefaultStack());
        }
        if(blockEntity.isBurning()){
            --blockEntity.currentFuel;
            world.setBlockState(blockPos, state.with(LIT, true));
        }else {
           world.setBlockState(blockPos, state.with(LIT, false));
        }
    }

}
