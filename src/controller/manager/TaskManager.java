package controller.manager;

import model.Epic;
import model.SubTask;
import model.Task;

import java.util.List;
import java.util.Set;

public interface TaskManager {
    List<Task> findAllTasks();

    List<Epic> findAllEpics();

    List<SubTask> findAllSubTasksOfEpic(Epic epic);

    SubTask findSubTaskById(Integer id);

    Task findTaskById(Integer id);

    Epic findEpicById(Integer id);

    Task createTask(Task task);

    SubTask createSubTask(SubTask subTask, Epic epic);

    Epic createEpic(Epic epic);

    Task updateTaskByID(Task task);

    SubTask updateSubTaskByID(SubTask subTask);

    Task updateEpicByID(Epic epic);

    void deleteAllTask();

    void deleteAllSubTasks();

    void deleteAllEpics();

    void deleteSubTaskById(Integer id);

    void deleteEpicById(Integer id);

    Task deleteTaskById(Integer id);

    void removeFromHistoryById(int id);

    List<Task> getHistory();

    void addInHistory(Task task);

    void removeAllHistory();

    Set<Task> getPrioritizedTasks();
}
