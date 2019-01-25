package xyz.arantes.dev.playerkillstats.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import xyz.arantes.dev.playerkillstats.database.BackUpManager;
import xyz.arantes.dev.playerkillstats.utils.Msg;

public class Statsadm implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String lb, String[] args) {
        if (cmd.getName().equalsIgnoreCase("statsadm")) {
            if (!sender.hasPermission("playerkillstats.statsadm")) {
                sender.sendMessage(Msg.getMessage("sem_perm"));
                return true;
            }

            if (args.length < 1){
                sender.sendMessage("§cUtilize: /statsadm <importar/exportar>");
                return true;
            }

            if (args[0].equalsIgnoreCase("importar")){
                if (BackUpManager.mysqlToSqlite()){
                    sender.sendMessage("§aOs dados do MySQL foram copiados para o SQLite com sucesso!");
                }else{
                    sender.sendMessage("§cErro ao copiar os dados do MySQL para o SQLite.");
                }
            }
            if (args[0].equalsIgnoreCase("exportar")){
                if (BackUpManager.sqliteToMysql()){
                    sender.sendMessage("§aOs dados do SQLite foram copiados para o MySQL com sucesso!");
                }else{
                    sender.sendMessage("§cErro ao copiar os dados do SQLite para o MySQL.");
                }
            }
        }
        return false;
    }
}
