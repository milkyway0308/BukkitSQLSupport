package skywolf46.bss.api;

import skywolf46.bss.util.PairMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SQLTable {
    private String tableName;

    public SQLTable(String tableName) {
        this.tableName = tableName;
    }

    class SQLTableColumnType {

    }

    class SQLTableBuilder {
        private PairMap<String, SQLTableColumn> objs = new PairMap<>();

        public void create() {
            List<SQLTableColumn> primaries = new ArrayList<>();
            StringBuilder text = new StringBuilder("create table if not exists " + tableName + "(");
            for (Map.Entry<String, SQLTableColumn> col : objs.entrySet()) {
                text.append("");
            }
            text.append(");");
        }

        class SQLTableColumn {
            private String type;
            private boolean isPrimary = false;
            private boolean isNullable = false;
            private int size = -1;

            public String toString(String columnName) {
                return columnName + " " + type + (isNullable ? "NULL" : "NOT NULL");
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
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
        }
    }


}
