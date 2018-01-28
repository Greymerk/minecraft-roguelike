package greymerk.roguelike.command;

import java.util.List;

public interface ICommandRouter {
	
	public void execute(ICommandContext context, List<String> args);
	
	public List<String> getTabCompletion(List<String> args);
	
}
