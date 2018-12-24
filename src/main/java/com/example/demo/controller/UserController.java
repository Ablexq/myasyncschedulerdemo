package com.example.demo.controller;

import com.example.demo.task.AsyncTask;
import com.example.demo.task.SyncTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Future;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    private AsyncTask asyncTask;

    @Autowired
    private SyncTask syncTask;


    @GetMapping("async_task")
    public long exeAsyncTask() throws InterruptedException {

        long begin = System.currentTimeMillis();

        Future<String> task4 = asyncTask.task4();
        Future<String> task5 = asyncTask.task5();
        Future<String> task6 = asyncTask.task6();

        //如果都执行完就可以跳出循环,isDone方法如果此任务完成，true
        for (; ; ) {
            if (task4.isDone() && task5.isDone() && task6.isDone()) {
                break;
            }
        }

        long end = System.currentTimeMillis();
        long total = end - begin;
        System.out.println("执行总耗时=" + total);//3000ms左右
        return total;
    }

    @GetMapping("sync_task")
    public long exeSyncTask() throws InterruptedException {

        long begin = System.currentTimeMillis();

        String task4 = syncTask.task4();
        String task5 = syncTask.task5();
        String task6 = syncTask.task6();

        long end = System.currentTimeMillis();
        long total = end - begin;
        System.out.println("执行总耗时=" + total);//固定6000
        return total;
    }
}