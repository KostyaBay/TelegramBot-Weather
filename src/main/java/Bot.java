import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Bot extends TelegramLongPollingBot {
    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new Bot());

        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
    }

    public void sendMsg(Message message, String text) {
        SendMessage sendMessage = new SendMessage(); //initialization for send message
        sendMessage.enableMarkdown(false); //enable markup capability
        sendMessage.setChatId(message.getChatId().toString()); //determining who to answer
        sendMessage.setReplyToMessageId(message.getMessageId()); //determining which message to reply to
        sendMessage.setText(text); //text which we send to Bot
        try {
            setButtons(sendMessage); //buttons
            execute(sendMessage); //sending a message (message with answer)
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        Model model = new Model(); //model who keeping the data
        Message message = update.getMessage(); //initialization the message
        //check the message, if it not empty - answer
        if (message != null && message.hasText()){
            switch (message.getText()){
                case "/help":
                    sendMsg(message, "How can l help you?");
                    break;
                case "/setting":
                    sendMsg(message, "What are we going to configure?");
                    break;
//                case "/start":
//                    sendMsg(message, "Hello! I'm the WeatherBot. I show to you weather of the different city in the world! For help in use me click on /help");
//                    break;
                default:
                    try{
                        sendMsg(message, Weather.getWeather(message.getText(), model));
                    } catch (IOException e) {
                        sendMsg(message, "The entered city not found. Try again.. "); //exception
                    }
            }
        }
    }

    public void setButtons(SendMessage sendMessage) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(); //initialization keyboard
        sendMessage.setReplyMarkup(replyKeyboardMarkup); //setting markup
        replyKeyboardMarkup.setSelective(true); //link message with keyboard and keyboard output to specific users/all users
        replyKeyboardMarkup.setResizeKeyboard(true); //setting keyboard for the number of buttons (keyboard zoom in/zoom out)
        replyKeyboardMarkup.setOneTimeKeyboard(false); //hide keyboard after use or not

        List <KeyboardRow> keyboardRowList = new ArrayList<>(); //create keyboard button list
        KeyboardRow keyboardFirstRow = new KeyboardRow(); //initialization first line of keyboard

        //added existing buttons "/help" and "/setting" in KeyboardFirstRow
        //keyboardFirstRow.add(new KeyboardButton("/start"));
        keyboardFirstRow.add(new KeyboardButton("/help"));
        keyboardFirstRow.add(new KeyboardButton("/setting"));



        keyboardRowList.add(keyboardFirstRow); //added all lines of keyboard in the list
        replyKeyboardMarkup.setKeyboard(keyboardRowList); //install list to keyboard
    }

    @Override
    public String getBotUsername() {
        return "shop_ua_ru_bot";
    }

    @Override
    public String getBotToken() {

        return "1280489025:AAE11_FRii-0GfUmoEG2T1htfoGlmyisHqw";
    }
}
