package xyz.arantes.dev.playerkillstats.database;

import org.bukkit.entity.Player;
import xyz.arantes.dev.playerkillstats.Main;
import xyz.arantes.dev.playerkillstats.utils.ConfigManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static xyz.arantes.dev.playerkillstats.database.DataManager.con;

public class GettersAndSetters {
    private static ConfigManager ranks = new ConfigManager(Main.plugin, "ranks.yml");

    public static boolean playerExists(Player player){
        try {
            PreparedStatement stm = con.prepareStatement("SELECT * FROM playerstats WHERE uuid=?");
            stm.setString(1, player.getUniqueId().toString());

            ResultSet results = stm.executeQuery();
            if (results.next()){
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void createPlayer(Player player){
        String rank = null;
        for (String key : ranks.getData().getKeys(false)){
            if (ranks.getData().getInt(key + ".peso") == 1){
                rank = key;
                break;
            }
        }
        try {
            if (!playerExists(player)) {
                PreparedStatement stm = con.prepareStatement("INSERT INTO playerstats VALUES (?, ?, ?, ?, ?, ?, ?)");
                stm.setString(1, player.getUniqueId().toString());
                stm.setString(2, player.getDisplayName());
                stm.setInt(3, 0);
                stm.setInt(4, 0);
                stm.setInt(5, 0);
                stm.setInt(6, 0);
                stm.setString(7, rank);
                stm.executeUpdate();
                stm.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int getPlayerKills(Player player){
        try {
            PreparedStatement stm = con.prepareStatement("SELECT * FROM playerstats WHERE uuid=?");
            stm.setString(1, player.getUniqueId().toString());
            ResultSet results = stm.executeQuery();
            results.next();
            return results.getInt("playerkills");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static int getAnimalKills(Player player){
        try {
            PreparedStatement stm = con.prepareStatement("SELECT * FROM playerstats WHERE uuid=?");
            stm.setString(1, player.getUniqueId().toString());
            ResultSet results = stm.executeQuery();
            results.next();
            return results.getInt("animalkills");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static int getMonsterKills(Player player){
        try {
            PreparedStatement stm = con.prepareStatement("SELECT * FROM playerstats WHERE uuid=?");
            stm.setString(1, player.getUniqueId().toString());
            ResultSet results = stm.executeQuery();
            results.next();
            return results.getInt("monsterkills");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static int getDeaths(Player player){
        try {
            PreparedStatement stm = con.prepareStatement("SELECT * FROM playerstats WHERE uuid=?");
            stm.setString(1, player.getUniqueId().toString());
            ResultSet results = stm.executeQuery();
            results.next();
            return results.getInt("deaths");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static String getRank(Player player){
        try {
            PreparedStatement stm = con.prepareStatement("SELECT * FROM playerstats WHERE uuid=?");
            stm.setString(1, player.getUniqueId().toString());
            ResultSet results = stm.executeQuery();
            results.next();
            return results.getString("rank");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void updatePlayerKills(Player player, int kills){
        try {
            PreparedStatement stm = con.prepareStatement("UPDATE playerstats SET playerkills=? WHERE uuid=?");
            stm.setInt(1, kills);
            stm.setString(2, player.getUniqueId().toString());
            stm.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void updateAnimalKills(Player player, int kills){
        try {
            PreparedStatement stm =con.prepareStatement("UPDATE playerstats SET animalkills=? WHERE uuid=?");
            stm.setInt(1, kills);
            stm.setString(2, player.getUniqueId().toString());
            stm.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void updateMonsterKills(Player player, int kills){
        try {
            PreparedStatement stm =con.prepareStatement("UPDATE playerstats SET monsterkills=? WHERE uuid=?");
            stm.setInt(1, kills);
            stm.setString(2, player.getUniqueId().toString());
            stm.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void updateDeaths(Player player, int deaths){
        try {
            PreparedStatement stm = con.prepareStatement("UPDATE playerstats SET deaths=? WHERE uuid=?");
            stm.setInt(1, deaths);
            stm.setString(2, player.getUniqueId().toString());
            stm.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void updateRank(Player player, String rank){
        try {
            PreparedStatement stm = con.prepareStatement("UPDATE playerstats SET rank=? WHERE uuid=?");
            stm.setString(1, rank);
            stm.setString(2, player.getUniqueId().toString());
            stm.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static Map<String, Integer> getTopPlayerKills(int limite){
        Map<String, Integer> map = new HashMap<>();
        try {
            PreparedStatement stm = con.prepareStatement("SELECT * FROM playerstats ORDER BY playerkills DESC LIMIT ?");
            stm.setInt(1, limite);
            ResultSet results = stm.executeQuery();
            while (results.next()){
                map.put(results.getString("displayname"), results.getInt("playerkills"));
            }
            return map;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return map = null;
    }

    public static Map<String, Integer> getTopAnimalKills(int limite){
        Map<String, Integer> map = new HashMap<>();
        try {
            PreparedStatement stm = con.prepareStatement("SELECT * FROM playerstats ORDER BY playerkills DESC LIMIT ?");
            stm.setInt(1, limite);
            ResultSet results = stm.executeQuery();
            while (results.next()){
                map.put(results.getString("displayname"), results.getInt("animalkills"));
            }
            return map;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return map = null;
    }

    public static Map<String, Integer> getTopMonsterKills(int limite){
        Map<String, Integer> map = new HashMap<>();
        try {
            PreparedStatement stm = con.prepareStatement("SELECT * FROM playerstats ORDER BY playerkills DESC LIMIT ?");
            stm.setInt(1, limite);
            ResultSet results = stm.executeQuery();
            while (results.next()){
                map.put(results.getString("displayname"), results.getInt("monsterkills"));
            }
            return map;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return map = null;
    }

    public static Map<String, Integer> getTopDeaths(int limite){
        Map<String, Integer> map = new HashMap<>();
        try {
            PreparedStatement stm = con.prepareStatement("SELECT * FROM playerstats ORDER BY playerkills DESC LIMIT ?");
            stm.setInt(1, limite);
            ResultSet results = stm.executeQuery();
            while (results.next()){
                map.put(results.getString("displayname"), results.getInt("deaths"));
            }
            return map;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return map = null;
    }
}
