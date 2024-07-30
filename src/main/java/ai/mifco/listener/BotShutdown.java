package ai.mifco.listener;

import ai.mifco.Bot;
import net.dv8tion.jda.api.events.session.ShutdownEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class BotShutdown extends ListenerAdapter {

    @Override
    public void onShutdown(@NotNull ShutdownEvent event) {
        Bot.saveSticky();
    }
}
