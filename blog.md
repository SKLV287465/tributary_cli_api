# Preliminary Design
# Video Link:
https://youtu.be/uvkeiwWPy8A
## Analysis of Engineering Requirements

**Scope**
The scope of this project is to create a java API, which would help another Java developer to implement an event driven solution. However, instead of just
dealing with a single stream of interactions, this API should allow for the developer to deal with multiple channels.

**Requirement Review**
_Structure_

- Producers - responsible for sending messages to the tributary system
- Tributary Cluster - grouping of topics
- Topic - events grouped together logically
- Partition - queue where new messages are appended at the end of the partition
- Message - header-key-value container to be send around.
- Consumer Group - grouping of Consumers
- Consumer - responsible for consuming (processing) messages. Many partitions to 1 Consumer.

_Behaviour_

- Random Producers - the producer requests the Tributary system to randomly assign a message to a partition
- Manual Producers - the producer requests the Tributary system to assign a message to a particular partition by providing its corresponding key.
- 1 topic for 1 Consumer
- all consumers that share a partition consume messages parallel to each other, so that each message is only consumed once (except in controlled replays)

- able to dynamically change the rebalancing strategy between one of two rebalancing strategies - range rebalancing, and round robin rebalancing
- Range - The partitions are divided up evenly and allocated to the consumers. If there is an odd number of partitions, the first consumer takes one extra.
- Round Robin - In a round robin fashion, the partitions are allocated like cards being dealt out, where consumers take turns being allocated the next partition.
- ability to replay messages that are stored in the queue

_Considerations_

- Concurrency - all producers/consumers run in parallel
- Generics - Ensure that all messages can be received/sent

_Restrictions_

- No useage of existing synchronization and concurrency libraries

_Assumptions_

-

_Folders_

- core - backend implementation (blackbox)
- api - what developers have access to when using this api
- tributary - TributaryCLI.java - wrappers for testing

## List of Usability Tests

_Test 1_

1. create a single producer
2. create a single topic
3. create a single parititon for the topic
4. create a single consumer group
5. create a single consumer for the consumer group, such that it recieves messages from the single partition
6. produce a message
7. consume the message
8. play the message back

_Test 2_

1. create multiple produces
2. create multiple topics
3. create multiple partition for each topic
4. create multiple partition for each topic
5. create multiple consumer groups for each topic
6. create a single consumer for each partition
7. produce a single message using random allocation, and repeat this multiple times to check randomness
8. parallel consume the messages
9. replay for each consumer to check that randomness is there.

_Test 3_

1. create multiple produces
2. create multiple topics
3. create multiple partition for each topic
4. create multiple partition for each topic
5. create multiple consumer groups for each topic
6. create a single consumer for each partition
7. produce a single message using manual allocation, and repeat this multiple times to check randomness
8. parallel consume the messages
9. replay for each consumer to check that manual allocation works.

_Test 4_

1. create multiple produces
2. create multiple topics
3. create multiple partition for each topic
4. create multiple consumer groups for each topic
5. create multiple consumers for each consumer group
6. produce messages in parallel, with each message being of different value types.
7. consume messages in parallel for each consumer.

## Initial UML Diagram

/home/sklave/assignment-iii/preliminaryUml.pdf

## Design for Java API

- API will not be a static class as the user may want to have multiple instances of the system.
- public, static, final ENUMS will be used to decided options for any methods calls.
- API will consist of whitebox methods which can be called with appropriate parameters directed by the javadocs.

## Testing Plan

Feature driven approach:

1. For each feature we implement, we first write blackbox unit tests to test that they work
2. We will then write integration tests for the feature alongside already implemented features to check that correct integration is reached
3. After all the necessary features are implemented, we will write usability tests that test that users can use the program as intended and that the API functions as required.

## Implementation

We have decided to take on an feature driven approach.
This is due to the following reasons:

- We will be working as a pair, since we have ready contact with each other, it is possible to mitigate the harder teamwork
- The base of this project can be completed very quickly by creating stubs upon which a feature driven approach would be efficient to progress.
- Testing would be rather difficult with a component driven approach as this program's components are by nature highly coupled and would this require functionality of other components before unit testing is possible.

# Tributary Implementation

# Final Design
# Video Link:
https://youtu.be/uvkeiwWPy8A