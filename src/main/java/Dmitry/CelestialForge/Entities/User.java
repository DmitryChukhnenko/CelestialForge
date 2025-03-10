package Dmitry.CelestialForge.Entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
@Entity
@Table(name="Users")
public class User implements Serializable{
	private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Имя пользователя не может быть пустым.")
    private String username;

    @NotBlank(message = "Email не может быть пустым.")
    @Email(message = "Введите корректный email.")    
    private String email;

    @Column(nullable = false)
    @Size(min = 5, message = "Пароль должен содержать не менее 5 символов.")
    @Pattern(regexp = ".*\\d.*", message = "Пароль должен содержать хотя бы одну цифру.")
    private String password;

    private String socialNetworks; 
    private String pictureUrl;
    
    @OneToMany(mappedBy = "user")
    private Set<Project> projects = new HashSet<>();
    
    @ManyToMany
    @JoinTable(
        name = "user_contributing_projects",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "project_id")
    )
    private Set<Project> contributingProjects = new HashSet<>();
    
    @OneToMany(mappedBy = "user")
    private Set<Donation> donations = new HashSet<>();

    public User(Long id, String pictureUrl) {
        this.id = id;
        this.pictureUrl = pictureUrl;
    }
    public User() {}

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
    public Set<Project> getProjects() {
        return projects;
    }
    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }
    public Set<Project> getContributingProjects() {
        return contributingProjects;
    }
    public void setContributingProjects(Set<Project> contributingProjects) {
        this.contributingProjects = contributingProjects;
    }
    public Set<Donation> getDonations() {
        return donations;
    }
    public void setDonations(Set<Donation> donations) {
        this.donations = donations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return id != null && id.equals(user.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
