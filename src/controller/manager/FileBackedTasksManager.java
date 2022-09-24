package controller.manager;

import controller.TaskTypes;
import exceptions.ManagerSaveException;
import model.Epic;
import model.Status;
import model.SubTask;
import model.Task;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static controller.TaskTypes.*;

public class FileBackedTasksManager extends InMemoryTasksTaskManager {

    private static final File TASKS_STORAGE = new File("resources/test_save.csv");

    public static File getTasksStorage() {
        return TASKS_STORAGE;
    }

    @Override
    public Task createTask(Task task) {
        super.createTask(task);
        save();
        return task;
    }

    @Override
    public SubTask createSubTask(SubTask subTask, Epic epic) {
        super.createSubTask(subTask, epic);
        save();
        return subTask;
    }

    @Override
    public Epic createEpic(Epic epic) {
        super.createEpic(epic);
        save();
        return epic;
    }

    @Override
    public Task findTaskById(Integer id) {
        super.findTaskById(id);
        save();
        return taskController.findById(id);
    }

    @Override
    public SubTask findSubTaskById(Integer id) {
        super.findSubTaskById(id);
        save();
        return subTaskController.findById(id);
    }

    @Override
    public Epic findEpicById(Integer id) {
        super.findEpicById(id);
        save();
        return epicController.findById(id);
    }

    @Override
    public Task deleteTaskById(Integer id) {
        final Task deletedTask = super.deleteTaskById(id);
        save();
        return deletedTask;
    }

    @Override
    public void deleteSubTaskById(Integer id) {
        super.deleteSubTaskById(id);
        save();
    }

    @Override
    public void deleteEpicById(Integer id) {
        super.deleteEpicById(id);
        save();
    }

    @Override
    public List<Task> getHistory() {
        final List<Task> history = super.getHistory();
        save();
        return history;
    }

    public void save() {
        try (BufferedWriter writer = Files.newBufferedWriter(TASKS_STORAGE.toPath(),
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING)) {
            for (Task task : taskController.getTasks().values()) {
                writer.write(task.toString());
                writer.newLine();
            }
            for (SubTask subTask : subTaskController.getSubTasks().values()) {
                writer.write(subTask.toString());
                writer.newLine();
            }
            for (Epic epic : epicController.getEpics().values()) {
                writer.write(epic.toString());
                writer.newLine();
            }

            writer.newLine();

            String historyId = getHistoryId();
            writer.write(historyId);
        } catch (IOException e) {
            throw new ManagerSaveException("Error saving to file");
        }
    }

    static String getHistoryId() {
        List<Integer> listHistoryId = new ArrayList<>();
        for (Map.Entry<Integer, Task> taskId : taskController.getTasks().entrySet()) {
            listHistoryId.add(taskId.getKey());
        }
        for (Map.Entry<Integer, SubTask> subTaskId : subTaskController.getSubTasks().entrySet()) {
            listHistoryId.add(subTaskId.getKey());
        }
        for (Map.Entry<Integer, Epic> epicId : epicController.getEpics().entrySet()) {
            listHistoryId.add(epicId.getKey());
        }
        return listHistoryId.toString().replaceAll("^\\[|\\]$", "");
    }

    public static void loadFromFile(File storageTask) {
        try (BufferedReader reader = new BufferedReader(new FileReader(storageTask, StandardCharsets.UTF_8))) {
            boolean readHistory = false;
            while (reader.ready()) {
                String line = reader.readLine();
                if (line.isBlank()) {
                    readHistory = true;
                } else if (readHistory) {
                    break;
                } else {
                    String[] splitter = line.split(", ");
                    TaskTypes type = TaskTypes.valueOf(splitter[0]);
                    String name = splitter[1];
                    String description = splitter[2];
                    Integer id = Integer.parseInt(splitter[3]);
                    Status status = Status.valueOf(splitter[4]);

                    if (type == TASK) {
                        taskController.getTasks().put(id,
                                new Task("TASK", name, description, id, status));
                    } else if (type == SUBTASK) {
                        Integer epicId = Integer.parseInt(splitter[5]);
                        subTaskController.getSubTasks().put(id,
                                new SubTask("SUBTASK", name, description, id, status, epicId));
                    } else if (type == EPIC) {
                        epicController.getEpics().put(id,
                                new Epic("EPIC", name, description, id, status));
                    }
                }
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Error loading from file");
        }
    }

}

    
