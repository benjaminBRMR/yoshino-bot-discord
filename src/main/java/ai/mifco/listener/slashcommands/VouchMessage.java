package ai.mifco.listener.slashcommands;

import ai.mifco.Bot;
import ai.mifco.manager.ConfigManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;

public class VouchMessage extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (event.getName().equals("vouch")) {

            OptionMapping rating = event.getOption("rating");
            OptionMapping feedback = event.getOption("feedback");
            OptionMapping proof = event.getOption("proof");
            ConfigManager configManager = new ConfigManager();

            if (event.getMember().getRoles().contains(event.getGuild().getRoleById(Bot.configManager.getProperty("CUSTOMER_ROLE_ID")))) {

                if (rating == null || feedback == null || proof == null) {
                    event.reply("Füll alles aus du monkey").setEphemeral(true).queue();
                    return;
                }

                if (rating.getAsDouble() < 0D || rating.getAsDouble() > 5D) {
                    event.reply("1-5").setEphemeral(true).queue();
                    return;
                }

                if (feedback.getAsString().length() > 500) {
                    event.reply("Feedback zu lange").setEphemeral(true).queue();
                    return;
                }

                if (!proof.getAsAttachment().isImage()) {
                    event.reply("Ungültiges Bild").setEphemeral(true).queue();
                    return;
                }

                StringBuilder stars = new StringBuilder();
                for (int i = 0; i < rating.getAsDouble(); i++) {
                    stars.append(Emoji.fromFormatted(Bot.configManager.getProperty("VOUCH_STAR_EMOJI")).getFormatted());
                }

                EmbedBuilder embedBuilder = new EmbedBuilder();
                embedBuilder.setTitle("Vouch (#" + configManager.getProperty("VOUCHES") + ")");
                embedBuilder.setImage(proof.getAsAttachment().getUrl());
                embedBuilder.setAuthor(event.getMember().getEffectiveName() + " " + new SimpleDateFormat("dd.MM.yy HH:mm").format(new Date()), null, event.getMember().getEffectiveAvatarUrl());
                embedBuilder.setDescription(stars + "\n" + feedback.getAsString());
                event.replyEmbeds(embedBuilder.build()).queue();
                configManager.setProperty("VOUCHES", String.valueOf((Integer.parseInt(configManager.getProperty("VOUCHES")) + 1)));


            } else {
                event.reply("You can only use this command with customer role.\nPlease contact an admin or owner of YOSHINO Selling.").setEphemeral(true).queue();

            }
        }
    }
}
