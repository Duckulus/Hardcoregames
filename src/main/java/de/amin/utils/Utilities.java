//Created by Duckulus on 02 Jul, 2021 

package de.amin.utils;



import org.bukkit.block.Block;

import java.util.ArrayList;

public class Utilities {

    public static ArrayList<Block> connectedBlocks(Block b){
        ArrayList<Block> blocks = new ArrayList<>();
        int limit = 0;
        for (int x = -1; x < 1 ; x++) {
            for (int y = -1; y < 1 ; y++) {
                for (int z = -1; z < 1 ; z++) {
                    limit +=1;
                    if(b.getRelative(x,y,z).getType().equals(b.getType())) {
                        blocks.add(b.getRelative(x, y, z));
                        blocks.addAll(connectedBlocks(b.getRelative(x, y, z)));
                    }
                    if(limit>=100)return blocks;
                }
            }
        }

        return blocks;
    }

    public static ArrayList<?> removeDublicates(ArrayList<Object> list){
        ArrayList<Object> list2 = new ArrayList<>();
        for(Object o : list){
            if(!list2.contains(o)){
                list2.add(o);
            }
        }
        return list2;
    }

}
