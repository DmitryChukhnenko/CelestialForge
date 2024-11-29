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
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
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

    // Если лайк относится к посту, то будет заполнено поле blogPost.
    @ManyToOne
    @JoinColumn(name = "blog_post_id", nullable = true)
    private BlogPost blogPost; 

    // Если лайк относится к комментариям, то будет заполнено поле comment.
    @ManyToOne
    @JoinColumn(name = "comment_id", nullable = true)
    private Comment comment;
}
