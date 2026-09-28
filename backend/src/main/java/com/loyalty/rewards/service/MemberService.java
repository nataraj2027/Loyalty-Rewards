package com.loyalty.rewards.service;

import com.loyalty.rewards.dto.MemberDto;
import com.loyalty.rewards.exception.BadRequestException;
import com.loyalty.rewards.exception.ResourceNotFoundException;
import com.loyalty.rewards.model.Member;
import com.loyalty.rewards.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public List<MemberDto> getAllMembers() {
        return memberRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public MemberDto getMemberById(Long id) {
        return toDto(findMemberOrThrow(id));
    }

    public MemberDto createMember(MemberDto dto) {
        if (memberRepository.existsByEmail(dto.getEmail())) {
            throw new BadRequestException("A member with email '" + dto.getEmail() + "' already exists");
        }
        Member member = new Member(
                dto.getFirstName(),
                dto.getLastName(),
                dto.getEmail(),
                dto.getPhone(),
                dto.getJoinDate() != null ? dto.getJoinDate() : LocalDate.now()
        );
        return toDto(memberRepository.save(member));
    }

    public MemberDto updateMember(Long id, MemberDto dto) {
        Member member = findMemberOrThrow(id);
        member.setFirstName(dto.getFirstName());
        member.setLastName(dto.getLastName());
        member.setPhone(dto.getPhone());
        // Email intentionally not editable here to keep it stable as a lookup key.
        return toDto(memberRepository.save(member));
    }

    public void deleteMember(Long id) {
        Member member = findMemberOrThrow(id);
        memberRepository.delete(member);
    }

    Member findMemberOrThrow(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found with id: " + id));
    }

    private MemberDto toDto(Member member) {
        return new MemberDto(
                member.getId(),
                member.getFirstName(),
                member.getLastName(),
                member.getEmail(),
                member.getPhone(),
                member.getJoinDate(),
                member.getPointsBalance()
        );
    }
}
