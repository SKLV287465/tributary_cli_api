package tributary.core;

import org.json.JSONObject;

import tributary.core.allocationStrategy.AllocationStrategy;
import tributary.core.deserializers.IntegerDeserializer;
import tributary.core.deserializers.StringDeserializer;
import tributary.core.deserializers.ValueDeserializer;

public class Producer {
    private String id;
    private String type;
    private AllocationStrategy allocationStrategy;

    private ValueDeserializer<?> deserializer;

    public Producer(String id, String type, String allocationStrategy) {
        this.id = id;
        this.type = type;

        switch (type) {
        case "Integer":
            this.deserializer = new IntegerDeserializer();
            break;
        case "String":
            this.deserializer = new StringDeserializer();
            break;
        default:
            System.out.println("Have not implemented Types");
            System.exit(1);
        }

        switch (allocationStrategy) {
        case "Random":
            this.allocationStrategy = AllocationStrategy.RANDOM;
            break;
        case "Manual":
            this.allocationStrategy = AllocationStrategy.MANUAL;
            break;
        default:
            System.out.println("Allocation Strategy Input not valid");
            System.exit(1);
        }
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public AllocationStrategy getAllocationStrategy() {
        return allocationStrategy;
    }

    public void sendMessage(JSONObject messageObject, Topic topic) {
        Event<?> event = Event.fromJSONObject(messageObject, deserializer, id);
        if (!event.getKey().equals("")) {
            topic.addEvent(event, event.getKey());
            return;
        }
        topic.addEvent(event);
    }

    public void sendMessage(JSONObject messageObject, Topic topic, String partitionId) {
        Event<?> event = Event.fromJSONObject(messageObject, deserializer, id);
        topic.addEvent(event, partitionId);
    }

}
