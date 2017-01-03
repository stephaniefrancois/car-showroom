package core.domain.deal;

import core.domain.UserProfile;

public final class SalesRepresentative {
    private final int salesRepresentativeId;
    private final UserProfile profile;

    public SalesRepresentative(int salesRepresentativeId, UserProfile profile) {

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
