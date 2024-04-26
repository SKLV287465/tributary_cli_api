package tributary.api;

import java.util.List;

import tributary.core.Core;
import tributary.core.CoreResponse;

/**
 * Represents  a stream of events, which can be produced and consumed concurrently.
 * Invariants:
 * - none.
 */
public class API {
    /**
     * The abstracted core of the program.
     */
    private Core core;

    /**
     * Constructor for the API class.
     */
    public API() {
        this.core = new Core();
    }

    /**
     * Creates a new topic.
     *<pre>
     * Preconditions:
     * - id must be unique from all other id`s
     * - type must be a valid type or class
     * Postconditions:
     * - a new Topic with the given id is created
     * </pre>
     *
     * @param id the new Topic's id.
     * @param type the type of Events which this topic handles.
     */
    public CoreResponse createTopic(String id, String type) {
        return core.createTopic(id, type);
    }

    /**
     * Creates a new partition
     * <pre>
     * Preconditions:
     * - id must be unique from all other id`s
     * - there must exist a Topic of topicId
     * Postconditions:
     * - a new Parition of the given id is added to the Topic with the topicId
     * </pre>
     * @param topicId id of the Topic which the created Partition will be added to.
     * @param id the id this partition's topic. There must exist a topic of this id.
     */
    public CoreResponse createPartition(String topicId, String id) {
        return core.createPartition(topicId, id);
    }

    /**
     * Creates a new consumer group.
     * <pre>
     * Preconditions:
     * - id must be unique from all other id`s.
     * - topicId must be of an existing topic.
     * - rebalancingStrategy must be valid.
     * Postconditions:
     * - a new ConsumerGroup subscribed to the given id will be created.
     * </pre>
     *
     * @param id of to be created ConsumerGroup.
     * @param topicId of Topic to be subscribed to.
     * @param rebalancingStrategy how partitions are to be dividided across consumers.
     */
    public CoreResponse createConsumerGroup(String id, String topicId, String rebalancingStrategy) {
        return core.createConsumerGroup(id, topicId, rebalancingStrategy);
    }

    /**
     * Creates a new Consumer.
     * <pre>
     * Preconditions:
     * - id must be unique from all other id`s
     * - there must exist a ConsumerGroup with consumerGroupId
     * Postconditions:
     * - a new Consumer will be created with id, added to the ConsumerGroup with consumerGroupId.
     * </pre>
     *
     * @param consumerGroupId of ConsumerGroup to be added to.
     * @param id of to be created Consumer.
     */
    public CoreResponse createConsumer(String consumerGroupId, String id) {
        return core.createConsumer(consumerGroupId, id);
    }

    /**
     * Deletes a Consumer.
     * <pre>
     * Preconditions:
     * - there must exist a Consumer of consumerId
     * Postconditions:
     * - the Consumer of consumerId is deleted
     * </pre>
     * @param consumerId of to be deleted Consumer.
     */
    public CoreResponse deleteConsumer(String consumerId) {
        return core.deleteConsumer(consumerId);
    }

    /**
     * Creates a new Producer.
     * <pre>
     * Preconditions:
     * - id must be unique from all other id`s
     * - type must be a valid type
     * - allocationStrategy must be a valid allocationStrategy
     * Postconditions:
     * - a Producer of given id and allocationStrategy and type is created.
     * </pre>
     * @param id of to be created Producer.
     * @param type of Events to be produced by Producer.
     * @param allocationStrategy of Producer, when producing Events.
     */
    public CoreResponse createProducer(String id, String type, String allocationStrategy) {
        return core.createProducer(id, type, allocationStrategy);
    }

    /**
     * Produces a new Event.
     * <pre>
     * Preconditions:
     * - there must exist a Producer of producerId
     * - there must exist a Topic of topicId
     * - there must exist a file of eventFilename
     * Postconditions:
     * - an Event composed of the file of eventFilename is produced by the Producer of producerId
     * </pre>
     * @param producerId of Producer that produces Event.
     * @param topicId of Topic which Event is allocated to.
     * @param eventFilename name of the file which is produced into Event.
     */
    public CoreResponse produceEvent(String producerId, String topicId, String eventFilename) {
        return core.produceEvent(producerId, topicId, eventFilename);
    }

    /**
     * Produces a new Event which is allocated specifically to given partitionId.
     * <pre>
     * Preconditions:
     * - there must exist a Producer of producerId
     * - there must exist a Topic of topicId
     * - there must exist a file of eventFilename
     * - there must exist a partition of partitionId
     * Postconditions:
     * - an Event composed of the file of eventFilename is produced by the Producer of
     *   producerId which is allocated to Partition of partitionId
     * </pre>
     * @param producerId
     * @param topicId
     * @param eventFilename
     * @param partitionId
     */
    public CoreResponse produceEvent(String producerId, String topicId, String eventFilename, String partitionId) {
        return core.produceEvent(producerId, topicId, eventFilename, partitionId);
    }

