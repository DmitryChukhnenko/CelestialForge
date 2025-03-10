package Dmitry.CelestialForge.Entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

// TODO remove likes as a type
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="Likes")
public class Like implements Serializable{
	private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime createdAt; 

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; 

    @ManyToOne
    @JoinColumn(name = "blog_post_id", nullable = true)
    private BlogPost blogPost; 

    @ManyToOne
    @JoinColumn(name = "comment_id", nullable = true)
    private Comment comment;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public BlogPost getBlogPost() {
        return blogPost;
    }
    public void setBlogPost(BlogPost blogPost) {
        this.blogPost = blogPost;
    }
    public Comment getComment() {
        return comment;
    }
    public void setComment(Comment comment) {
        this.comment = comment;
    }
}
