package org.uqbar_project.films.domain;

import java.util.List;
import org.eclipse.xtend.lib.annotations.Accessors;
import org.eclipse.xtext.xbase.lib.Pure;
import org.uqbar_project.films.domain.Film;
import org.uqbar_project.films.domain.Review;

@SuppressWarnings("all")
public class Critic {
  @Accessors
  private List<Review> reviews;
  
  public boolean critic(final Film film, final Review review) {
    boolean _xblockexpression = false;
    {
      this.reviews.add(review);
      _xblockexpression = film.addReview(review);
    }
    return _xblockexpression;
  }
  
  @Pure
  public List<Review> getReviews() {
    return this.reviews;
  }
  
  public void setReviews(final List<Review> reviews) {
    this.reviews = reviews;
  }
}
