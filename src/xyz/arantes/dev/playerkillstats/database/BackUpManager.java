package xyz.arantes.dev.playerkillstats.database;

import xyz.arantes.dev.playerkillstats.Main;
import xyz.arantes.dev.playerkillstats.utils.Msg;

import javax.xml.crypto.Data;
import java.io.File;
import java.sql.*;

@SuppressWarnings("Duplicates")
public class BackUpManager {

    private static Connection con = null;
    private static ResultSet rs = null;

    public static boolean mysqlToSqlite() {
        DataManager.close();
        String host = Main.plugin.getConfig().getString("mysql.host");
        int port = Main.plugin.getConfig().getInt("mysql.port");
        String database = Main.plugin.getConfig().getString("mysql.database");
        String user = Main.plugin.getConfig().getString("mysql.user");
        String password = Main.plugin.getConfig().getString("mysql.password");

        String url = "jdbc:mysql://" + host + ":" + port + "/" + database;

        Main.plugin.getServer().getConsoleSender().sendMessage(Msg.prefix + "§eTentando se conectar com o MySQL.");
        try {
            con = DriverManager.getConnection(url, user, password);
            Main.plugin.getServer().getConsoleSender().sendMessage(Msg.prefix + "§aMySQL conectado! §aPreparando BackUp.");
            String query = "SELECT * FROM playerstats";
            PreparedStatement stm = con.prepareStatement(query);
            if (stm.execute()){
                rs = stm.getResultSet();
            }
            File file = new File(Main.plugin.getDataFolder(), "data.db");

            String url2 = "jdbc:sqlite:" + file;

            try {
                Class.forName("org.sqlite.JDBC");
                con = DriverManager.getConnection(url2);
                Main.plugin.getServer().getConsoleSender().sendMessage(Msg.prefix + "§aSQLite conectado! §aPreparando BackUp.");
                createTable();
                while (rs.next()){
                    PreparedStatement stm2 = con.prepareStatement("UPDATE playerstats SET uuid=?, displayname=?, playerkills=?, animalkills=?, monsterkills=?, deaths=?, rank=?");
                    stm2.setString(1, rs.getString("uuid"));
                    stm2.setString(2, rs.getString("displayname"));
                    stm2.setInt(3, rs.getInt("playerkills"));
                    stm2.setInt(4, rs.getInt("animalkills"));
                    stm2.setInt(5, rs.getInt("monsterkills"));
                    stm2.setInt(6, rs.getInt("deaths"));
                    stm2.setString(7, rs.getString("rank"));
                    stm2.executeUpdate();
                }
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                Main.plugin.getServer().getConsoleSender().sendMessage(Msg.prefix + "§cA conexão com o SQLite falhou. §cFalha no BackUP");
                Main.plugin.getServer().getConsoleSender().sendMessage(Msg.prefix + "§cCaso ache que isso seja um erro entre em contato com o desenvolvedor. §ahttps://dev.arantes.xyz/");

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Main.plugin.getServer().getConsoleSender().sendMessage(Msg.prefix + "§cA conexão com o servidor MySQL falhou.");
        }
        Main.reload();
        return false;
    }

    public static boolean sqliteToMysql(){
        DataManager.close();
        File file = new File(Main.plugin.getDataFolder(), "data.db");

        String url = "jdbc:sqlite:" + file;

        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection(url);
            Main.plugin.getServer().getConsoleSender().sendMessage(Msg.prefix + "§aSQLite conectado! §aPreparando BackUp.");
            String query = "SELECT * FROM playerstats";
            PreparedStatement stm = con.prepareStatement(query);
            if (stm.execute()){
                rs = stm.getResultSet();
            }

            String host = Main.plugin.getConfig().getString("mysql.host");
            int port = Main.plugin.getConfig().getInt("mysql.port");
            String database = Main.plugin.getConfig().getString("mysql.database");
            String user = Main.plugin.getConfig().getString("mysql.user");
            String password = Main.plugin.getConfig().getString("mysql.password");

            String url2 = "jdbc:mysql://" + host + ":" + port + "/" + database;

            Main.plugin.getServer().getConsoleSender().sendMessage(Msg.prefix + "§eTentando se conectar com o MySQL.");
            try {
                con = DriverManager.getConnection(url2, user, password);
                Main.plugin.getServer().getConsoleSender().sendMessage(Msg.prefix + "§aMySQL conectado! §aPreparando BackUp.");
                createTable();
                while (rs.next()){
                    PreparedStatement stm2 = con.prepareStatement("UPDATE playerstats SET uuid=?, displayname=?, playerkills=?, animalkills=?, monsterkills=?, deaths=?, rank=?");
                    stm2.setString(1, rs.getString("uuid"));
                    stm2.setString(2, rs.getString("displayname"));
                    stm2.setInt(3, rs.getInt("playerkills"));
                    stm2.setInt(4, rs.getInt("animalkills"));
                    stm2.setInt(5, rs.getInt("monsterkills"));
                    stm2.setInt(6, rs.getInt("deaths"));
                    stm2.setString(7, rs.getString("rank"));
                    stm2.executeUpdate();
                }
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                Main.plugin.getServer().getConsoleSender().sendMessage(Msg.prefix + "§cA conexão com o servidor MySQL falhou.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Main.plugin.getServer().getConsoleSender().sendMessage(Msg.prefix + "§cA conexão com o SQLite falhou. §cFalha no BackUP");
            Main.plugin.getServer().getConsoleSender().sendMessage(Msg.prefix + "§cCaso ache que isso seja um erro entre em contato com o desenvolvedor. §ahttps://dev.arantes.xyz/");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Main.reload();
        return false;
    }

    // table:  UUID, displayname, playerkills, animalkills, monsterkills, deaths, rank
    private static void createTable(){
        PreparedStatement stm;
        try {
            stm = con.prepareStatement("CREATE TABLE IF NOT EXISTS `playerstats` (`uuid` TEXT, `displayname` TEXT, `playerkills` INTEGER, `animalkills` INTEGER, `monsterkills` INTEGER, `deaths` INTEGER, `rank` TEXT)");
            stm.execute();
            stm.close();
            Main.plugin.getServer().getConsoleSender().sendMessage("§aA tabela §e'playerstats' §afoi criada/carregada com sucesso");
        } catch (SQLException e) {
            e.printStackTrace();
            Main.plugin.getServer().getConsoleSender().sendMessage("§cFalha na criação da tabela §e'playerstats'. §cFalha no Backup.");
            Main.plugin.getServer().getConsoleSender().sendMessage(Msg.prefix + "§cCaso ache que isso seja um erro entre em contato com o desenvolvedor. §ahttps://dev.arantes.xyz/");
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
