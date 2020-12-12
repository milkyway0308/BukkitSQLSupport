package skywolf46.bss.interfaces;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface SQLInserter<X> {
    void insert(PreparedStatement stmt, int index, X type) throws SQLException;
}
