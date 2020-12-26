package skywolf46.bss.api;

import skywolf46.bss.abstraction.AbstractSQLType;
import skywolf46.bss.data.AppendableStringList;
import skywolf46.bss.data.SQLInsertList;
import skywolf46.bss.data.SQLSelectList;
import skywolf46.bss.util.AppendableList;
import skywolf46.bss.util.ItemPair;
import skywolf46.bss.util.ItemTriple;
import skywolf46.bss.util.PairMap;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class SQLTable {
    private String tableName;
    private List<AbstractSQLType> types = new ArrayList<>();

    private Connection con;


    public static SQLTable of(Connection con, String tableName) {
        return new SQLTable(con, tableName);
    }

    public SQLTable(Connection con, String tableName) {
        this.tableName = tableName;
        this.con = con;
    }

    public SQLTableBuilder builder() {
        return new SQLTableBuilder();
    }

    public void replace(SQLInsertList list) {
        StringBuilder selector = new StringBuilder("replace into ").append(tableName).append(" values (");
        for (int i = 0; i < list.size(); i++) {
            if (i != 0) {
                selector.append(", ");
            }
            selector.append("?");
        }
        selector.append(");");
        try (PreparedStatement stmt = con.prepareStatement(selector.toString())) {
            for (int i = 0; i < list.size(); i++) {
                ItemPair<AbstractSQLType, Object> k = list.get(i);
                k.getK().insert(stmt, i + 1, k.getV());
            }
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void insert(SQLInsertList list) {
        StringBuilder selector = new StringBuilder("insert into ").append(tableName).append(" values (");
        for (int i = 0; i < list.size(); i++) {
            if (i != 0) {
                selector.append(", ");
            }
            selector.append("?");
        }
        selector.append(");");
        try (PreparedStatement stmt = con.prepareStatement(selector.toString())) {
            for (int i = 0; i < list.size(); i++) {
                ItemPair<AbstractSQLType, Object> k = list.get(i);
                k.getK().insert(stmt, i + 1, k.getV());
            }
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public SelectableResult selectMultiple(AppendableStringList name, SQLSelectList list) {
        StringBuilder selector = new StringBuilder("select ");
        for (int i = 0; i < name.size(); i++) {
            if (i != 0)
                selector.append(", ");
            selector.append(name.get(i));
        }
        selector.append(" from ").append(tableName).append(" where ");
        for (int i = 0; i < list.size(); i++) {
            ItemTriple<AbstractSQLType, String, Object> k = list.get(i);
            if (i != 0) {
                selector.append(" and ");
            }
            selector.append("`").append(k.getV()).append("` = ?");
        }
        selector.append(";");
        try {
            PreparedStatement stmt = con.prepareStatement(selector.toString());
            for (int i = 0; i < list.size(); i++) {
                ItemTriple<AbstractSQLType, String, Object> k = list.get(i);
                k.getK().insert(stmt, i + 1, k.getX());
            }
            return new SelectableResult(stmt, stmt.executeQuery());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public <T> List<T> select(AbstractSQLType<T> type, String name, SQLSelectList list) {
        StringBuilder selector = new StringBuilder("select ").append(name).append(" from ").append(tableName).append(" where ");
        for (int i = 0; i < list.size(); i++) {
            ItemTriple<AbstractSQLType, String, Object> k = list.get(i);
            if (i != 0) {
                selector.append(" and ");
            }
            selector.append("`").append(k.getV()).append("` = ?");
        }
        selector.append(";");
        try (PreparedStatement stmt = con.prepareStatement(selector.toString())) {
            for (int i = 0; i < list.size(); i++) {
                ItemTriple<AbstractSQLType, String, Object> k = list.get(i);
                k.getK().insert(stmt, i + 1, k.getX());
            }
            List<T> lst = new ArrayList<>();
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    lst.add(type.extract(rs, 1));
                }
            }
            return lst;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T selectOne(AbstractSQLType<T> type, String name, SQLSelectList list) {
        List<T> sel = select(type, name, list);
        if (sel.size() <= 0)
            return null;
        return sel.get(0);
    }

    public void selectRaw(AppendableList<String> names, AppendableList<ItemTriple<AbstractSQLType, String, Object>> list, Consumer<ResultSet> rsCons) {
        StringBuilder selector = new StringBuilder("select ");
        for (int i = 0; i < names.size(); i++) {
            if (i != 0)
                selector.append(", ");
            selector.append(names.get(i));
        }
        selector.append(" from ").append(tableName).append(" where ");
        for (int i = 0; i < list.size(); i++) {
            ItemTriple<AbstractSQLType, String, Object> k = list.get(i);
            if (i != 0) {
                selector.append(" and ");
            }
            selector.append("`").append(k.getV()).append("` = ?");
        }
        selector.append(";");
        try (PreparedStatement stmt = con.prepareStatement(selector.toString());) {
            try (ResultSet rs = stmt.executeQuery()) {
                rsCons.accept(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public class SQLTableBuilder {
        private PairMap<String, SQLTableColumn> objs = new PairMap<>();
        private SQLTableColumn last;

        public SQLTableBuilder column(String name, AbstractSQLType type) {
            if (objs.containsKey(name))
                throw new IllegalStateException("Column name already defined");
            last = new SQLTableColumn();
            last.setType(type);
            objs.put(name, last);
            return this;
        }

        public SQLTableBuilder size(int size) {
            last.setSize(size);
            return this;
        }

        public SQLTableBuilder nullable(boolean nullable) {
            last.setNullable(nullable);
            return this;
        }

        public SQLTableBuilder primary(boolean primary) {
            last.setPrimary(primary);
            return this;
        }

        public SQLTableBuilder nullable() {
            return nullable(true);
        }

        public SQLTableBuilder primary() {
            return primary(true);
        }

        public void create() {
            types.clear();
            List<String> primaries = new ArrayList<>();
            StringBuilder text = new StringBuilder("create table if not exists " + tableName + "(");
            for (Map.Entry<String, SQLTableColumn> col : objs.entrySet()) {
                text.append(col.getValue().toString(col.getKey())).append(",");
                if (col.getValue().isPrimary()) {
                    primaries.add(col.getKey());
                }
            }
            if (primaries.size() > 0) {
                text.append("primary key (");
                for (String x : primaries) {
                    text.append(x).append(",");
                }
                text.deleteCharAt(text.length() - 1);
                text.append(")");
            } else {
                text.deleteCharAt(text.length() - 1);
            }
            text.append(");");
            try (PreparedStatement stmt = con.prepareStatement(text.toString())) {
                stmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public class SQLTableColumn {
            private AbstractSQLType type;
            private boolean isPrimary = false;
            private boolean isNullable = false;
            private int size = -1;

            public String toString(String columnName) {
                return columnName + " " + type.getType() + (size == -1 ? "" : "(" + size + ")") + (isNullable ? " NULL" : " NOT NULL");
            }

            public AbstractSQLType getType() {
                return type;
            }

            public void setType(AbstractSQLType type) {
                this.type = type;
                this.type.create(this);
            }

            public boolean isPrimary() {
                return isPrimary;
            }

            public void setPrimary(boolean primary) {
                isPrimary = primary;
            }

            public boolean isNullable() {
                return isNullable;
            }

            public void setNullable(boolean nullable) {
                isNullable = nullable;
            }

            public int getSize() {
                return size;
            }

            public void setSize(int size) {
                this.size = size;
            }
        }
    }

    public class SelectableResult implements AutoCloseable {
        private ResultSet result;
        private PreparedStatement stmt;

        public SelectableResult(PreparedStatement stmt, ResultSet rs) {
            this.result = rs;
            this.stmt = stmt;
        }

        public <T> T get(AbstractSQLType<T> type, int index) {
            try {
                return type.extract(result, index);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }

        public void close() {
            try {
                stmt.close();
                result.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public boolean hasNext() {
            try {
                return result.next();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return false;
        }

    }


}
