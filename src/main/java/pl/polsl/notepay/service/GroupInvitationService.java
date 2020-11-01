package pl.polsl.notepay.service;

import pl.polsl.notepay.model.dto.GroupInvitationDto;

import java.util.List;

public interface GroupInvitationService {

    GroupInvitationDto createGroupInvitation(GroupInvitationDto groupInvitationDto, String token);

    List<GroupInvitationDto> getOwnGroupInvitations(String token);

    void acceptGroupInvitation(Long idGroupInvitation, String token);

    void declineGroupInvitation(Long idGroupInvitation, String token);
}
