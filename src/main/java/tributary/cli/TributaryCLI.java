package tributary.cli;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tributary.api.API;

public class TributaryCLI {
    private static API api = new API();

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        while (true) {
            String input = scanner.nextLine();
            String[] arguments = input.split(" ");
            if (arguments[0].equals("quit")) {
                break;
            }
            executeCommand(arguments);
        }

        scanner.close();
    }

    private static void executeCommand(String[] arguments) {
        String[] nextArguments = Arrays.copyOfRange(arguments, 1, arguments.length);

        switch (arguments[0]) {
        case "create":
            handleCreateCommand(nextArguments);
            break;
        case "delete":
            handleDeleteCommand(nextArguments);
            break;
        case "produce":
            handleProduceCommand(nextArguments);
            break;
        case "consume":
            handleConsumeCommand(nextArguments);
            break;
        case "show":
            handleShowCommand(nextArguments);
            break;
        case "parallel":
            handleParallelCommand(nextArguments);
            break;
        case "set":
            handleSetCommand(nextArguments);
            break;
        case "playback":
            handlePlayback(nextArguments);
            break;
        default:
            printUnknownCommand();
        }
    }

    // CREATE
    private static void handleCreateCommand(String[] arguments) {
        String[] nextArguments = Arrays.copyOfRange(arguments, 1, arguments.length);

        switch (arguments[0]) {
        case "topic":
            handleCreateTopic(nextArguments);
            break;
        case "partition":
            handleCreatePartition(nextArguments);
            break;
        case "consumer":
            handleCreateConsumerCommand(nextArguments);
            break;
        case "producer":
            handleCreateProducer(nextArguments);
            break;
        default:
            printUnknownCommand();
        }
    }

    private static void handleCreateTopic(String[] arguments) {
        if (arguments.length == 2) {
            api.createTopic(arguments[0], arguments[1]);
        } else {
            printUnknownCommand();
        }
    }

    private static void handleCreatePartition(String[] arguments) {
        if (arguments.length == 2) {
            api.createPartition(arguments[0], arguments[1]);
        } else {
            printUnknownCommand();
        }
    }

    private static void handleCreateConsumerCommand(String[] arguments) {
        String[] nextArguments = Arrays.copyOfRange(arguments, 1, arguments.length);

        if (arguments[0].equals("group")) {
            handleCreateConsumerGroup(nextArguments);
        } else {
            handleCreateConsumer(arguments);
        }
    }

    private static void handleCreateConsumerGroup(String[] arguments) {
        if (arguments.length == 3) {
            api.createConsumerGroup(arguments[0], arguments[1], arguments[2]);
        } else {
            printUnknownCommand();
        }
    }

    private static void handleCreateConsumer(String[] arguments) {
        if (arguments.length == 2) {
            api.createConsumer(arguments[0], arguments[1]);
        } else {
            printUnknownCommand();
        }
    }

    private static void handleCreateProducer(String[] arguments) {
        if (arguments.length == 3) {
            api.createProducer(arguments[0], arguments[1], arguments[2]);
        } else {
            printUnknownCommand();
        }
    }

    // DELETE
    private static void handleDeleteCommand(String[] arguments) {
        String[] nextArguments = Arrays.copyOfRange(arguments, 1, arguments.length);

        switch (arguments[0]) {
        case "consumer":
            handleDeleteConsumer(nextArguments);
            break;
        default:
            printUnknownCommand();
        }
    }

    private static void handleDeleteConsumer(String[] arguments) {
        if (arguments.length == 1) {
            api.deleteConsumer(arguments[0]);
        } else {
            printUnknownCommand();
        }
    }

    // PRODUCE
    private static void handleProduceCommand(String[] arguments) {
        String[] nextArguments = Arrays.copyOfRange(arguments, 1, arguments.length);

        switch (arguments[0]) {
        case "event":
            handleProduceEventCommand(nextArguments);
            break;
        default:
            printUnknownCommand();
        }
    }

    private static void handleProduceEventCommand(String[] arguments) {
        if (arguments.length == 3) {
            api.produceEvent(arguments[0], arguments[1], arguments[2]);
        } else if (arguments.length == 4) {
            api.produceEvent(arguments[0], arguments[1], arguments[2], arguments[3]);
        }
    }

    // CONSUME
    private static void handleConsumeCommand(String[] arguments) {
        String[] nextArguments = Arrays.copyOfRange(arguments, 1, arguments.length);

        switch (arguments[0]) {
        case "event":
            handleConsumeEvent(nextArguments);
            break;
        case "events":
            handleConsumeEvents(nextArguments);
            break;
        default:
            printUnknownCommand();
        }
    }

    private static void handleConsumeEvent(String[] arguments) {
        if (arguments.length == 2) {
            api.consumeEvent(arguments[0], arguments[1]);
        } else {
            printUnknownCommand();
        }
    }

    private static void handleConsumeEvents(String[] arguments) {
        if (arguments.length == 3) {
            api.consumeEvents(arguments[0], arguments[1], arguments[2]);
        } else {
            printUnknownCommand();
        }
    }

    // SHOW
    private static void handleShowCommand(String[] arguments) {
        String[] nextArguments = Arrays.copyOfRange(arguments, 1, arguments.length);

        switch (arguments[0]) {
        case "topic":
            handleShowTopic(nextArguments);
            break;
        case "consumer":
            handleShowConsumerCommand(nextArguments);
            break;
        default:
            printUnknownCommand();
        }
    }

    private static void handleShowTopic(String[] arguments) {
        if (arguments.length == 1) {
            api.showTopic(arguments[0]);
        } else {
            printUnknownCommand();
        }
    }

    private static void handleShowConsumerCommand(String[] arguments) {
        String[] nextArguments = Arrays.copyOfRange(arguments, 1, arguments.length);

        switch (arguments[0]) {
        case "group":
            handleShowConsumerGroup(nextArguments);
            break;
        default:
            printUnknownCommand();
        }
    }

    private static void handleShowConsumerGroup(String[] arguments) {
        if (arguments.length == 1) {
            api.showConsumerGroup(arguments[0]);
        } else {
            printUnknownCommand();
        }
    }

    // PARALLEL
    private static void handleParallelCommand(String[] arguments) {
        String[] nextArguments = Arrays.copyOfRange(arguments, 1, arguments.length);

        switch (arguments[0]) {
        case "produce":
            handleParallelProduce(nextArguments);
            break;
        case "consume":
            handleParallelConsume(nextArguments);
            break;
        default:
            printUnknownCommand();
        }
    }

    private static void handleParallelProduce(String[] arguments) {
        if (arguments.length % 3 != 0) {
            printUnknownCommand();
            return;
        }

        List<String> producerIds = new ArrayList<>();
        List<String> topicIds = new ArrayList<>();
        List<String> eventFilesnames = new ArrayList<>();

        for (int i = 0; i < arguments.length; i += 3) {
            producerIds.add(arguments[i]);
            topicIds.add(arguments[i + 1]);
            eventFilesnames.add(arguments[i + 2]);
        }

        api.parallelProduce(producerIds, topicIds, eventFilesnames);
    }

    private static void handleParallelConsume(String[] arguments) {
        if (arguments.length % 2 != 0) {
            printUnknownCommand();
            return;
        }

        List<String> consumerIds = new ArrayList<>();
        List<String> partitionIds = new ArrayList<>();

        for (int i = 0; i < arguments.length; i += 2) {
            consumerIds.add(arguments[i]);
            partitionIds.add(arguments[i + 1]);
        }

        api.parallelConsume(consumerIds, partitionIds);
    }

    // SET
    private static void handleSetCommand(String[] arguments) {
        if (arguments[0].equals("consumer") && arguments[1].equals("group") && arguments[2].equals("rebalancing")) {
            String[] nextArguments = Arrays.copyOfRange(arguments, 3, arguments.length);

            handleSetConsumerGroupRebalancing(nextArguments);
        } else {
            printUnknownCommand();
        }
    }

    private static void handleSetConsumerGroupRebalancing(String[] arguments) {
        if (arguments.length == 2) {
            api.setConsumerGroupRebalancing(arguments[0], arguments[1]);
        } else {
            printUnknownCommand();
        }
    }

    // PLAYBACK
    private static void handlePlayback(String[] arguments) {
        if (arguments.length == 3) {
            api.playback(arguments[0], arguments[1], arguments[2]);
        } else {
            printUnknownCommand();
        }
    }

    // HELPERS
    private static void printUnknownCommand() {
        System.out.println("Unknown command");
    }
}
