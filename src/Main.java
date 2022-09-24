import controller.manager.FileBackedTasksManager;
import model.Epic;
import model.SubTask;
import model.Task;

import java.io.File;

import static model.Status.NEW;

public class Main {

    public static void main(String[] args) {
        startTaskManager();
    }

    private static void startTaskManager() {
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager();
        File storage = FileBackedTasksManager.getTasksStorage();
        FileBackedTasksManager.loadFromFile(storage);

        String lineSeparator = "\n";

        Task task1 = new Task("TASK", "task 1", "desc task 1", 1, NEW);
        fileBackedTasksManager.createTask(task1);
        Task task2 = new Task("TASK", "task 2", "desc task 2", 2, NEW);
        fileBackedTasksManager.createTask(task2);
        System.out.println("Show tasks: " + lineSeparator + fileBackedTasksManager.findAllTasks());

        Epic epic1 = new Epic("EPIC", "epic 1", "desc epic 1", 1, NEW);
        fileBackedTasksManager.createEpic(epic1);
        Epic epic2 = new Epic("EPIC", "epic 2", "desc epic 2", 2, NEW);
        fileBackedTasksManager.createEpic(epic2);
        System.out.println("Show Epics:" + lineSeparator + fileBackedTasksManager.findAllEpics());

        SubTask subtask1 = new SubTask("SUBTASK", "subTask 1", "desc subTask 1",
                5, NEW, 1);
        fileBackedTasksManager.createSubTask(subtask1, epic1);
        SubTask subtask2 = new SubTask("SUBTASK", "subTask 2", "desc subTask 2",
                6, NEW, 2);
        fileBackedTasksManager.createSubTask(subtask2, epic1);
        SubTask subtask3 = new SubTask("SUBTASK", "subTask 3", "desc subTask 3",
                7, NEW, 1);
        fileBackedTasksManager.createSubTask(subtask3, epic1);
        System.out.println("Show subtasks first epic: " + lineSeparator + fileBackedTasksManager.findAllSubTasksOfEpic(epic1));

        System.out.println("Show blank history: " + lineSeparator + fileBackedTasksManager.getHistory());

        fileBackedTasksManager.findEpicById(epic1.getId());
        fileBackedTasksManager.findEpicById(epic2.getId());
        fileBackedTasksManager.findEpicById(epic1.getId());
        fileBackedTasksManager.findEpicById(epic2.getId());
        fileBackedTasksManager.findEpicById(epic1.getId());
        fileBackedTasksManager.findEpicById(epic2.getId());
        fileBackedTasksManager.findEpicById(epic1.getId());
        fileBackedTasksManager.findEpicById(epic2.getId());

        System.out.println("History: " + lineSeparator
                + fileBackedTasksManager.getHistory());

        fileBackedTasksManager.deleteTaskById(task1.getId());
        System.out.println("History without one task" + lineSeparator
                + fileBackedTasksManager.getHistory());
    }

}