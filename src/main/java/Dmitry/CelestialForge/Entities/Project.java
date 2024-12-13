package Dmitry.CelestialForge.Entities;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String pictureUrl; 
    private Double targetAmount;
    private Double raisedAmount = 0.0;
    private LocalDateTime endTime;    
    
    @ManyToOne
    private User user;

    @ManyToMany(targetEntity = User.class, mappedBy = "contributingProjects")
    private Set<User> contributors = new HashSet<>();

    @OneToMany(mappedBy = "project")
    private Set<Donation> donations = new HashSet<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<BlogPost> blogPosts = new HashSet<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Milestone> milestones = new HashSet<>();

    public void addContributor(User user) {
        this.contributors.add(user);
        user.getContributingProjects().add(this);
    }

    public void removeContributor(User user) {
        this.contributors.remove(user);
        user.getContributingProjects().remove(this);
    }

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
    public Set<User> getContributors() {
        return contributors;
    }
    public void setContributors(Set<User> contributors) {
        this.contributors = contributors;
    }
    public Set<Donation> getDonations() {
        return donations;
    }
    public void setDonations(Set<Donation> donations) {
        this.donations = donations;
    }
    public void setBlogPost(BlogPost blogPost) {
        this.blogPosts.add(blogPost);
    }
    public Set<BlogPost> getBlogPosts() {
        return blogPosts;
    }
    public void setMilestone(Milestone milestone) {
        this.milestones.add(milestone);
    }
    public Set<Milestone> getMilestones() {
        return milestones;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Project)) return false;
        Project project = (Project) o;
        return id != null && id.equals(project.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    

}

