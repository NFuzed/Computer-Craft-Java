package computercraft.commands;

import java.util.concurrent.*;

public class CommandQueue {
    private final BlockingQueue<Runnable> queue;

    public CommandQueue() {
        queue = new LinkedBlockingQueue<>();
        Thread thread = new Thread(this::runCommands);
        thread.start();
    }

    public <T> Future<T> enqueueCommand(Callable<T> command) {
        FutureTask<T> task = new FutureTask<>(command);
        queue.add(task);
        return task;
    }

    private void runCommands() {
        while (true) {
            try {
                Runnable command = queue.take();
                command.run();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}