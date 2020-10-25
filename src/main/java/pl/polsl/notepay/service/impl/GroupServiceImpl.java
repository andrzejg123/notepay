package pl.polsl.notepay.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.polsl.notepay.exception.WrongRequestException;
import pl.polsl.notepay.model.dto.GroupDto;
import pl.polsl.notepay.model.entity.Group;
import pl.polsl.notepay.model.entity.User;
import pl.polsl.notepay.repository.GroupRepository;
import pl.polsl.notepay.service.GroupService;
import pl.polsl.notepay.util.AuthenticationUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final AuthenticationUtils authenticationUtils;
    private final GroupRepository groupRepository;

    @Override
    public GroupDto createGroup(GroupDto groupDto, String token) {

        User currentUser = authenticationUtils.getUserFromToken(token);

        if(groupDto.getName() == null || groupDto.getName().equals(""))
            throw new WrongRequestException("Empty group name");

        Group group = Group.builder()
                .name(groupDto.getName())
                .description(groupDto.getDescription())
                .createDate(LocalDateTime.now())
                .deleted(false)
                .users(Collections.singletonList(currentUser))
                .build();

        return new GroupDto(groupRepository.save(group));
    }

    @Override
    public List<GroupDto> getOwnGroups(String token) {

        User currentUser = authenticationUtils.getUserFromToken(token);
        return currentUser.getGroups().stream().map(GroupDto::new).collect(Collectors.toList());
    }
}
