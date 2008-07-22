package org.apache.mahout.classifier;

import java.text.*;
import java.util.*;

import org.apache.commons.lang.StringUtils;
import org.apache.mahout.common.Summarizable;

/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with this
 * work for additional information regarding copyright ownership. The ASF
 * licenses this file to You under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

public class ResultAnalyzer implements Summarizable {
  ConfusionMatrix confusionMatrix = null;

  /*
   * === Summary ===
   * 
   * Correctly Classified Instances 635 92.9722 % Incorrectly Classified
   * Instances 48 7.0278 % Kappa statistic 0.923 Mean absolute error 0.0096 Root
   * mean squared error 0.0817 Relative absolute error 9.9344 % Root relative
   * squared error 37.2742 % Total Number of Instances 683
   */
  int correctlyClassified = 0;

  int incorrectlyClassified = 0;

  public ResultAnalyzer(Collection<String> labelSet) {
    confusionMatrix = new ConfusionMatrix(labelSet);
  }

  public void addInstance(String correctLabel, ClassifierResult classifiedResult)
      throws Exception {
    if (correctLabel.equals(classifiedResult.getLabel()))
      correctlyClassified++;
    else
      incorrectlyClassified++;
    confusionMatrix.addInstance(correctLabel, classifiedResult);
  }

  public String toString() {
    return "";
  }

  public String summarize() throws Exception {
    StringBuilder returnString = new StringBuilder();

    returnString
        .append("=======================================================\n");
    returnString.append("Summary\n");
    returnString
        .append("-------------------------------------------------------\n");
    int totalClassified = correctlyClassified + incorrectlyClassified;
    double percentageCorrect = (double) 100 * correctlyClassified
        / (totalClassified);
    double percentageIncorrect = (double) 100 * incorrectlyClassified
        / (totalClassified);
    DecimalFormat decimalFormatter = new DecimalFormat("0.####");

    returnString.append(StringUtils.rightPad("Correctly Classified Instances",
        40)
        + ": "
        + StringUtils.leftPad(new Integer(correctlyClassified).toString(), 10)
        + "\t"
        + StringUtils.leftPad(decimalFormatter.format(percentageCorrect), 10)
        + "%\n");
    returnString.append(StringUtils.rightPad(
        "Incorrectly Classified Instances", 40)
        + ": "
        + StringUtils
            .leftPad(new Integer(incorrectlyClassified).toString(), 10)
        + "\t"
        + StringUtils.leftPad(decimalFormatter.format(percentageIncorrect), 10)
        + "%\n");
    returnString.append(StringUtils.rightPad("Total Classified Instances", 40)
        + ": "
        + StringUtils.leftPad(new Integer(totalClassified).toString(), 10)
        + "\n");
    returnString.append("\n");

    returnString.append(confusionMatrix.summarize());

    return returnString.toString();
  }
}
