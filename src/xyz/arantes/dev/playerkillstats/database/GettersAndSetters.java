package xyz.arantes.dev.playerkillstats.database;

import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import static xyz.arantes.dev.playerkillstats.database.DataManager.con;

public class GettersAndSetters {

    public static boolean playerExists(Player player){
        try {
            PreparedStatement stm = con.prepareStatement("SELECT * FROM playerstats WHERE uuid = ?");
            stm.setString(1, player.getUniqueId().toString());
            if (stm.getResultSet().getString("uuid").equals(player.getUniqueId().toString())) {
                stm.close();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean createPlayer(Player player){
        try {
            PreparedStatement stm = con.prepareStatement("INSERT INTO playerstats VALUES (?, ?, ?, ?, ?)");
            stm.setString(1, player.getUniqueId().toString());
            stm.setString(2, player.getDisplayName());
            stm.setInt(3, 0);
            stm.setInt(4, 0);
            stm.setInt(5, 0);
            stm.executeUpdate();
            stm.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static int getPlayerKills(Player player){
        try {
            PreparedStatement stm = con.prepareStatement("SELECT playerkills FROM playerstats WHERE uuid = ?");
            stm.setString(1, player.getUniqueId().toString());
            stm.executeQuery();
            int kills = stm.getResultSet().getInt("playerkills");
            stm.close();
            return kills;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static int getMobKills(Player player){
        try {
            PreparedStatement stm = con.prepareStatement("SELECT mobkills FROM playerstats WHERE uuid = ?");
            stm.setString(1, player.getUniqueId().toString());
            stm.executeQuery();
            int kills = stm.getResultSet().getInt("mobkills");
            stm.close();
            return kills;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
