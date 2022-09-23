package model;

import java.util.Objects;

public class SubTask extends Task {
    private Integer epicID;

    public Integer getEpicID() {
        return epicID;
    }

    public void setEpicID(Integer epicID) {
        this.epicID = epicID;
    }

    public SubTask(String type, String name, String description, Integer id, Status status, Integer epicId) {
        super(type, name, description, id, status);
        this.epicID = epicId;
    }

    public SubTask(String name, String description, Integer id, Integer epicID) {
        super(name, description, id);
        this.epicID = epicID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SubTask)) return false;
        if (!super.equals(o)) return false;
        SubTask subTask = (SubTask) o;
        return Objects.equals(getEpicID(), subTask.getEpicID());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getEpicID());
    }

    @Override
    public String toString() {
        return type +
                ", " + name +
                ", " + description +
                ", " + id +
                ", " + status +
                ", " + epicID;
    }
}