package de.amin.hardcoregames;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import de.amin.Feast.FeastProtection;
import de.amin.MySQL.MySQL;
import de.amin.commands.*;
import de.amin.gamestates.GameState;
import de.amin.gamestates.GameStateManager;
import de.amin.kit.KitManager;
import de.amin.kit.KitSelector;
import de.amin.kit.StartItems;
import de.amin.kit.impl.*;
import de.amin.kit.impl.gladiator.GladiatorKit;
import de.amin.listeners.*;
import de.amin.managers.VanishManager;
import de.amin.mechanics.*;
import de.amin.managers.ItemManager;
import de.amin.utils.CustomDeathMessages;
import fr.minuskube.inv.InventoryManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

import javax.sql.DataSource;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public final class HG extends JavaPlugin implements PluginMessageListener {

    public static HG INSTANCE;
    private GameStateManager gameStateManager;
    private ArrayList<Player> players;
    private KitManager kitManager;
    private ItemManager itemManager;
    private VanishManager vanishManager;
    private AdminMode adminMode;
    private InventoryManager inventoryManager;
    private RecraftNerf recraftNerf;
    private StartItems startItems;
    private SpectatorMode specMode;

    public static MySQL mySQL;
    private DataSource dataSource;

    private HashMap<String, Long> cooldown;
    private HashMap<String, Long> launchedPlayers;
    private HashMap<String, Integer> kills;
    private int playersAtStart;

    private File file = new File("plugins//Hardcoregames//config.yml");
    private FileConfiguration config = getConfig();

    public final String PREFIX = "§a§l> §r";


    public static void deleteFolder(File folder) {
        File[] files = folder.listFiles();
        if (files != null) { //some JVMs return null for empty dirs
            for (File f : files) {
                if (f.isDirectory()) {
                    deleteFolder(f);
                } else {
                    f.delete();
                }
            }
        }
        folder.delete();
    }

    @Override
    public void onLoad() {
        super.onLoad();
        Bukkit.unloadWorld("world", false);
        File folder = new File("world");
        deleteFolder(folder);
            if(!file.exists()) {
//                try {
//                    file.createNewFile();
//
//                    config.save(file);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
            }

    }

    @Override
    public void onEnable() {
        config.addDefault("game.min_players", 2);
        config.addDefault("timers.pregame", 180);
        config.addDefault("timers.invincibility", 120);
        config.addDefault("mechanics.launchfactor", 2.0);
        config.addDefault("mechanics.bordersize", 500);
        config.addDefault("feast.time", 600);
        config.addDefault("bonusfeast.time", 1200);
        config.addDefault("gameend.time", 6000);
        config.addDefault("pit.time", 2400-5*60);
        config.addDefault("pit.countdown", 5*60);
        config.addDefault("pit.radius", 50);
        config.options().copyDefaults(true);
        saveConfig();

        INSTANCE = this;



        players = new ArrayList<>();

        itemManager = new ItemManager();
        vanishManager = new VanishManager();
        kitManager = new KitManager();
        gameStateManager = new GameStateManager(this, kitManager, itemManager);
        gameStateManager.setGameState(GameState.LOBBY_STATE);
        adminMode = new AdminMode();
        recraftNerf = new RecraftNerf(config, gameStateManager);
        startItems = new StartItems();
        specMode = new SpectatorMode();

        new RecipeLoader(kitManager).registerRecipes();

        cooldown = new HashMap<>();
        launchedPlayers = new HashMap<>();
        kills = new HashMap<>();
        playersAtStart = 0;

        inventoryManager = new InventoryManager(this);
        inventoryManager.init();


        init();
        initKits();

        getServer().getConsoleSender().sendMessage(PREFIX + ChatColor.AQUA + "Plugin initialized succesfully");
    }

    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage(PREFIX + ChatColor.AQUA + "Plugin disabled");
    }

    private void init() {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new ChatListener(), this);
        pm.registerEvents(new ConnectionListener(), this);
        pm.registerEvents(new BlockListeners(), this);
        pm.registerEvents(new DamageListener(), this);
        pm.registerEvents(new FoodLevelChangeListener(), this);
        pm.registerEvents(new DeathListener(), this);
        pm.registerEvents(new SoupListener(), this);
        pm.registerEvents(new KitSelector(), this);
        pm.registerEvents(new RandomTP(), this);
        pm.registerEvents(new DropPickupListeners(), this);
        pm.registerEvents(new Launcher(), this);
        pm.registerEvents(new WeatherListener(), this);
        pm.registerEvents(new WorldBorder(), this);
        pm.registerEvents(new AchievementListener(), this);
        pm.registerEvents(new EntityTargetListener(), this);
        pm.registerEvents(new Tracker(), this);
        pm.registerEvents(new ItemManager(), this);
        pm.registerEvents(new DamageNerf(), this);
        pm.registerEvents(new AdminUtils(), this);
        pm.registerEvents(new CustomDeathMessages(), this);
        pm.registerEvents(new DimensionChangeListener(), this);
        pm.registerEvents(new StartItems(), this);
        pm.registerEvents(new Grenade(), this);
        pm.registerEvents(new SpectatorMode(), this);

        getCommand("heal").setExecutor(new healCommand());
        getCommand("up").setExecutor(new upCommand());
        getCommand("fs").setExecutor(new ForceStartCommand());
        getCommand("skipinvis").setExecutor(new SkipInvincibilityCommand());
        getCommand("ping").setExecutor(new PingCommand());
        getCommand("randomtp").setExecutor(new RandomTeleportCommand());
        getCommand("getkit").setExecutor(new GetKitCommand());
        getCommand("gm").setExecutor(new GameModeCommand());
        getCommand("kitinfo").setExecutor(new KitInfoCommand());
        getCommand("setborder").setExecutor(new SetBorderCommand());
        getCommand("endgame").setExecutor(new EndGameCommand());
        getCommand("kit").setExecutor(new KitCommand());
        getCommand("kit").setTabCompleter(new KitCommand());
        getCommand("forcefeast").setExecutor(new ForceFeastCommand());
        getCommand("feast").setExecutor(new FeastCommand());
        getCommand("test").setExecutor(new TestCommand());
        getCommand("vanish").setExecutor(new VanishCommand());
        getCommand("invsee").setExecutor(new InvseeCommand());
        getCommand("admin").setExecutor(new AdminCommand());
        getCommand("skipcd").setExecutor(new SkipcdCommand());
        getCommand("stats").setExecutor(new StatsCommand());
        getCommand("forcekit").setExecutor(new ForceKitCommand(gameStateManager, kitManager));
        getCommand("settime").setExecutor(new SetTimeCommand());
        getCommand("aura").setExecutor(new AuraCommand());
        getCommand("kitsettings").setExecutor(new KitSettingsCommand());
    }


    private void initKits() {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new JackhammerKit(), this);
        pm.registerEvents(new PhantomKit(), this);
        pm.registerEvents(new SwitcherKit(), this);
        pm.registerEvents(new WormKit(), this);
        pm.registerEvents(new AnchorKit(), this);
        pm.registerEvents(new DiggerKit(), this);
        pm.registerEvents(new HulkKit(), this);
        pm.registerEvents(new FiremanKit(), this);
        pm.registerEvents(new FeastProtection(), this);
        pm.registerEvents(new MagmaKit(), this);
        pm.registerEvents(new AchillesKit(), this);
        pm.registerEvents(new StomperKit(), this);
        pm.registerEvents(new ScoutKit(), this);
        pm.registerEvents(new BlinkKit(), this);
        pm.registerEvents(new KangarooKit(), this);
        pm.registerEvents(new FlashKit(), this);
        pm.registerEvents(new KayaKit(), this);
        pm.registerEvents(new VikingKit(), this);
        pm.registerEvents(new TimelordKit(), this);
        pm.registerEvents(new GladiatorKit(), this);
        pm.registerEvents(new MonkKit(), this);
        pm.registerEvents(new TankKit(), this);
        pm.registerEvents(new NinjaKit(), this);
        pm.registerEvents(new RevivalKit(), this);
        pm.registerEvents(new KunaiKit(), this);
        pm.registerEvents(new XrayKit(), this);
    }

    private void connectMySql() {
        mySQL = new MySQL("remotemysql.com", "3ScbLV7LoQ", "3ScbLV7LoQ", "wSMRQ2Abnn");
        mySQL.update("CREATE TABLE IF NOT EXISTS Stats(UUID varchar(64), KILLS int, DEATHS int, WINS int, PLAYEDGAMES int)");
    }



    public ArrayList<Player> getPlayers() {
        return players;
    }

    public GameStateManager getGameStateManager() {
        return gameStateManager;
    }

    public KitManager getKitManager() {
        return kitManager;
    }

    public FileConfiguration getFileConfig() {
        return config;
    }

    public HashMap<String, Long> getCooldown() {
        return cooldown;
    }

    public HashMap<String, Long> getLaunchedPlayers() {
        return launchedPlayers;
    }

    @Override
    public File getFile() {
        return file;
    }

    public HashMap<String, Integer> getKills() {
        return kills;
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (!channel.equals("BungeeCord")) {
            return;
        }
        ByteArrayDataInput in = ByteStreams.newDataInput(message);
        String subchannel = in.readUTF();
        if (subchannel.equals("SomeSubChannel")) {
            String userName = in.readUTF();
            String address = in.readUTF(); //if it's a Unix socket address then this has the form "unix://" followed by a path
            int port = in.readInt();
        }
    }

    public int getPlayersAtStart() {
        return playersAtStart;
    }

    public void setPlayersAtStart(int playersAtStart) {
        this.playersAtStart = playersAtStart;
    }

    public ItemManager getItemManager() {
        return itemManager;
    }

    public VanishManager getVanishManager() {
        return vanishManager;
    }

    public AdminMode getAdminMode() {
        return adminMode;
    }

    public InventoryManager getInventoryManager() {
        return inventoryManager;
    }

    public StartItems getStartItems() {
        return startItems;
    }

    public SpectatorMode getSpecMode() {
        return specMode;
    }
}
