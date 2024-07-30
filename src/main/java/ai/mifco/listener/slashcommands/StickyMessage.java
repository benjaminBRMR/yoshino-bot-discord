package ai.mifco.listener.slashcommands;

import ai.mifco.Bot;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.jetbrains.annotations.NotNull;

public class StickyMessage extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (event.getName().equals("sticky")) {
            if (event.getMember() != null && event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
                if (event.getChannelId().equals(Bot.configManager.getProperty("VOUCH_CHANNEL_ID"))) {
                    Bot.stickyChannel.put(event.getChannel(), !Bot.stickyChannel.getOrDefault(event.getChannel(), false));

                    event.replyEmbeds(Bot.stickyManager.getStickyEmbed(event.getChannel()).build()).addActionRow(
                            Button.danger("delete-sticky", "Click to delete")
                                    .withEmoji(Emoji.fromFormatted(Bot.configManager.getProperty("DELETE_EMOJI")))
                    ).queue();
                    Bot.stickyMessage.put(event.getChannel(), event.getChannel().getLatestMessageId());

                    for (Message message : event.getChannel().asTextChannel().getIterableHistory()) {
                        if (message.getAuthor().isBot()) {
                            message.delete().queue();
                        }
                    }

                    Bot.saveSticky();

                } else {
                    event.reply("This command can only be executed in <#" + Bot.configManager.getProperty("VOUCH_CHANNEL_ID") + ">.").setEphemeral(true).queue();
                }
            } else {
                event.reply("You can only use this command with administrator permissions.\nPlease contact an admin or owner of YOSHINO Selling.").setEphemeral(true).queue();
            }
        }
    }


}
