package org.apache.mahout.classifier;


/**
 *
 *
 **/
public class ClassifierResult {
  private String label;
  private float score;

  public ClassifierResult() {
  }

  public ClassifierResult(String label, float score) {
    this.label = label;
    this.score = score;
  }

  public ClassifierResult(String label) {
    this.label = label;
  }

  public String getLabel() {
    return label;
  }

  public float getScore() {
    return score;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public void setScore(float score) {
    this.score = score;
  }


  public String toString() {
    return "ClassifierResult{" +
            "category='" + label + '\'' +
            ", score=" + score +
            '}';
  }
}
