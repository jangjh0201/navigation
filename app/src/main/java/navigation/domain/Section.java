package navigation.domain;

public class Section {

    private Long lineId; // 등록된 노선에 대한 정보
    private Station upStation; // 정점1
    private Station downStation; // 정점2
    private int distance; // 가중치 // 생성자 등 세부 사항 생략

    public Section(Long lineId, Station upStation, Station downStation, int distance) {
        this.lineId = lineId;
        this.upStation = upStation;
        this.downStation = downStation;
        this.distance = distance;
    }

    public Station getUpStation() {
        return upStation;
    }

    public Station getDownStation() {
        return downStation;
    }

    public int getDistance() {
        return distance;
    }

}
