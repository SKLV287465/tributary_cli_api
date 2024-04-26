# Test 1: Simple creation of entities

create topic topic1 Integer
create topic topic2 String
create partition topic1 partition1
create partition topic2 partition2
create consumer group consumerGroup1 topic1 Range
create consumer group consumerGroup2 topic1 RoundRobin
create consumer consumerGroup1 consumer1
create consumer consumerGroup1 consumer2
create consumer consumerGroup2 consumer3
create consumer consumerGroup2 consumer4
delete consumer consumer2
delete consumer consumer3
create producer producer1 String Random
create producer producer2 Integer Manual
create producer producer3 String Manual
create producer producer4 Integer Random

# Test 2: Producing Events

create producer producer1 String Random
create topic topic1 String
create partition topic1 partition1
produce event producer1 topic1 src/test/json/eventFiles/StringEvent1.json
create producer producer2 Integer Manual
create topic topic2 Integer
create partition topic2 partition2

produce event producer2 topic2 src/test/json/eventFiles/IntEvent1.json partition2

# Test 3: Consuming Events

create topic topic1 Integer
create producer producer1 String Random
create partition topic1 partition1
produce event producer1 topic1 src/test/json/eventFiles/StringEvent1.json
produce event producer1 topic1 src/test/json/eventFiles/StringEvent2.json
create consumer group consumerGroup1 topic1 Range
create consumer consumerGroup1 consumer1
create consumer consumerGroup1 consumer2
consume event consumer1 partition1
consume event consumer2 partition1

# Test 4: Consuming Multiple Events

create topic topic1 Integer
create producer producer1 String Random
create partition topic1 partition1
produce event producer1 topic1 src/test/json/eventFiles/StringEvent1.json
produce event producer1 topic1 src/test/json/eventFiles/StringEvent2.json
create consumer group consumerGroup1 topic1 Range
create consumer consumerGroup1 consumer1
create consumer consumerGroup1 consumer2
consume events consumer1 partition1 2

# Test 5: Show Topic and Show Consumer Group

create topic topic1 Integer
create partition topic1 partition1
create partition topic1 partition2
show topic topic1
create consumer group consumerGroup1 topic1 Range
create consumer consumerGroup1 consumer1
create consumer consumerGroup1 consumer2
create consumer consumerGroup1 consumer3
show consumer group consumerGroup1

# Test 6: Parallel Production and Consumption + Playback

create producer producer1 String Random
create producer producer2 String Random
create producer producer3 Integer Random
create producer producer4 Integer Random
create topic topic1 String
create topic topic2 Integer
create topic topic3 Integer
create consumer group CG1 topic1 Range
create consumer group CG2 topic1 RoundRobin
create consumer group CG3 topic2 Range
create consumer group CG4 topic2 RoundRobin
create consumer group CG5 topic3 Range
create partition topic1 partition1
create partition topic2 partition2
create partition topic3 partition3
create consumer CG1 consumer1
create consumer CG1 consumer2
create consumer CG2 consumer3
create consumer CG3 consumer4
create consumer CG4 consumer5
create consumer CG5 consumer6
create consumer CG1 consumer7
create consumer CG2 consumer8
create consumer CG3 consumer9
parallel produce producer3 topic2 src/test/json/eventFiles/IntEvent1.json producer4 topic2 src/test/json/eventFiles/IntEvent2.json producer3 topic2 src/test/json/eventFiles/IntEvent3.json producer4 topic3 src/test/json/eventFiles/IntEvent4.json producer3 topic2 src/test/json/eventFiles/IntEvent5.json producer4 topic3 src/test/json/eventFiles/IntEvent6.json producer3 topic2 src/test/json/eventFiles/IntEvent7.json producer4 topic3 src/test/json/eventFiles/IntEvent8.json producer3 topic2 src/test/json/eventFiles/IntEvent9.json producer4 topic3 src/test/json/eventFiles/IntEvent10.json producer1 topic1 src/test/json/eventFiles/StringEvent1.json producer2 topic1 src/test/json/eventFiles/StringEvent2.json producer1 topic1 src/test/json/eventFiles/StringEvent3.json producer2 topic1 src/test/json/eventFiles/StringEvent4.json producer1 topic1 src/test/json/eventFiles/StringEvent5.json producer2 topic1 src/test/json/eventFiles/StringEvent6.json producer1 topic1 src/test/json/eventFiles/StringEvent7.json
parallel produce producer2 topic1 src/test/json/eventFiles/StringEvent8.json producer1 topic1 src/test/json/eventFiles/StringEvent9.json producer2 topic1 src/test/json/eventFiles/StringEvent10.json
parallel consume consumer4 partition2 consumer6 partition3 consumer5 partition2 consumer6 partitition3 consumer9 partition2 consumer6 partition3 consumer4 partition2 consumer6 partition3 consumer5 partition2 consumer6 partition3 consumer1 partition1 consumer2 partition1 consumer3 partition1 consumer7 partition1 consumer8 partition1 consumer1 partition1 consumer2 partition1 consumer3 partition1 consumer7 partition1 consumer8 partition1
playback consumer2 partition1 0
playback consumer6 partition3 0
playback consumer9 partition2 0
playback consumer1 partition1 0

# Test 8: Setting consumer group rebalancing Round Robin

create producer producer1 String Manual
create topic topic1 String
create consumer group CG1 topic1 Range
create partition topic1 partition1
create partition topic1 partition2
create partition topic1 partition3
create partition topic1 partition4
create consumer CG1 consumer1
create consumer CG1 consumer2
produce event producer1 topic1 src/test/json/eventFiles/StringEvent1.json partition1
produce event producer1 topic1 src/test/json/eventFiles/StringEvent2.json partition2
produce event producer1 topic1 src/test/json/eventFiles/StringEvent3.json partition3
produce event producer1 topic1 src/test/json/eventFiles/StringEvent4.json partition4
consume event consumer1 partition1
consume event consumer1 partition2
consume event consumer2 partition3
consume event consumer2 partition4
playback consumer1 partiti
on2 0

# Test 9: Setting consumer group rebalancing Range

create producer producer1 String Manual
create topic topic1 String
create consumer group CG1 topic1 RoundRobin
create partition topic1 partition1
create partition topic1 partition2
create partition topic1 partition3
create partition topic1 partition4
create consumer CG1 consumer1
create consumer CG1 consumer2
produce event producer1 topic1 src/test/json/eventFiles/StringEvent1.json partition1
produce event producer1 topic1 src/test/json/eventFiles/StringEvent2.json partition2
produce event producer1 topic1 src/test/json/eventFiles/StringEvent3.json partition3
produce event producer1 topic1 src/test/json/eventFiles/StringEvent4.json partition4
consume event consumer1 partition1
consume event consumer1 partition3
consume event consumer2 partition2
consume event consumer2 partition4
playback consumer1 partition3 0