// Jade recovery: original class: jade.deps.eLz.u2GEq84
package jade.client.core;

import jade.client.command.AddCommand;
import jade.client.command.BindCommand;
import jade.client.command.BindsCommand;
import jade.client.command.ClearCommand;
import jade.client.command.ClickguiCommand;
import jade.client.command.Command;
import jade.client.command.ConfigCommand;
import jade.client.command.DangerCommand;
import jade.client.command.DebugCommand;
import jade.client.command.EnemyCommand;
import jade.client.command.FriendCommand;
import jade.client.command.HelpCommand;
import jade.client.command.HideCommand;
import jade.client.command.IdentifyCommand;
import jade.client.command.IrcCommand;
import jade.client.command.JengaCommand;
import jade.client.command.LastseenCommand;
import jade.client.command.ModeCommand;
import jade.client.command.NameCommand;
import jade.client.command.OnlineCommand;
import jade.client.command.PingCommand;
import jade.client.command.PrefixCommand;
import jade.client.command.QueueCommand;
import jade.client.command.RemoveCommand;
import jade.client.command.ShowCommand;
import jade.client.command.StatusCommand;
import jade.client.command.ToggleCommand;
import jade.client.command.TrackerCommand;
import jade.client.command.UnbindCommand;
import jade.client.command.WinstreakCommand;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class CommandRegistry {
   private CommandRegistry() {
   }

   public static List<Command> createCommandList() {
      return new ArrayList<>(
         Arrays.asList(
            new QueueCommand(),
            new HelpCommand(),
            new PingCommand(),
            new OnlineCommand(),
            new IrcCommand(),
            new NameCommand(),
            new ClickguiCommand(),
            new ToggleCommand(),
            new BindCommand(),
            new UnbindCommand(),
            new BindsCommand(),
            new DebugCommand(),
            new DangerCommand(),
            new FriendCommand(),
            new EnemyCommand(),
            new AddCommand(),
            new RemoveCommand(),
            new ClearCommand(),
            new ModeCommand(),
            new PrefixCommand(),
            new StatusCommand(),
            new LastseenCommand(),
            new WinstreakCommand(),
            new TrackerCommand(),
            new ConfigCommand(),
            new ShowCommand(),
            new HideCommand(),
            new IdentifyCommand(),
            new JengaCommand()
         )
      );
   }
}
