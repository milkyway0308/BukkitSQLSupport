package skywolf46.bss.threads;

import skywolf46.bss.client.BukkitSQLSupport;
import skywolf46.bss.interfaces.SQLFunction;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

public class SQLThread {
    private List<ConnectionManagement> manager = new ArrayList<>();
    private AtomicBoolean enabled = new AtomicBoolean(false);

    public SQLThread(int threads) throws SQLException {
        for (int i = 0; i < threads; i++) {
            manager.add(new ConnectionManagement(BukkitSQLSupport.createNewConnection()));
        }
    }

    public void addTask(SQLFunction funct) {
        List<Integer> tasking = manager.stream().map(ConnectionManagement::getTask).collect(Collectors.toList());
        int min = Integer.MAX_VALUE;
        int index = 0;
        for (int i = 0; i < tasking.size(); i++) {
            if (min > tasking.get(i)) {
                index = i;
                min = tasking.get(i);
            }
        }
        manager.get(index).appendTask(funct);
    }

    class ConnectionManagement extends Thread {
        private Connection connection;
        private AtomicInteger tasks = new AtomicInteger(0);
        private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        private List<SQLFunction> functs = new ArrayList<>();

        public ConnectionManagement(Connection con) {
            this.connection = con;
            start();
        }

        public int getTask() {
            return tasks.get();
        }

        public void increaseTask() {
            tasks.incrementAndGet();
        }

        public void decreaseTask() {
            tasks.decrementAndGet();
        }

        public void appendTask(SQLFunction funct) {
            lock.writeLock().lock();
            functs.add(funct);
            lock.writeLock().unlock();
        }

        public SQLFunction next() {
            lock.readLock().lock();
            SQLFunction funct = nextFunction();
            lock.readLock().unlock();
            return funct;
        }

        private SQLFunction nextFunction() {
            if (functs.size() <= 0)
                return null;
            return functs.remove(0);
        }

        @Override
        public void run() {
            while (enabled.get()) {
                SQLFunction funct;
                while ((funct = next()) != null) {
                    funct.work(connection);
                }
            }
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }
}
