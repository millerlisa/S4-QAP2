package com.keyin.membership;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/members")
public class MemberController {

    @Autowired
    private MemberService memberService;


    @GetMapping
    public ResponseEntity<List<Member>> getAllMembers() {
        return ResponseEntity.ok(memberService.getAllMembers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Member> getMemberById(@PathVariable Long id) {
        return memberService.getMemberById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Member> addMember(@RequestBody Member member) {
        return ResponseEntity.ok(memberService.addMember(member));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Member> updateMember(@PathVariable Long id, @RequestBody Member member) {
        return memberService.getMemberById(id)
                .map(existingMember -> {
                    member.setId(id);
                    return ResponseEntity.ok(memberService.addMember(member));
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

    @GetMapping("/search")
    public ResponseEntity<List<Member>> searchByName(@RequestParam String name) {
        return ResponseEntity.ok(memberService.searchByName(name));
    }

    @GetMapping("/search/phone/{phone}")
    public ResponseEntity<List<Member>> searchByPhone(@PathVariable String phone) {
        return ResponseEntity.ok(memberService.searchByPhone(phone));
    }
}
