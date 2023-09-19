package gsc.projects.eventsmcs.model;

public enum EventType {

    MUSIC("music"),
    THEATER("theater"),
    CHARITY("charity"),
    BUSINESS("business"),
    SPORTS("sports"),
    CULTURE("culture"),
    RELIGIOUS("religious"),
    ART("art"),
    WORKSHOP("workshop"),
    FASHION("fashion"),
    GASTRONOMIC("gastronomic");

    public final String type;

    EventType(String type) {
        this.type = type;
    }
}
