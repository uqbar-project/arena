package org.uqbar_project.films.domain;

import org.eclipse.xtend.lib.annotations.Accessors;
import org.eclipse.xtext.xbase.lib.Pure;

@SuppressWarnings("all")
public class Review {
  public final static Integer MAX_SCORE = Integer.valueOf(10);
  
  @Accessors
  private Integer score;
  
  @Pure
  public Integer getScore() {
    return this.score;
  }
  
  public void setScore(final Integer score) {
    this.score = score;
  }
}
