package com.reticket.reticket.domain;


import com.reticket.reticket.domain.enums.ContributorType;
import jakarta.persistence.*;

@Entity
@Table(name = "play_contributor_types")
public class PlayContributorTypes {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Play play;
    @OneToOne
    private Contributor contributor;
    @Enumerated(EnumType.STRING)
    @Column(name = "contributor_type")
    private ContributorType contributorType;

    public PlayContributorTypes() {
    }

    public Play getPlay() {
        return play;
    }

    public void setPlay(Play play) {
        this.play = play;
    }

    public Contributor getContributor() {
        return contributor;
    }

    public void setContributor(Contributor contributor) {
        this.contributor = contributor;
    }

    public ContributorType getContributorType() {
        return contributorType;
    }

    public void setContributorType(String contributorType) {
        switch (contributorType) {
            case "Director":
                this.contributorType = ContributorType.DIRECTOR;
                break;
            case "Actor":
                this.contributorType = ContributorType.ACTOR;
                break;
            case "Assistant":
                this.contributorType = ContributorType.ASSISTANT;
                break;
            default:
                this.contributorType = ContributorType.AUTHOR;
        }
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
