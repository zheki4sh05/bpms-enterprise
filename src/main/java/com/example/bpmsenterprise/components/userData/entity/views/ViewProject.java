package com.example.bpmsenterprise.components.userData.entity.views;

public class ViewProject {
    private String name;
    private String done;
    private String finish;
    private String workers;
    private String role;
    private String access;

    public ViewProject(String name, String done, String finish, String workers, String role, String access) {
        this.name = name;
        this.done = done;
        this.finish = finish;
        this.workers = workers;
        this.role = role;
        this.access = access;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDone() {
        return done;
    }

    public void setDone(String done) {
        this.done = done;
    }

    public String getFinish() {
        return finish;
    }

    public void setFinish(String finish) {
        this.finish = finish;
    }

    public String getWorkers() {
        return workers;
    }

    public void setWorkers(String workers) {
        this.workers = workers;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }
}
