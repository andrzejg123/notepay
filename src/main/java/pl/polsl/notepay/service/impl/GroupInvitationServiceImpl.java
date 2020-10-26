package pl.polsl.notepay.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.polsl.notepay.exception.NotAuthorizedActionException;
import pl.polsl.notepay.exception.WrongRequestException;
import pl.polsl.notepay.model.dto.GroupInvitationDto;
import pl.polsl.notepay.model.entity.Group;
import pl.polsl.notepay.model.entity.GroupInvitation;
import pl.polsl.notepay.model.entity.User;
import pl.polsl.notepay.repository.GroupInvitationRepository;
import pl.polsl.notepay.repository.GroupRepository;
import pl.polsl.notepay.repository.UserRepository;
import pl.polsl.notepay.service.GroupInvitationService;
import pl.polsl.notepay.util.AuthenticationUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GroupInvitationServiceImpl implements GroupInvitationService {

    private final AuthenticationUtils authenticationUtils;
    private final GroupRepository groupRepository;
    private final GroupInvitationRepository groupInvitationRepository;
    private final UserRepository userRepository;

    @Override
    public GroupInvitationDto createGroupInvitation(GroupInvitationDto groupInvitationDto, String token) {

        User currentUser = authenticationUtils.getUserFromToken(token);

        Group group = groupRepository.findById(groupInvitationDto.getIdGroup()).orElseThrow(() ->
                new WrongRequestException("There is no group with such an id"));

        if(!group.getUsers().contains(currentUser))
            throw new NotAuthorizedActionException("You do not belong to this group");

        User user = userRepository.findById(groupInvitationDto.getIdUser()).orElseThrow(() ->
                new WrongRequestException("There is no user with such an id"));

        if(group.getUsers().contains(user))
            throw new WrongRequestException("This user already belongs to this group");

        GroupInvitation groupInvitation = GroupInvitation.builder()
                .user(user)
                .group(group)
                .build();

        return new GroupInvitationDto(groupInvitationRepository.save(groupInvitation));
    }

    @Override
    public List<GroupInvitationDto> getOwnGroupInvitations(String token) {

        User currentUser = authenticationUtils.getUserFromToken(token);
        return currentUser.getGroupInvitations().stream().map(GroupInvitationDto::new).collect(Collectors.toList());
    }
}
