package core.deal.model;

import core.authentication.model.UserProfile;

import java.util.Objects;

public final class SalesRepresentative {
    private final int salesRepresentativeId;
    private final UserProfile profile;

    public SalesRepresentative(int salesRepresentativeId, UserProfile profile) {
        Objects.requireNonNull(profile,
                "'profile' must be supplied!");

        this.salesRepresentativeId = salesRepresentativeId;
        this.profile = profile;
    }

    public int getSalesRepresentativeId() {
        return salesRepresentativeId;
    }

    public String getFullName() {
        return String.format("%s %s",
                profile.getFirstName(),
                profile.getLastName());
    }
}
