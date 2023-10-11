package pathfinding.domain;

import lombok.Builder;

public class Station {

    private Long id;
    private String name;

    @Builder
    public Station(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
