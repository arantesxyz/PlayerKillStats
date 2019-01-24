package xyz.arantes.dev.playerkillstats.database;

import xyz.arantes.dev.playerkillstats.Main;
import xyz.arantes.dev.playerkillstats.utils.Msg;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DataManager {

    public static Connection con = null;

    public static void openMySQL(){
        if (Main.plugin.getConfig().getBoolean("mysql.enable")){
            String host = Main.plugin.getConfig().getString("mysql.host");
            int port = Main.plugin.getConfig().getInt("mysql.port");
            String database = Main.plugin.getConfig().getString("mysql.database");
            String user = Main.plugin.getConfig().getString("mysql.user");
            String password = Main.plugin.getConfig().getString("mysql.password");

            String url = "jdbc:mysql://" + host + ":" + port + "/" + database;

            Main.plugin.getServer().getConsoleSender().sendMessage(Msg.prefix + "§eTentando se conectar com o MySQL.");
            try{
                con = DriverManager.getConnection(url, user, password);
                Main.plugin.getServer().getConsoleSender().sendMessage(Msg.prefix + "§aMySQL conectado! §aUtilizando MySQL.");
                createTable();
            }catch (SQLException e){
                e.printStackTrace();
                Main.plugin.getServer().getConsoleSender().sendMessage(Msg.prefix + "§cA conexão com o servidor MySQL falhou. §aUtilizando SQLite.");
                openSQLite();
            }
        }else{
            Main.plugin.getServer().getConsoleSender().sendMessage(Msg.prefix + "§eTentando se conectar com o SQLite.");
            openSQLite();
        }
    }

    private static void openSQLite(){
        File file = new File(Main.plugin.getDataFolder(), "data.db");

        String url = "jdbc:sqlite:" + file;

        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection(url);
            Main.plugin.getServer().getConsoleSender().sendMessage(Msg.prefix + "§aSQLite conectado! §aUtilizando SQLite.");
            createTable();
        } catch (SQLException e) {
            e.printStackTrace();
            Main.plugin.getServer().getConsoleSender().sendMessage(Msg.prefix + "§cA conexão com o SQLite falhou. §co plugin será desabilitado");
            Main.plugin.getServer().getConsoleSender().sendMessage(Msg.prefix + "§cCaso ache que isso seja um erro entre em contato com o desenvolvedor. §ahttps://dev.arantes.xyz/");
            Main.plugin.getPluginLoader().disablePlugin(Main.plugin);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // table:  UUID, displayname, playerkills, mobkills, deaths
    private static void createTable(){
        PreparedStatement stm = null;
        try {
            stm = con.prepareStatement("CREATE TABLE IF NOT EXISTS `playerstats` (`uuid` TEXT, `displayname` TEXT, `playerkills` INTEGER, `mobkills` INTEGER, `deaths` INTEGER )");
            stm.execute();
            stm.close();
            Main.plugin.getServer().getConsoleSender().sendMessage("§aA tabela §e'playerstats' §afoi criada/carregada com sucesso");
        } catch (SQLException e) {
            e.printStackTrace();
            Main.plugin.getServer().getConsoleSender().sendMessage("§cFalha na criação da tabela §e'playerstats'. §cO plugin será desabilitado.");
            Main.plugin.getServer().getConsoleSender().sendMessage(Msg.prefix + "§cCaso ache que isso seja um erro entre em contato com o desenvolvedor. §ahttps://dev.arantes.xyz/");
            Main.plugin.getPluginLoader().disablePlugin(Main.plugin);
        }
    }

    public static void close(){
        if (con != null){
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
