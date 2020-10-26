package pl.polsl.notepay.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.polsl.notepay.model.dto.GroupInvitationDto;
import pl.polsl.notepay.service.GroupInvitationService;

import java.util.List;

@RestController
@RequestMapping(value = "/group-invitations")
@AllArgsConstructor
public class GroupInvitationController {

    private final GroupInvitationService groupInvitationService;

    @PostMapping
    public ResponseEntity<GroupInvitationDto> createGroupInvitation(@RequestBody GroupInvitationDto groupInvitationDto,
                                                                    @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(groupInvitationService.createGroupInvitation(groupInvitationDto, token));
    }

    @GetMapping(value = "/own")
    public ResponseEntity<List<GroupInvitationDto>> getOwnGroupInvitations(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(groupInvitationService.getOwnGroupInvitations(token));
    }

}
