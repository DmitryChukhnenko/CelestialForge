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
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
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
    private String pictureUrl;  // Ссылка на изображение в S3

    @OneToMany(mappedBy = "user")
    private List<Project> projects;

    @ManyToMany(targetEntity = Project.class)
    private List<Project> contributingProjects;

    @OneToMany(mappedBy = "user")
    private List<Donation> donations;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getSocialNetworks() {
        return socialNetworks;
    }
    public void setSocialNetworks(String socialNetworks) {
        this.socialNetworks = socialNetworks;
    }
    public String getPictureUrl() {
        return pictureUrl;
    }
    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }
    public List<Project> getProjects() {
        return projects;
    }
    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }
    public List<Project> getContributingProjects() {
        return contributingProjects;
    }
    public void setContributingProjects(List<Project> contributingProjects) {
        this.contributingProjects = contributingProjects;
    }
    public List<Donation> getDonations() {
        return donations;
    }
    public void setDonations(List<Donation> donations) {
        this.donations = donations;
    }
}
