package pl.polsl.notepay.service;

import pl.polsl.notepay.model.dto.GroupDto;

import java.util.List;

public interface GroupService {

    GroupDto createGroup(GroupDto groupDto, String token);

    List<GroupDto> getOwnGroups(String token);
}
