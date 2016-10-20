package io.pivotal.boston;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SlackReminderStoreTests {
    @Test
    public void it_should_store_and_retrieve_a_reminder(){
        SlackReminder reminder = new SlackReminder("lorem ipsum");

        SlackReminderStore sut = new SlackReminderStore();
        sut.addReminder(reminder);

        assertThat(sut.getTodayRemindersCount()).isEqualTo(1);
        assertHasExpectedReminders(reminder, sut, true);

        assertThat(sut.getTodayRemindersCount()).isEqualTo(0);
        assertHasExpectedReminders(reminder, sut, false);
    }

    private void assertHasExpectedReminders(SlackReminder reminder, SlackReminderStore sut, boolean expectedGotSomething) {
        Iterable<SlackReminder> retrievedReminders = sut.pullTodayReminders();

        boolean gotSomething = false;

        for (SlackReminder r : retrievedReminders ) {
            gotSomething = true;
            assertThat(r.getLunchName()).isEqualTo(reminder.getLunchName());
        }

        assertThat(gotSomething).isEqualTo(expectedGotSomething);
    }
}
