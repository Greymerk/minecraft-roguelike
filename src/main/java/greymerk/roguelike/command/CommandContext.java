package greymerk.roguelike.command;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.WorldEditor;

public class CommandContext implements ICommandContext {

  MinecraftServer server;
  ICommandSender sender;

  public CommandContext(MinecraftServer server, ICommandSender sender) {
    this.server = server;
    this.sender = sender;
  }

  public void sendMessage(String message, MessageType type) {
    String formattedMessage = type.apply(message);
    TextComponentString text = new TextComponentString(formattedMessage);
    sender.sendMessage(text);
  }

  public IWorldEditor createEditor() {
    World world = sender.getEntityWorld();
    return new WorldEditor(world);
  }

  public Coord getPos() {
    BlockPos bp = sender.getPosition();
    return new Coord(bp.getX(), bp.getY(), bp.getZ());
  }

  public void give(ItemStack item) {
    Entity player = sender.getCommandSenderEntity();
    EntityItem drop = player.entityDropItem(item, 0);
    drop.setNoPickupDelay();
  }
}
