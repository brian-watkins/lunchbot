package io.pivotal.boston;

public class SlackReminder {
    private String lunchName;

    public SlackReminder(String lunchName){
        this.lunchName = lunchName;
    }

    public String getLunchName() {
        return lunchName;
    }
}
