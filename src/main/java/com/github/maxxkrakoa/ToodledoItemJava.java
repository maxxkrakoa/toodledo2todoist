package com.github.maxxkrakoa;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * Created by mv on 2017-09-14.
 */
@JsonPropertyOrder({"TASK", "FOLDER", "CONTEXT", "GOAL", "LOCATION", "STARTDATE", "STARTTIME", "DUEDATE", "DUETIME", "REPEAT",
        "LENGTH", "TIMER", "PRIORITY", "TAG", "STATUS", "STAR", "NOTE"})
public class ToodledoItemJava {
    public String TASK;
    public String FOLDER;
    public String CONTEXT;
    public String GOAL;
    public String LOCATION;
    public String STARTDATE;
    public String STARTTIME;
    public String DUEDATE;
    public String DUETIME;
    public String REPEAT;
    public String LENGTH;
    public String TIMER;
    public String PRIORITY;
    public String TAG;
    public String STATUS;
    public String STAR;
    public String NOTE;
}
