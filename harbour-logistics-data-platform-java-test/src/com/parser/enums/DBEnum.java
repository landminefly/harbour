package com.parser.enums;

public enum DBEnum {
    MYSQL(0);

    private final int typeCode;

    DBEnum(int typeCode) {
        this.typeCode = typeCode;
    }

    public int getTypeCode() {
        return typeCode;
    }
}
