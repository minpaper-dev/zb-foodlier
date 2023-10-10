package com.zerobase.foodlier.global.profile.controller;

import com.zerobase.foodlier.common.response.ListResponse;
import com.zerobase.foodlier.common.security.provider.dto.MemberAuthDto;
import com.zerobase.foodlier.global.profile.facade.ProfileFacade;
import com.zerobase.foodlier.module.member.chef.dto.ChefIntroduceForm;
import com.zerobase.foodlier.module.member.chef.service.ChefMemberService;
import com.zerobase.foodlier.module.member.member.profile.dto.MemberPrivateProfileForm;
import com.zerobase.foodlier.module.member.member.profile.dto.MemberPrivateProfileResponse;
import com.zerobase.foodlier.module.member.member.profile.dto.PasswordChangeForm;
import com.zerobase.foodlier.module.member.member.service.MemberService;
import com.zerobase.foodlier.module.review.chef.dto.ChefReviewResponseDto;
import com.zerobase.foodlier.module.review.chef.service.ChefReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/profile")
@Slf4j
public class profileController {
    private final MemberService memberService;
    private final ProfileFacade profileFacade;
    private final ChefMemberService chefMemberService;
    private final ChefReviewService chefReviewService;

    @GetMapping("/private")
    public ResponseEntity<MemberPrivateProfileResponse> getPrivateProfile(
            @AuthenticationPrincipal MemberAuthDto memberAuthDto
    ) {
        return ResponseEntity.ok(memberService.getPrivateProfile(memberAuthDto.getEmail()));
    }

    @PutMapping(value = "/private")
    public ResponseEntity<String> updatePrivateProfile(
            @AuthenticationPrincipal MemberAuthDto memberAuthDto,
            @ModelAttribute MemberPrivateProfileForm form
    ) {
        profileFacade.deleteProfileUrlAndGetAddressUpdateProfile(
                memberAuthDto.getEmail(), form);
        return ResponseEntity.ok("내 정보 수정 완료");
    }

    @PostMapping("/private/registerchef")
    public ResponseEntity<String> registerChef(
            @AuthenticationPrincipal MemberAuthDto memberAuthDto,
            @RequestBody ChefIntroduceForm chefIntroduceForm
    ) {
        chefMemberService.registerChef(memberAuthDto.getId(), chefIntroduceForm);
        return ResponseEntity.ok("요리사가 되었습니다.");
    }

    @PutMapping("/private/introduce")
    public ResponseEntity<String> updateChefIntroduce(
            @AuthenticationPrincipal MemberAuthDto memberAuthDto,
            @RequestBody ChefIntroduceForm chefIntroduceForm
    ) {
        chefMemberService.updateChefIntroduce(memberAuthDto.getId(), chefIntroduceForm);
        return ResponseEntity.ok("요리사 소개가 수정되었습니다.");
    }

    @GetMapping("/public/chefreview/{pageIdx}/{pageSize}/{chefMemberId}")
    public ResponseEntity<ListResponse<ChefReviewResponseDto>> getChefReviewList(
            @PathVariable int pageIdx,
            @PathVariable int pageSize,
            @PathVariable Long chefMemberId
    ) {
        return ResponseEntity.ok(
                chefReviewService.getChefReviewList(chefMemberId,
                        PageRequest.of(pageIdx, pageSize))
        );
    }

    @PutMapping("/private/password")
    public ResponseEntity<String> updatePassword(
            @AuthenticationPrincipal MemberAuthDto memberAuthDto,
            @RequestBody PasswordChangeForm form
    ) {
        return ResponseEntity.ok(memberService.updatePassword(memberAuthDto, form));
    }
}
