package com.subterra.block;

import com.subterra.world.dimension.SubterraPortal;
import net.minecraft.block.BlockState;
import net.minecraft.block.NetherPortalBlock;
import net.minecraft.block.Portal;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockLocating;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.dimension.DimensionType;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class SubterraPortalBlock extends NetherPortalBlock {
    RegistryKey<World> SUBTERRA = RegistryKey.of(RegistryKeys.WORLD, Identifier.of("subterra", "subterra"));
    public SubterraPortalBlock(Settings settings) {
        super(settings);
    }
    @Override
    protected void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random){}

    @Override
    @Nullable
    public TeleportTarget createTeleportTarget(ServerWorld world, Entity entity, BlockPos pos) {
        RegistryKey<World> registryKey = world.getRegistryKey() == SUBTERRA ? World.OVERWORLD : SUBTERRA;
        ServerWorld serverWorld = world.getServer().getWorld(registryKey);
        if (serverWorld == null) {
            return null;
        }
        boolean bl = serverWorld.getRegistryKey() == SUBTERRA;
        WorldBorder worldBorder = serverWorld.getWorldBorder();
        double d = DimensionType.getCoordinateScaleFactor(world.getDimension(), serverWorld.getDimension());
        BlockPos blockPos = worldBorder.clamp(entity.getX() * d, entity.getY(), entity.getZ() * d);
        return this.getOrCreateExitPortalTarget(serverWorld, entity, pos, blockPos, bl, worldBorder);
    }

    @Nullable
    private TeleportTarget getOrCreateExitPortalTarget(ServerWorld world, Entity entity2, BlockPos pos, BlockPos scaledPos, boolean inSubterra, WorldBorder worldBorder) {
        TeleportTarget.PostDimensionTransition postDimensionTransition;
        BlockLocating.Rectangle rectangle;
        Optional<BlockPos> optional = world.getPortalForcer().getPortalPos(scaledPos, inSubterra, worldBorder);
        if (optional.isPresent()) {
            BlockPos blockPos = optional.get();
            BlockState blockState = world.getBlockState(blockPos);
            rectangle = BlockLocating.getLargestRectangle(blockPos, blockState.get(Properties.HORIZONTAL_AXIS), 21, Direction.Axis.Y, 21, posx -> world.getBlockState((BlockPos)posx) == blockState);
            postDimensionTransition = TeleportTarget.SEND_TRAVEL_THROUGH_PORTAL_PACKET.then(entity -> entity.addPortalChunkTicketAt(blockPos));
        } else {
            Direction.Axis axis = entity2.getWorld().getBlockState(pos).getOrEmpty(AXIS).orElse(Direction.Axis.X);
            Optional<BlockLocating.Rectangle> optional2 = world.getPortalForcer().createPortal(scaledPos, axis);
            if (optional2.isEmpty()) {
                System.out.println("Unable to create a portal, likely target out of worldborder");
                return null;
            }
            rectangle = optional2.get();
            postDimensionTransition = TeleportTarget.SEND_TRAVEL_THROUGH_PORTAL_PACKET.then(TeleportTarget.ADD_PORTAL_CHUNK_TICKET);
        }
        return SubterraPortalBlock.getExitPortalTarget(entity2, pos, rectangle, world, postDimensionTransition);
    }

    private static TeleportTarget getExitPortalTarget(Entity entity, BlockPos pos, BlockLocating.Rectangle exitPortalRectangle, ServerWorld world, TeleportTarget.PostDimensionTransition postDimensionTransition) {
        Vec3d vec3d;
        Direction.Axis axis;
        BlockState blockState = entity.getWorld().getBlockState(pos);
        if (blockState.contains(Properties.HORIZONTAL_AXIS)) {
            axis = blockState.get(Properties.HORIZONTAL_AXIS);
            BlockLocating.Rectangle rectangle = BlockLocating.getLargestRectangle(pos, axis, 21, Direction.Axis.Y, 21, posx -> entity.getWorld().getBlockState((BlockPos)posx) == blockState);
            vec3d = entity.positionInPortal(axis, rectangle);
        } else {
            axis = Direction.Axis.X;
            vec3d = new Vec3d(0.5, 0.0, 0.0);
        }
        return SubterraPortalBlock.getExitPortalTarget(world, exitPortalRectangle, axis, vec3d, entity, entity.getVelocity(), entity.getYaw(), entity.getPitch(), postDimensionTransition);
    }

    private static TeleportTarget getExitPortalTarget(ServerWorld world, BlockLocating.Rectangle exitPortalRectangle, Direction.Axis axis, Vec3d positionInPortal, Entity entity, Vec3d velocity, float yaw, float pitch, TeleportTarget.PostDimensionTransition postDimensionTransition) {
        BlockPos blockPos = exitPortalRectangle.lowerLeft;
        BlockState blockState = world.getBlockState(blockPos);
        Direction.Axis axis2 = blockState.getOrEmpty(Properties.HORIZONTAL_AXIS).orElse(Direction.Axis.X);
        double d = exitPortalRectangle.width;
        double e = exitPortalRectangle.height;
        EntityDimensions entityDimensions = entity.getDimensions(entity.getPose());
        int i = axis == axis2 ? 0 : 90;
        Vec3d vec3d = axis == axis2 ? velocity : new Vec3d(velocity.z, velocity.y, -velocity.x);
        double f = (double)entityDimensions.width() / 2.0 + (d - (double)entityDimensions.width()) * positionInPortal.getX();
        double g = (e - (double)entityDimensions.height()) * positionInPortal.getY();
        double h = 0.5 + positionInPortal.getZ();
        boolean bl = axis2 == Direction.Axis.X;
        Vec3d vec3d2 = new Vec3d((double)blockPos.getX() + (bl ? f : h), (double)blockPos.getY() + g, (double)blockPos.getZ() + (bl ? h : f));
        Vec3d vec3d3 = SubterraPortal.findOpenPosition(vec3d2, world, entity, entityDimensions);
        return new TeleportTarget(world, vec3d3, vec3d, yaw + (float)i, pitch, postDimensionTransition);
    }

    @Override
    public Portal.Effect getPortalEffect() {
        return Effect.NONE;
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (random.nextInt(100) == 0) {
            world.playSound((double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5, SoundEvents.BLOCK_PORTAL_AMBIENT, SoundCategory.BLOCKS, 0.5f, random.nextFloat() * 0.4f + 0.8f, false);
        }
        for (int i = 0; i < 4; ++i) {
            double d = (double)pos.getX() + random.nextDouble();
            double e = (double)pos.getY() + random.nextDouble();
            double f = (double)pos.getZ() + random.nextDouble();
            int k = random.nextInt(2) * 2 - 1;
            if (world.getBlockState(pos.west()).isOf(this) || world.getBlockState(pos.east()).isOf(this)) {
                f = (double)pos.getZ() + 0.5 + 0.25 * (double)k;
            } else {
                d = (double)pos.getX() + 0.5 + 0.25 * (double)k;
            }
            world.addParticle(ParticleTypes.FLAME, d, e, f, 0.0, 0.0, 0.0);
        }
    }

    @Override
    public ItemStack getPickStack(WorldView world, BlockPos pos, BlockState state) {
        return ItemStack.EMPTY;
    }
}
