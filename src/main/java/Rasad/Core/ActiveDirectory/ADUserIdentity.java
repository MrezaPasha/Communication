package main.java.Rasad.Core.ActiveDirectory;

import java.util.UUID;

public class ADUserIdentity {
    private String Name;

    public final String getName() {
        return Name;
    }

    public final void setName(String value) {
        Name = value;
    }

    private UUID Guid;

    public final UUID getGuid() {
        return Guid;
    }

    public void setGuid(UUID value) {
        Guid = value;
    }

    private String SID;

    public final String getSID() {
        return SID;
    }

    public final void setSID(String value) {
        SID = value;
    }
}