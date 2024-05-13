package narnew.cellimagingsystem.enums;

public enum MsgTypeEnum {
    INFO("info"),
    WARN("warn"),
    ERROR("error");

    private String type;

    private MsgTypeEnum(String type) {
        this.type = type;
    }
}