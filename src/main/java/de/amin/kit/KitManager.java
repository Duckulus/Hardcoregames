package de.amin.kit;

import de.amin.hardcoregames.HG;
import de.amin.kit.impl.*;
import de.amin.kit.impl.gladiator.GladiatorKit;
import de.amin.mechanics.conversation.KitSearchPromt;
import org.bukkit.Bukkit;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

public class KitManager {

    private ArrayList<Kit> kitArray;
    private HashMap<String, Kit> kitHashMap;
    private ArrayList<Kit> disabledKits;
    private HG plugin;
    private Kit forcedKit;

    public KitManager(HG plugin){
        kitArray = new ArrayList<>();
        kitHashMap = new HashMap<>();
        disabledKits = new ArrayList<>();
        this.plugin = plugin;

        Bukkit.getScheduler().scheduleSyncDelayedTask(HG.INSTANCE, new Runnable() {
            @Override
            public void run() {
                init();
            }
        }, 5);

    }

    private void init() {
        registerKit(new NoneKit());
        registerKit(new JackhammerKit());
        registerKit(new PhantomKit());
        registerKit(new LauncherKit());
        registerKit(new SwitcherKit());
        registerKit(new WormKit());
        registerKit(new AnchorKit());
        registerKit(new DiggerKit());
        registerKit(new HulkKit());
        registerKit(new FiremanKit());
        registerKit(new MagmaKit());
        registerKit(new AchillesKit());
        registerKit(new StomperKit());
        registerKit(new SurpriseKit());
        registerKit(new ScoutKit());
        registerKit(new BlinkKit());
        registerKit(new KangarooKit());
        registerKit(new FlashKit());
        registerKit(new KayaKit());
        registerKit(new VikingKit());
        registerKit(new TimelordKit());
        registerKit(new GladiatorKit());
        registerKit(new MonkKit());
        registerKit(new TankKit());
        registerKit(new NinjaKit());
        registerKit(new GrandpaKit());
        registerKit(new RedstonerKit());
        registerKit(new RevivalKit());
        registerKit(new KunaiKit());
        registerKit(new XrayKit());
        registerKit(new NeoKit());
        registerKit(new HermitKit());
        registerKit(new GliderKit());
        registerKit(new BackpackerKit(this, plugin.getGameStateManager()));
        registerKit(new JokerKit(plugin));

        registerKitListeners();

        kitArray.sort(Comparator.comparing(Kit::getName));

    }

    public void setKit(String playerName, Kit kit){
        if(!isEnabled(kit) && !(kit instanceof NoneKit)){
            Bukkit.getPlayer(playerName).sendMessage("§cKit disabled!");
            return;
        }
        kitHashMap.remove(playerName);
        kitHashMap.put(playerName, kit);
        Bukkit.getPlayer(playerName).sendMessage(HG.INSTANCE.PREFIX + "§7You selected §a" + getKitHashMap().get(playerName).getName());
        Bukkit.getPlayer(playerName).closeInventory();
    }

    private void registerKit(Kit kit){
        kitArray.add(kit);
    }

    private void registerKitListeners() {
        for(Kit kit : kitArray){
            if(kit instanceof Listener){
                Bukkit.getPluginManager().registerEvents((Listener) kit, plugin);
            }
        }
    }

    public ArrayList<Kit> getKitArray() {
        return kitArray;
    }

    public HashMap<String, Kit> getKitHashMap() {
        return kitHashMap;
    }

    public Kit getKit(Player player){
        return kitHashMap.get(player.getName());
    }

    public Kit getKitByName(String kitName){
        for(Kit k : getKitArray()){
            if(kitName.equalsIgnoreCase(k.getName())){
                return k;
            }
        }
        return null;
    }

    public void forceKit(Kit k){
        for(Player player : HG.INSTANCE.getPlayers()){
            player.closeInventory();
            setKit(player.getName(), k);
            forcedKit = k;
        }
    }

    public void disable(String kit){
        if(getKitByName(kit)==null)return;
        disabledKits.add(getKitByName(kit));
    }

    public void enable(String kit) {
        if(getKitByName(kit)==null)return;
        disabledKits.remove(getKitByName(kit));
    }

    public void setEnabled(String kit, Boolean enabled, Player player){
        if (enabled){
            enable(kit);
            player.sendMessage("§7" + getKitByName(kit).getName() + " has been §aenabled");
        }else {
            disable(kit);
            player.sendMessage("§7" + getKitByName(kit).getName() + " has been §cdisabled");
        }
    }

    public boolean isEnabled(Kit kit) {
        for(Kit k : disabledKits){
            if(kit.getName().equals(k.getName())){
                return false;
            }
        }
        return true;
    }

    public void enableAll(){
        disabledKits.clear();
    }

    public void disableAll() {
        disabledKits.clear();
        disabledKits.addAll(kitArray);
    }


    public void selectRandomKit(Player player){
        int random = ThreadLocalRandom.current().nextInt(getKitArray().size());
        setKit(player.getName(), getKitArray().get(random));
        player.closeInventory();
    }

    public void promtKitSearch(Player player){
        ConversationFactory factory = new ConversationFactory(HG.INSTANCE);
        factory.withFirstPrompt(new KitSearchPromt()).withEscapeSequence("exit").thatExcludesNonPlayersWithMessage("bugger off")
                .withLocalEcho(false).buildConversation(player).begin();
        player.closeInventory();
    }

    public Kit getForcedKit() {
        return forcedKit;
    }

    public void setForcedKit(Kit forcedKit) {
        this.forcedKit = forcedKit;
    }

    public ArrayList<Kit> getDisabledKits() {
        return disabledKits;
    }
}
