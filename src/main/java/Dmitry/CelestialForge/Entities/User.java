package Dmitry.CelestialForge.Entities;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
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
@Table(name="Users")
public class User implements Serializable{
	private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String email;
    private String password;
    private String socialNetworks; // json
    private String profilePictureUrl;  // Ссылка на изображение в S3

    @OneToMany(mappedBy = "user")
    private List<Project> projects;

    @ManyToMany(targetEntity = Dmitry.CelestialForge.Entities.Project.class)
    private List<Project> contributingProjects;

    @OneToMany(mappedBy = "user")
    private List<Donation> donations;
}
