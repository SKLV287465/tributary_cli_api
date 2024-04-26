package tributary.core;

import java.util.ArrayList;
import java.util.List;

public class Partition {
    private String id;
    private int offset = 0;
    private List<Event<?>> events = new ArrayList<Event<?>>();
    private ArrayList<String> consumeLog = new ArrayList<>();
    private Consumer consumer;

    public void setConsumer(Consumer consumer) {
        this.consumer = consumer;
    }

    public Partition(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void addEvent(Event<?> event) {
        System.out.println("Produced Event \'" + event.getId() + "\' stored in Partition \'" + id + "\'.");
        events.add(event);
    }

    public List<Event<?>> getEvents() {
        return events;
    }

    public void consume(String consumerId) {
        consumeLog.add(consumerId);
        offset++;
    }

    public void playback(String consumerId, String offset) {
        for (int i = Integer.parseInt(offset); i < consumeLog.size(); i++) {
            if (consumeLog.get(i).equals(consumerId)) {
                System.out.println("\'" + events.get(i).getId() + "\'" + "at offset " + i + ".");
            }
        }
    }

    public String getInfo() {
        String result = "\nPartitionId: " + id + "\nCurrent offset: " + offset + "\nEvents:\n";

        for (Event<?> event : events) {
            result += "\nID: " + event.getId();
            result += "Datetime created: " + event.getDateTimeString();
            result += "\nPayload type: " + event.getPayloadType();
            result += "\nKey: " + event.getKey();
            result += "\nValue: " + event.getValue();
            result += "\n\n";
        }

        return result;
    }

    // for testing

    public String getStatus() {
        String status = Integer.toString(offset);
        for (int i = 0; i < events.size(); i++) {
            if (i >= consumeLog.size()) {
                status += "(" + events.get(i).getId() + ":" + "not consumed" + ")";
            } else {
                status += "(" + events.get(i).getId() + ":" + consumeLog.get(i) + ")";
            }

        }
        consumer.getId();
        return status;
    }
}
