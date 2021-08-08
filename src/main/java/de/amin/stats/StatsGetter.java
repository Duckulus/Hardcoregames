//Created by Duckulus on 07 Aug, 2021 

package de.amin.stats;

import de.amin.hardcoregames.HG;
import de.amin.utils.UUIDFetcher;
import org.bukkit.entity.Player;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;

public class StatsGetter {

    private final DataSource source;

    public StatsGetter(DataSource source) {
        this.source = source;
    }

    public void createTable() {
        //if(!HG.isConnected)return;
        try (Connection con = source.getConnection();
             PreparedStatement stmt = con.prepareStatement("CREATE TABLE IF NOT EXISTS stats " +
                     "(PLAYER_UUID VARCHAR(255), NAME VARCHAR(255), KILLS INT, DEATHS INT, GAMESPLAYED INT, WINS INT, PRIMARY KEY (PLAYER_UUID))")) {

            stmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void createPlayer(Player player){
        if(exists(player.getName()))return;
        try (Connection con = source.getConnection();
             PreparedStatement stmt = con.prepareStatement("INSERT IGNORE INTO stats (PLAYER_UUID, NAME, KILLS, DEATHS, GAMESPLAYED, WINS) VALUES (?,?,?,?,?,?)")){
            stmt.setString(1, UUIDFetcher.getUUID(player.getName()).toString());
            stmt.setString(2, player.getName());
            stmt.setInt(3, 0);
            stmt.setInt(4, 0);
            stmt.setInt(5, 0);
            stmt.setInt(6, 0);
            stmt.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean exists(String player){
        if(!HG.isConnected)return false;
        try (Connection con = source.getConnection();
             PreparedStatement stmt = con.prepareStatement("SELECT * FROM stats WHERE PLAYER_UUID = ?")){
            stmt.setString(1, UUIDFetcher.getUUID(player).toString());
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }


    public int get(String player, String query) {
        if(!HG.isConnected)return 0;
        try (Connection con = source.getConnection();
                PreparedStatement stmt = con.prepareStatement("SELECT * FROM stats WHERE PLAYER_UUID = ?")){

            stmt.setString(1, UUIDFetcher.getUUID(player).toString());
            ResultSet rs = stmt.executeQuery();
            rs.next();
            return rs.getInt(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

    public void set(String player, String query, int value){
            if(!HG.isConnected)return;
            try (Connection con = source.getConnection();
                 PreparedStatement stmt = con.prepareStatement("UPDATE stats SET " + query + " = ? WHERE PLAYER_UUID = ?")){

                stmt.setInt(1, value);
                stmt.setString(2, UUIDFetcher.getUUID(player).toString());
                stmt.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

    }

    public void increment(String player, String query){
        set(player, query, get(player, query) + 1);
    }



}
