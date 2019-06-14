package com.dkasiian.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "reports")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    private Long id;

    @Column(name = "report_theme")
    @NotEmpty(message = "*Please provide theme")
    private String theme;

    @Column(name = "report_datetime")
    @NotNull(message = "*Please provide date")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
//    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}")
    private LocalDateTime datetime;

    @Column(name = "speaker_id")
    @NotNull(message = "*Please provide speakerId")
    private Long speakerId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "speaker_id", insertable=false, updatable=false)
    private User speaker;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "reports_conferences",
            joinColumns = @JoinColumn(name = "report_id"),
            inverseJoinColumns = @JoinColumn(name = "conference_id"))
    private Set<Conference> conferences;
}
