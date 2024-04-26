package tributary.core;

import org.json.JSONObject;

import tributary.core.deserializers.ValueDeserializer;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.io.IOException;

public class Event<T> {
    private String eventId;
    private String producerId;
    private String payloadType;
    private LocalDateTime created;
    private String key;
    private T value;
    private JSONObject jsonObject;

    public Event(String eventId, String producerId, String payloadType, LocalDateTime created, String key, T value,
            JSONObject jsonObject) {
        this.eventId = eventId;
        this.producerId = producerId;
        this.payloadType = payloadType;
        this.created = created;
        this.key = key;
        this.value = value;
        this.jsonObject = jsonObject;
    }

    /**
     * Converts a filename to a JSONObject.
     * <pre>
     * Preconditions:
     * - File must be in current directory.
     * @param filename
     * @return JSONObject
     */
    public static JSONObject filenameToJSONObject(String filename) {
        try {
            String fileContents = new String(Files.readAllBytes(Paths.get(filename)));
            return new JSONObject(fileContents);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> Event<T> fromJSONObject(JSONObject jsonObject, ValueDeserializer<T> deserializer,
            String producerId) {
        JSONObject headers = jsonObject.getJSONObject("Headers");
        String eventId = headers.optString("ID");
        String payloadType = headers.optString("Payload type");

        LocalDateTime created = LocalDateTime.ofInstant(
                Instant.ofEpochSecond(Long.parseLong(headers.optString("Datetime created"))), ZoneId.systemDefault());
        String key = jsonObject.optString("Key");
        T value = deserializer.deserialize(jsonObject.optString("Value"));

        return new Event<T>(eventId, producerId, payloadType, created, key, value, jsonObject);
    }

    public String getId() {
        return eventId;
    }

    public String getProducerId() {
        return producerId;
    }

    // public void setProducerId(String producerId) {
    //     this.producerId = producerId;
    // }

    public String getPayloadType() {
        return payloadType;
    }

    public String getDateTimeString() {
        return created.toString();
    }

    public String getKey() {
        return key;
    }

    public T getValue() {
        return value;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }
}
