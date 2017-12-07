package server;

import common.WorkingSchedule;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;

public class ScheduleUpdater {

    private IDBAdapter adapter;

    public ScheduleUpdater(IDBAdapter adapter) {
        this.adapter = adapter;
    }

    public void automaticUpdater() {
        new Thread(() -> {

            while (true) {
                LocalDateTime l = LocalDateTime.now();
                ArrayList<String> times = readFile();

                System.out.println("current times: ");
                System.out.println(times);

                long updateInterval = Long.valueOf(times.get(0));
                long lastUpdated = Long.valueOf(times.get(1));
                long currentTime = l.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

                if (lastUpdated + updateInterval < currentTime) {
                    // call adapter update method
                    ArrayList<WorkingSchedule> schedules = adapter.allWorkingSchedulesPerWeek();

                    // add 7 days
                    schedules.forEach(e -> e.setWorkingDate(LocalDate.parse(e.getWorkingDate()).plusDays(7).toString()));

                    schedules.forEach(e -> {
                        adapter.addToWorkingSchedule(e);
                        try {
                            Thread.sleep(20);
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }
                    });

                    System.out.println("Updating...");
                    times.set(1, String.valueOf(currentTime));

                    try {
                        Files.write(FileSystems.getDefault().getPath(System.getProperty("user.dir") + "/src/times.txt"), times);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                try {
                    Thread.sleep(updateInterval);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }).start();

    }

    private ArrayList<String> readFile() {
        ArrayList<String> res = new ArrayList<>();
        try {
            Files.lines(FileSystems.getDefault().getPath(System.getProperty("user.dir") + "/src/times.txt"))
                    .forEach(res::add);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }
}
