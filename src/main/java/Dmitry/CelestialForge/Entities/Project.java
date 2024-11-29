package Dmitry.CelestialForge.Entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Table(name="Projects")
public class Project implements Serializable{
	private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description; // потенциально - сложный структурированый элемент
    private String pictureUrl;  // Ссылка на изображение в S3
    private Double targetAmount;
    private Double raisedAmount = 0.0;
    private LocalDateTime endTime;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(targetEntity = Dmitry.CelestialForge.Entities.User.class, mappedBy = "project")
    private List<User> contributors;

    @OneToMany(mappedBy = "project")
    private List<Donation> donations;
}

