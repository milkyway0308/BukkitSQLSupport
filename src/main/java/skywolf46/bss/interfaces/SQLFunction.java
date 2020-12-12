package skywolf46.bss.interfaces;

import java.sql.Connection;

@FunctionalInterface
public interface SQLFunction {
    void work(Connection con);
}
