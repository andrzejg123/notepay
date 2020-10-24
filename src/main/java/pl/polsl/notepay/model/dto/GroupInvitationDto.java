package pl.polsl.notepay.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.polsl.notepay.model.entity.GroupInvitation;

@Data
@NoArgsConstructor
public class GroupInvitationDto {

    private Long id;

    private Long idUser;

    private Long idGroup;

    public GroupInvitationDto(GroupInvitation groupInvitation) {
        this.id = groupInvitation.getId();
        this.idUser = groupInvitation.getUser().getId();
        this.idGroup = groupInvitation.getGroup().getId();
    }

}
