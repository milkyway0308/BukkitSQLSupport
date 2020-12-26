package skywolf46.bss.enums;

import skywolf46.bss.types.impl.java.DateType;
import skywolf46.bss.types.impl.java.UUIDType;
import skywolf46.bss.types.impl.java.array.ByteArrayType;
import skywolf46.bss.types.impl.java.natives.*;
import skywolf46.bss.types.impl.minecraft.InventorySnapshotType;
import skywolf46.bss.types.impl.minecraft.ItemStackType;

public class SQLTypes {
    public static class Java {
        public static final ByteType BYTE = new ByteType();
        public static final ShortType SHORT = new ShortType();
        public static final IntegerType INTEGER = new IntegerType();
        public static final LongType LONG = new LongType();
        public static final FloatType FLOAT = new FloatType();
        public static final DoubleType DOUBLE = new DoubleType();
        public static final StringType STRING = new StringType();
        public static final UUIDType UUID = new UUIDType();
        public static final DateType DATE = new DateType();
        public static final ByteArrayType BYTE_ARRAY = new ByteArrayType();
    }

    public static class Minecraft {
        public static final ItemStackType ITEMSTACK = new ItemStackType();
        public static final InventorySnapshotType INVENTORY_SNAPSHOT = new InventorySnapshotType();
    }

}
