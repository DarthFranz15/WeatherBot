package com.telegram.bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.io.InputStream;
import java.util.Properties;

public class WeatherBot extends TelegramLongPollingBot {
	private static final String BOT_TOKEN;

    static {
        Properties props = new Properties();
        try (InputStream input = WeatherBot.class.getClassLoader().getResourceAsStream("config.properties")) {
            props.load(input);
            BOT_TOKEN = props.getProperty("telegram.bot.token");
        } catch (Exception e) {
            throw new RuntimeException("Could not load bot token from config.properties", e);
        }
    }

    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public String getBotUsername() {
        return "YourWeatherBuddyBot"; // <-- sostituisci con lo username del tuo bot
    }


    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            String replyText;

            switch (messageText) {
                case "/start":
                    replyText = "Benvenuto! Sono WeatherBot+. Scrivi /help per vedere cosa posso fare.";
                    break;
                case "/help":
                    replyText = "Comandi disponibili:\n/start – Messaggio di benvenuto\n/help – Elenco comandi";
                    break;
                default:
                    replyText = "Comando non riconosciuto. Scrivi /help per vedere i comandi disponibili.";
            }

            SendMessage message = new SendMessage(String.valueOf(chatId), replyText);
            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }
}
