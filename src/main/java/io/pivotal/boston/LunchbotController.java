package io.pivotal.boston;

import me.ramswaroop.jbot.core.slack.Bot;
import me.ramswaroop.jbot.core.slack.Controller;
import me.ramswaroop.jbot.core.slack.EventType;
import me.ramswaroop.jbot.core.slack.SlackService;
import me.ramswaroop.jbot.core.slack.models.Event;
import me.ramswaroop.jbot.core.slack.models.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

@Component
public class LunchbotController extends Bot {

    private SlackReminderStore slackReminderStore;

    @Autowired
    public LunchbotController (SlackReminderStore slackReminderStore) {
        super();
        this.slackReminderStore = slackReminderStore;
    }

    @Value("${slackBotToken}")
    private String slackToken;

    @Override
    public String getSlackToken() {
        return slackToken;
    }

    @Override
    public Bot getSlackBot() {
        return this;
    }

    @Controller(events = {EventType.DIRECT_MENTION, EventType.DIRECT_MESSAGE})
    public void onReceiveDM(WebSocketSession session, Event event) {

        String lunchTitle = event.getText();

        this.slackReminderStore.addReminder(new SlackReminder(lunchTitle));

        String messageText = "I will remind you tomorrow at 9AM about '"+ lunchTitle + "'";
        reply(session, event, new Message(messageText));
    }
}
