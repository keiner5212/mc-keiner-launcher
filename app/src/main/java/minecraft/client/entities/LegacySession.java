package minecraft.client.entities;

import java.util.List;

public class LegacySession implements ISession {
    private String username;

    public LegacySession(String username) {
        this.username = username;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getSessionID() {
        return "00000000-0000-0000-0000-000000000000";
    }

    @Override
    public String getUUID() {
        return "00000000-0000-0000-0000-000000000000";
    }

    @Override
    public ESessionType getType() {
        return ESessionType.LEGACY;
    }

    @Override
    public List<Prop> getProperties() {
        return List.of();
    }
    
}
