package controller;

import model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskController {
    public Map<Integer, Task> getTasks() {
        return tasks;
    }

    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private Integer counterIDTasks = 0;

    public List<Task> findAll() {
        return new ArrayList<>(tasks.values());
    }

    public Task findById(Integer id) {
        return tasks.get(id);
    }

    public Task update(Task task) {
        final Task originalTask = tasks.get(task.getId());
        if (originalTask == null) {
            System.out.println("There is no task with this ID.");
            return null;
        }
        originalTask.setDescription(task.getDescription());
        originalTask.setName(task.getName());
        originalTask.setStatus(task.getStatus());
        return originalTask;
    }

    public Task create(Task task) {
        final Task newTask = new Task(
                task.getType(), task.getName(),
                task.getDescription(), ++counterIDTasks, task.getStatus());
        if (!tasks.containsKey(newTask.getId()))
            tasks.put(newTask.getId(), newTask);
        else {
            System.out.println("A task with this ID already exists");
            return null;
        }
        return newTask;
    }

    public Task deleteById(Integer id) {
        final Task deletedTask = tasks.get(id);
        tasks.remove(id);
        return deletedTask;
    }

    public void deleteAll() {
        tasks.clear();
    }

}
