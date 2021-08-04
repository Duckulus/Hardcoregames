//Created by Duckulus on 05 Jul, 2021 

package de.amin.MySQL;

import de.amin.hardcoregames.HG;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Stats {


    public static boolean exists(String uuid) {
        try {
            ResultSet rs = HG.mySQL.query("SELECT * FROM Stats WHERE UUID= '" + uuid + "'");

            if (rs.next()) {
                return rs.getString("UUID") != null;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static void createPlayer(String uuid){
        if(!exists(uuid)){
            HG.mySQL.update("INSERT INTO Stats(UUID, KILLS, DEATHS, WINS, PLAYEDGAMES) VALUES ('" + uuid + "', '0', '0', '0', '0');");
        }
    }

    public static Integer getKills(String uuid){
        int i = 0;
        if(exists(uuid)) {
            try {
                ResultSet rs = HG.mySQL.query("SELECT * FROM Stats WHERE UUID= '" + uuid + "'");
                if(rs.next() || (Integer.valueOf(rs.getInt("KILLS")) == null));

                i = rs.getInt("KILLS");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else {
            createPlayer(uuid);
            getKills(uuid);
        }
        return i;
    }

    public static Integer getDeaths(String uuid){
        int i = 0;
        if(exists(uuid)) {
            try {
                ResultSet rs = HG.mySQL.query("SELECT * FROM Stats WHERE UUID= '" + uuid + "'");
                if(rs.next() || (Integer.valueOf(rs.getInt("DEATHS")) == null));

                i = rs.getInt("DEATHS");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else {
            createPlayer(uuid);
            getDeaths(uuid);
        }
        return i;
    }

    public static Integer getWins(String uuid){
        int i = 0;
        if(exists(uuid)) {
            try {
                ResultSet rs = HG.mySQL.query("SELECT * FROM Stats WHERE UUID= '" + uuid + "'");
                if(rs.next() || (Integer.valueOf(rs.getInt("WINS")) == null));

                i = rs.getInt("WINS");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else {
            createPlayer(uuid);
            getWins(uuid);
        }
        return i;
    }

    public static Integer getPlayedGames(String uuid){
        int i = 0;
        if(exists(uuid)) {
            try {
                ResultSet rs = HG.mySQL.query("SELECT * FROM Stats WHERE UUID= '" + uuid + "'");
                if(rs.next() || (Integer.valueOf(rs.getInt("PLAYEDGAMES")) == null));

                i = rs.getInt("PLAYEDGAMES");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else {
            createPlayer(uuid);
            getPlayedGames(uuid);
        }
        return i;
    }

    public static void setKills(String uuid, Integer kills){
        if(exists(uuid)){
            HG.mySQL.update("UPDATE Stats SET KILLS= '" + kills + "' WHERE UUID= '" + uuid + "';");
        }else {
            createPlayer(uuid);
            setKills(uuid, kills);
        }
    }

    public static void setDeaths(String uuid, Integer deaths){
        if(exists(uuid)){
            HG.mySQL.update("UPDATE Stats SET DEATHS= '" + deaths + "' WHERE UUID= '" + uuid + "';");
        }else {
            createPlayer(uuid);
            setDeaths(uuid, deaths);
        }
    }

    public static void setWins(String uuid, Integer wins){
        if(exists(uuid)){
            HG.mySQL.update("UPDATE Stats SET WINS= '" + wins + "' WHERE UUID= '" + uuid + "';");
        }else {
            createPlayer(uuid);
            setWins(uuid, wins);
        }
    }

    public static void setPlayedGames(String uuid, Integer playedGames){
        if(exists(uuid)){
            HG.mySQL.update("UPDATE Stats SET PLAYEDGAMES= '" + playedGames + "' WHERE UUID= '" + uuid + "';");
        }else {
            createPlayer(uuid);
            setPlayedGames(uuid, playedGames);
        }
    }

    public static void incrementKills(String uuid){
        setKills(uuid, getKills(uuid) + 1);
    }

    public static void incrementDeaths(String uuid){
        setDeaths(uuid, getDeaths(uuid) + 1);
    }

    public static void incrementWins(String uuid){
        setWins(uuid, getWins(uuid) + 1);
    }

    public static void incrementPlayedGames(String uuid){
        setPlayedGames(uuid, getPlayedGames(uuid) + 1);
    }


}
