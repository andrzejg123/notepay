package pl.polsl.notepay.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.polsl.notepay.model.entity.Group;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class GroupDto {

    private Long id;

    private String name;

    private String description;

    private LocalDateTime createDate;

    public GroupDto(Group group) {
        this.id = group.getId();
        this.name = group.getName();
        this.description = group.getDescription();
        this.createDate = group.getCreateDate();
    }

}
