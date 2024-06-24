package minecraft.client.GUI.Background.download;

import minecraft.client.entities.versions.IVersion;

public final class VersionIncompatibleException extends Exception {
    private static final long serialVersionUID = 1949257318419028252L;
    private final IVersion v;

    public VersionIncompatibleException(IVersion v) {
        this.v = v;
    }

    @Override
    public String getMessage() {
        return "Version " + v.getDisplayName() + " is not compatible with your operating system. Reason: " + v.getIncompatibilityReason();
    }

}
