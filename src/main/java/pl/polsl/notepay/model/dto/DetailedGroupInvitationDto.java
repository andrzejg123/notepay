package pl.polsl.notepay.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.polsl.notepay.model.entity.GroupInvitation;

@Data
@NoArgsConstructor
public class DetailedGroupInvitationDto extends GroupInvitationDto {

    String groupName;

    String groupDescription;

    public DetailedGroupInvitationDto(GroupInvitation groupInvitation) {
        super(groupInvitation);
        this.groupName = groupInvitation.getGroup().getName();
        this.groupDescription = groupInvitation.getGroup().getDescription();
    }

}
