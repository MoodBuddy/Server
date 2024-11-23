package moodbuddy.moodbuddy.domain.quddyTI.domain.type;

public enum QuddyTIType {
    TYPE_J("J"),
    TYPE_P("P"),
    NO_DIARY("noDiary");
    private final String value;
    QuddyTIType(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}
