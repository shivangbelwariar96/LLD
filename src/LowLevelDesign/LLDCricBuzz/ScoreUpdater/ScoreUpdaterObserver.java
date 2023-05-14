package LowLevelDesign.LLDCricBuzz.ScoreUpdater;

import LowLevelDesign.LLDCricBuzz.Inning.BallDetails;

public interface ScoreUpdaterObserver {

    public void update(BallDetails ballDetails);
}
