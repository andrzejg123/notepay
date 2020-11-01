package pl.polsl.notepay.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.polsl.notepay.exception.NotAuthorizedActionException;
import pl.polsl.notepay.exception.ResourceNotFoundException;
import pl.polsl.notepay.exception.WrongRequestException;
import pl.polsl.notepay.model.dto.DetailedGroupInvitationDto;
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

        if(group.getGroupInvitations().stream().map(GroupInvitation::getUser).anyMatch(user::equals))
            throw new WrongRequestException("This user is already sent an invitation");

        GroupInvitation groupInvitation = GroupInvitation.builder()
                .user(user)
                .group(group)
                .build();

        return new GroupInvitationDto(groupInvitationRepository.save(groupInvitation));
    }

    @Override
    public List<GroupInvitationDto> getOwnGroupInvitations(String token) {

        User currentUser = authenticationUtils.getUserFromToken(token);
        return currentUser.getGroupInvitations().stream().map(DetailedGroupInvitationDto::new).collect(Collectors.toList());
    }

    @Override
    public void acceptGroupInvitation(Long idGroupInvitation, String token) {
        GroupInvitation groupInvitation = groupInvitationRepository
                .findById(idGroupInvitation).orElseThrow(() ->
                        new ResourceNotFoundException("There is no invitation with such an id."));

        User currentUser = authenticationUtils.getUserFromToken(token);

        if(!groupInvitation.getUser().equals(currentUser))
            throw new NotAuthorizedActionException("This is not yours invitation");

        Group group = groupInvitation.getGroup();
        currentUser.getGroups().add(group);
        group.getUsers().add(currentUser);
        groupRepository.flush();
        userRepository.flush();
        groupInvitationRepository.delete(groupInvitation);
    }

    @Override
    public void declineGroupInvitation(Long idGroupInvitation, String token) {
        GroupInvitation groupInvitation = groupInvitationRepository
                .findById(idGroupInvitation).orElseThrow(() ->
                        new ResourceNotFoundException("There is no invitation with such an id."));

        User currentUser = authenticationUtils.getUserFromToken(token);

        if(!groupInvitation.getUser().equals(currentUser))
            throw new NotAuthorizedActionException("This is not yours invitation");

        groupInvitationRepository.delete(groupInvitation);
    }
}
