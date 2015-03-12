package org.uqbar_project.films.domain;

import java.util.List;
import org.eclipse.xtend.lib.annotations.Accessors;
import org.eclipse.xtext.xbase.lib.Functions.Function2;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.Pure;
import org.uqbar_project.films.domain.Actor;
import org.uqbar_project.films.domain.Review;

@SuppressWarnings("all")
public class Film {
  @Accessors
  private Boolean isAtp;
  
  private List<Actor> cast;
  
  private List<Review> reviews;
  
  public Integer score() {
    int _xblockexpression = (int) 0;
    {
      final Function2<Integer, Review, Integer> _function = new Function2<Integer, Review, Integer>() {
        public Integer apply(final Integer sum, final Review review) {
          Integer _score = review.getScore();
          return Integer.valueOf(((_score).intValue() + (sum).intValue()));
        }
      };
      Integer votes = IterableExtensions.<Review, Integer>fold(this.reviews, Integer.valueOf(0), _function);
      int _size = this.reviews.size();
      int _multiply = (_size * (Review.MAX_SCORE).intValue());
      _xblockexpression = (((votes).intValue() * 100) / _multiply);
    }
    return Integer.valueOf(_xblockexpression);
  }
  
  public boolean addReview(final Review review) {
    return this.reviews.add(review);
  }
  
  public boolean addActor(final Actor actor) {
    return this.cast.add(actor);
  }
  
  @Pure
  public Boolean getIsAtp() {
    return this.isAtp;
  }
  
  public void setIsAtp(final Boolean isAtp) {
    this.isAtp = isAtp;
  }
}
