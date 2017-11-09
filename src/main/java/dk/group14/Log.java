package dk.group14;

public class Log {
    private String level;
    private int systemID;
    private int instanceID;
    private int actionID;
    private Long timestamp;

    public Log(String level, int systemID, int instanceID, int actionID, Long timestamp) {
        this.level = level;
        this.systemID = systemID;
        this.instanceID = instanceID;
        this.actionID = actionID;
        this.timestamp = timestamp;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int getSystemID() {
        return systemID;
    }

    public void setSystemID(int systemID) {
        this.systemID = systemID;
    }

    public int getInstanceID() {
        return instanceID;
    }

    public void setInstanceID(int instanceID) {
        this.instanceID = instanceID;
    }

    public int getActionID() {
        return actionID;
    }

    public void setActionID(int actionID) {
        this.actionID = actionID;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
