package ai.mifco;

import ai.mifco.listener.BotShutdown;
import ai.mifco.listener.ButtonInteraction;
import ai.mifco.listener.MessageReceiver;
import ai.mifco.listener.slashcommands.StickyMessage;
import ai.mifco.listener.slashcommands.VouchMessage;
import ai.mifco.manager.ConfigManager;
import ai.mifco.manager.StickyManager;
import lombok.SneakyThrows;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Bot {

    public static HashMap<Channel, Boolean> stickyChannel = new HashMap<>();
    public static HashMap<Channel, String> stickyMessage = new HashMap<>();
    public static StickyManager stickyManager;
    public static ConfigManager configManager = new ConfigManager();
    public static JDA jda;

    public static void main(String[] args) {

        stickyManager = new StickyManager();
        var jdaBuilder = JDABuilder.createDefault(configManager.getProperty("TOKEN"));

        jdaBuilder.disableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE);
        jdaBuilder.setBulkDeleteSplittingEnabled(false);
        jdaBuilder.setActivity(Activity.watching("the yoshino discord chats."));
        jdaBuilder.setStatus(OnlineStatus.DO_NOT_DISTURB);
        jdaBuilder.enableIntents(GatewayIntent.GUILD_MESSAGES);

        jdaBuilder.addEventListeners(new StickyMessage());
        jdaBuilder.addEventListeners(new ButtonInteraction());
        jdaBuilder.addEventListeners(new MessageReceiver());
        jdaBuilder.addEventListeners(new BotShutdown());
        jdaBuilder.addEventListeners(new VouchMessage());



        jda = jdaBuilder.build();
        try {
            jda.upsertCommand("vouch", "Use this command to submit a vouch.")
                    .addOption(OptionType.NUMBER, "rating", "Rate our service with 1-5 stars.", true)
                    .addOption(OptionType.STRING, "feedback", "Write a short sentence evaluating our service.", true)
                    .addOption(OptionType.ATTACHMENT, "proof", "Show our members that you have received your goods.", true)
                    .queue();
            jda.upsertCommand("sticky", "Enable or disable the sticky message!").queue();
        } catch (Exception e) {
            e.printStackTrace();
        }


        loadStick();

    }
    
    public static void saveSticky() {

        List<String> channelIds = new ArrayList<>();

        for (Channel channel : stickyChannel.keySet()) {
            channelIds.add(channel.getId() + ":" + stickyChannel.get(channel));
        }
        var channelIdsString = String.join(",", channelIds);
        configManager.setProperty("stickyChannels", channelIdsString);
    }

    @SneakyThrows
    public static void loadStick() {

        System.out.println("loading stickys");

        var discord = jda.awaitReady();
        String channelIdsString = configManager.getProperty("stickyChannels");

        if (channelIdsString != null && !channelIdsString.isEmpty()) {
            String[] channelPairs = channelIdsString.split(",");

            for (String pair : channelPairs) {
                String[] parts = pair.split(":");
                if (parts.length == 2) {
                    String channelId = parts[0];
                    boolean isSticky = Boolean.parseBoolean(parts[1]);
                    stickyChannel.put(discord.getChannelById(MessageChannel.class, channelId), isSticky);

                }
            }
        }
    }
}
