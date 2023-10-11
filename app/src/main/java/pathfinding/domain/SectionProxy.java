package pathfinding.domain;

import org.jgrapht.graph.DefaultWeightedEdge;

public class SectionProxy extends DefaultWeightedEdge {
    private final Section section;

    public SectionProxy(Section section) {
        this.section = section;
    }

    public Station getSourceVertex() {
        return section.getUpStation();
    }

    public Station getTargetVertex() {
        return section.getDownStation();
    }

    public Section toSection() {
        return section;
    }

    @Override
    protected double getWeight() {
        return section.getDistance();
    }
}
