package entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "interest_area")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InterestArea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "area_id")
    private Long id;

    @Column(name = "area_name")
    private String name;

    @OneToMany(mappedBy = "interestArea",cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Topic> topics;

    @OneToMany(mappedBy = "interestArea", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Interest> interests;

    public InterestArea(String name) {
        this.name = name;
        topics = new ArrayList<>();
        interests = new ArrayList<>();
    }

    public void addTopic(Topic topic) {
        topic.setInterestArea(this);
        topics.add(topic);
    }

    public void addInterest(Interest interest) {
        interest.setInterestArea(this);
        interests.add(interest);
    }

}
