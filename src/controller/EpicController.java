package controller;

import model.Epic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EpicController {
    HashMap<Integer, Epic> epics = new HashMap<>();
    Integer counterIDEpics = 0;

    public Map<Integer, Epic> getEpics() {
        return epics;
    }

    public Epic findById(Integer id) {
        return epics.get(id);
    }

    public List<Epic> findAll() {
        return new ArrayList<>(epics.values());
    }

    public Epic update(Epic epic) {
        final Epic originalTask = epics.get(epic.getId());
        if (originalTask == null) {
            System.out.println("There is no task with this ID.");
            return null;
        }
        originalTask.setDescription(epic.getDescription());
        originalTask.setName(epic.getName());
        return originalTask;
    }

    public Epic create(Epic task) {
        final Epic newTask = new Epic(
                task.getType(), task.getName(), task.getDescription(), ++counterIDEpics, task.getStatus());
        if (!epics.containsKey(newTask.getId())) {
            epics.put(newTask.getId(), newTask);
        } else {
            System.out.println("A task with this ID already exists");
            return null;
        }
        return newTask;
    }

    public void deleteById(Integer id) {
        epics.remove(id);
    }

    public void deleteAll() {
        epics.clear();
    }

}
