package com.example.myfirstapp.app;

import java.util.Comparator;

/**
 * Created by kcf412 on 4/27/14.
 */
public interface TaskOrderer extends Comparator<Task> {

    @Override
    public int compare(Task one, Task two);
}
