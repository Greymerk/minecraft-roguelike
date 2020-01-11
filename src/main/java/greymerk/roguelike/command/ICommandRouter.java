package greymerk.roguelike.command;

import java.util.List;

public interface ICommandRouter {

  void execute(ICommandContext context, List<String> args);

  List<String> getTabCompletion(List<String> args);

}
