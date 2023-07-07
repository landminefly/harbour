package com.parser.enums;

import com.parser.FileSpecificParser;
import com.parser.fileSpecificParserImpl.FileCsvParser;
import com.parser.fileSpecificParserImpl.FileTxtParser;
import com.parser.fileSpecificParserImpl.FileXlsxParser;

public enum FileEnum {
    TXT(new FileTxtParser()),
    XLSX(new FileXlsxParser()),
    CSV(new FileCsvParser());

    private final FileSpecificParser sp;

    FileEnum(FileSpecificParser sp) {
        this.sp = sp;
    }

    public FileSpecificParser getSp() {
        return sp;
    }
}
