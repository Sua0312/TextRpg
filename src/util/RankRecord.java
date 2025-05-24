package util;

/**
 * 한 명의 플레이어 클리어 기록을 나타내는 클래스 - 이름과 클리어 시간을 포함 - 정렬 기준은 클리어 시간 오름차순
 */
public class RankRecord implements Comparable<RankRecord> {
    public final String name; // 플레이어 이름
    public final long timeMillis; // 클리어 시간 (밀리초 단위)

    /**
     * 생성자: 이름과 클리어 시간으로 기록을 생성
     *
     * @param name       플레이어 이름
     * @param timeMillis 클리어 시간 (ms)
     */
    public RankRecord(String name, long timeMillis) {
        this.name = name;
        this.timeMillis = timeMillis;
    }

    /**
     * 랭킹 파일에 저장할 문자열 포맷 생성 예: "홍길동 54321"
     */
    public String toFileString() {
        return name + " " + timeMillis;
    }

    /**
     * 사용자에게 보여줄 출력용 문자열 생성 예: "홍길동 - 00:01:32"
     */
    public String toDisplayString() {
        long seconds = timeMillis / 1000;
        long h = seconds / 3600; // 시간
        long m = (seconds % 3600) / 60; // 분
        long s = seconds % 60; // 초
        return String.format("%s - %02d:%02d:%02d", name, h, m, s);
    }

    /**
     * 정렬 기준 설정: 클리어 시간이 짧을수록 높은 순위
     */
    @Override
    public int compareTo(RankRecord other) {
        return Long.compare(this.timeMillis, other.timeMillis); // 오름차순 정렬 기준
    }
}