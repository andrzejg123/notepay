package pl.polsl.notepay.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.polsl.notepay.model.dto.GroupDto;
import pl.polsl.notepay.service.GroupService;

import java.util.List;

@RestController
@RequestMapping(value = "/groups")
@AllArgsConstructor
public class GroupController {

    private final GroupService groupService;

    @PostMapping
    public ResponseEntity<GroupDto> createGroup(@RequestBody GroupDto groupDto,
                                                @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(groupService.createGroup(groupDto, token));
    }

    @GetMapping(value = "/own")
    public ResponseEntity<List<GroupDto>> getOwnGroups(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(groupService.getOwnGroups(token));
    }

}
