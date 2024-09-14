import java.util.SortedSet;
import java.util.TreeSet;

record Event(int start, int end) implements Comparable<Event> {
    @Override
    public int compareTo(Event that) {
        return this.start - that.start;
    }
}

class MyCalendar {
    SortedSet<Event> events;

    public MyCalendar() {
        this.events = new TreeSet<>();
    }
    
    public boolean book(int start, int end) {
        for(var event : this.events) {
            if (event.end() > start && end > event.start()) {
                return false;
            }
        }

        return events.add(new Event(start, end));
    }
}

/**
 * Your MyCalendar object will be instantiated and called as such:
 * MyCalendar obj = new MyCalendar();
 * boolean param_1 = obj.book(start,end);
 */