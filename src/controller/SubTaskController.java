package controller;

import model.Epic;
import model.Status;
import model.SubTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubTaskController {
    private Integer counterIDSubTasks = 0;
    private final HashMap<Integer, SubTask> subTasks = new HashMap<>();
    EpicController epicController;

    public Map<Integer, SubTask> getSubTasks() {
        return subTasks;
    }

    public SubTaskController(EpicController epicController) {
        this.epicController = epicController;
    }

    // Добавить подзадачу.
    public SubTask create(SubTask subTask, Epic epic) {
        final SubTask newSubTask = new SubTask(
                subTask.getType(), subTask.getName(),
                subTask.getDescription(), ++counterIDSubTasks, subTask.getStatus(), epic.getId());
        if (!subTasks.containsKey(newSubTask.getId())) {
            subTasks.put(newSubTask.getId(), newSubTask);
            epicController.epics.get(epic.getId()).getSubTasks().add(newSubTask);
        } else {
            System.out.println("A task with this ID already exists");
            return null;
        }
        return newSubTask;
    }

    public SubTask findById(Integer id) {
        return subTasks.get(id);
    }

    public SubTask update(SubTask task) {
        final SubTask originalTask = subTasks.get(task.getId());
        if (originalTask == null) {
            System.out.println("There is no task with this ID.");
            return null;
        }
        originalTask.setDescription(task.getDescription());
        originalTask.setName(task.getName());
        originalTask.setStatus(task.getStatus());
        epicController.epics.get(task.getEpicID()).getSubTasks().remove(originalTask);
        epicController.epics.get(task.getEpicID()).getSubTasks().add(task);
        refreshStatus(task);
        return originalTask;
    }

    public SubTask deleteById(Integer id) {
        final SubTask deletedTask = subTasks.get(id);
        epicController.epics.get(deletedTask.getEpicID()).getSubTasks().remove(deletedTask);
        subTasks.remove(id);
        return deletedTask;
    }

    public void deleteAll() {
        subTasks.clear();
    }

    public List<SubTask> findAllOfEpic(Epic epic) {
        return epicController.epics.get(epic.getId()).getSubTasks();
    }

    public void refreshStatus(SubTask task) {
        ArrayList<SubTask> subTasksOfEpic = epicController.epics.get(task.getEpicID()).getSubTasks();
        int counterNew = 0;
        int counterDone = 0;
        for (SubTask subTask : subTasksOfEpic) {
            if (subTask.getStatus() == Status.NEW) {
                counterNew++;
            } else if (subTask.getStatus() == Status.DONE) {
                counterDone++;
            }
        }
        if (counterNew == subTasksOfEpic.size()) {
            epicController.epics.get(task.getEpicID()).setStatus(Status.NEW);
        } else if (counterDone == subTasksOfEpic.size()) {
            epicController.epics.get(task.getEpicID()).setStatus(Status.DONE);
        } else {
            epicController.epics.get(task.getEpicID()).setStatus(Status.IN_PROGRESS);
        }
    }

}
