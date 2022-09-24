package controller.manager;

import controller.EpicController;
import controller.SubTaskController;
import controller.TaskController;
import model.Epic;
import model.SubTask;
import model.Task;

import java.time.LocalDateTime;
import java.util.*;

public class InMemoryTasksTaskManager implements TaskManager {

    static TaskController taskController = new TaskController();
    static EpicController epicController = new EpicController();
    static SubTaskController subTaskController = new SubTaskController(epicController);
    static InMemoryHistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();
    private static final Set<Task> prioritizedTasks =
            new TreeSet<>(Comparator.<Task, LocalDateTime>comparing(
                            Task::getStartTime,
                            Comparator.nullsLast(Comparator.naturalOrder())
                    )
                    .thenComparingInt(Task::getId));

    @Override
    public List<Task> findAllTasks() {
        return taskController.findAll();
    }

    @Override
    public List<Epic> findAllEpics() {
        return epicController.findAll();
    }

    @Override
    public List<SubTask> findAllSubTasksOfEpic(Epic epic) {
        return subTaskController.findAllOfEpic(epic);
    }

    @Override
    public SubTask findSubTaskById(Integer id) {
        final SubTask subTask = subTaskController.findById(id);
        addInHistory(subTask);
        return subTask;
    }

    @Override
    public Task findTaskById(Integer id) {
        final Task task = taskController.findById(id);
        addInHistory(task);
        return task;
    }

    @Override
    public Epic findEpicById(Integer id) {
        final Epic epic = epicController.findById(id);
        addInHistory(epic);
        return epic;
    }

    @Override
    public Task createTask(Task task) {
        return taskController.create(task);
    }

    @Override
    public SubTask createSubTask(SubTask subTask, Epic epic) {
        return subTaskController.create(subTask, epic);
    }

    @Override
    public Epic createEpic(Epic epic) {
        return epicController.create(epic);
    }

    @Override
    public Task updateTaskByID(Task task) {
        return taskController.update(task);
    }

    @Override
    public SubTask updateSubTaskByID(SubTask subTask) {
        return subTaskController.update(subTask);
    }

    @Override
    public Task updateEpicByID(Epic epic) {
        return epicController.update(epic);
    }

    @Override
    public void deleteAllTask() {
        if (!inMemoryHistoryManager.getMap().isEmpty()) {
            for (var historyTask : inMemoryHistoryManager.getMap().values()) {
                for (var task : taskController.getTasks().values()) {
                    if (task.equals(historyTask.task)) {
                        inMemoryHistoryManager.remove(historyTask.task.getId());
                    }
                }
            }
        }
        taskController.deleteAll();
    }

    @Override
    public void deleteAllSubTasks() {
        subTaskController.deleteAll();
    }

    @Override
    public void deleteAllEpics() {
        epicController.deleteAll();
    }

    @Override
    public void deleteSubTaskById(Integer id) {
        removeFromHistoryById(id);
        subTaskController.deleteById(id);
    }

    @Override
    public void deleteEpicById(Integer id) {
        removeFromHistoryById(id);
        epicController.deleteById(id);
    }

    @Override
    public Task deleteTaskById(Integer id) {
        removeFromHistoryById(id);
        return taskController.deleteById(id);
    }

    public List<Task> getHistory() {
        return inMemoryHistoryManager.getHistory();
    }

    public void removeAllHistory() {
        inMemoryHistoryManager.removeAll();
    }

    public void addInHistory(Task task) {
        inMemoryHistoryManager.add(task);
    }

    public void removeFromHistoryById(int id) {
        inMemoryHistoryManager.remove(id);
    }

    @Override
    public Set<Task> getPrioritizedTasks() {
        return prioritizedTasks;
    }

}
