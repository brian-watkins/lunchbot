package io.pivotal.boston;

import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class SlackReminderStore {
    private int todayRemindersCount;
    private ArrayList<SlackReminder> todayReminders = new ArrayList<>();

    public void addReminder(SlackReminder reminder) {
        this.todayReminders.add(reminder);
        this.todayRemindersCount++;
    }

    public int getTodayRemindersCount() {
        return todayRemindersCount;
    }

    public Iterable<SlackReminder> pullTodayReminders() {
        ArrayList<SlackReminder> reminders = new ArrayList<>(this.todayReminders);
        this.todayReminders.clear();
        this.todayRemindersCount = 0;
        return reminders;
    }
}
