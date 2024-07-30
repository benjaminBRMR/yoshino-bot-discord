package ai.mifco.listener;

import ai.mifco.Bot;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class MessageReceiver extends ListenerAdapter {


    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (event.getMessage().getChannelId().equals(Bot.configManager.getProperty("VOUCH_CHANNEL_ID"))) {

            if (event.getAuthor().isBot()) return;
            if (!Bot.stickyChannel.getOrDefault(event.getChannel(), false)) return;


            for (var message : event.getChannel().asTextChannel().getIterableHistory()) {
                if (message.getAuthor().isBot()) {
                    message.delete().queue();
                }
            }
            event.getChannel().sendMessageEmbeds(Bot.stickyManager.getVouchTemplate(event.getChannel()).build()).queue();
        }
    }
}
