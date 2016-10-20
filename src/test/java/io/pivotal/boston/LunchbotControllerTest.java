package io.pivotal.boston;

import me.ramswaroop.jbot.core.slack.EventType;
import me.ramswaroop.jbot.core.slack.models.Event;
import me.ramswaroop.jbot.core.slack.models.Message;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class LunchbotControllerTest {
    final String lunchTitle = "pivotal lunch";

    WebSocketSession mockSession;
    SlackReminderStore mockReminderStore;
    Event directMessageEvent;
    LunchbotController sut;


    @Before
    public void setUp() {

        this.mockSession = mock(WebSocketSession.class);
        this.mockReminderStore = mock(SlackReminderStore.class);

        this.directMessageEvent = new Event();
        this.directMessageEvent.setText(this.lunchTitle);

        this.sut = new LunchbotController(this.mockReminderStore);
    }


    @Test
    public void it_responds_with_a_message_when_a_direct_message_is_received() throws Exception {
        sut.onReceiveDM(this.mockSession, this.directMessageEvent);

        ArgumentCaptor<TextMessage> textMessageCaptor = ArgumentCaptor.forClass(TextMessage.class);
        verify(this.mockSession).sendMessage(textMessageCaptor.capture());
        TextMessage capturedTextMessage = textMessageCaptor.getValue();

        String sentMsgText = "I will remind you tomorrow at 9AM about '" + this.lunchTitle + "'";
        assertThat(capturedTextMessage.getPayload()).contains(sentMsgText);
    }

    @Test
    public void it_should_store_a_new_slack_reminder_when_a_direct_message_is_received() throws Exception {
        sut.onReceiveDM(this.mockSession, this.directMessageEvent);

        ArgumentCaptor<SlackReminder> slackReminderCaptor = ArgumentCaptor.forClass(SlackReminder.class);
        verify(this.mockReminderStore).addReminder(slackReminderCaptor.capture());
        SlackReminder capturedSlackReminder = slackReminderCaptor.getValue();

        assertThat(capturedSlackReminder.getLunchName()).isEqualTo(this.lunchTitle);
    }
}
