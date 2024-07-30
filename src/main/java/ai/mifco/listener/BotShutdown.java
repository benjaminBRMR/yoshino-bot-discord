package ai.mifco.listener;

import ai.mifco.Bot;
import net.dv8tion.jda.api.events.session.ShutdownEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

/**
 * functionality written by sebvstian.
 * BotShutdown > written on 09.01.2024
 */
public class BotShutdown extends ListenerAdapter {

    @Override
    public void onShutdown(@NotNull ShutdownEvent event) {
        Bot.saveSticky();
    }
}
