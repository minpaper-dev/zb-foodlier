package com.zerobase.foodlier.module.member.chef.service;

import com.zerobase.foodlier.module.member.chef.dto.AroundChefDto;
import com.zerobase.foodlier.module.member.chef.dto.ChefIntroduceForm;
import com.zerobase.foodlier.module.member.chef.dto.RequestedChefDto;
import com.zerobase.foodlier.module.member.chef.type.ChefSearchType;

import java.util.List;

public interface ChefMemberService {
    void registerChef(Long memberId, ChefIntroduceForm chefIntroduceForm);
    void updateChefIntroduce(Long memberId, ChefIntroduceForm chefIntroduceForm);

    List<RequestedChefDto> getRequestedChefList(Long memberId,
                                                int pageIdx, int pageSize);

    List<AroundChefDto> getAroundChefList(Long memberId,
                                          int pageIdx, int pageSize, ChefSearchType type);
    void plusExp(Long chefMemberId, int star);

    void plusStar(Long chefMemberId, int star);

}