package navigation;

import java.util.List;
import java.util.stream.Collectors;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.WeightedMultigraph;
import org.junit.jupiter.api.Test;

import navigation.domain.Section;
import navigation.domain.Station;
import navigation.domain.SectionProxy;

import static org.assertj.core.api.Assertions.*;

public class Dijkstra {

    @Test
    public void getDijkstraShortestPath() {
        WeightedMultigraph<String, DefaultWeightedEdge> graph = new WeightedMultigraph<>(DefaultWeightedEdge.class);
        graph.addVertex("v1");
        graph.addVertex("v2");
        graph.addVertex("v3");
        graph.setEdgeWeight(graph.addEdge("v1", "v2"), 1);
        graph.setEdgeWeight(graph.addEdge("v2", "v3"), 1);
        graph.setEdgeWeight(graph.addEdge("v1", "v3"), 100);

        DijkstraShortestPath<String, DefaultWeightedEdge> shortestPath = new DijkstraShortestPath<>(graph);
        List<String> path = shortestPath.getPath("v1", "v3").getVertexList();
        double distance = shortestPath.getPathWeight("v1", "v3");

        assertThat(path).containsExactly("v1", "v2", "v3");
        assertThat(distance).isEqualTo(2);
    }

    @Test
    public void noneConnectedPath() {
        WeightedMultigraph<String, DefaultWeightedEdge> graph = new WeightedMultigraph<>(DefaultWeightedEdge.class);
        graph.addVertex("v1");
        graph.addVertex("v2");
        graph.addVertex("v3");
        DijkstraShortestPath<String, DefaultWeightedEdge> shortestPath = new DijkstraShortestPath<>(graph);
        GraphPath<String, DefaultWeightedEdge> path = shortestPath.getPath("v3", "v1");
        assertThat(path).isNull();
    }

    @Test
    public void proxyAsEdge() {
        Station station1 = new Station(1L, "선릉역");
        Station station2 = new Station(2L, "잠실역");
        Station station3 = new Station(3L, "강남역");
        WeightedMultigraph<Station, SectionProxy> graph = new WeightedMultigraph<>(SectionProxy.class);
        graph.addVertex(station1);
        graph.addVertex(station2);
        graph.addVertex(station3);

        Section section1 = new Section(1L, station1, station2, 1);
        Section section2 = new Section(1L, station2, station3, 1);
        Section section3 = new Section(2L, station1, station3, 100);
        SectionProxy edge1 = new SectionProxy(section1);
        SectionProxy edge2 = new SectionProxy(section2);
        SectionProxy edge3 = new SectionProxy(section3);
        graph.addEdge(edge1.getSourceVertex(), edge1.getTargetVertex(), edge1);
        graph.addEdge(edge2.getSourceVertex(), edge2.getTargetVertex(), edge2);
        graph.addEdge(edge3.getSourceVertex(), edge3.getTargetVertex(), edge3);

        DijkstraShortestPath<Station, SectionProxy> shortestPath = new DijkstraShortestPath<>(graph);
        GraphPath<Station, SectionProxy> path = shortestPath.getPath(station1, station3);
        // List<SectionProxy> edgesInOrder = path.getEdgeList();
        List<Station> vertexInOrder = path.getVertexList();
        double distance = path.getWeight();

        // assertThat(edgesInOrder).containsExactly(edge1, edge2);
        assertThat(vertexInOrder).containsExactly(station1, station2, station3);
        assertThat(distance).isEqualTo(2);

        List<Section> edgesInOrder = path.getEdgeList()
                .stream()
                .map(SectionProxy::toSection)
                .collect(Collectors.toList());
        assertThat(edgesInOrder).containsExactly(section1, section2);
    }
}
