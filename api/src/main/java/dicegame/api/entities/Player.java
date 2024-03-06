package dicegame.api.entities;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "players")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

    @Column(name="name", nullable = false)
    private String name;

    @CreationTimestamp
	@Column(nullable = false, updatable = false)
	private Timestamp playerCreationTime;

    public Player(String name) {

        this.name = name;
    } 
}
