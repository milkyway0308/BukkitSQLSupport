package skywolf46.bss.interfaces;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface SQLExtractor<X> {
    X extract(ResultSet stmt, int index) throws SQLException;
}
