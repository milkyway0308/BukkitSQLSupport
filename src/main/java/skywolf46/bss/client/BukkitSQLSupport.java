package skywolf46.bss.client;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import skywolf46.bss.api.SQLTable;
import skywolf46.bss.threads.SQLThread;

import java.io.File;
import java.sql.*;
import java.util.Properties;

public class BukkitSQLSupport extends JavaPlugin {

    private static Properties sqlProp;

    private static String sqlAddress;

    private SQLThread sharedPool;

    private static Connection SQL;

    private static SQLThread threads;

    @Override
    public void onEnable() {
        File fl = new File(getDataFolder(), "config.yml");
        if (!fl.exists()) {
            fl.getParentFile().mkdirs();
            saveResource("config.yml", true);
        }
        YamlConfiguration conf = YamlConfiguration.loadConfiguration(fl);
        String url = (sqlAddress = conf.getString("SQL.Address"));
        int sqlThreadAmount = Math.max(1, conf.getInt("SQL.SQL Connection Threads", 1));
        try {
            Properties p = (sqlProp = new Properties());
            p.setProperty("autoReconnect", "true");
            p.setProperty("user", conf.getString("SQL.User"));
            p.setProperty("password", conf.getString("SQL.Password"));
            SQL = DriverManager.getConnection(url, p);
            PreparedStatement stmt = getSQL().prepareStatement("show databases like 'BukkitSQLSupport'");
            ResultSet rs = stmt.executeQuery();
            if (!rs.next())
                getSQL().prepareStatement("create database BukkitSQLSupport CHARACTER SET utf8 COLLATE utf8_general_ci;").execute();
            try (PreparedStatement xt = getSQL().prepareStatement("use BukkitSQLSupport")) {
                xt.executeUpdate();
            }
            threads = createThread(sqlThreadAmount);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static SQLThread getDefaultThread() {
        return threads;
    }

    public static Connection getSQL() {
        return SQL;
    }

    public static Connection createNewConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(sqlAddress, sqlProp);
        try (PreparedStatement xt = conn.prepareStatement("use BukkitSQLSupport")) {
            xt.executeUpdate();
        }
        return conn;
    }

    public static SQLThread createThread(int connectionAmount) throws SQLException {
        return new SQLThread(connectionAmount);
    }
}
