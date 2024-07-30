package ai.mifco.manager;

import ai.mifco.Bot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.Channel;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class StickyManager {

    public EmbedBuilder getStickyEmbed(@NotNull Channel channel) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("Sticky Message");
        embedBuilder.setDescription("**DESCRIPTION**\nThe sticky message ensures that a message is always displayed at the bottom and does not get lost in the chat.\n\n**STATUS**\nThe sticky message is now " + (Bot.stickyChannel.getOrDefault(channel, false) ? "activated" : "deactivated") + ".");
        embedBuilder.setFooter("© Made by mifco with <3");
        embedBuilder.setImage("https://giffiles.alphacoders.com/220/220069.gif");
        embedBuilder.setColor(new Color(0, 0, 0));
        return embedBuilder;
    }

    /*
    Wird gesendet wenn man nachricht schreibt und sticky aktiviert ist :-)
     */
    public EmbedBuilder getVouchTemplate(@NotNull Channel channel) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("Sticky Message");
        embedBuilder.setDescription("**DESCRIPTION**\nThe sticky message ensures that a message is always displayed at the bottom and does not get lost in the chat.\n\n**STATUS**\nThe sticky message is now " + (Bot.stickyChannel.getOrDefault(channel, false) ? "activated" : "deactivated") + ".");
        embedBuilder.setFooter("© Made by mifco with <3");
        embedBuilder.setImage("https://giffiles.alphacoders.com/220/220069.gif");
        embedBuilder.setColor(new Color(0, 0, 0));
        return embedBuilder;
    }
}
