package com.keyin.membership;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/members")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @GetMapping
    public List<Member> getAllMembers() {
        return memberService.getAllMembers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Member> getMemberById(@PathVariable Long id) {
        return memberService.getMemberById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Member createMember(@RequestBody Member member) {
        return memberService.saveMember(member);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Member> updateMember(@PathVariable Long id, @RequestBody Member member) {
        return memberService.getMemberById(id)
                .map(existingMember -> {
                    member.setId(id);
                    return ResponseEntity.ok(memberService.saveMember(member));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long id) {
        if (memberService.getMemberById(id).isPresent()) {
            memberService.deleteMember(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Search endpoints
    @GetMapping("/search/name/{name}")
    public List<Member> searchByName(@PathVariable String name) {
        return memberService.searchByName(name);
    }

    @GetMapping("/search/phone/{phone}")
    public List<Member> searchByPhone(@PathVariable String phone) {
        return memberService.searchByPhone(phone);
    }

    @GetMapping("/search/startDate")
    public List<Member> searchByStartDate(@RequestParam LocalDate startDate) {
        return memberService.searchByStartDate(startDate);
    }
    @GetMapping("/search/duration/{duration}")
    public List<Member> searchByMembershipDuration(@PathVariable Integer duration) {
        return memberService.searchByMembershipDuration(duration);
    }
}
