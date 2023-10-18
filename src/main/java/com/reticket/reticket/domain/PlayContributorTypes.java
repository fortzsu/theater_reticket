package com.reticket.reticket.domain;


import com.reticket.reticket.domain.enums.ContributorType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "play_contributor_types")
@Getter
@Setter
@NoArgsConstructor
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

    public void setContributorType(String contributorType) {
        switch (contributorType) {
            case "Director" -> this.contributorType = ContributorType.DIRECTOR;
            case "Actor" -> this.contributorType = ContributorType.ACTOR;
            case "Assistant" -> this.contributorType = ContributorType.ASSISTANT;
            default -> this.contributorType = ContributorType.AUTHOR;
        }
    }

}
