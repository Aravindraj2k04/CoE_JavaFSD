import java.util.*;

class Task {
    private String id;
    private String description;
    private int priority;

    public Task(String id, String description, int priority) {
        this.id = id;
        this.description = description;
        this.priority = priority;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public int getPriority() {
        return priority;
    }

    @Override
    public String toString() {
        return "Task{ID='" + id + "', Description='" + description + "', Priority=" + priority + "}";
    }
}
class TaskManager {
    private PriorityQueue<Task> taskQueue;
    private Map<String, Task> taskMap;

    public TaskManager() {
        taskQueue = new PriorityQueue<>(Comparator.comparingInt(Task::getPriority).reversed());
        taskMap = new HashMap<>();
    }

    public void addTask(String id, String description, int priority) {
        if (taskMap.containsKey(id)) {
            System.out.println("Task with ID " + id + " already exists.");
            return;
        }
        Task task = new Task(id, description, priority);
        taskQueue.add(task);
        taskMap.put(id, task);
    }

    public void removeTask(String id) {
        if (!taskMap.containsKey(id)) {
            System.out.println("Task with ID " + id + " not found.");
            return;
        }
        Task task = taskMap.get(id);
        taskQueue.remove(task);
        taskMap.remove(id);
    }

    public Task getHighestPriorityTask() {
        return taskQueue.peek();
    }

    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();
        taskManager.addTask("1", "Complete Java Assignment", 3);
        taskManager.addTask("2", "Prepare for Meeting", 5);
        taskManager.addTask("3", "Buy Groceries", 2);
        
        System.out.println("Highest Priority Task: " + taskManager.getHighestPriorityTask());
        
        taskManager.removeTask("2");
        System.out.println("Highest Priority Task after removal: " + taskManager.getHighestPriorityTask());
    }
}