    /**
     * An Event in a particular Partition gets consumed by a particular Consumer.
     * <pre>
     * Preconditions:
     * - there must exist a Consumer of consumerId
     * - there must exist a Partition of partitionId
     * - the Partition of partitionId must possess unconsumed Events
     * Postconditions:
     * - An unconsumed Event is consumed by the Consumer.
     * @param consumerId of Consumer.
     * @param partitionId of Partition.
     */
    public CoreResponse consumeEvent(String consumerId, String partitionId) {
        return core.consumeEvent(consumerId, partitionId);
    }

    /**
     * An Event in a particular Partition gets consumed by a particular Consumer.
     * <pre>
     * Preconditions:
     * - there must exist a Consumer of consumerId
     * - there must exist a Partition of partitionId
     * - the Partition of partitionId must possess numberOfEvents of unconsumed Events or more
     * Postconditions:
     * - numberOfEvents unconsumed Events is consumed by the Consumer.
     * @param consumerId
     * @param partitionId
     * @param numberOfEvents
     */
    public CoreResponse consumeEvents(String consumerId, String partitionId, String numberOfEvents) {
        return core.consumeEvents(consumerId, partitionId, numberOfEvents);
    }

    /**
     * Prints content of Topic.
     * <pre>
     * Preconditions:
     * - There must exist a Topic of topicId
     * Postconditions:
     * - the contents of Topic of topicId is printed.
     * </pre>
     * @param topicId of to be printed Topic.
     */
    public CoreResponse showTopic(String topicId) {
        return core.showTopic(topicId);
    }

    /**
     * Prints contents of ConsumerGroup.
     * <pre>
     * Preconditions:
     * - there must exist a ConsumerGroup of consumerGroupId
     * Postconditions:
     * - the contents of ConsumerGroup of consumerGroupId is printed
     * @param consumerGroupId of to be printed ConsumerGroup.
     */
    public CoreResponse showConsumerGroup(String consumerGroupId) {
        return core.showConsumerGroup(consumerGroupId);
    }

    /**
     * Produces Events in parallel.
     * <pre>
     * Preconditions:
     * - there must exist Producers for each producerIds
     * - there must exist Topics for each topicIds
     * - there must exist files for each eventFilenames
     * - the types of the files must correspond to the Topic and Producer.
     * Postconditions:
     * - Events will be produced in parallel.
     * </pre>
     * @param ProducerIds of Producers.
     * @param topicIds of Topics.
     * @param eventFilesnames of files to be printed.
     */
    public CoreResponse parallelProduce(List<String> producerIds, List<String> topicIds, List<String> eventFilesnames) {
        return core.parallelProduce(producerIds, topicIds, eventFilesnames);
    }

    /**
     * Consumes Events in parallel.
     * <pre>
     * Preconditions:
     * - there must exist Consumers for each consumerIds
     * - there must exist Partitions for each partitionIds
     * - each Consumer must correspond to each Partition
     * - there must exist Events for each consumption
     * Postconditions:
     * Events will be consumed in parallel from the given Partitions by the given Consumers.
     * @param consumerIds of Consumers.
     * @param partitionIds of Partitions.
     */
    public CoreResponse parallelConsume(List<String> consumerIds, List<String> partitionIds) {
        return core.parallelConsume(consumerIds, partitionIds);
    }

    /**
     * Sets the rebalancing strategy of the ConsumerGroup.
     * <pre>
     * Preconditions:
     * - there must exist a ConsumerGroup of given consumerGroupId
     * - there must exist a valid rebalancingStrategy of rebalancingStrategy
     * Postconditions:
     * - The rebalancing strategy of the ConsumerGroup will be set to the given RebalancingStrategy.
     * @param consumerGroupId of ConsumerGroup to be changed.
     * @param rebalancingStrategy String describing a valid RebalancingStrategy.
     */
    public CoreResponse setConsumerGroupRebalancing(String consumerGroupId, String rebalancingStrategy) {
        return core.setConsumerGroupRebalancing(consumerGroupId, rebalancingStrategy);
    }

    /**
     * Replays the previous consumptions.
     * <pre>
     * Preconditions:
     * - there must exist a Consumer of given consumerId
     * - there must exist a Partition of given partitionId
     * - the offset must be valid
     * - the Consumer must correspond to the Partition
     * Postconditions:
     * - The previous Consumptions from the given offset will be printed.
     * @param consumerId of Consumer.
     * @param partitionId of Partition.
     * @param offset of Consumed Events.
     */
    public CoreResponse playback(String consumerId, String partitionId, String offset) {
        return core.playback(consumerId, partitionId, offset);
    }
}
