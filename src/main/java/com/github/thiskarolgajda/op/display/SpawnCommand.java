package com.github.thiskarolgajda.op.display;

import me.opkarol.oplibrary.commands.annotations.Command;
import me.opkarol.oplibrary.commands.annotations.NoUse;
import me.opkarol.oplibrary.commands.annotations.Subcommand;
import org.bukkit.entity.Player;

@Command("test-spawn")
public class SpawnCommand {

    @NoUse
    public void defaultCommand(Player player) {
//        DisplayBlockSpawner.spawnDisplayBlock(player);
//        Main.test();
        ///summon block_display ~-0.5 ~-0.5 ~-0.5 {Passengers:[{id:"minecraft:block_display",block_state:{Name:"minecraft:light_blue_terracotta",Properties:{}},transformation:}]}
//        DisplayBlockSpawner.spawnDisplayBlocks(player, DisplayBlockParser.parse("/summon block_display ~-0.5 ~-0.5 ~-0.5 {Passengers:[{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:dark_oak_planks\",Properties:{}},transformation:[1f,0f,0f,0f,0f,0.1875f,0f,0f,0f,0f,1f,0f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:dark_oak_planks\",Properties:{}},transformation:[0.125f,0f,0f,0f,0f,0.6875f,0f,0.1875f,0f,0f,1f,0f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:dark_oak_planks\",Properties:{}},transformation:[0.125f,0f,0f,0.875f,0f,0.6875f,0f,0.1875f,0f,0f,1f,0f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:dark_oak_planks\",Properties:{}},transformation:[0f,0f,0.75f,0.125f,0f,0.6875f,0f,0.1875f,-0.125f,0f,0f,0.125f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:dark_oak_planks\",Properties:{}},transformation:[0f,0f,0.75f,0.125f,0f,0.6875f,0f,0.1875f,-0.125f,0f,0f,1f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:dark_oak_planks\",Properties:{}},transformation:[1f,0f,0f,0f,0f,0.6187f,-0.1326f,0.875f,0f,0.6187f,0.1326f,0f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:polished_blackstone\",Properties:{}},transformation:[1.125f,0f,0f,-0.0625f,0f,0.125f,0f,0f,0f,0f,1.125f,-0.0625f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:polished_blackstone\",Properties:{}},transformation:[1.125f,0f,0f,-0.0625f,0f,0.125f,0f,0.75f,0f,0f,0.0625f,-0.0625f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:polished_blackstone\",Properties:{}},transformation:[1.125f,0f,0f,-0.0625f,0f,0.125f,0f,0.75f,0f,0f,0.0625f,1f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:polished_blackstone\",Properties:{}},transformation:[0f,0f,0.0625f,-0.0625f,0f,0.125f,0f,0.75f,-1.0625f,0f,0f,1f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:polished_blackstone\",Properties:{}},transformation:[0f,0f,0.0625f,1f,0f,0.125f,0f,0.75f,-1.0625f,0f,0f,1f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:polished_blackstone\",Properties:{}},transformation:[0.125f,0f,0f,0.9375f,0f,0.625f,0f,0.125f,0f,0f,0.125f,-0.0625f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:polished_blackstone\",Properties:{}},transformation:[0.125f,0f,0f,0.9375f,0f,0.625f,0f,0.125f,0f,0f,0.125f,0.9375f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:polished_blackstone\",Properties:{}},transformation:[0.125f,0f,0f,-0.0625f,0f,0.625f,0f,0.125f,0f,0f,0.125f,0.9375f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:polished_blackstone\",Properties:{}},transformation:[0.125f,0f,0f,-0.0625f,0f,0.625f,0f,0.125f,0f,0f,0.125f,-0.0625f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:polished_blackstone\",Properties:{}},transformation:[0.125f,0f,0f,0f,0f,0.6629f,-0.0442f,0.875f,0f,0.6629f,0.0442f,-0.0625f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:polished_blackstone\",Properties:{}},transformation:[0.125f,0f,0f,0.875f,0f,0.6629f,-0.0442f,0.875f,0f,0.6629f,0.0442f,-0.0625f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:polished_blackstone\",Properties:{}},transformation:[1f,0f,0f,0f,0f,0.0884f,-0.0442f,1.4375f,0f,0.0884f,0.0442f,0.5f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:raw_gold_block\",Properties:{}},transformation:[0.8125f,0f,0f,0.0625f,0f,0.4375f,0f,0.25f,0f,0f,0.8125f,0.125f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:bamboo_fence\",Properties:{}},transformation:[0.75f,0f,0f,0f,0f,0.0625f,0f,0.6875f,0f,0f,0.6875f,0f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:bamboo_fence\",Properties:{}},transformation:[0.7244f,0f,0.1779f,0.3125f,0f,0.0625f,0f,0.6875f,-0.1941f,0f,0.6641f,0f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:bamboo_fence\",Properties:{}},transformation:[0.75f,0f,0f,0.1875f,0f,0.0625f,0f,0.6875f,0f,0f,0.6875f,0.25f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:bamboo_fence\",Properties:{}},transformation:[0.75f,0f,0f,-0.125f,0f,0.0625f,0f,0.6875f,0f,0f,0.6875f,0.25f,0f,0f,0f,1f]}]}"));
    String command = "/summon block_display ~-0.5 ~ ~-0.5 {Passengers:[{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:black_wool\",Properties:{}},transformation:[-0.4219f,0f,0f,0.8945f,0f,0.9492f,0f,0.0625f,0f,0f,-0.9492f,2.4716f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:light_blue_terracotta\",Properties:{}},transformation:[-2.5313f,0f,0f,0.6836f,0f,1.0547f,0f,0.3789f,0f,0f,-1.4766f,2.6826f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:light_blue_terracotta\",Properties:{}},transformation:[-0.1055f,0f,0f,-1.7422f,0f,1.2656f,0f,0.3789f,0f,0f,-1.8984f,1.206f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:light_blue_terracotta\",Properties:{}},transformation:[-0.1055f,0f,0f,-1.7422f,0f,1.2656f,0f,0.3789f,0f,0f,-1.582f,-0.668f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:black_wool\",Properties:{}},transformation:[-0.4219f,0f,0f,0.8945f,0f,0.9492f,0f,0.0625f,0f,0f,-0.9492f,-1.2198f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:black_wool\",Properties:{}},transformation:[-0.4219f,0f,0f,-1.6367f,0f,0.9492f,0f,0.0625f,0f,0f,-0.9492f,2.4716f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:black_wool\",Properties:{}},transformation:[-0.4219f,0f,0f,-1.6367f,0f,0.9492f,0f,0.0625f,0f,0f,-0.9492f,-1.2198f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:white_stained_glass\",Properties:{}},transformation:[-0.3164f,0f,0f,0.2617f,0f,0.3164f,0f,1.0117f,0f,0f,-0.5273f,2.707f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:iron_block\",Properties:{}},transformation:[-0.4219f,0f,0f,1f,0f,0.5273f,0f,0.2734f,0f,0f,-0.5273f,-1.4307f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:iron_block\",Properties:{}},transformation:[-0.4219f,0f,0f,-1.7422f,0f,0.5273f,0f,0.2734f,0f,0f,-0.5273f,-1.4307f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:iron_block\",Properties:{}},transformation:[-0.4219f,0f,0f,-1.7422f,0f,0.5273f,0f,0.2734f,0f,0f,-0.5273f,2.2607f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:iron_block\",Properties:{}},transformation:[-2.5313f,0f,0f,0.6836f,0f,0.2109f,0f,0.5898f,0f,0f,-0.2109f,2.788f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:glass\",Properties:{}},transformation:[-2.3203f,0f,0f,0.5781f,0f,0.815f,-0.0273f,1.75f,0f,-0.2184f,-0.1019f,1.2305f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:light_blue_terracotta\",Properties:{}},transformation:[-0.1055f,0f,0f,0.6836f,0f,1.2656f,0f,0.3789f,0f,0f,-1.8984f,1.206f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:light_blue_terracotta\",Properties:{}},transformation:[-2.5313f,0f,0f,0.6836f,0f,0.1055f,0f,0.5898f,0f,0f,-1.8984f,1.2305f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:light_blue_terracotta\",Properties:{}},transformation:[-2.5313f,0f,0f,0.6836f,0f,0.1055f,0f,2.4883f,0f,0f,-1.6875f,1.0195f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:light_blue_terracotta\",Properties:{}},transformation:[-2.5313f,0f,0f,0.6836f,0f,1.0187f,0.3822f,0.3789f,0f,0.273f,-1.4262f,2.3906f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:light_blue_terracotta\",Properties:{}},transformation:[-2.5313f,0f,0f,0.6836f,0f,0.1827f,-0.1055f,2.4156f,0f,-0.1055f,-0.1827f,-0.4946f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:light_blue_terracotta\",Properties:{}},transformation:[-0.1055f,0f,0f,0.6836f,0f,0.9169f,-0.0273f,1.6445f,0f,-0.2457f,-0.1019f,1.206f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:light_blue_terracotta\",Properties:{}},transformation:[-0.1055f,0f,0f,0.6836f,0f,0.8438f,0f,1.6445f,0f,0f,-0.1055f,-0.2461f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:black_stained_glass\",Properties:{}},transformation:[0f,0f,-0.1055f,0.6814f,0f,0.8438f,0f,1.6445f,1.1602f,0f,0f,-0.2461f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:black_stained_glass\",Properties:{}},transformation:[0f,0f,-0.1055f,0.6814f,0f,0.6328f,0f,1.6445f,0.1055f,0f,0f,0.9141f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:black_stained_glass\",Properties:{}},transformation:[0f,0f,-0.1055f,0.6814f,0f,0.2109f,0f,1.6445f,0.1055f,0f,0f,1.0195f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:light_blue_terracotta\",Properties:{}},transformation:[-0.1055f,0f,0f,-1.7422f,0f,0.8438f,0f,1.6445f,0f,0f,-0.1055f,-0.2461f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:light_blue_terracotta\",Properties:{}},transformation:[-0.1055f,0f,0f,-1.7422f,0f,0.9169f,-0.0273f,1.6445f,0f,-0.2457f,-0.1019f,1.206f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:black_stained_glass\",Properties:{}},transformation:[0f,0f,-0.1055f,-1.7388f,0f,0.8438f,0f,1.6445f,1.1602f,0f,0f,-0.2461f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:black_stained_glass\",Properties:{}},transformation:[0f,0f,-0.1055f,-1.7388f,0f,0.2109f,0f,1.6445f,0.1055f,0f,0f,1.0195f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:black_stained_glass\",Properties:{}},transformation:[0f,0f,-0.1055f,-1.7388f,0f,0.6328f,0f,1.6445f,0.1055f,0f,0f,0.9141f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:light_blue_terracotta\",Properties:{}},transformation:[-2.5313f,0f,0f,0.6836f,0f,0.4567f,-0.3164f,1.4174f,0f,-0.2637f,-0.548f,-1.3797f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:black_stained_glass\",Properties:{}},transformation:[0f,0f,-0.1055f,0.6814f,0f,0.5273f,0f,1.6445f,0.9492f,0f,0f,-1.3008f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:black_stained_glass\",Properties:{}},transformation:[0f,0f,-0.1055f,0.6814f,0f,0.2109f,0f,2.1719f,0.5273f,0f,0f,-0.8789f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:black_stained_glass\",Properties:{}},transformation:[0f,0f,-0.1055f,-1.7388f,0f,0.5273f,0f,1.6445f,0.9492f,0f,0f,-1.3008f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:black_stained_glass\",Properties:{}},transformation:[0f,0f,-0.1055f,-1.7388f,0f,0.2109f,0f,2.1719f,0.5273f,0f,0f,-0.8789f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:black_stained_glass\",Properties:{}},transformation:[0f,0f,-0.1055f,0.6814f,0f,0.1055f,0f,2.3828f,0.2109f,0f,0f,-0.5625f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:black_stained_glass\",Properties:{}},transformation:[0f,0f,-0.1055f,-1.7388f,0f,0.1055f,0f,2.3828f,0.2109f,0f,0f,-0.5625f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:white_stained_glass\",Properties:{}},transformation:[-0.3164f,0f,0f,-1.1094f,0f,0.3164f,0f,1.0117f,0f,0f,-0.5273f,2.707f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:orange_stained_glass\",Properties:{}},transformation:[-0.2109f,0f,0f,-0.793f,0f,0.2109f,0f,1.0581f,0f,0f,-0.5273f,2.707f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:orange_stained_glass\",Properties:{}},transformation:[-0.2109f,0f,0f,-0.1602f,0f,0.2109f,0f,1.0581f,0f,0f,-0.5273f,2.707f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:coal_block\",Properties:{}},transformation:[-1.0547f,0f,0f,-0.0547f,0f,0.3164f,0f,1.0117f,0f,0f,-0.5273f,2.7028f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:orange_stained_glass\",Properties:{}},transformation:[-0.2109f,0f,0f,-1.6494f,0f,0.1055f,0f,1.1172f,0f,0f,-0.5273f,2.3906f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:orange_stained_glass\",Properties:{}},transformation:[-0.2109f,0f,0f,0.6979f,0f,0.1055f,0f,1.1172f,0f,0f,-0.5273f,2.3906f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:iron_block\",Properties:{}},transformation:[-0.1055f,0f,0f,-1.7506f,0f,0.1055f,0f,1.2227f,0f,0f,-0.4219f,0.8086f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:iron_block\",Properties:{}},transformation:[-0.4219f,0f,0f,1f,0f,0.5273f,0f,0.2734f,0f,0f,-0.5273f,2.2607f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:coal_block\",Properties:{}},transformation:[-1.1602f,0f,0f,0.0508f,0f,0.4219f,0f,0.4844f,0f,0f,-0.1055f,2.8007f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:iron_block\",Properties:{}},transformation:[0.9492f,0f,0f,-1.0039f,0f,-0.2109f,0f,0.8008f,0f,0f,-0.1055f,2.8091f,0f,0f,0f,1f]},{id:\"minecraft:text_display\",text:'[{\"text\":\"FTU172\",\"color\":\"#000000\",\"bold\":false,\"italic\":false,\"underlined\":false,\"strikethrough\":false,\"font\":\"minecraft:uniform\"}]',text_opacity:255,background:0,alignment:\"center\",line_width:220.5,default_background:false,transformation:[1.2656f,0f,0f,-0.523f,0f,1.2656f,0f,0.5266f,0f,0f,1.6875f,2.8462f,0f,0f,0f,1f],background:0,default_background:false},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:coal_block\",Properties:{}},transformation:[1.1602f,0f,0f,-1.1094f,0f,0.4219f,0f,0.8008f,0f,0f,0.1055f,-2.3437f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:iron_block\",Properties:{}},transformation:[-0.9492f,0f,0f,-0.0547f,0f,-0.2109f,0f,1.1172f,0f,0f,0.1055f,-2.3555f,0f,0f,0f,1f]},{id:\"minecraft:text_display\",text:'[{\"text\":\"FTU172\",\"color\":\"#000000\",\"bold\":false,\"italic\":false,\"underlined\":false,\"strikethrough\":false,\"font\":\"minecraft:uniform\"}]',text_opacity:255,background:0,alignment:\"center\",line_width:220.5,default_background:false,transformation:[-1.2656f,0f,0f,-0.5356f,0f,1.2656f,0f,0.843f,0f,0f,-1.6875f,-2.3892f,0f,0f,0f,1f],background:0,default_background:false},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:red_stained_glass\",Properties:{}},transformation:[-0.4219f,0f,0f,-1.3203f,0f,0.3164f,0f,1.0117f,0f,0f,-0.5273f,-1.8281f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:red_stained_glass\",Properties:{}},transformation:[-0.4219f,0f,0f,0.5781f,0f,0.3164f,0f,1.0117f,0f,0f,-0.5273f,-1.8281f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:orange_stained_glass\",Properties:{}},transformation:[-0.4219f,0f,0f,0.5781f,0f,0.2109f,0f,0.8008f,0f,0f,-0.5273f,-1.8281f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:orange_stained_glass\",Properties:{}},transformation:[-0.4219f,0f,0f,-1.3203f,0f,0.2109f,0f,0.8008f,0f,0f,-0.5273f,-1.8281f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:iron_block\",Properties:{}},transformation:[-2.5313f,0f,0f,0.6836f,0f,0.2109f,0f,0.4844f,0f,0f,-0.2109f,-2.1445f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:light_blue_terracotta\",Properties:{}},transformation:[-2.5313f,0f,0f,0.6836f,0f,0.1827f,-0.3164f,1.7828f,0f,-0.1055f,-0.548f,-1.5907f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:light_blue_terracotta\",Properties:{}},transformation:[-0.2109f,0f,0f,0.6836f,0f,0.1827f,-0.5274f,2.3101f,0f,-0.1055f,-0.9134f,-0.6773f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:light_blue_terracotta\",Properties:{}},transformation:[-0.2109f,0f,0f,-1.6367f,0f,0.1827f,-0.5274f,2.3101f,0f,-0.1055f,-0.9134f,-0.6773f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:black_stained_glass\",Properties:{}},transformation:[0f,0f,-0.1055f,0.6814f,0f,0.3164f,0f,1.6445f,0.3164f,0f,0f,-1.6172f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:black_stained_glass\",Properties:{}},transformation:[0f,0f,-0.1055f,-1.7388f,0f,0.3164f,0f,1.6445f,0.3164f,0f,0f,-1.6172f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:black_stained_glass\",Properties:{}},transformation:[-2.1094f,0f,0f,0.4727f,0f,0.5273f,0.0913f,1.8741f,0f,0.9134f,-0.0527f,-1.6434f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:gray_glazed_terracotta\",Properties:{facing:\"east\"}},transformation:[-0.1055f,0f,0f,0.3166f,0f,0.1055f,0f,0.2313f,0f,0f,-0.7383f,-1.9336f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:gray_glazed_terracotta\",Properties:{facing:\"east\"}},transformation:[-0.2109f,0f,0f,0.3672f,0f,0.2109f,0f,0.168f,0f,0f,-0.7383f,-1.5117f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:light_blue_terracotta\",Properties:{}},transformation:[-0.1055f,0f,0f,0.6836f,0f,1.2656f,0f,0.3789f,0f,0f,-1.582f,-0.668f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:light_blue_terracotta\",Properties:{}},transformation:[-2.5313f,0f,0f,0.6836f,0f,0.9492f,0f,0.4844f,0f,0f,-0.6328f,-1.3797f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:light_blue_terracotta\",Properties:{}},transformation:[-2.5313f,0f,0f,0.6836f,0f,0.1055f,0f,0.5898f,0f,0f,-1.8984f,-0.3516f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:light_blue_terracotta\",Properties:{}},transformation:[0f,0f,2.3203f,-1.7422f,0f,1.2656f,0f,0.3789f,-0.1055f,0f,0f,-2.1445f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:light_blue_terracotta\",Properties:{}},transformation:[-0.7383f,0f,0f,0.4727f,0f,0.1055f,0f,0.9063f,0f,0f,-0.9492f,0.7031f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:gray_wool\",Properties:{}},transformation:[-0.1055f,0f,0f,0.1562f,0f,0.1055f,0f,1.4336f,0f,0f,-0.1055f,1.2305f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:gray_wool\",Properties:{}},transformation:[-0.1055f,0f,0f,0.5781f,0f,0.1055f,0f,0.9063f,0f,0f,-0.9492f,0.7031f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:gray_wool\",Properties:{}},transformation:[-0.1055f,0f,0f,0.5781f,0f,1.1602f,0f,0.9063f,0f,0f,-0.1055f,-0.2461f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:gray_wool\",Properties:{}},transformation:[-0.1055f,0f,0f,-0.2656f,0f,1.1602f,0f,0.9063f,0f,0f,-0.1055f,-0.2461f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:light_blue_terracotta\",Properties:{}},transformation:[-0.7383f,0f,0f,0.4727f,0f,1.1602f,0f,0.9063f,0f,0f,-0.1055f,-0.2461f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:gray_wool\",Properties:{}},transformation:[-0.7383f,0f,0f,0.4727f,0f,0.1055f,0f,1.4336f,0f,0f,-0.1055f,1.125f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:gray_wool\",Properties:{}},transformation:[-0.1055f,0f,0f,-0.2656f,0f,0.1055f,0f,0.9063f,0f,0f,-0.9492f,0.7031f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:gray_wool\",Properties:{}},transformation:[-0.1055f,0f,0f,-0.2656f,0f,0.1055f,0f,1.4336f,0f,0f,-0.1055f,1.125f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:gray_wool\",Properties:{}},transformation:[-0.1055f,0f,0f,-0.2656f,0f,0.1055f,0f,1.5391f,0f,0f,-0.1055f,1.125f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:gray_wool\",Properties:{}},transformation:[-0.1055f,0f,0f,-0.2656f,0f,0.1055f,0f,1.3281f,0f,0f,-0.1055f,1.125f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:gray_wool\",Properties:{}},transformation:[-0.1055f,0f,0f,-0.1602f,0f,0.1055f,0f,1.2227f,0f,0f,-0.1055f,1.125f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:gray_wool\",Properties:{}},transformation:[-0.1055f,0f,0f,-0.0547f,0f,0.1055f,0f,1.1172f,0f,0f,-0.1055f,1.125f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:gray_wool\",Properties:{}},transformation:[-0.1055f,0f,0f,0.0508f,0f,0.1055f,0f,1.0117f,0f,0f,-0.1055f,1.125f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:gray_wool\",Properties:{}},transformation:[-0.1055f,0f,0f,0.3672f,0f,0.1055f,0f,1.1172f,0f,0f,-0.1055f,1.125f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:gray_wool\",Properties:{}},transformation:[-0.1055f,0f,0f,0.2617f,0f,0.1055f,0f,1.0117f,0f,0f,-0.1055f,1.125f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:gray_wool\",Properties:{}},transformation:[-0.1055f,0f,0f,0.1562f,0f,0.1055f,0f,1.0117f,0f,0f,-0.1055f,1.125f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:gray_wool\",Properties:{}},transformation:[-0.1055f,0f,0f,0.4727f,0f,0.1055f,0f,1.2227f,0f,0f,-0.1055f,1.125f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:gray_wool\",Properties:{}},transformation:[-0.1055f,0f,0f,0.5781f,0f,0.1055f,0f,1.3281f,0f,0f,-0.1055f,1.125f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:gray_wool\",Properties:{}},transformation:[-0.1055f,0f,0f,0.5781f,0f,0.1055f,0f,1.4336f,0f,0f,-0.1055f,1.125f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:gray_wool\",Properties:{}},transformation:[-0.1055f,0f,0f,0.5781f,0f,0.1055f,0f,1.5391f,0f,0f,-0.1055f,1.125f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:gray_wool\",Properties:{}},transformation:[-0.1055f,0f,0f,0.4727f,0f,0.1055f,0f,1.6445f,0f,0f,-0.1055f,1.125f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:gray_wool\",Properties:{}},transformation:[-0.1055f,0f,0f,0.3672f,0f,0.1055f,0f,1.75f,0f,0f,-0.1055f,1.125f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:gray_wool\",Properties:{}},transformation:[-0.1055f,0f,0f,0.2617f,0f,0.1055f,0f,1.8555f,0f,0f,-0.1055f,1.125f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:gray_wool\",Properties:{}},transformation:[-0.1055f,0f,0f,0.1562f,0f,0.1055f,0f,1.8555f,0f,0f,-0.1055f,1.125f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:gray_wool\",Properties:{}},transformation:[-0.1055f,0f,0f,0.0508f,0f,0.1055f,0f,1.8555f,0f,0f,-0.1055f,1.125f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:gray_wool\",Properties:{}},transformation:[-0.1055f,0f,0f,-0.0547f,0f,0.1055f,0f,1.75f,0f,0f,-0.1055f,1.125f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:gray_wool\",Properties:{}},transformation:[-0.1055f,0f,0f,-0.1602f,0f,0.1055f,0f,1.6445f,0f,0f,-0.1055f,1.125f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:gray_wool\",Properties:{}},transformation:[-0.3164f,0f,0f,0.2617f,0f,0.1055f,0f,1.5391f,0f,0f,-0.1055f,1.125f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:gray_wool\",Properties:{}},transformation:[-0.3164f,0f,0f,0.2617f,0f,0.1055f,0f,1.3281f,0f,0f,-0.1055f,1.125f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:netherite_block\",Properties:{}},transformation:[0f,0f,-0.0169f,0.0771f,0f,-0.0169f,0f,0.8535f,-0.1055f,0f,0f,1.0195f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:netherite_block\",Properties:{}},transformation:[0f,0f,-0.0169f,0.0771f,0.0914f,-0.0084f,0f,0.8535f,-0.0527f,-0.0146f,0f,0.9404f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:iron_block\",Properties:{}},transformation:[0f,0f,-0.1055f,0.0972f,-0.0548f,-0.0163f,0.0001f,0.959f,-0.2037f,0.0044f,0f,0.9404f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:iron_block\",Properties:{}},transformation:[0f,0f,-0.1055f,0.2881f,-0.0548f,-0.0163f,0.0001f,0.959f,-0.2037f,0.0044f,0f,0.9404f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:netherite_block\",Properties:{}},transformation:[0f,0f,-0.0169f,0.2881f,0.0914f,-0.0084f,0f,0.8535f,-0.0527f,-0.0146f,0f,0.9404f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:netherite_block\",Properties:{}},transformation:[0f,0f,-0.0169f,0.2881f,0f,-0.0169f,0f,0.8535f,-0.1055f,0f,0f,1.0195f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:light_blue_terracotta\",Properties:{}},transformation:[-0.7383f,0f,0f,-0.8984f,0f,0.1055f,0f,0.9063f,0f,0f,-0.9492f,0.7031f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:gray_wool\",Properties:{}},transformation:[-0.1055f,0f,0f,-1.6367f,0f,0.1055f,0f,0.9063f,0f,0f,-1.0547f,0.7031f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:gray_wool\",Properties:{}},transformation:[-0.1055f,0f,0f,-0.793f,0f,0.1055f,0f,0.9063f,0f,0f,-0.9492f,0.7031f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:gray_wool\",Properties:{}},transformation:[-0.1055f,0f,0f,-0.793f,0f,1.1602f,0f,0.9063f,0f,0f,-0.1055f,-0.2461f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:gray_wool\",Properties:{}},transformation:[-0.1055f,0f,0f,-1.6367f,0f,1.1602f,0f,0.9063f,0f,0f,-0.1055f,-0.2461f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:light_blue_terracotta\",Properties:{}},transformation:[-0.7383f,0f,0f,-0.8984f,0f,1.1602f,0f,0.9063f,0f,0f,-0.1055f,-0.2461f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:iron_block\",Properties:{}},transformation:[-0.1055f,0f,0f,0.6962f,0f,0.1055f,0f,1.2227f,0f,0f,-0.4219f,0.8086f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:glass\",Properties:{}},transformation:[0f,0f,-0.3164f,1.2109f,0f,0.2109f,0f,1.6445f,0.1055f,0f,0f,1.0195f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:light_blue_terracotta\",Properties:{}},transformation:[0f,0f,-0.1055f,1.3164f,0f,0.2109f,0f,1.6445f,0.2109f,0f,0f,1.0195f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:light_blue_terracotta\",Properties:{}},transformation:[0f,0f,-0.1055f,0.8945f,0f,0.2109f,0f,1.6445f,0.2109f,0f,0f,1.0195f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:light_blue_terracotta\",Properties:{}},transformation:[0f,0f,-0.5273f,1.3164f,0f,0.1055f,0f,1.5391f,0.2109f,0f,0f,1.0195f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:light_blue_terracotta\",Properties:{}},transformation:[0f,0f,-0.5273f,1.3164f,0f,0.1055f,0f,1.8555f,0.2109f,0f,0f,1.0195f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:light_blue_terracotta\",Properties:{}},transformation:[0f,0f,-0.3164f,1.2109f,0f,0.2109f,0f,1.6445f,0.1055f,0f,0f,1.125f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:blackstone\",Properties:{}},transformation:[0f,0.2037f,-0.0273f,0.6435f,0f,0.0546f,0.1019f,1.6841f,0.1055f,0f,0f,1.0195f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:glass\",Properties:{}},transformation:[0f,0f,-0.3164f,-2.0586f,0f,0.2109f,0f,1.6445f,0.1055f,0f,0f,1.0195f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:light_blue_terracotta\",Properties:{}},transformation:[0f,0f,-0.1055f,-1.9531f,0f,0.2109f,0f,1.6445f,0.2109f,0f,0f,1.0195f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:light_blue_terracotta\",Properties:{}},transformation:[0f,0f,-0.1055f,-2.375f,0f,0.2109f,0f,1.6445f,0.2109f,0f,0f,1.0195f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:light_blue_terracotta\",Properties:{}},transformation:[0f,0f,-0.5273f,-1.9531f,0f,0.1055f,0f,1.5391f,0.2109f,0f,0f,1.0195f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:light_blue_terracotta\",Properties:{}},transformation:[0f,0f,-0.5273f,-1.9531f,0f,0.1055f,0f,1.8555f,0.2109f,0f,0f,1.0195f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:light_blue_terracotta\",Properties:{}},transformation:[0f,0f,-0.3164f,-2.0586f,0f,0.2109f,0f,1.6445f,0.1055f,0f,0f,1.125f,0f,0f,0f,1f]},{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:blackstone\",Properties:{}},transformation:[0f,0.1827f,0.0527f,-1.9795f,0f,-0.1055f,0.0913f,1.7896f,0.1055f,0f,0f,1.0195f,0f,0f,0f,1f]}]}";
        DisplayBlockSpawner.spawnDisplayBlocks(player.getLocation(), DisplayBlockParser.parse(command));
    }

    @Subcommand("spawn")
    public void spawnCommand(Player player, String type) {
        DisplayBlockType blockType = DisplayBlockType.valueOf(type);
        DisplayBlockSpawner.spawnDisplayBlocks(player.getLocation(), DisplayBlockParser.parse(blockType.getCommands()));

    }


}
