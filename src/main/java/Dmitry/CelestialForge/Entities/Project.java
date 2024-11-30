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
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
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

    @ManyToMany(targetEntity = User.class, mappedBy = "contributingProjects")
    private List<User> contributors;

    @OneToMany(mappedBy = "project")
    private List<Donation> donations;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getPictureUrl() {
        return pictureUrl;
    }
    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }
    public Double getTargetAmount() {
        return targetAmount;
    }
    public void setTargetAmount(Double targetAmount) {
        this.targetAmount = targetAmount;
    }
    public Double getRaisedAmount() {
        return raisedAmount;
    }
    public void setRaisedAmount(Double raisedAmount) {
        this.raisedAmount = raisedAmount;
    }
    public LocalDateTime getEndTime() {
        return endTime;
    }
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public List<User> getContributors() {
        return contributors;
    }
    public void setContributors(List<User> contributors) {
        this.contributors = contributors;
    }
    public List<Donation> getDonations() {
        return donations;
    }
    public void setDonations(List<Donation> donations) {
        this.donations = donations;
    }
}

