package base.concurrent;

public class Event implements Comparable<Event> {

    private String threadName;

    private int priority;

    public Event(String thread, int priority) {
        this.threadName = thread;
        this.priority = priority;
    }

    public String getThreadName() {
        return threadName;
    }

    public int getPriority() {
        return priority;
    }

    @Override
    public int compareTo(Event e) {
        return e.getPriority() - this.priority;
    }
}