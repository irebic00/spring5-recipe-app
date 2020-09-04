package guru.springframework.model;

import java.util.Arrays;

public enum Difficulty {
    EASY("Easy"),
    MODERATE("Moderate"),
    KIND_OFF_HARD("Kind off hard"),
    HARD("Hard");

    private final String difficulty;

    Difficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public static Difficulty of(String difficulty) {
        return Arrays.stream(values())
                .filter(release -> release.getDifficulty().equals(difficulty))
                .findFirst()
                .orElse(null);
    }
}

