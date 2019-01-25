package xyz.arantes.dev.playerkillstats.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.arantes.dev.playerkillstats.database.GettersAndSetters;
import xyz.arantes.dev.playerkillstats.utils.Msg;

public class Statset implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String lb, String[] args) {
        if (cmd.getName().equalsIgnoreCase("statset")) {
            if (!sender.hasPermission("playerkillstats.statset")) {
                sender.sendMessage(Msg.getMessage("sem_perm"));
                return true;
            }

            if (args.length < 3){
                sender.sendMessage("§cUtilize: /statset <jogadores/animais/monstros/mortes> <jogador> <quantidade>");
                return true;
            }

            if (args[0].equalsIgnoreCase("jogadores")){
                Player target = Bukkit.getPlayer(args[1]);
                if (target != null && GettersAndSetters.playerExists(target)){
                    int qnt = Integer.valueOf(args[2]);
                    if (qnt < 0){
                        sender.sendMessage("§cVocê não pode definir uma quantidade negativa de kills para o jogador");
                        return true;
                    }
                    GettersAndSetters.updatePlayerKills(target, qnt);
                    sender.sendMessage("§aA quantidade de kills de jogador do jogador §e" + target.getDisplayName() + "§a foram atualizadas para §e" + qnt);
                    return true;
                }else{
                    sender.sendMessage("§cO jogador solicitado não existe no banco de dados ou não está online.");
                    return true;
                }
            }else if (args[0].equalsIgnoreCase("animais")){
                Player target = Bukkit.getPlayer(args[1]);
                if (target != null && GettersAndSetters.playerExists(target)){
                    int qnt = Integer.valueOf(args[2]);
                    if (qnt < 0){
                        sender.sendMessage("§cVocê não pode definir uma quantidade negativa de kills para o jogador");
                        return true;
                    }
                    GettersAndSetters.updateAnimalKills(target, qnt);
                    sender.sendMessage("§aA quantidade de kills de animais do jogador §e" + target.getDisplayName() + "§a foram atualizadas para §e" + qnt);
                    return true;
                }else{
                    sender.sendMessage("§cO jogador solicitado não existe no banco de dados ou não está online.");
                    return true;
                }

            }else if (args[0].equalsIgnoreCase("monstros")){
                Player target = Bukkit.getPlayer(args[1]);
                if (target != null && GettersAndSetters.playerExists(target)){
                    int qnt = Integer.valueOf(args[2]);
                    if (qnt < 0){
                        sender.sendMessage("§cVocê não pode definir uma quantidade negativa de kills para o jogador");
                        return true;
                    }
                    GettersAndSetters.updateMonsterKills(target, qnt);
                    sender.sendMessage("§aA quantidade de kills de monstro do jogador §e" + target.getDisplayName() + "§a foram atualizadas para §e" + qnt);
                    return true;
                }else{
                    sender.sendMessage("§cO jogador solicitado não existe no banco de dados ou não está online.");
                    return true;
                }

            }else if (args[0].equalsIgnoreCase("mortes")){
                Player target = Bukkit.getPlayer(args[1]);
                if (target != null && GettersAndSetters.playerExists(target)){
                    int qnt = Integer.valueOf(args[2]);
                    if (qnt < 0){
                        sender.sendMessage("§cVocê não pode definir uma quantidade negativa de kills para o jogador");
                        return true;
                    }
                    GettersAndSetters.updateDeaths(target, qnt);
                    sender.sendMessage("§aA quantidade de mortes do jogador §e" + target.getDisplayName() + "§a foram atualizadas para §e" + qnt);
                    return true;
                }else{
                    sender.sendMessage("§cO jogador solicitado não existe no banco de dados ou não está online.");
                    return true;
                }
            }
        }
        return false;
    }
}
