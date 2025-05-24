package util;

import java.io.*;
import java.util.*;

/**
 * 게임 클리어 시간을 기록하고 관리하는 랭킹 시스템 클래스. - 최대 3개의 상위 랭킹만 유지 - 기록은 파일("ranking.txt")에
 * 저장되고 불러올 수 있음 - 기록 추가 시 자동 정렬 (오름차순)
 */
public class RankingManager {
    // 랭킹 데이터가 저장될 파일 경로
    private static final String FILE_PATH = "ranking.txt";
    // 랭킹에 저장할 최대 기록 수
    private static final int MAX_RANKINGS = 3;

    // 랭킹 기록을 저장하는 리스트 (클리어 시간 오름차순 정렬 유지)
    private final List<RankRecord> records = new ArrayList<>();

    // 생성자: 인스턴스 생성 시 랭킹 파일로부터 기존 기록을 불러옴
    public RankingManager() {
        load();
    }

    /**
     * 새로운 클리어 기록을 추가함
     *
     * @param name       플레이어 이름
     * @param timeMillis 클리어 시간 (밀리초 단위)
     */
    public void record(String name, long timeMillis) {
        records.add(new RankRecord(name, timeMillis)); // 새로운 기록 추가
        Collections.sort(records); // 시간 기준 오름차순 정렬 (클리어 시간 짧을수록 상위)

        // 랭킹 개수가 3개 초과인 경우 하위 기록 삭제
        if (records.size() > MAX_RANKINGS) {
            records.subList(MAX_RANKINGS, records.size()).clear();
        }

        save(); // 최신 랭킹 파일에 저장
    }

    /**
     * 현재 저장된 상위 랭킹 기록 리스트를 반환
     *
     * @return 복사된 랭킹 기록 리스트
     */
    public List<RankRecord> getTopRankings() {
        return new ArrayList<>(records); // 불변성을 위해 복사본 반환
    }

    /**
     * 랭킹 기록을 파일에서 불러옴 ranking.txt 파일이 존재하면 내용을 읽고 파싱하여 records에 추가 잘못된 줄은 무시하며,
     * 최종적으로 상위 3개만 유지
     */
    private void load() {
        records.clear(); // 기존 기록 초기화
        File file = new File(FILE_PATH);
        if (!file.exists())
            return; // 파일 없으면 아무 작업 안 함

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length != 2)
                    continue; // 형식이 틀린 경우 무시
                String name = parts[0];
                long time = Long.parseLong(parts[1]);
                records.add(new RankRecord(name, time));
            }
            Collections.sort(records); // 오름차순 정렬

            // 상위 MAX_RANKINGS 개수만 유지
            if (records.size() > MAX_RANKINGS) {
                records.subList(MAX_RANKINGS, records.size()).clear();
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace(); // 파일 오류 또는 형식 오류 시 출력
        }
    }

    /**
     * 현재 랭킹 기록을 파일에 저장함 ranking.txt 파일을 덮어쓰며, 각 줄에 name과 timeMillis를 출력
     */
    private void save() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (RankRecord record : records) {
                writer.write(record.toFileString()); // "이름 시간" 형태
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace(); // 저장 중 오류 발생 시 출력
        }
    }
}