package apuhostelmanagementfeessystem;

public class Logs {
    private String username;
    private String action;
    private String timestamp;

    public Logs(String username, String action, String timestamp) {
        this.username = username;
        this.action = action;
        this.timestamp = timestamp;
    }

    public String getUsername() {
        return username;
    }

    public String getAction() {
        return action;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String toString() {
        return username + "|" + action + "|" + timestamp;
    }

    public static Logs fromString(String logLine) {
        String[] parts = logLine.split("\\|");
        if (parts.length == 3) {
            return new Logs(parts[0], parts[1], parts[2]);
        }
        return null;
    }
}

