### Final Testing Plan and Usability Tests
Ultimately, we decided on a variety of testing methods.
1. Firstly, we would do Integration tests, which include tests for the api.
    (a) Creation of entities
    (b) Deletion of entities
    (c) Lifecycle test - testing consumption and production of messages and such
2. And we also created some usability tests, which test our command line interface with out program, and the corresponding output messages

These two methods of testsing in conjunction with each other ensure that both the implementation in our Core works as it should, all the logic and interactions between classes is correct and data is processed and stored correctly. And then our usability tests ensure that our CLI works as intended, that the correct corresponding commands correlate with the correct functionality in the backend of the API.
To finalise and to add another layer of testing, we also wrote a department store simulation, wherein we setup a simulation which has a wholeSaler and some stores, along with some topics, that get run through and tested with parallel consumption and production, which in essence also tests individual production and consumption as our parallel methods call our individual methods. It also tests playback, showing the history of purchases, and has a profit counter too which ensures that items being produced and consumed have an effect.

### Overview of Design Patterns
TributaryCLI.java
- Here, we used a Command Pattern to implement how our CLI handles different commands. Each command is encapsulated as a method.

API.java
- In this class, a Facade pattern is used to abstract the logic away from the API class. There is plenty of javaDoc for the user to read and understand what the API does, but the underlying logic is hidden in the Core class.

deserializers
- Here, we used a strategy pattern to help abide by the open/closed principle. We wrote the interface ValueDeserializer, and now if support for any other deserializers wants to be added, a new class like IntegerDeserializer or StringDeserializer can be added to the deserializers folder!

rebalancingStrategy
- Here, very similar to deserialzers, we implemented a Strategy pattern. For now, we have implememnted RangeStrategy and RoundRobinStrategy, but this design is open for extension!

Event.java
- Here, we used Generic Programming. This allows the Event class to be flexible and type safe, handling different types of value attributes without needing to know what they are. This directly works with our deserializer Strategy Pattern.

### How we accommodated for the design considerations
There were two main design considerations to keep in mind for this task:
- Concurrency
- Generics

Concurrency:
We implemented concurrency by using threads in Java. In our parallelProduce and parallelConsume methods, we recursively call threads until the production or consumption is done, resulting in our best implementation of concurrency!

Generics:
For the Event class, we implemented Generics, as the Event class should be able to hold a value of any type. This means that our code is extendable, and the only things that need to be added to support new types is a deserializer - to convert from the data in JSON to the actual type stored in the Event class, and this implementation is closed for modification, but open to extension

### Brief reflection on the assignment including adapting to challenges
The beginning of this assignment was quite simple. The creation and deletion of entities was an easy task to implement, as it was just creating classes, and storing instances in lists of parent classes. But when it came to consumption, production and the message/event class, things got a lot tricker. We had to truly delve into what consumption and production entailed. How did they work? What action happens when an event is produced or when it is consumed? We had to think this through, then implement the Event class accordingly, parsing data from JSON, then figuring out how to translate that data into a class stored as a generic. We came up with the idea of Deserializers. For the two examples that we were required to support, Strings and Integers, writing deserializers was easy. It also supported an open/closed approach, meaning that to support new types, only extension is necessary.
Our CLI was tricky at first, we didn't really understand how to implement it, but then after some research, we discovered System.in, and learned how to write a command pattern to handle text input and delegate it to the core's methods.
Overall, we feel like we learned a lot, especially about the creation of an API. How it deals with input, produces output, and functions on the inside. We also learned how to test it and ensure that it produces the correct output from a given input.

# Video Link
https://youtu.be/uvkeiwWPy8A